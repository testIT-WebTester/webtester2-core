[Home](../README.md)

# @Wait
This annotation can be added to `@IdentifyUsing` annotated methods of `Page` or `PageFragment` subclasses.
When the annotated method is invoked a 'wait until' operation is executed using the annotations condition.

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
 
    @Wait(Until.VISIBLE)
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    ...
 
}
```

**Example for page fragment:**
```java
public interface FooWidget extends PageFragment {
 
    @Wait(Until.VISIBLE)
    @IdentifyUsing("#one")
    TextField fieldOne();
    
    @Wait(Until.VISIBLE)
    @IdentifyUsing("#two")
    TextField fieldTwo();
 
    ...
 
}
```

# Linked Documentation

- [Pages](page.md)
- [Page Fragments](page-fragment.md)
