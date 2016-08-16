package info.novatec.testit.webtester.junit5.internal;

/**
 * Implementations of this interface are used to create {@link TestClassModel} instances.
 *
 * @since 2.1
 */
public interface TestClassModelFactory {

    /**
     * Creates a new {@link TestClassModel} for the given {@code testClass}.
     *
     * @param testClass the class to create the model from
     * @return the created model
     * @since 2.1
     */
    TestClassModel create(Class<?> testClass);

}
