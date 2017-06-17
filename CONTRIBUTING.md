# Contributing

Firstly, you should create an [issue](https://github.com/testIT-WebTester/webtester2-core/issues) 
for your enhancement request.

WebTester uses a “Fork & Pull” model for collaborative development. If you have changes that you 
would like us to consider for introduction to WebTester, you will need to fork the repository, 
commit and push your changes to your forked project, and send us a pull request referencing the 
previously created issue.

Please note that, in order to keep WebTester clean and minimal, we consider all enhancement requests 
carefully. Should your enhancement not be appropriate for WebTester, we may reject the pull request.

Here are some pointers in case you want to contribute code to the project.

## Code Format
In order to keep the format of Java classes the same for every class we provide formatting settings for the following IDEs:

- [IntelliJ XML](https://github.com/testIT-WebTester/webtester2-core/blob/master/development/intellij_formatter.xml) / [IntelliJ Settings JAR](https://github.com/testIT-WebTester/webtester2-core/blob/master/development/intellij-formatter-settings.jar)
- [Eclipse](https://github.com/testIT-WebTester/webtester2-core/blob/master/development/eclipse_formatter.xml)

## Project Lombok
WebTester 2 makes use of [Project Lombok](https://projectlombok.org). You might need to install a plugin in order to compile code within your IDE.

## Quality Standards

* We try to adhere to the clean code standards proposed by [Robert. C. Martin](https://www.google.com/search?q=bob+martin+clean+code)
* There are a hand full of explicit [Guidelines](https://github.com/testIT-WebTester/webtester2-core/wiki/Coding-Guidelines).
* There must be tests for every feature!
  * unit over integration tests
  * prove your assumption of Selenium's behavior with a simple integration test
  * use existing tests as guides and how to patterns
* There is a [user documentation](https://github.com/testIT-WebTester/webtester2-core/blob/master/documentation/README.md)!
  * if you change a feature and there is a documentation for that area of that framework you have to adapt the documentation as well
  * if you create something new, create a matching user documentation as well
  * if you find something missing or wrong in the documentation fix it!
* Ask for a review!
  * every pull request will be reviewed before it is merged
  * if you want to get early feedback feel free to contact us directly
* We like our commit history to be easy to understand. Please rebase your pull requests!
