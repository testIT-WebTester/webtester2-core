package info.novatec.testit.webtester.junit5.extensions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.function.Consumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestExtensionContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import utils.MockitoExtension;

import info.novatec.testit.webtester.junit5.internal.ReflectionUtils;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;


@ExtendWith(MockitoExtension.class)
public class BaseExtensionTest {

    @Mock
    TestExtensionContext context;
    @Mock
    Consumer<ExtensionContext> contextConsumer;

    @Mock
    ReflectionUtils reflectionUtils;
    @InjectMocks
    BaseExtension cut;

    @Test
    @DisplayName("default constructor uses default reflection util implementation")
    void defaultConstructorCreatesDefaultReflectionUtil() {
        ReflectionUtils utils = new BaseExtension().getReflectionUtils();
        assertThat(utils).isNotNull();
    }

    @Test
    @DisplayName("executeHandlingUndeclaredThrowables(..) consumer is executed")
    void consumerIsExecuted() throws Exception {
        cut.executeHandlingUndeclaredThrowables(context, contextConsumer);
        verify(contextConsumer).accept(context);
        verifyNoMoreInteractions(contextConsumer);
    }

    @Test
    @DisplayName("executeHandlingUndeclaredThrowables(..) undeclared runtime exceptions are rethrown")
    void undeclaredRuntimeExceptionsAreRethrown() throws Exception {

        RuntimeException cause = new RuntimeException();

        RuntimeException actualException = expectThrows(RuntimeException.class, () -> {
            cut.executeHandlingUndeclaredThrowables(context, testContext -> {
                throw new UndeclaredThrowableException(cause);
            });
        });
        assertThat(actualException).isSameAs(cause);

    }

    @Test
    @DisplayName("executeHandlingUndeclaredThrowables(..) undeclared exceptions are rethrown")
    void undeclaredExceptionsAreRethrown() throws Exception {

        Exception cause = new Exception();

        Exception actualException = expectThrows(Exception.class, () -> {
            cut.executeHandlingUndeclaredThrowables(context, testContext -> {
                throw new UndeclaredThrowableException(cause);
            });
        });
        assertThat(actualException).isSameAs(cause);

    }

    @Test
    @DisplayName("executeHandlingUndeclaredThrowables(..) undeclared errors are rethrown")
    void undeclaredErrorsAreRethrown() throws Exception {

        Error cause = new Error();

        Error actualException = expectThrows(Error.class, () -> {
            cut.executeHandlingUndeclaredThrowables(context, testContext -> {
                throw new UndeclaredThrowableException(cause);
            });
        });
        assertThat(actualException).isSameAs(cause);

    }

    @Test
    @DisplayName("executeHandlingUndeclaredThrowables(..) undeclared throwables are not handled")
    void undeclaredThrowablesAreNotHandled() throws Exception {

        Throwable cause = new Throwable();

        UndeclaredThrowableException actualException = expectThrows(UndeclaredThrowableException.class, () -> {
            cut.executeHandlingUndeclaredThrowables(context, testContext -> {
                throw new UndeclaredThrowableException(cause);
            });
        });
        assertThat(actualException.getCause()).isSameAs(cause);

    }

    @Test
    @DisplayName("setModel(..) stores model in namespace store")
    void modelIsStoredInNamespaceStore() {

        ExtensionContext context = mock(ExtensionContext.class);
        Store store = mock(Store.class);
        doReturn(store).when(context).getStore(BaseExtension.NAMESPACE);

        TestClassModel model = mock(TestClassModel.class);
        cut.setModel(context, model);

        verify(store).put(BaseExtension.EXTENSION_MODEL_KEY, model);
        verifyNoMoreInteractions(store);

    }

    @Test
    @DisplayName("getModel(..) gets model from namespace store")
    void modelIsRetrievedFromNamespaceStore() {

        ExtensionContext context = mock(ExtensionContext.class);
        Store store = mock(Store.class);
        doReturn(store).when(context).getStore(BaseExtension.NAMESPACE);

        TestClassModel model = mock(TestClassModel.class);
        doReturn(model).when(store).get(BaseExtension.EXTENSION_MODEL_KEY, TestClassModel.class);

        TestClassModel actualModel = cut.getModel(context);

        assertThat(actualModel).isSameAs(model);

    }

    @Test
    @DisplayName("setValue(..) delegates to reflection utils")
    void settingValueDelegatesToReflectionUtils() throws NoSuchFieldException {

        TestClass instance = new TestClass();
        Field field = instance.getClass().getDeclaredField("field");
        Object value = new Object();

        cut.setValue(field, instance, value);

        verify(reflectionUtils).setValue(field, instance, value);

    }

    @Test
    @DisplayName("getValue(..) delegates to reflection utils")
    void gettingValueDelegatesToReflectionUtils() throws NoSuchFieldException {

        Object value = new Object();
        TestClass instance = new TestClass();
        Field field = instance.getClass().getDeclaredField("field");
        doReturn(value).when(reflectionUtils).getValue(field, instance);

        Object actualValue = cut.getValue(field, instance);

        assertThat(actualValue).isSameAs(value);

    }

    static class TestClass {
        Object field;
    }

}
