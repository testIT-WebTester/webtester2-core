[Home](../README.md)

# @WaitUntil
This annotation can be added to `@IdentifyUsing` annotated methods of `Page` or `PageFragment` subclasses.
When the annotated method is invoked a 'wait until' operation is executed using the annotations condition.
The condition is provided via a class reference in order to support custom conditions. See [Conditions](conditions.md) for
a set of provided page fragment related predicates.

It is important to note that not all Predicate classes will work with this annotation.
The mechanism with which the predicate is evaluated will initialize the given class via reflection
and needs a default constructor to work!

> Collection and Streams are currently NOT supported!

**Example for page:**
```java
public interface FooPage extends Page {
 
    @WaitUntil(Visible.class)
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    ...
 
}
```

**Example for page fragment:**
```java
public interface FooWidget extends PageFragment {
 
    @WaitUntil(Visible.class)
    @IdentifyUsing("#one")
    TextField fieldOne();
    
    @WaitUntil(Visible.class)
    @IdentifyUsing("#two")
    TextField fieldTwo();
 
    ...
 
}
```

# Linked Documentation

- [Pages](page.md)
- [Page Fragments](page-fragment.md)
- [Conditions](conditions.md)
