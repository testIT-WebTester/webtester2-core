package info.novatec.testit.webtester.support.assertj.assertions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class AbstractWebTesterAssertTest {

    @Nested
    class AssertionsProvideFluentApi {

        @Test
        void and() {
            TestAssert original = new TestAssert("foo");
            TestAssert returned = original.and();
            Assertions.assertThat(returned).isSameAs(original);
        }

    }

    private static class TestAssert extends AbstractWebTesterAssert<TestAssert, String> {

        TestAssert(String actual) {
            super(actual, TestAssert.class);
        }

    }

}
