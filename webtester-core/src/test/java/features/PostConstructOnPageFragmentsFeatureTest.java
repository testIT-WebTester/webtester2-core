package features;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.internal.postconstruct.PostConstructInvocationException;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.Form;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class PostConstructOnPageFragmentsFeatureTest extends BaseIntegrationTest {

    @Before
    public void openPage(){
        open("html/features/post-construct.html");
    }

    @Test
    public void demonstratePassingPostConstructBehaviour(){
        browser().create(FeaturePage.class).passingForm();
    }

    @Test(expected = PostConstructInvocationException.class)
    public void demonstrateFailingPostConstructBehaviour(){
        browser().create(FeaturePage.class).failingForm();
    }

    @Test
    public void demonstrateInheritanceOfPostConstructBehaviour() {
        TestContext.reset();
        browser().create(FeaturePage.class).passingForm();
        assertThat(TestContext.executed).isTrue();
        assertThat(TestContext.parentExecuted).isTrue();
    }

    /* test pages */

    public interface FeaturePage extends Page {

        @IdentifyUsing("#loginForm")
        PassingLoginForm passingForm();

        @IdentifyUsing("#loginForm")
        FailingLoginForm failingForm();

    }

    public interface PassingLoginForm extends LoginForm {

        @PostConstruct
        default void assertLoginFieldsAreVisible(){
            TestContext.executed = true;
            // will pass because all fields are visible
            assertThat(username().isVisible()).isTrue();
            assertThat(password().isVisible()).isTrue();
            assertThat(login().isVisible()).isTrue();
        }

    }

    public interface FailingLoginForm extends LoginForm {

        @PostConstruct
        default void assertUsernameIsVisible(){
            // will fail because no text is set by default
            assertThat(username().getText()).isEqualTo("username");
        }

    }

    public interface LoginForm extends Form {

        @IdentifyUsing("#username")
        TextField username();

        @IdentifyUsing("#password")
        PasswordField password();

        @IdentifyUsing("#login")
        Button login();

        @PostConstruct
        default void inheritedPostConstruct() {
            TestContext.parentExecuted = true;
        }

    }

    private static class TestContext {

        private static boolean executed;
        private static boolean parentExecuted;

        private static void reset() {
            executed = false;
            parentExecuted = false;
        }

    }

}
