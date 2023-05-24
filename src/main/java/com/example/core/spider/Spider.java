package com.example.core.spider;

import com.example.model.Request;
import com.example.model.Response;
import com.example.model.SpiderResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface Spider {
    Request startRequest();

    default SpiderResponse parse(Response response){

        Method[] declaredMethods = this.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (method.getName().equals(response.getCallbackKey())) {
                try {
                    return (SpiderResponse) method.invoke(this, response);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new RuntimeException("no method: " + response.getCallbackKey());
    }
}
