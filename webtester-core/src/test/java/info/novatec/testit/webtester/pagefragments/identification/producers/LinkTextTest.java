package info.novatec.testit.webtester.pagefragments.identification.producers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;


public class LinkTextTest {

    LinkText cut = new LinkText();

    @Test
    public void producesCorrectBy() {
        By by = cut.createBy("a link");
        assertThat(by).isInstanceOf(By.ByLinkText.class);
        assertThat(by).hasToString("By.linkText: a link");
    }

    @Test
    public void hasCorrectToString() {
        assertThat(cut).hasToString("Link Text");
    }

}
