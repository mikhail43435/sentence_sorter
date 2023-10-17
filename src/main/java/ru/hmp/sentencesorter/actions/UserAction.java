package ru.hmp.sentencesorter.actions;

import ru.hmp.sentencesorter.io.Input;

public interface UserAction {
    String name();

    boolean execute(Input input) throws Exception;
}