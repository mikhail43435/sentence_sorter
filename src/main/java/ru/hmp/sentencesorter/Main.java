package ru.hmp.sentencesorter;

import ru.hmp.sentencesorter.actions.GetHelpAction;
import ru.hmp.sentencesorter.actions.ProcessFileAction;
import ru.hmp.sentencesorter.dataproccesor.BaseProcessor;
import ru.hmp.sentencesorter.dataprovider.DataProvider;
import ru.hmp.sentencesorter.dataprovider.FileDataProvider;
import ru.hmp.sentencesorter.io.*;
import ru.hmp.sentencesorter.actions.ExitConsoleAction;
import ru.hmp.sentencesorter.actions.UserAction;
import ru.hmp.sentencesorter.dataproccesor.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private final Output output;

    public Main(Output output) {
        this.output = output;
    }

    public void init(Input input, List<UserAction> actions) throws Exception {

        boolean run = true;
        List<String> actionsStrings = actions.stream().
                map(UserAction::name).
                collect(Collectors.toList());

        while (run) {
            UserAction action = actions.get(InputHandler.handleSelectItems(
                    input,
                    output,
                    System.lineSeparator() + "<<< Menu >>> ",
                    actionsStrings,
                    "Select action: ") - 1);
            run = action.execute(input);
        }
    }

    public static void main(String[] args) throws Exception {
        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());
        DataProvider dataProvider = new FileDataProvider();
        Processor fileProcessor = new BaseProcessor();
        ArrayList<UserAction> actions = new ArrayList<>();
        actions.add(new ProcessFileAction(output, dataProvider, fileProcessor));
        actions.add(new GetHelpAction(output));
        actions.add(new ExitConsoleAction(output));
        new Main(output).init(input, actions);
    }
}
