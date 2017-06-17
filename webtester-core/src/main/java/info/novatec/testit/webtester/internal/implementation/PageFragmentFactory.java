package info.novatec.testit.webtester.internal.implementation;

import java.util.function.Supplier;

import org.openqa.selenium.WebElement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.implementation.pagefragments.ByteBuddyPageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public interface PageFragmentFactory {

    PageFragment createInstanceOf(PageFragmentDescriptor descriptor);

    static PageFragmentFactory createInstanceFor(Browser browser) {
        return new ByteBuddyPageFragmentFactory(browser);
    }

    @Data
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    class PageFragmentDescriptor {
        @NonNull
        private final Class<? extends PageFragment> pageFragmentType;
        @NonNull
        private final Supplier<WebElement> webElementSupplier;
        private final String name;
    }

}
