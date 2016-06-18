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

    public static void setFieldValue(Object object, String fieldName, Object value)
        throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private TestUtils() {
        // utility class constructor
    }

}
