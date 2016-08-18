package info.novatec.testit.webtester.junit5.extensions;

import lombok.Getter;

import info.novatec.testit.webtester.browser.Browser;


/**
 * This exception is thrown in case there is no managed {@link Browser} with a given name when execution an extension.
 *
 * @since 2.1
 */
@Getter
public class NoManagedBrowserForNameException extends TestClassFormatException {

    /** The name of the browser that wasn't found. */
    private final String name;

    public NoManagedBrowserForNameException(String name) {
        super("There is no managed browser with the name: " + name);
        this.name = name;
    }

}
