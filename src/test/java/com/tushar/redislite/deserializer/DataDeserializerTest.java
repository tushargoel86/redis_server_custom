package com.tushar.redislite.deserializer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DataDeserializerTest {

    private static final String CRLF = "\\r\\n";
    private DataDeserializer deserializer;

    @BeforeEach
    public void setup() {
        deserializer = new DataDeserializer();
    }

    @Test
    public void testDeserializerForEmptyString() {
        String res = deserializer.deserialize("+" + CRLF);
        assertEquals("", res);
    }

    @Test
    public void testDeserializerForSimpleString() {
        String res = deserializer.deserialize("+OK" + CRLF);
        assertEquals("OK", res);
    }

    @Test
    public void testDeserializerForInteger() {
        String res = deserializer.deserializeInteger(":10" + CRLF);
        assertEquals("10", res);
    }

    @Test
    public void testDeserializerForEmptyBulkString() {
        String res = deserializer.deserialize("$0" + CRLF);
        assertEquals("", res);
    }

    @Test
    public void testDeserializerForNullBulkString() {
        String res = deserializer.deserialize("$-1" + CRLF);
        assertNull(res);
    }

    @Test
    public void testDeserializerForBulkString() {
        String res = deserializer.deserialize("$3" + CRLF + "abc" + CRLF);
        assertEquals("abc", res);
    }

    @Test
    public void testDeserializeStringArray() {
		Object[] res = deserializer.deserializeObject("*2\\r\\n$5\\r\\nhello\\r\\n$5\\r\\nworld\\r\\n");
        assertEquals("hello", res[0]);
        assertEquals("world", res[1]);
    }

    @Test
    public void testDeserializeStringAndIntegerArray() {
        Object[] res = deserializer.deserializeObject("*2\\r\\n$5\\r\\nhello\\r\\n:2\\r\\n");
        assertEquals("hello", res[0]);
        assertEquals(2, res[1]);
    }

    @Test
    public void testDeserializeEmptyArray() {
        Object[] res = deserializer.deserializeObject("*0\\r\\n");
        assertEquals(0, res.length);
    }
}
