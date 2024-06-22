package com.tushar.redislite.command;

import com.tushar.redislite.cache.Memory;
import com.tushar.redislite.exception.IncorrectUsageException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LpushCommand extends Command{

    private final static String COMMAND  = "LPUSH";

    @Override
    public void validate() {
        if (!COMMAND.equalsIgnoreCase(this.getCommand()))
            throw new IncorrectUsageException("No correct use of Lpush command");
    }

    @Override
    public Object execute(Memory memory) {
       Object []args = this.getArgs();
       String listName = String.valueOf(args[0]);

       List<String> dataList =  memory.getData(listName) == null ? new LinkedList<>() :
               (List<String>) memory.getData(listName);

        dataList.addFirst(String.valueOf(args[1]));
        memory.setData(listName, dataList);

        return dataList.size();
    }
}
