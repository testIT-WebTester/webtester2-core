package info.novatec.testit.webtester.junit5.extensions.analysis;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.Extension;

import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;


/**
 * This {@link Extension} analyzes the test class and extracts a {@link TestClassModel} from that analysis. This model is
 * then stored within the current test context to bes used by other extensions.
 *
 * @since 2.1
 */
public class TestClassAnalysisExtension extends BaseExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ContainerExtensionContext context) {
        Class<?> testClass = context.getTestClass().get();
        TestClassModel model = TestClassModel.fromTestClass(testClass);
        setModel(context, model);
    }

}
