package com.tushar.redislite.serializer;

public class DataSerializer {

    private static final String CRLF = "\r\n";
    private static final String SIMPLE_STRING_SYMBOL = "+";
    private static final String BULK_STRING_SYMBOL = "$";
    private static final String INTEGER_SYMBOL = ":";
    private static final String OBJECT_SYMBOL = "*";
    private static final String NEGATIVE_ONE = "-1";

    private String serialize(String inp) {
        if (inp == null) return SIMPLE_STRING_SYMBOL + NEGATIVE_ONE + CRLF;
        if (inp.contains("\r")) return "-Invalid String" + CRLF;
        return SIMPLE_STRING_SYMBOL + inp + CRLF;
    }

    public String serialize(int inp) {
        return INTEGER_SYMBOL + inp + CRLF;
    }

    public String serialize(String inp, boolean isBulk) {
        if (isBulk) {
            if (inp == null) return BULK_STRING_SYMBOL + NEGATIVE_ONE + CRLF;
			return BULK_STRING_SYMBOL + inp.length() + CRLF + inp + CRLF;
        }
        return serialize(inp);
    }

    public String serialize(Object[] inp) {
        if (inp == null) return OBJECT_SYMBOL + NEGATIVE_ONE + CRLF;
        if (inp.length == 0) return OBJECT_SYMBOL + "0" + CRLF;
        int len = inp.length;
        StringBuilder builder = new StringBuilder();
        builder.append(OBJECT_SYMBOL).append(len).append(CRLF);

        for (Object obj : inp) {
            if (obj == null) builder.append(serialize(null, true));
            else if (obj instanceof String) builder.append(serialize( (String) obj, true));
            else if (obj instanceof Integer) builder.append(serialize( (Integer) obj));
        }
        return builder.toString();
    }
}
