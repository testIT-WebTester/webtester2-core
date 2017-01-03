package info.novatec.testit.webtester.junit5.internal;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


/**
 * This model stores information about the test class and is used by WebTester's JUnit extensions to exchange data.
 *
 * @since 2.1
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("PMD.UnusedPrivateField")
public class TestClassModel {

    private final List<Field> browserFields;
    private final Map<String, Field> namedBrowserFields;
    private final List<Field> eventListenerFields;
    private final List<Field> pageFields;
    private final List<Field> configurationValueFields;

    /**
     * Creates a {@link TestClassModel} from the given test class.
     *
     * @param testClass the class to analyze
     * @return the resulting model
     * @since 2.1
     */
    public static TestClassModel fromTestClass(Class<?> testClass) {
        return new TestClassAnalyzer(testClass, new ReflectionUtils()).analyze();
    }

}
