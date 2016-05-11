[Home](../README.md)

# The ByProducers Utility Class
The utility class `ByProducers` provides several factory methods for creating `ByProducer` instances.
These are used by WebTester as an abstraction over Selenium's `By` classes.
They are relevant to the following (sub-)systems:

- [Ad-Hoc](ad-hoc-find.md) finding of page fragments

```java
// Ad-Hoc finding of page fragment
browser.findBy(ByProducers.id("username"));
```

# Linked Documentation

- [Ad-Hoc Finding](ad-hoc-find.md)
