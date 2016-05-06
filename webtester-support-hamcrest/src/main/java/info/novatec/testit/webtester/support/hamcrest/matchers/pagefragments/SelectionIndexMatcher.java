package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.Optional;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectionIndexMatcher<T extends SingleSelect> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final Integer index;
    private Optional<Integer> actualIndex;

    public SelectionIndexMatcher(Integer index) {
        this.index = index;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with index <" + index + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualIndex = item.getSelectionIndex();
        return actualIndex.filter(index::equals).isPresent();
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualIndex + ">");
    }

}
