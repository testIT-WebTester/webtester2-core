package info.novatec.testit.webtester.pagefragments.identification.producers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;


public class NameTest {

    Name cut = new Name();

    @Test
    public void producesCorrectBy() {
        By by = cut.createBy("name");
        assertThat(by).isInstanceOf(By.ByName.class);
        assertThat(by).hasToString("By.name: name");
    }

    @Test
    public void hasCorrectToString() {
        assertThat(cut).hasToString("Name");
    }

}
