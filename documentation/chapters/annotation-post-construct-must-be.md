[Home](../README.md)

# @PostConstructMustBe
This annotation can be added to `@IdentifyUsing` annotated methods of `Page` or `PageFragment` subclasses.
Every annotated method will be invoked after an instance of this subclass was initialized and the condition provided by 
the annotation will be checked. This mechanism is intended to be used in order to prevent unnecessary `@PostConstruct` 
methods to check basic conditions of parts of the page / fragment. As with `@PostConstruct`, the order in which these 
methods are invoked / checked is not deterministic!

It is important to note that not all Predicate classes will work with this annotation.
The mechanism with which the predicate is evaluated will initialize the given class via reflection
and needs a default constructor to work!

> Collection and Streams are currently NOT supported!

**Example for page:**
```java
public interface FooPage extends Page {
 
    @PostConstructMustBe(Visible.class)
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    ...
 
}
```

**Example for page fragment:**
```java
public interface FooWidget extends PageFragment {
 
    @PostConstructMustBe(Visible.class)
    @IdentifyUsing("#one")
    TextField fieldOne();
    
    @PostConstructMustBe(Visible.class)
    @IdentifyUsing("#two")
    TextField fieldTwo();
 
    ...
 
}
```

## Combination with @WaitUntil

The `@PostConstructMustBe` annotation can be used in combination with `WaitUntil`. This is especially useful in AJAX heavy
applications where a fragment might be created with a short delay.

**Example:**
In this example the `widget` is checked as soon as `BarPage` is initialized. But `WaitUntil` will be triggered when invoking 
the method assuring that the widget is present before checking if it is visible.
```java
public interface BarPage extends Page {
 
    @PostConstructMustBe(Visible.class)
    @WaitUntil(Present.class)
    @IdentifyUsing("#bar")
    BarWidget widget();
 
    ...
 
}
```

# Linked Documentation

- [Pages](page.md)
- [Page Fragments](page-fragment.md)
- [@PostConstruct](annotation-post-construct.md)
- [@WaitUntil](annotation-wait-until.md)
