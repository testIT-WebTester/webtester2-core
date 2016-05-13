package features;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.Div;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;
import info.novatec.testit.webtester.mouse.Mouse;


public class MouseUtilsFeatureTest extends BaseIntegrationTest {

    FeaturePage page;

    @Before
    public final void openPage() {
        open("html/features/mouse-utils.html");
        page = browser().create(FeaturePage.class);
    }

    @Test
    public void demonstrateOnPageFragment(){

        Mouse.on(page.firstButton())
            .click()
            .doubleClick()
            .contextClick();

        assertThat(page.getMessage(1)).isEqualTo("Context Clicked");

    }

    @Test
    public void demonstrateSequence(){

        Mouse.sequence()
            .click(page.secondButton())
            .moveTo(page.thirdButton())
            .moveTo(page.firstButton())
            .contextClick();

        assertThat(page.getMessage(1)).isEqualTo("Context Clicked");
        assertThat(page.getMessage(2)).isEqualTo("Clicked");
        assertThat(page.getMessage(3)).isEqualTo("Moved To");

    }

    @Test
    public void demonstrateMoveToEach(){

        // move mouse to each button in the given order
        Mouse.moveToEach(page.firstButton(), page.secondButton(), page.thirdButton());

        // assert mouse was moved
        assertThat(page.getMessage(1)).isEqualTo("Moved To");
        assertThat(page.getMessage(2)).isEqualTo("Moved To");
        assertThat(page.getMessage(3)).isEqualTo("Moved To");

    }

    public interface FeaturePage extends Page {

        @IdentifyUsing("#firstButton")
        Button firstButton();
        @IdentifyUsing("#secondButton")
        Button secondButton();
        @IdentifyUsing("#thirdButton")
        Button thirdButton();

        @IdentifyUsing("#div1")
        Div message1();
        @IdentifyUsing("#div2")
        Div message2();
        @IdentifyUsing("#div3")
        Div message3();

        default String getMessage(int number){
            switch (number){
                case 1:
                    return message1().getVisibleText();
                case 2:
                    return message2().getVisibleText();
                case 3:
                    return message3().getVisibleText();
                default:
                    throw new IllegalArgumentException("unknown message field: #" + number);
            }
        }

    }

}
