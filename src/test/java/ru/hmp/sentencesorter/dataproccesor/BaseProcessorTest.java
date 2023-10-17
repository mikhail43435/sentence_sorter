package ru.hmp.sentencesorter.dataproccesor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.hmp.sentencesorter.io.ConsoleInput;
import ru.hmp.sentencesorter.io.ConsoleOutput;
import ru.hmp.sentencesorter.io.ValidateInput;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BaseProcessorTest {

    private static ConsoleOutput output;
    private static ValidateInput input;
    private static BaseProcessor processor;
    private static List<String> baseInputListForSort;

    @BeforeAll
    public static void init() {
        output = new ConsoleOutput();
        input = new ValidateInput(output, new ConsoleInput());
        processor = new BaseProcessor();
        baseInputListForSort = new ArrayList<>();
        baseInputListForSort.add("I'm happy.");
        baseInputListForSort.add("She exercises every morning.");
        baseInputListForSort.add("His dog barks loudly.");
        baseInputListForSort.add("    My school starts at 8:00.");
        baseInputListForSort.add("We always eat dinner together.");
        baseInputListForSort.add("They take the bus to work.");
        baseInputListForSort.add("He doesn't like vegetables.");
        baseInputListForSort.add("I don't want anything to drink.");
    }

    @Test
    void testSortDataWhenSortByAlphabet() {
        List<String> outputList = new ArrayList<>();
        outputList.add("    My school starts at 8:00.");
        outputList.add("He doesn't like vegetables.");
        outputList.add("His dog barks loudly.");
        outputList.add("I don't want anything to drink.");
        outputList.add("I'm happy.");
        outputList.add("She exercises every morning.");
        outputList.add("They take the bus to work.");
        outputList.add("We always eat dinner together.");
        assertThat(processor.sortData(new ArrayList<>(baseInputListForSort),
                SortingTypes.ALPHABET, 0)).isEqualTo(outputList);
    }

    @Test
    void testSortDataWhenSortBySentenceLength() {
        List<String> outputList = new ArrayList<>();
        outputList.add("I'm happy.");
        outputList.add("His dog barks loudly.");
        outputList.add("They take the bus to work.");
        outputList.add("He doesn't like vegetables.");
        outputList.add("She exercises every morning.");
        outputList.add("    My school starts at 8:00.");
        outputList.add("We always eat dinner together.");
        outputList.add("I don't want anything to drink.");

        assertThat(processor.sortData(new ArrayList<>(baseInputListForSort),
                SortingTypes.SENTENCE_LENGTH, 0)).isEqualTo(outputList);
    }

    @org.junit.jupiter.api.Test
    void testSortDataWhenSortByWordNumber01Regular() {
        List<String> outputList = new ArrayList<>();
        outputList.add("He doesn't like vegetables.");
        outputList.add("His dog barks loudly.");
        outputList.add("I don't want anything to drink.");
        outputList.add("I'm happy.");
        outputList.add("    My school starts at 8:00.");
        outputList.add("She exercises every morning.");
        outputList.add("They take the bus to work.");
        outputList.add("We always eat dinner together.");

        assertThat(processor.sortData(new ArrayList<>(baseInputListForSort),
                SortingTypes.WORD_NUMBER, 1)).isEqualTo(outputList);
    }


    @org.junit.jupiter.api.Test
    void testWhenSortByWordNumber02OutOfRangeUp() {
        List<String> outputList = new ArrayList<>();
        outputList.add("I'm happy.");
        outputList.add("She exercises every morning.");
        outputList.add("His dog barks loudly.");
        outputList.add("    My school starts at 8:00.");
        outputList.add("We always eat dinner together.");
        outputList.add("They take the bus to work.");
        outputList.add("He doesn't like vegetables.");
        outputList.add("I don't want anything to drink.");

        assertThat(processor.sortData(new ArrayList<>(baseInputListForSort),
                SortingTypes.WORD_NUMBER, 100)).isEqualTo(outputList);
    }

    @org.junit.jupiter.api.Test
    void testFreqInfoToStrings1() {
        List<String> inputList = new ArrayList<>();
        inputList.add("I'm happy.");
        inputList.add("She exercises every morning.");
        inputList.add("She exercises every morning.");
        inputList.add("His dog barks loudly.");
        inputList.add("    My school starts at 8:00.");
        inputList.add("We always eat dinner together.");
        inputList.add("They take the bus to work.");
        inputList.add("They take the bus to work.");
        inputList.add("They take the bus to work.");
        inputList.add("They take the bus to work.");
        inputList.add("He doesn't like vegetables.");
        inputList.add("I don't want anything to drink.");

        List<String> outputList = new ArrayList<>();
        outputList.add("I'm happy. 1");
        outputList.add("She exercises every morning. 2");
        outputList.add("She exercises every morning. 2");
        outputList.add("His dog barks loudly. 1");
        outputList.add("    My school starts at 8:00. 1");
        outputList.add("We always eat dinner together. 1");
        outputList.add("They take the bus to work. 4");
        outputList.add("They take the bus to work. 4");
        outputList.add("They take the bus to work. 4");
        outputList.add("They take the bus to work. 4");
        outputList.add("He doesn't like vegetables. 1");
        outputList.add("I don't want anything to drink. 1");
        assertThat(processor.appendFreqInfoToStrings(inputList)).isEqualTo(outputList);
    }
}