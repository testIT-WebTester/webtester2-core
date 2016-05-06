package info.novatec.testit.webtester.pagefragments;

import java.util.List;
import java.util.stream.Stream;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.identification.producers.XPath;


@Mapping(tag = "ul")
@Mapping(tag = "ol")
public interface GenericList extends PageFragment {

    /**
     * Returns a list of this {@link GenericList list's} {@link ListItem items}.
     * <p>
     * These are only the direct children of this list. Nested lists will not be resolved!
     *
     * @return the list item children of this list
     * @see GenericList
     * @see ListItem
     * @see OrderedList
     * @see UnorderedList
     * @since 2.0
     */
    @IdentifyUsing(value = "./li", how = XPath.class)
    List<ListItem> items();

    /**
     * Returns a stream of this {@link GenericList list's} {@link ListItem items}.
     * <p>
     * This is equivalent to calling <code>{@link #items()}.stream()</code>.
     *
     * @return the item children of this list
     * @see GenericList
     * @see ListItem
     * @see OrderedList
     * @see UnorderedList
     * @since 2.0
     */
    default Stream<ListItem> streamItems() {
        return items().stream();
    }

    /**
     * Returns the {@link ListItem list item} for the given index. The index starts with <code>0</code> for the first item.
     *
     * @param index the zero-based index of the item to return
     * @return the list item
     * @throws IndexOutOfBoundsException in case there is no item for the given index
     * @see GenericList
     * @see ListItem
     * @see OrderedList
     * @see UnorderedList
     * @since 2.0
     */
    default ListItem getItem(int index) throws IndexOutOfBoundsException {
        return items().get(index);
    }

    /**
     * Returns the number of {@link ListItem items} of this {@link GenericList list}.
     *
     * @return the number of items of this list
     * @see GenericList
     * @see OrderedList
     * @see UnorderedList
     * @since 2.0
     */
    default int size() {
        return items().size();
    }

    /**
     * Returns whether or not this {@link GenericList list} does not contain any {@link ListItem items}.
     *
     * @return true in case the list does not contain any items, otherwise false
     * @see GenericList
     * @see OrderedList
     * @see UnorderedList
     * @since 2.0
     */
    default boolean isEmpty() {
        return items().isEmpty();
    }

}
