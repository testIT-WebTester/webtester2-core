package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AttributeMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final String attributeName;

    public AttributeMatcher(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("attribute <" + attributeName + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        return Conditions.attribute(attributeName).test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was not present");
    }

}
