[Home](../README.md)

# @Action
This annotation can be added to methods of `Page` or `PageFragment` subclasses in order to mark these methods as actions.
Actions can be configured to behave in certain ways:

- The execution of actions can be delayed by setting the property `actions.deceleration` to a certain amount of milliseconds.

## Example

```java
public interface FooPage extends Page {
 
    @Action
    default void doSomething() {
        ...
    }
 
}
```
