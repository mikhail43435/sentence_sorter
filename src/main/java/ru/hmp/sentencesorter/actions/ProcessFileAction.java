package ru.hmp.sentencesorter.actions;

import ru.hmp.sentencesorter.dataproccesor.Processor;
import ru.hmp.sentencesorter.dataproccesor.SortingTypes;
import ru.hmp.sentencesorter.dataprovider.DataProvider;
import ru.hmp.sentencesorter.io.Input;
import ru.hmp.sentencesorter.io.Output;
import ru.hmp.sentencesorter.io.InputHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ProcessFileAction implements UserAction {

    private final Output output;
    private final DataProvider dataProvider;
    private final Processor dataProcessor;

    public ProcessFileAction(Output output, DataProvider dataProvider, Processor dataProcessor) {
        this.output = output;
        this.dataProvider = dataProvider;
        this.dataProcessor = dataProcessor;
    }

    @Override
    public String name() {
        return "Process file";
    }

    @Override
    public boolean execute(Input input) {

        String source = input.askStr("Specify the file to process: ");
        if (!dataProvider.setSource(source)) {
            output.println(String.format("Source file does not exist: %s", source));
            return true;
        }

        String target = input.askStr("Specify the file for output: ");
        if (!dataProvider.setTarget(target)) {
            output.println(String.format("Can not create output file: %s", target));
            return true;
        }

        SortingTypes sortingTypes;

        int select = InputHandler.handleSelectItems(
                input,
                output,
                "Available types of sorting: ",
                Arrays.stream(SortingTypes.values()).
                        map(SortingTypes::toString).
                        collect(Collectors.toList()),
                "Select sorting type: ");

        sortingTypes = SortingTypes.values()[select - 1];
        output.println(
                String.format("You have selected the following sort type: %s", sortingTypes));

        int wordNumToSort = -1;
        if (sortingTypes == SortingTypes.WORD_NUMBER) {
            wordNumToSort = InputHandler.handleSingleIntInput(
                    input,
                    output,
                    "Select number of word to sort: ",
                    1,
                    Integer.MAX_VALUE
            );
            output.println(
                    String.format("You have selected the following number of word to sort by: %d",
                            wordNumToSort));
        }

        List<String> sourceDataList;

        try {
            sourceDataList = dataProvider.getData();
        } catch (IOException e) {
            output.println(e.getMessage());
            return true;
        }
        output.println(String.format("Data has been successfully obtained from source file: %s", source));

        try {
            dataProvider.saveData(dataProcessor.sortData(new ArrayList<>(sourceDataList),
                    sortingTypes,
                    wordNumToSort));
        } catch (IOException e) {
            output.println("Error occurred while writing to target file");
            return true;
        }
        output.println(String.format("Data has been successfully sorted and saved to destination file: %s", target));

        dataProvider.setTarget(source);
        try {
            dataProvider.saveData(dataProcessor.appendFreqInfoToStrings(sourceDataList));
        } catch (IOException e) {
            output.println(e.getMessage());
            return true;
        }
        output.println("Data in source file has been successfully updated");

        return true;
    }
}