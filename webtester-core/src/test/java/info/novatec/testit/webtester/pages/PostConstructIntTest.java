package info.novatec.testit.webtester.pages;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.internal.postconstruct.PostConstructInvocationException;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;


public class PostConstructIntTest extends BaseIntTest {

    @Before
    public void openPage() {
        open("html/features/post-construct.html");
    }

    @Test
    public void demonstratePassingPostConstructBehaviour() {
        browser().create(PassingFeaturePage.class);
    }

    @Test(expected = PostConstructInvocationException.class)
    public void demonstrateFailingPostConstructBehaviour() {
        browser().create(FailingFeaturePage.class);
    }

    @Test
    public void demonstrateInheritanceOfPostConstructBehaviour() {
        TestContext.reset();
        browser().create(PassingFeaturePage.class);
        assertThat(TestContext.executed).isTrue();
        assertThat(TestContext.parentExecuted).isTrue();
    }

    /* test pages */

    public interface PassingFeaturePage extends FeaturePage {

        @PostConstruct
        default void assertLoginFieldsAreVisible() {
            TestContext.executed = true;
            // will pass because all fields are visible
            assertThat(username().isVisible()).isTrue();
            assertThat(password().isVisible()).isTrue();
            assertThat(login().isVisible()).isTrue();
        }

    }

    public interface FailingFeaturePage extends FeaturePage {

        @PostConstruct
        default void assertUsernameIsVisible() {
            // will fail because no text is set by default
            assertThat(username().getText()).isEqualTo("username");
        }

    }

    public interface FeaturePage extends Page {

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
