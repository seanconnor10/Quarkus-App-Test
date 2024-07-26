package org.acme.model;

import java.lang.reflect.Field;

public abstract class AbstractModel {
    private final StringBuffer JSONText = new StringBuffer();
    private final Field[] subclassFields;

    boolean changedSinceLastSerialization = true;

    public AbstractModel() {
        subclassFields = this.getClass().getDeclaredFields();
    }

    public String asJSON() {
        if (changedSinceLastSerialization)
            serialize();

        return JSONText.toString();
    }

    // -------------------------------------------------------

    private void serialize() {
        //Effectively clear previous text
        JSONText.setLength(0);

        JSONText.append('{');
        for (Field field : subclassFields) {
            JSONText.append(serializeField(field));
        }
        JSONText.append('}');

        removeTrailingComma(JSONText);

        changedSinceLastSerialization = false;
    }

    private String serializeField(Field property) {
        String str =  "";

        try {
            str = '"' + property.getName() + "\":\"" + property.get(this) + "\",";
        } catch (IllegalAccessException e) {
            //System.out.println(e.getMessage());
        }

       return str;
    }

    private void removeTrailingComma(StringBuffer text) {
        int end = text.length() - 1;
        int position = end - 1;
        if (text.lastIndexOf(",}") == position) {
            text.deleteCharAt(position);
        } else if (text.charAt(end) == ',') {
            text.deleteCharAt(end);
        }
    }

}