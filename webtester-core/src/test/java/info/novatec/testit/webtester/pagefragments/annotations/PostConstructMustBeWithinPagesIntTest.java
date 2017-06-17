package info.novatec.testit.webtester.pagefragments.annotations;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.conditions.collections.NotEmpty;
import info.novatec.testit.webtester.conditions.pagefragments.Present;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructMustBeConditionException;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pages.Page;


public class PostConstructMustBeWithinPagesIntTest extends BaseIntTest {

    @Before
    public void openPage() {
        open("html/features/must-be.html");
    }

    @Test
    public void demonstratePassingMustBeBehaviour() {
        browser().create(PassingFeaturePage.class);
    }

    @Test(expected = PostConstructMustBeConditionException.class)
    public void demonstrateFailingMustBeBehaviour_PageFragment() {
        browser().create(FailingBecauseOfFragmentPage.class);
    }

    @Test(expected = PostConstructMustBeConditionException.class)
    public void demonstrateFailingMustBeBehaviour_Collection() {
        browser().create(FailingBecauseOfCollectionPage.class);
    }

    @Test(expected = PostConstructMustBeConditionException.class)
    public void demonstrateInheritanceFailingMustBeBehaviour() {
        browser().create(InheritedFailingFeaturePage.class);
    }

    @Test(expected = PostConstructMustBeConditionException.class)
    public void exceptionInCaseTheConditionCantHandleMethodReturnType() {
        browser().create(FailingBecauseOfConditionTypePage.class);
    }

    /* test pages */

    public interface PassingFeaturePage extends Page {

        @PostConstructMustBe(Visible.class)
        @IdentifyUsing("#username")
        TextField username();

        @PostConstructMustBe(NotEmpty.class)
        @IdentifyUsing("#username")
        List<TextField> usernames();

    }

    public interface InheritedFailingFeaturePage extends FailingBecauseOfFragmentPage {
        // no own @PostConstructMustBe condition - it should fail because of inheritance
    }

    public interface FailingBecauseOfFragmentPage extends Page {

        @PostConstructMustBe(Present.class)
        @IdentifyUsing("#unknown")
        PageFragment unknown();

    }

    public interface FailingBecauseOfCollectionPage extends Page {

        @PostConstructMustBe(NotEmpty.class)
        @IdentifyUsing("#unknown")
        List<PageFragment> unknowns();

    }

    public interface FailingBecauseOfConditionTypePage extends Page {

        @PostConstructMustBe(Visible.class)
        @IdentifyUsing("#username")
        List<TextField> usernames();

    }

}
