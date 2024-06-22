package com.tushar.redislite.serializer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DataSerializerTest {
    private static final String CLRF = "\\r\\n";

    private void testSerialization(String inp, String expected, boolean isBulk) {
        DataSerializer dataSerializer = new DataSerializer();
        String res = dataSerializer.serialize(inp, isBulk);
        assertEquals(expected, res);
    }

    @Test
    public void testEmptyStringSerialization() {
        testSerialization("", "+" + CLRF, false);
    }

    @Test
    public void testSimpleStringSerialization() {
        testSerialization("hello", "+hello" + CLRF, false);
    }

    @Test
    public void testSimpleStringForCarrot() {
        testSerialization("hello\\r", "-Invalid String" + CLRF, false);
    }

    @Test
    public void testNullInput() {
        testSerialization(null, "+-1" + CLRF, false);
    }

    @Test
    public void testForInteger() {
        DataSerializer dataSerializer = new DataSerializer();
        String res = dataSerializer.serialize(1234);
        assertEquals(":1234" + CLRF, res);

        res = dataSerializer.serialize(0);
        assertEquals(":0" + CLRF, res);

        res = dataSerializer.serialize(-1);
        assertEquals(":-1" + CLRF, res);
    }

    @Test
    public void testForBulkString() {
        testSerialization("abc","$3" + CLRF + "abc" + CLRF, true);
    }

    @Test
    public void testForEmptyBulkString() {
        testSerialization("","$0" + CLRF + CLRF, true);
    }

    @Test
    public void testForNullBulkString() {
        testSerialization(null,"$-1" + CLRF, true);
    }

    @Test
    public void testForEmptyObject() {
        testSerializationForObjectInput(new Object[]{}, "*0" + CLRF);
    }

    @Test
    public void testForNullObject() {
        testSerializationForObjectInput(null,"*-1" + CLRF);
    }

    @Test
    public void testForStringObject() {
        testSerializationForObjectInput(new Object[]{"hello", "world"}, "*2\\r\\n$5\\r\\nhello\\r\\n$5\\r\\nworld\\r\\n");
    }

    @Test
    public void testForStringAndIntegerObject() {
        testSerializationForObjectInput(new Object[]{"hello", 2}, "*2\\r\\n$5\\r\\nhello\\r\\n:2\\r\\n");
    }

    @Test
    public void testForMixedObject() {
        testSerializationForObjectInput(new Object[]{null, "hello", 2}, "*3\\r\\n$-1\\r\\n$5\\r\\nhello\\r\\n:2\\r\\n");
    }

    private static void testSerializationForObjectInput(Object[] inp, String result) {
        DataSerializer dataSerializer = new DataSerializer();
        String res = dataSerializer.serialize(inp);
        assertEquals(result, res);
    }
}
