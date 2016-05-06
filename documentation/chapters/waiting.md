[Home](../README.md)

# The Waits Utility Class
The `Wait` utility class provides a fluent API for all kinds of wait operations. This includes waiting an exact amount of 
time and waiting for certain conditions with a timeout.

## Examples
```java
// waits 5 seconds
Wait.exactly(5, TimeUnit.SECONDS);
 
// waits 150 milliseconds
Wait.exactly(150, TimeUnit.MILLISECONDS);
 
// waits 1 hour
Wait.exactly(1, TimeUnit.HOURS);
 
// waits until the hidden field is visible on the DOM - with default timeout
Wait.until(hiddenField, is(visible()));
 
// waits until the hidden field is visible on the DOM - with custom timeout
Wait.withTimeoutOf(10, TimeUnit.SECONDS).until(hiddenField, is(visible()));
```

# Linked Documentation

- [Conditions](conditions.md)
