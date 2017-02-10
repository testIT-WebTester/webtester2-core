package info.novatec.testit.webtester.pagefragments.annotations;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pages.Page;


public class NamedIntTest extends BaseIntTest {

    @Before
    public void openPage(){
        open("html/features/named-page-fragments.html");
    }

    @Test
    public void demonstrateNamingOfPageFragments(){

        FeaturePage page = browser().create(FeaturePage.class);

        assertThat(page.username().getName()).contains("Username Field");
        assertThat(page.password().getName()).contains("PasswordField identified by CssSelector: #password");
        assertThat(page.login().getName()).contains("Login Button");

    }

    /* test pages */

    public interface FeaturePage extends Page {

        @Named("Username Field")
        @IdentifyUsing("#username")
        TextField username();

        @IdentifyUsing("#password")
        PasswordField password();

        @Named("Login Button")
        @IdentifyUsing("#login")
        Button login();

    }

}
