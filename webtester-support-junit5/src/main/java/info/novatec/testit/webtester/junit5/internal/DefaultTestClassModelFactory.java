package info.novatec.testit.webtester.junit5.internal;

/**
 * Default implementation of {@link TestClassModelFactory}.
 *
 * @since 2.1
 */
public class DefaultTestClassModelFactory implements TestClassModelFactory {

    @Override
    public TestClassModel create(Class<?> testClass) {
        return TestClassModel.fromTestClass(testClass);
    }

}
