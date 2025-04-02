package utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class TestResultLogger implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("Тест " + context.getDisplayName().split(",")[0] + " - завершился успешно.");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("Тест " + context.getDisplayName().split(",")[0] + " - завершился неудачно.");
        System.out.println("Ошибка: " + cause.getMessage());
    }
}
