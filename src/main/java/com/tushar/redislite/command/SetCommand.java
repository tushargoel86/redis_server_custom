package com.tushar.redislite.command;

import com.tushar.redislite.cache.Memory;
import com.tushar.redislite.exception.IncorrectUsageException;

public class SetCommand extends Command{

    private final static String COMMAND  = "SET";

    @Override
    public void validate() {
        if (!COMMAND.equalsIgnoreCase(this.getCommand()))
            throw new IncorrectUsageException("No correct use of set command");
    }

    @Override
    public Object execute(Memory memory) {
        Object []args = this.getArgs();
        memory.setData(String.valueOf(args[0]), args[1]);
        return String.valueOf(args[1]);
    }
}
