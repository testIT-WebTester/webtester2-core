package info.novatec.testit.webtester.support.assertj.assertions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class AbstractWebTesterAssertTest {

    @Nested
    class AssertionsProvideFluentApi {

        @Test
        public void and() {
            TestAssert original = new TestAssert("foo");
            TestAssert returned = original.and();
            Assertions.assertThat(returned).isSameAs(original);
        }

    }

    private static class TestAssert extends AbstractWebTesterAssert<TestAssert, String> {

        public TestAssert(String actual) {
            super(actual, TestAssert.class);
        }

    }

}
