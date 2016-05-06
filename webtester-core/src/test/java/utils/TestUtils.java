package utils;

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

    private TestUtils() {
        // utility class constructor
    }

}
