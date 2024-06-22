package com.tushar.redislite.command;

import com.tushar.redislite.cache.Memory;
import com.tushar.redislite.exception.IncorrectUsageException;

public class PingCommand extends Command {

    private final static String COMMAND  = "PING";

    @Override
    public void validate() {
        if (!COMMAND.equalsIgnoreCase(this.getCommand()))
            throw new IncorrectUsageException("No correct use of ping command");
    }

    @Override
    public Object execute(Memory memory) {
        return "PONG";
    }
}
