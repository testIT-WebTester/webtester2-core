package info.novatec.testit.webtester.pagefragments.annotations;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.conditions.pagefragments.Editable;
import info.novatec.testit.webtester.conditions.pagefragments.Present;
import info.novatec.testit.webtester.conditions.pagefragments.PresentAndVisible;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructMustBeConditionException;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.Form;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pages.Page;


public class PostConstructMustBeWithinPageFragmentsIntTest extends BaseIntTest {

    @Before
    public void openPage(){
        open("html/features/must-be.html");
    }

    @Test
    public void demonstratePassingMustBehaviour(){
        browser().create(FeaturePage.class).passingForm();
    }

    @Test(expected = PostConstructMustBeConditionException.class)
    public void demonstrateFailingMustBehaviour(){
        browser().create(FeaturePage.class).failingForm();
    }

    @Test(expected = PostConstructMustBeConditionException.class)
    public void demonstrateInheritanceFailingMustBeBehaviour() {
        browser().create(FeaturePage.class).inheritedFailingForm();
    }

    /* test pages */

    public interface FeaturePage extends Page {

        @IdentifyUsing("#loginForm")
        PassingLoginForm passingForm();

        @IdentifyUsing("#loginForm")
        FailingLoginForm failingForm();

        @IdentifyUsing("#loginForm")
        InheritedFailingLoginForm inheritedFailingForm();

    }

    public interface PassingLoginForm extends Form {

        @PostConstructMustBe(Editable.class)
        @IdentifyUsing("#username")
        TextField username();

        @PostConstructMustBe(PresentAndVisible.class)
        @IdentifyUsing("#password")
        PasswordField password();

        @PostConstructMustBe(Visible.class)
        @IdentifyUsing("#login")
        Button login();

    }

    public interface InheritedFailingLoginForm extends FailingLoginForm {

    }

    public interface FailingLoginForm extends Form {

        @PostConstructMustBe(Present.class)
        @IdentifyUsing("#unknown")
        PageFragment unknown();

    }

}
