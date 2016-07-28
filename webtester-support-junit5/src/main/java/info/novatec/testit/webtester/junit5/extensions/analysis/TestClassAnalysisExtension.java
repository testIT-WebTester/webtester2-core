package info.novatec.testit.webtester.junit5.extensions.analysis;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;

import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;


public class TestClassAnalysisExtension extends BaseExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ContainerExtensionContext context) {
        Class<?> testClass = context.getTestClass().get();
        TestClassModel model = TestClassModel.fromTestClass(testClass);
        setModel(context, model);
    }

}
