[Home](../README.md)

# @Action
This annotation can be added to methods of `Page` or `PageFragment` subclasses in order to mark these methods as actions.
Currently the only effect of this annotation the option to delay the execution of annotated methods by setting the 
property `actions.deceleration` to a certain amount of milliseconds.

> The effects of this annotation will grow in future versions of WebTester

**Example of action method in page:**
```java
public interface FooPage extends Page {
 
    @Action
    default void doSomething() {
        ...
    }
 
}
```

**Example of action method in page fragment:**
```java
public interface BarFragment extends PageFragment {
 
    @Action
    default void doSomething() {
        ...
    }
 
}
```

# Linked Documentation

- [Pages](page.md)
- [Page Fragments](page-fragment.md)
