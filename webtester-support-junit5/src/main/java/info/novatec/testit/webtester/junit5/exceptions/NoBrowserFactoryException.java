package info.novatec.testit.webtester.junit5.exceptions;

import info.novatec.testit.webtester.junit5.extensions.browsers.CreateBrowsersUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateUsing;


public class NoBrowserFactoryException extends RuntimeException {
    public NoBrowserFactoryException() {
        super("No browser factory configured! Either annotate class with " + CreateBrowsersUsing.class
            + " or browser field with " + CreateUsing.class);
    }
}
