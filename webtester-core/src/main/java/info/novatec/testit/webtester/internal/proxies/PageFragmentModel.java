package info.novatec.testit.webtester.internal.proxies;

import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("PMD")
public class PageFragmentModel {

    @NonNull
    private Browser browser;
    @NonNull
    private Class<? extends PageFragment> type;
    @NonNull
    private Supplier<SearchContext> searchContextSupplier;
    @NonNull
    private By by;
    @NonNull
    private String name = "";

}
