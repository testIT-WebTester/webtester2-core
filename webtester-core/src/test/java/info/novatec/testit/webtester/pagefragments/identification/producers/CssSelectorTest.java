package info.novatec.testit.webtester.pagefragments.identification.producers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;


public class CssSelectorTest {

    CssSelector cut = new CssSelector();

    @Test
    public void producesCorrectBy() {
        By by = cut.createBy("#id");
        assertThat(by).isInstanceOf(By.ByCssSelector.class);
        assertThat(by).hasToString("By.cssSelector: #id");
    }

    @Test
    public void hasCorrectToString() {
        assertThat(cut).hasToString("CSS Selector");
    }

}
