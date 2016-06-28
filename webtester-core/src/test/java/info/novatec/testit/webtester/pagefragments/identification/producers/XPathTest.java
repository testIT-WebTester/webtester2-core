package info.novatec.testit.webtester.pagefragments.identification.producers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;


public class XPathTest {

    XPath cut = new XPath();

    @Test
    public void producesCorrectBy() {
        By by = cut.createBy("//div/a[1]");
        assertThat(by).isInstanceOf(By.ByXPath.class);
        assertThat(by).hasToString("By.xpath: //div/a[1]");
    }

    @Test
    public void hasCorrectToString() {
        assertThat(cut).hasToString("XPath");
    }

}
