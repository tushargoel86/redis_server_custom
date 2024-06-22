package com.tushar.redislite.cache;

import java.util.HashMap;

public class Memory {

    private final HashMap<String, Object> mainMemory;

    public Memory() {
        this.mainMemory = HashMap.newHashMap(10);
    }

    public Object setData(String key, Object value) {
        mainMemory.put(key, value);
        return value;
    }

    public Object getData(String key) {
        return mainMemory.get(key);
    }
}
