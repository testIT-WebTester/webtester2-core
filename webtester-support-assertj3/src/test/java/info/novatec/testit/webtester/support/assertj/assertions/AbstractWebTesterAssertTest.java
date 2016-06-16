package info.novatec.testit.webtester.support.assertj.assertions;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;


@RunWith(Enclosed.class)
public class AbstractWebTesterAssertTest {

    public static class FailOnActualBeingNull {

        @Test
        public void nonNullActualPasses() {
            new TestAssert("foo").failOnActualBeingNull();
        }

        @Test(expected = AssertionError.class)
        public void nullActualThrowsError() {
            new TestAssert(null).failOnActualBeingNull();
        }

    }

    public static class And {

        @Test
        public void invocationReturnsSameAssertionInstance() {
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
