package info.novatec.testit.webtester.junit5.extensions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.lang.reflect.Field;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import utils.MockitoExtension;

import info.novatec.testit.webtester.junit5.internal.ReflectionUtils;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;


@ExtendWith(MockitoExtension.class)
public class BaseExtensionTest {

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
