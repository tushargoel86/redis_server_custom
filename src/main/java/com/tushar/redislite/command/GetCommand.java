package com.tushar.redislite.command;

import com.tushar.redislite.cache.Memory;
import com.tushar.redislite.exception.IncorrectUsageException;

public class GetCommand extends Command{

    private final static String COMMAND  = "GET";

    @Override
    public void validate() {
        if (!COMMAND.equalsIgnoreCase(this.getCommand()))
            throw new IncorrectUsageException("No correct use of get command");
    }

    @Override
    public Object execute(Memory memory) {
        Object []args = this.getArgs();
        Object value = memory.getData(String.valueOf(args[0]));
        if (value == null) throw new IllegalArgumentException(args[0] + " key not found");
        return value;
    }
}
