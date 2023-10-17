package ru.hmp.sentencesorter.actions;

import ru.hmp.sentencesorter.io.Input;
import ru.hmp.sentencesorter.io.Output;

public final class GetHelpAction implements UserAction {

    private final Output out;

    public GetHelpAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Get help";
    }

    @Override
    public boolean execute(Input input) {
        out.println("Brief instruction...");
        return true;
    }
}