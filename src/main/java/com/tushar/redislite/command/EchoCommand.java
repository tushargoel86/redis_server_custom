package com.tushar.redislite.command;

import com.tushar.redislite.cache.Memory;
import com.tushar.redislite.exception.IncorrectUsageException;

public class EchoCommand extends Command {
    private final static String COMMAND  = "ECHO";
    @Override
    public void validate() {
        if (!COMMAND.equalsIgnoreCase(this.getCommand()))
            throw new IncorrectUsageException("No correct use of echo command");
    }

    @Override
    public Object execute(Memory memory) {
        return this.getArgs()[0];
    }
}
