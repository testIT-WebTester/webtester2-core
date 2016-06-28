package info.novatec.testit.webtester.pagefragments.identification.producers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;


public class PartialLinkTextTest {

    PartialLinkText cut = new PartialLinkText();

    @Test
    public void producesCorrectBy() {
        By by = cut.createBy("partial link");
        assertThat(by).isInstanceOf(By.ByPartialLinkText.class);
        assertThat(by).hasToString("By.partialLinkText: partial link");
    }

    @Test
    public void hasCorrectToString() {
        assertThat(cut).hasToString("Partial Link Text");
    }

}
