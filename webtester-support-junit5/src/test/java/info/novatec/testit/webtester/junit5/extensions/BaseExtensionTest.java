package info.novatec.testit.webtester.junit5.extensions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Optional;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestExtensionContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testit.testutils.mockito.junit5.EnableMocking;

import info.novatec.testit.webtester.junit5.internal.ReflectionUtils;
import info.novatec.testit.webtester.junit5.internal.TestClassModelFactory;


@EnableMocking
class BaseExtensionTest {

    @Mock
    Store store;
    @Mock
    TestExtensionContext context;
    @Mock
    Consumer<ExtensionContext> contextConsumer;

    @Mock
    ReflectionUtils reflectionUtils;
    @Mock
    TestClassModelFactory testClassModelFactory;
    @InjectMocks
    BaseExtension cut;

    @BeforeEach
    void buildTestContext() {
        doReturn(Optional.of(TestClass.class)).when(context).getTestClass();
        doReturn(store).when(context).getStore(BaseExtension.NAMESPACE);
    }

    @Test
    @DisplayName("default constructor creates default ReflectionUtils")
    void defaultConstructorCreatesDefaultReflectionUtils() {
        ReflectionUtils utils = new BaseExtension().getReflectionUtils();
        assertThat(utils).isNotNull();
    }

    @Test
    @DisplayName("default constructor creates default ReflectionUtils")
    void defaultConstructorCreatesDefaultTestClassModelProducer() {
        TestClassModelFactory factory = new BaseExtension().getTestClassModelFactory();
        assertThat(factory).isNotNull();
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

        RuntimeException actualException = assertThrows(RuntimeException.class, () -> {
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

        Exception actualException = assertThrows(Exception.class, () -> {
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

        Error actualException = assertThrows(Error.class, () -> {
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

        UndeclaredThrowableException actualException = assertThrows(UndeclaredThrowableException.class, () -> {
            cut.executeHandlingUndeclaredThrowables(context, testContext -> {
                throw new UndeclaredThrowableException(cause);
            });
        });
        assertThat(actualException.getCause()).isSameAs(cause);

    }

    @Test
    @DisplayName("getModel(..) exception is thrown if context doesn't contain test class")
    void exceptionIfNoTestClass() {
        doReturn(Optional.empty()).when(context).getTestClass();
        assertThrows(NoTestClassException.class, () -> {
            cut.getModel(context);
        });
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
