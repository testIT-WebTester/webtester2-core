package info.novatec.testit.webtester.internal.implementation.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;


class StaticWebElementSupplierTest {

    WebElement webElement = mock(WebElement.class);
    StaticWebElementSupplier cut = new StaticWebElementSupplier(webElement);

    @Test
    void getAlwaysReturnsSameInstance() {
        WebElement callResult1 = cut.get();
        WebElement callResult2 = cut.get();
        assertThat(callResult1).isSameAs(callResult2);
    }

    @Test
    void equalsAndHashCodeIsImplemented() {

        StaticWebElementSupplier instance1 = new StaticWebElementSupplier(webElement);
        StaticWebElementSupplier instance2 = new StaticWebElementSupplier(webElement);

        assertThat(instance1.hashCode()).isNotZero();
        assertThat(instance1.hashCode()).isEqualTo(instance2.hashCode());
        assertThat(instance1).isEqualTo(instance2);
        assertThat(instance1).isNotSameAs(instance2);

    }

}