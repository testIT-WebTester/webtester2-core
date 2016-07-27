package info.novatec.testit.webtester.junit5.extensions.evaluation;

import java.util.function.Supplier;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;

import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.internal.ExtensionModel;


public class TestClassEvaluationExtension extends BaseExtension implements BeforeAllCallback {

    private static Supplier<IllegalStateException> noTestClassAvailableException =
        () -> new IllegalStateException("No test class available!");

    @Override
    public void beforeAll(ContainerExtensionContext context) {
        Class<?> testClass = context.getTestClass().orElseThrow(noTestClassAvailableException);
        ExtensionModel model = ExtensionModel.fromTestClass(testClass);
        setModel(context, model);
    }

}
