package info.novatec.testit.webtester.pagefragments.identification;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.identification.producers.ClassName;


@RunWith(Enclosed.class)
public class ByProducersTest {

    public static class ClassNameTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.className("some-class");
            assertThat(by).isInstanceOf(By.ByClassName.class);
            assertThat(by).hasToString("By.className: some-class");
        }

    }

    public static class CssSelectorTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.css("#id");
            assertThat(by).isInstanceOf(By.ByCssSelector.class);
            assertThat(by).hasToString("By.cssSelector: #id");
        }

    }

    public static class IdEndsWithTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.idEndsWith(":partial-id");
            assertThat(by).isInstanceOf(By.ByCssSelector.class);
            assertThat(by).hasToString("By.cssSelector: [id$='\\:partial\\-id']");
        }

    }

    public static class IdStartsWithTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.idStartsWith("partial-id:");
            assertThat(by).isInstanceOf(By.ByCssSelector.class);
            assertThat(by).hasToString("By.cssSelector: [id^='partial\\-id\\:']");
        }

    }

    public static class IdTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.id("someId");
            assertThat(by).isInstanceOf(By.ById.class);
            assertThat(by).hasToString("By.id: someId");
        }

    }

    public static class LinkTextTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.linkText("a link");
            assertThat(by).isInstanceOf(By.ByLinkText.class);
            assertThat(by).hasToString("By.linkText: a link");
        }

    }

    public static class NameTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.name("name");
            assertThat(by).isInstanceOf(By.ByName.class);
            assertThat(by).hasToString("By.name: name");
        }

    }

    public static class PartialLinkTextTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.partialLinkText("partial link");
            assertThat(by).isInstanceOf(By.ByPartialLinkText.class);
            assertThat(by).hasToString("By.partialLinkText: partial link");
        }

    }

    public static class TagNameTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.tagName("tag");
            assertThat(by).isInstanceOf(By.ByTagName.class);
            assertThat(by).hasToString("By.tagName: tag");
        }

    }

    public static class XPathTest {

        @Test
        public void producesCorrectBy() {
            By by = ByProducers.xPath("//div/a[1]");
            assertThat(by).isInstanceOf(By.ByXPath.class);
            assertThat(by).hasToString("By.xpath: //div/a[1]");
        }

    }

    public static class CreateByIdentifyUsing {

        @Test
        public void producesCorrectBy() throws NoSuchMethodException {

            Method method = TestClass.class.getDeclaredMethod("fragment");
            IdentifyUsing annotation = method.getAnnotation(IdentifyUsing.class);

            By by = ByProducers.createBy(annotation);
            assertThat(by).isInstanceOf(By.ByClassName.class);
            assertThat(by).hasToString("By.className: some-class");

        }

        public interface TestClass {

            @IdentifyUsing(value = "some-class", how = ClassName.class)
            PageFragment fragment();

        }

    }

    public static class ByProducerInstantiation {

        @Test(expected = InvalidByProducerException.class)
        public void byProducerNeedsDefaultConstructor() throws NoSuchMethodException {
            try {
                ByProducers.createBy(NoDefaultConstructor.class, "value");
            } catch (InvalidByProducerException e) {
                assertThat(e).hasMessage("unable to create instance of " + NoDefaultConstructor.class);
                assertThat(e).hasCauseInstanceOf(InstantiationException.class);
                throw e;
            }
        }

        @Test(expected = InvalidByProducerException.class)
        public void defaultConstructorNeedsToBePublic() throws NoSuchMethodException {
            try {
                ByProducers.createBy(PrivateDefaultConstructor.class, "value");
            } catch (InvalidByProducerException e) {
                assertThat(e).hasMessage("unable to create instance of " + PrivateDefaultConstructor.class);
                assertThat(e).hasCauseInstanceOf(IllegalAccessException.class);
                throw e;
            }
        }

        public static class NoDefaultConstructor implements ByProducer {

            public NoDefaultConstructor(Object someObject) {
            }

            @Override
            public By createBy(String value) {
                return null;
            }

        }

        public static class PrivateDefaultConstructor implements ByProducer {

            private PrivateDefaultConstructor() {
            }

            @Override
            public By createBy(String value) {
                return null;
            }

        }

    }

}
