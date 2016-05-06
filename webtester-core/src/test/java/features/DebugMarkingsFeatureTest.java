package features;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class DebugMarkingsFeatureTest extends BaseIntegrationTest {

    boolean markingsWere;

    @Before
    public void openPage() {
        open("html/features/debug-markings.html");
        markingsWere = browser().configuration().isMarkingsEnabled();
        browser().configuration().setMarkings(true);
    }

    @After
    public void deactivateMarkings() {
        browser().configuration().setMarkings(markingsWere);
    }

    @Test
    public void demonstrateDebugMarkings(){
        browser().create(FeaturePage.class)
            .setUsername("user42")
            .setPassword("pswd123")
            .clickLoginExpectingError();
        browser().alert().accept();
    }

    public interface FeaturePage extends Page {

        @IdentifyUsing("#username")
        TextField username();

        @IdentifyUsing("#password")
        PasswordField password();

        @IdentifyUsing("#login")
        Button login();

        default FeaturePage setUsername(String username){
            username().setText(username);
            return this;
        }

        default FeaturePage setPassword(String password){
            password().setText(password);
            return this;
        }

        default FeaturePage clickLoginExpectingError(){
            login().click();
            return this;
        }

    }

}
