package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AbstractPageFragmentAssertTest {

    @Test(expected = AssertionError.class)
    public void nullPageFragmentsCantBeAsserted() {
        new TestAssert(null);
    }

    private static class TestAssert extends AbstractPageFragmentAssert<TestAssert, PageFragment> {

        public TestAssert(PageFragment actual) {
            super(actual, TestAssert.class);
        }

    }

}
