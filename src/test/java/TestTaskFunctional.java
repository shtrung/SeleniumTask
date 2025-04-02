import factory.CreateTaskPage;
import factory.LoginPage;
import factory.NavigationMenu;
import factory.TaskPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.TestHelper;
import utils.TestResultLogger;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Чек лист класса:
 * <p>
 * 1. Создание отдельной задачи
 * <p>
 * 2. Удаление задачи
 */

@ExtendWith(TestResultLogger.class)
public class TestTaskFunctional {

    private final ChromeOptions options = new ChromeOptions();
    {
        options.addArguments("--headless");
        options.addArguments("blink-settings=imagesEnabled=false");
    }

    private final WebDriver driver = new ChromeDriver(options);
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
    }

    @ParameterizedTest
    @CsvSource({
            "Создание задачи,Task002,Description,true"
    })
    public void test1(String description, String title, String descriptionTask, boolean expected) {
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.createTask();

            TestHelper.switchTo(driver);

            CreateTaskPage createTaskPage = new CreateTaskPage(driver);
            createTaskPage.createTask(title, descriptionTask);

            menu.goTasks();

            TaskPage taskPage = new TaskPage(driver);
            taskPage.goToCreatedByMeTasks();

            driver.navigate().refresh();

            assertEquals(expected, taskPage.isVisibleInTasks(title));
        }catch (Exception e){
            TestHelper.takeScreenshot(description, driver);
            throw e;
        } finally {
            finallyMethod();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Удаление задачи,Task002,true"
    })
    public void test2(String description, String title, boolean expected){
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.goTasks();

            TaskPage taskPage = new TaskPage(driver);
            taskPage.selectionTask(title);

            TaskPage.SettingsTask settingsTask = taskPage.new SettingsTask();
            settingsTask.deleteTask();

            driver.navigate().refresh();

            assertEquals(expected, !taskPage.isVisibleInTasks(title));
        }catch (Exception e){
            TestHelper.takeScreenshot(description, driver);
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
