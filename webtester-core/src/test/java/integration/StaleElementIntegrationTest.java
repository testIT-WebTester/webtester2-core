package integration;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.StaleElementReferenceException;

import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.Cached;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.identification.producers.Id;
import info.novatec.testit.webtester.pagefragments.identification.producers.IdEndsWith;
import info.novatec.testit.webtester.pages.Page;


/**
 * This test serves as a reminder that the stale element recovery was removed with version 2.0.
 * These types of exceptions should no longer occur since the caching is disabled by default.
 */
public class StaleElementIntegrationTest extends BaseIntegrationTest {

    private StaleElementReferencePage page;

    @Before
    public void setUp() {
        page = create(StaleElementReferencePage.class);
    }

    @Override
    protected String getHTMLFilePath() {
        return "html/stale-elements.html";
    }

    /* stale element recovery */

    @Test(expected = StaleElementReferenceException.class)
    public void staleElementWhenRemoved() {
        page.foo().setText("A");
        page.removeFoo();
        page.foo().setText("B");
    }

    @Test(expected = StaleElementReferenceException.class)
    public void staleElementWhenReplaced() {
        page.foo().setText("A");
        page.removeFoo();
        page.addFoo();
        page.foo().setText("B");
    }

    /* utilities */

    public interface StaleElementReferencePage extends Page {

        @Cached
        @IdentifyUsing(value = "foo", how = IdEndsWith.class)
        TextField foo();

        @Cached
        @IdentifyUsing(value = "container1:foo", how = Id.class)
        TextField waitFoo();

        default void removeFoo() {
            find("#removeFoo").click();
        }

        default void addFoo() {
            find("#addFoo").click();
        }

    }

}
