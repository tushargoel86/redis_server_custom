package com.tushar.redislite.deserializer;

public class DataDeserializer {
    public String deserialize(String inp) {
        char startChar = inp.charAt(0);
        int index = inp.indexOf("\r\n");
        if (index == -1) throw new IllegalStateException("Wrong encrypted format");

        return switch (startChar) {
            case '+' -> deserializeString(index, inp, false);
            case '$' -> deserializeString(index, inp, true);
            default -> "";
        };
    }

    private String deserializeInteger(int index, String inp) {
        return inp.substring(1, index);
    }

    private String deserializeString(int index, String inp, boolean bulk) {
        if ("0".equals(inp.substring(1, index))) return "";
		if (bulk) {
            if ("-1".equals(inp.substring(1, index))) return null;
            int lastIndex = inp.lastIndexOf("\r\n");
            return inp.substring(index + 4, lastIndex);
        }
        return inp.substring(1, index);
    }

    public String deserializeInteger(String inp) {
        int index = inp.indexOf("\r\n");
        if (index == -1) throw new IllegalStateException("Wrong encrypted format");
        if (inp.charAt(0) != ':') throw new IllegalStateException("Not an integer encrypted input");

        return deserializeInteger(index, inp);
    }

    public Object[] deserializeObject(String inp) {
        if (inp.charAt(0) != '*') throw new IllegalStateException("Not an integer encrypted input");

        String[]  tokens = inp.split("\r\n");
        var arrSize = Integer.parseInt(tokens[0].substring(1));

        if (arrSize == -1) return null;
        if (arrSize == 0) return new Object[]{};

        System.out.println(inp);

      //  String[] tokens = inp.substring(1).replace("\\r\\n", ",").split(",");
        int elements = Integer.parseInt(tokens[0].substring(1));
        Object[] result = new Object[elements];

        int k = 1, m = 2;
		for(int i = 0; i < elements; i++) {
			String type = tokens[k];
            switch(type.charAt(0)) {
                case '+':
                    result[i] = tokens[m];
                    break;
                case '$':
                    if (type.length() > 1 && Integer.parseInt(type.substring(1)) == tokens[m].length())
                  		  result[i] = tokens[m];
                    break;
                case ':':
                    result[i] = Integer.parseInt(type.substring(1));
                    break;
                default:
                    throw new IllegalStateException("Wrong encrypted format");
            }
            k = k + 2;
            m = m + 2;
        }

        return result;
    }
}
