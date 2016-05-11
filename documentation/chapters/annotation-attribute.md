[Home](../README.md)

# @Attribute
This annotation can be used within a `PageFragment` subclass in order to retrieve attributes of the underlying element.
Attribute values (which are returned as Strings from the `WebElement`) will be parsed to the method's return type.

**Supported Types:**

- String
- Boolean
- Long
- Integer
- Float
- Double
- Optional&lt;String&gt;
- Optional&lt;Boolean&gt;
- Optional&lt;Long&gt;
- Optional&lt;Integer&gt;
- Optional&lt;Float&gt;
- Optional&lt;Double&gt;

**Constraints:**

- Annotated method must not have arguments!
- Annotated methods have to be part of a page fragment!

> It is possible to declare annotated methods in any interface, as long as they are used from a page fragment.

**Example of different attribute methods:**
```java
public interface FooFragment extends PageFragment {
 
    // returns the string value of the 'value' attribute
    @Attribute("value")
    String value();
 
    // returns the long value of the 'number' attribute
    @Attribute("number")
    Long number();
 
    // returns the optional string value of the 'optional' attribute
    @Attribute("optional")
    Optional<String> optional();
 
}
```

**Example of trait interface with attribute method:**
```java
public interface HasValue {
 
    @Attribute("value")
    String value();
 
}

public interface BarFragment extends PageFragment, HasValue {
    // will have access to working 'value()' method
}
```

# Linked Documentation

- [Page Fragments](page-fragment.md)
