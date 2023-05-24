package com.example.util.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.example.util.args.ArgsException.ArgsErrorCode.INVALID_INTEGER;
import static com.example.util.args.ArgsException.ArgsErrorCode.MISSING_INTEGER;

public class IntegerArgumentMarshaller implements ArgumentMarshaller {
    private int intValue = 0;

    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            throw new ArgsException(INVALID_INTEGER, parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_INTEGER);
        }
    }

    public static int getValue(ArgumentMarshaller am) {
        if (am instanceof IntegerArgumentMarshaller) {
            return ((IntegerArgumentMarshaller) am).intValue;
        } else {
            return 0;
        }
    }
}
