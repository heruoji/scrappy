package com.example.util.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.example.util.args.ArgsException.ArgsErrorCode.*;

public class DoubleArgumentMarshaller implements ArgumentMarshaller {

    private double doubleValue = 0.0;

    public void set(Iterator<String> currentArgument) throws ArgsException{
        String parameter = null;
        try{
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        }catch (NumberFormatException e) {
            throw new ArgsException(INVALID_DOUBLE, parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_DOUBLE);
        }
    }

    public static double getValue(ArgumentMarshaller am) {
        if (am instanceof DoubleArgumentMarshaller) {
            return ((DoubleArgumentMarshaller) am).doubleValue;
        } else {
            return 0.0;
        }
    }
}
