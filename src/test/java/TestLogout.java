import factory.LoginPage;
import factory.LogoutPage;
import factory.NavigationMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.TestHelper;
import utils.TestResultLogger;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Чек лист класса:
 * <p>
 * 1. Тестирование выхода с ресурса
 */

@ExtendWith(TestResultLogger.class)
public class TestLogout {

    private final ChromeOptions options = new ChromeOptions();
    {
        options.addArguments("--headless");
        options.addArguments("blink-settings=imagesEnabled=false");
    }

    private final WebDriver driver = new ChromeDriver(options);
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
    }


    @Test
    public void test1() {
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.logout();

            LogoutPage logoutPage = new LogoutPage(driver);

            assertTrue(logoutPage.isLogout());
        } catch (Exception e) {
            TestHelper.takeScreenshot("Logout",driver);
            throw e;
        } finally {
            finallyMethod();
        }
    }

    @BeforeEach
    public void login() {
        driver.get("http://193.233.193.42:9091/");
        LoginPage login = new LoginPage(driver);
        String[] loginData = TestHelper.login();
        String email = loginData[0];
        String password = loginData[1];
        login.login(email, password);
        TestHelper.fullDownloadWait(driver);
    }

    public void finallyMethod() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
