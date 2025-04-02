import factory.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.TestHelper;
import utils.TestResultLogger;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Чек лист класса:
 * <p>
 * 1. Позитивное и негативное тестирования страницы атворизации
 */


@ExtendWith(TestResultLogger.class)
public class TestLogin {

    private final ChromeOptions options = new ChromeOptions();
    {
        options.addArguments("--headless");
        options.addArguments("blink-settings=imagesEnabled=false");
    }

    private final WebDriver driver = new ChromeDriver(options);
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }


    @ParameterizedTest
    @MethodSource("additionProviderLogin")
    public void testLogin(String description,String email,String password,boolean expected){
        try {
            driver.get("http://193.233.193.42:9091/");

            LoginPage login = new LoginPage(driver);
            login.login(email, password);

            assertEquals(expected, login.isLogin());
        }catch (AssertionError e){
            TestHelper.takeScreenshot(description,driver);
            throw  e;
        }finally {
            driver.quit();
        }
    }

    static Stream<Arguments> additionProviderLogin() throws IOException {
        return TestHelper.additionProviderLogin();
    }

    @AfterEach
    public void close(){
        TestHelper.close();
    }
}
