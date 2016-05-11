[Home](../README.md)

# @PostConstruct
This annotation can be added to methods of `Page` or `PageFragment` subclasses.
Every annotated method will be invoked after an instance of this subclass was initialized.
These methods should be used to verify that the correct page is displayed or the fragment has 'working' state.
The order in which multiple annotated methods are invoked is not deterministic.

> Each method should work on it's own and not depend on another method being invoked!

Since these methods are invoked using reflection, it is not possible to have method arguments!

As an alternative for `@PostConstruct` the `@Must` annotation can be used on page fragment returning methods.
For more about that see [@Must](annotation-must.md).

**Example for page:**
```java
public interface FooPage extends Page {
 
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    @PostConstruct
    void assertThatWidgetIsVisible () {
        assertThat(widget).is(visible());
    }
 
    ...
 
}
```

**Example for page fragment:**
```java
public interface FooWidget extends PageFragment {
 
    @IdentifyUsing("#one")
    TextField fieldOne();
    
    @IdentifyUsing("#two")
    TextField fieldTwo();
 
    @PostConstruct
    void assertThatTextFieldsAreVisible () {
        assertThat(fieldOne).is(visible());
        assertThat(fieldTwo).is(visible());
    }
 
    ...
 
}
```

# Linked Documentation

- [Pages](page.md)
- [Page Fragments](page-fragment.md)
- [@Must](annotation-must.md)
