package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.annotations.ReturnsBrowser;


public class BrowserReturningImpl implements Implementation {

    private final Browser browser;

    public BrowserReturningImpl(Browser browser) {
        this.browser = browser;
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return method.isAnnotationPresent(ReturnsBrowser.class);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return browser;
    }

}
