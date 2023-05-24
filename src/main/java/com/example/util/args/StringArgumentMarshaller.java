package com.example.util.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.example.util.args.ArgsException.ArgsErrorCode.MISSING_STRING;

public class StringArgumentMarshaller implements ArgumentMarshaller {
    private String stringValue = "";

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_STRING);
        }
    }

    public static String getValue(ArgumentMarshaller am) {
        if (am instanceof StringArgumentMarshaller) {
            return ((StringArgumentMarshaller) am).stringValue;
        } else {
            return "";
        }
    }
}
