[Home](../README.md)

# Browser
WebTester provides an abstraction layer on top of Selenium's WebDriver called (rather fittingly) `Browser`.

## Interfaces
There are several important interfaces related to browsers.

**Browser:**
A `Browser` provides a streamlined and context centric API for the interaction with a web browser.
It is the main entry point to the framework.

**BrowserBuilder:**
A `BrowserBuilder` provides a builder API for initializing `Browser` instances and setting custom service implementations 
like the [`Configuration`](configuration.md).

## The Web Driver Browser
The `WebDriverBrowser` class implements `Browser` and is used to wrap a Selenium `WebDriver`.
Instances can be created by using the `WebDriverBrowser's` factory methods:

1. `WebDriverBrowser.forWebDriver(webDriver).build();`
2. `WebDriverBrowser.buildForWebDriver(webDriver);`

Both of these are equal. The first method can be used to customize same aspects of the browser before it is build.

## Examples
**Initialization of a new WebDriverBrowser instance:**
```java
WebDriver webDriver = createWebDriver();
Browser browser = WebDriverBrowser.buildForWebDriver(webDriver);
Browser browser = WebDriverBrowser.forWebDriver(webDriver).build();
Browser browser = new WebDriverBrowserBuilder(webDriver).build();
```
**Initialization of a new WebDriverBrowser instance with custom service implementations:**
```java
Configuration config = createConfiguration();
PageObjectFactory factory = createFactory();

Browser browser = WebDriverBrowser.forWebDriver(webDriver)
                        .withConfiguration(config)
                        .withFactory(factory)
                        .build();

Browser browser = new WebDriverBrowserBuilder(webDriver)
                        .withConfiguration(config)
                        .withFactory(factory)
                        .build();
```

# Linked Documentation

- [Configuration](configuration.md)
- [Browser Factories](browser-factories.md)
