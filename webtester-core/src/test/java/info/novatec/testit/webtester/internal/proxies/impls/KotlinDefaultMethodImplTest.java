package info.novatec.testit.webtester.internal.proxies.impls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;


class KotlinDefaultMethodImplTest {

    KotlinDefaultMethodImpl cut = new KotlinDefaultMethodImpl();

    @Test
    void interfaceWithInternalDefaultImplsClassCanBeHandled() throws NoSuchMethodException {
        Method method = TestInterface.class.getMethod("someMethod", String.class);
        boolean result = cut.isImplementationFor(method);
        assertThat(result).isTrue();
    }

    @Test
    void interfaceWithoutInternalDefaultImplsClassCannotBeHandled() throws NoSuchMethodException {
        Method method = AnotherInterface.class.getMethod("someMethod", String.class);
        boolean result = cut.isImplementationFor(method);
        assertThat(result).isFalse();
    }

    @Test
    void matchingMethodOnDefaultImplsClassIsUsed() throws Exception {
        Object proxy = mock(TestInterface.class);
        Method method = TestInterface.class.getMethod("someMethod", String.class);

        Object result = cut.invoke(proxy, method, new String[] { "Hello" });

        assertThat(result).isEqualTo("HelloHello");
    }

    @Test
    void defaultMethodIsOnlyUsedIfArgumentsMatchInCount() throws Exception {
        Object proxy = mock(TestInterface.class);
        Method method = TestInterface.class.getMethod("someMethod");
        assertThrows(IllegalStateException.class, () -> {
            cut.invoke(proxy, method, new Object[0]);
        });
    }

    @Test
    void defaultMethodIsOnlyUsedIfArgumentsMatchInType() throws Exception {
        Object proxy = mock(TestInterface.class);
        Method method = TestInterface.class.getMethod("anotherMethod", String.class);
        assertThrows(IllegalStateException.class, () -> {
            cut.invoke(proxy, method, new String[] { "Hello" });
        });
    }

    @Test
    void noArgsMethodsCanBeHandled() throws Exception {
        Object proxy = mock(TestInterface.class);
        Method method = TestInterface.class.getMethod("noArgsMethod");
        Object result = cut.invoke(proxy, method, null);
        assertThat(result).isEqualTo("42");
    }

    public interface TestInterface {

        String someMethod();

        String someMethod(String value);

        String anotherMethod(String value);

        String noArgsMethod();

        class DefaultImpls {

            public static String someMethod(TestInterface testInterface, String value) {
                return value + value;
            }

            public static Integer anotherMethod(TestInterface testInterface, Integer value) {
                return value + value;
            }

            public static String noArgsMethod(TestInterface testInterface) {
                return "42";
            }

        }

    }

    public interface AnotherInterface {
        String someMethod(String value);
    }

}
