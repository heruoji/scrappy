package com.example.util.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.example.util.args.ArgsException.ArgsErrorCode.MISSING_STRING;

public class StringArrayArgumentMarshaller implements ArgumentMarshaller {
    private String[] stringArrayValue;

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringArrayValue = currentArgument.next().split(",");
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_STRING);
        }
    }

    public static String[] getValue(ArgumentMarshaller am) {
        if (am instanceof StringArrayArgumentMarshaller) {
            return ((StringArrayArgumentMarshaller) am).stringArrayValue;
        } else {
            return new String[0];
        }
    }
}
