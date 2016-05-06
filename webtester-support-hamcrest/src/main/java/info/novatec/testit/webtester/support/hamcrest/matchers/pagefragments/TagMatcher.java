package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class TagMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String tag;
    private String actualTag;

    public TagMatcher(String tag) {
        this.tag = tag;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("tag <" + tag + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualTag = item.getTagName();
        return tag.equals(actualTag);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualTag + ">");
    }

}
