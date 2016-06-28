package info.novatec.testit.webtester.pagefragments.identification.producers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;


public class IdTest {

    Id cut = new Id();

    @Test
    public void producesCorrectBy() {
        By by = cut.createBy("someId");
        assertThat(by).isInstanceOf(By.ById.class);
        assertThat(by).hasToString("By.id: someId");
    }

    @Test
    public void hasCorrectToString() {
        assertThat(cut).hasToString("ID");
    }

}
