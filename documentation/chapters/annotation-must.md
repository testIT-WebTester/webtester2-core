[Home](../README.md)

# @Must
This annotation can be added to `@IdentifyUsing` annotated methods of `Page` or `PageFragment` subclasses.
Every annotated method will be invoked after an instance of this subclass was initialized and the condition provided by 
the annotation will be checked. This mechanism is intended to be used in order to prevent unnecessary `@PostConstruct` 
methods to check basic conditions of parts of the page / fragment. As with `@PostConstruct`, the order in which these 
methods are invoked / checked is not deterministic!

> Collection and Streams are currently NOT supported!

**The following conditions are available:**

- `VISIBLE`: The fragment is displayed on the current page.
- `PRESENT`: The fragment is present in the current page's DOM.
- `PRESENT_AND_VISIBLE`: The fragment is present in the current page's DOM and is displayed.
- `ENABLED`: The fragment is enabled / not disabled.
- `EDITABLE`: The fragment is enabled and not 'read-only'
- `INTERACTABLE`: The fragment is visible and editable.

**Example for page:**
```java
public interface FooPage extends Page {
 
    @Must(Be.VISIBLE)
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    ...
 
}
```

**Example for page fragment:**
```java
public interface FooWidget extends PageFragment {
 
    @Must(Be.VISIBLE)
    @IdentifyUsing("#one")
    TextField fieldOne();
    
    @Must(Be.VISIBLE)
    @IdentifyUsing("#two")
    TextField fieldTwo();
 
    ...
 
}
```

## Combination with @Wait

The `@Must` annotation can be used in combination with `@Wait`. This is especially useful in AJAX heavy applications where
a fragment might be created with a short delay.

**Example:**
In this example the `widget` is checked as soon as `BarPage` is initialized. But `@Wait` will be triggered when invoking 
the method assuring that the widget is present before checking if it is visible.
```java
public interface BarPage extends Page {
 
    @Must(Be.VISIBLE)
    @Wait(Until.PRESENT)
    @IdentifyUsing("#bar")
    BarWidget widget();
 
    ...
 
}
```

# Linked Documentation

- [Pages](page.md)
- [Page Fragments](page-fragment.md)
- [@PostConstruct](annotation-post-construct.md)
- [@Wait](annotation-wait.md)
