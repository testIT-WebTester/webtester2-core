package info.novatec.testit.webtester.pagefragments.identification.producers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;


public class ClassNameTest {

    ClassName cut = new ClassName();

    @Test
    public void producesCorrectBy() {
        By by = cut.createBy("some-class");
        assertThat(by).isInstanceOf(By.ByClassName.class);
        assertThat(by).hasToString("By.className: some-class");
    }

    @Test
    public void hasCorrectToString() {
        assertThat(cut).hasToString("Class Name");
    }

}
