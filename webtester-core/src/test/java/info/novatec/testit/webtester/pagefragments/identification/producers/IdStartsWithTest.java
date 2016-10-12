package info.novatec.testit.webtester.pagefragments.identification.producers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;


public class IdStartsWithTest {

    IdStartsWith cut = new IdStartsWith();

    @Test
    public void producesCorrectBy() {
        By by = cut.createBy("partial-id:");
        assertThat(by).isInstanceOf(By.ByCssSelector.class);
        assertThat(by).hasToString("By.cssSelector: [id^='partial\\-id\\:']");
    }

    @Test
    public void hasCorrectToString() {
        assertThat(cut).hasToString("ID Starts With");
    }

}
