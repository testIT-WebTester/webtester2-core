[Home](../README.md)

# @Named
This annotation can be added to `@IdentifyUsing` annotated `PageFragment` returning methods of `Page` or `PageFragment` 
subclasses in order to override the name of the returned fragment.

> Collections of fragments can't be named at the moment!

This is useful in cases where the element IDs are not very clear or event cryptic. The name is used in any logs where the 
fragment is referenced. As well as events fired by the framework.

**Example:**
```java
public interface FooPage extends Page {
 
    @Named("The Foo Widget 42")
    @IdentifyUsing("#foo")
    FooWidget widget();
 
    ...
 
}
```

# Linked Documentation

- [Page Fragments](page-fragment.md)
