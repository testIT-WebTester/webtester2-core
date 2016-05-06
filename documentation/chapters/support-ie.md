[Home](../README.md)

# Internet Explorer Browser Factory
The module `webtester-support-ie` provides a default `BrowserFactory` implementation called `InternetExplorerFactory`.

## Default Driver Configuration
In order to optimize testing the following properties are set when creating a `WebDriver` using the `InternetExplorerFactory`:

- "Native Events" are disabled -> Selenium does not simulate human typing.

## Additional Service Executable
The 'InternetExplorerDriver' needs an additional executable to communicate with a IE browser.
It can be downloaded [here](http://selenium-release.storage.googleapis.com/index.html).

The path to the executable must be declared as a system or environment property named: `webdriver.ie.driver`

**For more details take a look at these links:**

- https://github.com/SeleniumHQ/selenium/wiki/InternetExplorerDriver

# Linked Documentation

- [Browser](browser.md)
