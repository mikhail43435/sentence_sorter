package ru.hmp.sentencesorter.actions;


import ru.hmp.sentencesorter.io.Input;
import ru.hmp.sentencesorter.io.Output;

public final class ExitConsoleAction implements UserAction {
    private final Output out;

    public ExitConsoleAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Close application";
    }

    @Override
    public boolean execute(Input input) throws Exception {
        out.println("Application is closed");
        return false;
    }
}

