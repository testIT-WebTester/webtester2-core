package utils;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.MalformedURLException;
import java.net.URL;


public final class TestUtils {

    public static URL toUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    /**
     * Sets the value for a field with the given name of the given object using reflection.
     * <p>
     * <b>Note:</b> the field's visibility will be changed in order for this operation to work.
     *
     * @param object the object who's field value should be set
     * @param fieldName the name of the field
     * @param value the value to set
     * @throws IllegalArgumentException in case of an error
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("could not set field value of '" + fieldName + "'", e);
        }
    }

    private TestUtils() {
        // utility class constructor
    }

}
