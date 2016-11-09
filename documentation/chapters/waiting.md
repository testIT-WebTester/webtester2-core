[Home](../README.md)

# The Waits Utility Class
The `Wait` utility class provides a fluent API for all kinds of wait operations.
This includes waiting an exact amount of time and waiting for certain conditions
with a timeout.

**There are 3 distinct kinds of Wait operations:**
1. Waiting an exact amount of time: `Wait.exactly(..)`
2. Waiting until an object state is reached: `Wait.until(..)`
3. Waiting until an object supplier return value's state is reached: `Wait.untilSupplied(..)`

**Examples**
```java
// waits 5 seconds
Wait.exactly(5, TimeUnit.SECONDS);
 
// waits 150 milliseconds
Wait.exactly(150, TimeUnit.MILLISECONDS);
 
// waits 1 hour
Wait.exactly(1, TimeUnit.HOURS);
 
// waits until the hidden field is visible on the DOM - with default timeout
PageFragment hiddenField = ...;
Wait.until(hiddenField).is(visible());
 
// waits until the hidden field is visible on the DOM - with custom timeout
Wait.withTimeoutOf(10, TimeUnit.SECONDS).until(hiddenField).is(visible());

// waits until the call to 'findMany(".foo")' returns a non empty list
Wait.untilSupplied(() -> findMany(".foo")).is((foos) -> !foos.isEmpty());
```

## Wait.exactly(...)

This is the most primitive wait operation.
It allows to wait for a specific amount of time.
That amount is specified by to parameters: the amount and the time unit.

The maximum precision for the wait operation is *milliseconds*.
If any more precise unit is defined (e.g. nanoseconds), there will be no wait.

## Wait.until(..)

This kind of wait operation will take any object instance and allows for the
definition of several conditions to be waited on in order.
It is important to note that the conditions will always be evaluated against the
initially specified instance!

In the above example you can see a command which will wait until a 'hidden' field
is visible. This will work because the given object is a `PageFragment`. Since
page fragments act as proxies for `WebElement` instances, which are not cached,
the check on visibility can return a different result for each invocation.

But let's say, as an example, the given object is a list of page fragments and
you want to wait until the list has a certain size. In this case the size of the
list will never change unless it's contents is manipulated asynchronously.

In order to check something like this take a look at `Wait.untilSupplied(..)`.

## Wait.untilSupplied(..)

This kind of wait operation will take an object supplier as its parameter.
The supplier is invoked every time a condition is checked.
With this approach you can wait until a dynamic object - like a list of
page fragments - has a certain state (e.g. size).

# Linked Documentation

- [Conditions](conditions.md)
