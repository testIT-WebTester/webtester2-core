package info.novatec.testit.webtester.pagefragments.identification.producers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;


public class TagNameTest {

    TagName cut = new TagName();

    @Test
    public void producesCorrectBy() {
        By by = cut.createBy("tag");
        assertThat(by).isInstanceOf(By.ByTagName.class);
        assertThat(by).hasToString("By.tagName: tag");
    }

    @Test
    public void hasCorrectToString() {
        assertThat(cut).hasToString("Tag Name");
    }

}
