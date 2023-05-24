package com.example.model.item;

import java.lang.reflect.Field;

public abstract class Item {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append("{");
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            sb.append(field.getName());
            sb.append("='");
            try {
                sb.append(field.get(this));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            sb.append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }
}
