package info.novatec.testit.webtester.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.openqa.selenium.WebElement;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.must.MustChecker;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructInvoker;
import info.novatec.testit.webtester.internal.proxies.PageFragmentModel;
import info.novatec.testit.webtester.internal.proxies.PageFragmentProxyHandler;
import info.novatec.testit.webtester.pagefragments.PageFragment;

@AllArgsConstructor
public class PageFragmentFactory {

    @NonNull
    private final Browser browser;

    @SuppressWarnings("unchecked")
    public <T extends PageFragment> T pageFragment(PageFragmentModel model) {

        ClassLoader classLoader = model.getType().getClassLoader();
        Class[] interfaces = { model.getType() };

        InvocationHandler invocationHandler = PageFragmentProxyHandler.fromModel(model);

        T pageFragment = ( T ) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        PostConstructInvoker.invokePostConstructMethods(( Class<T> ) model.getType(), pageFragment);
        MustChecker.checkMustMethods(( Class<T> ) model.getType(), pageFragment);
        return pageFragment;

    }

    @SuppressWarnings("unchecked")
    public <T extends PageFragment> T pageFragment(Class<T> type, WebElement webElement) {

        ClassLoader classLoader = type.getClassLoader();
        Class[] interfaces = { type };

        InvocationHandler invocationHandler = PageFragmentProxyHandler.forWebElement(browser, webElement, type);

        T pageFragment = ( T ) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        PostConstructInvoker.invokePostConstructMethods(type, pageFragment);
        MustChecker.checkMustMethods(type, pageFragment);
        return pageFragment;

    }

}
