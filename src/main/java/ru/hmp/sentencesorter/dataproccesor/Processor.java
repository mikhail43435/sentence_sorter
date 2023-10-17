package ru.hmp.sentencesorter.dataproccesor;

import java.util.List;

public interface Processor {

    List<String> sortData(List<String> list, SortingTypes sortingTypes, int wordNumToSort);

    List<String> appendFreqInfoToStrings(List<String> list);

}
