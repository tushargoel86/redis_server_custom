package com.tushar.redislite.command;


import com.tushar.redislite.cache.Memory;

public abstract class Command {
    private String command;
    private String[] args;

    public abstract void validate();
    public abstract Object execute(Memory memory);

    public final Object handle(Memory memory) {
        validate();
        return execute(memory);
    }

    public final Command builder(Object[] deserializedArray) {
        if (deserializedArray != null) {
			this.command = String.valueOf(deserializedArray[0]);
			this.args = new String[deserializedArray.length - 1];
            for (int i = 1; i < deserializedArray.length ; i++) {
                this.args[i - 1] = String.valueOf(deserializedArray[i]);
            }
        }
        return this;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }
}