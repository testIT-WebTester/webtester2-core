package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.annotations.CreatesPage;
import info.novatec.testit.webtester.pages.Page;


public class PageCreatingImpl implements Implementation {

    private final Browser browser;

    public PageCreatingImpl(Browser browser) {
        this.browser = browser;
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return method.isAnnotationPresent(CreatesPage.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object proxy, Method method, Object[] args) {
        Class<? extends Page> pageClass = ( Class<? extends Page> ) args[0];
        return browser.create(pageClass);
    }

}
