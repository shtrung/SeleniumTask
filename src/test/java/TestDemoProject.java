import factory.*;
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
 * 1. Создание демо проекта
 * <p>
 * 2. Создание карточки в дефолтном проекте
 * <p>
 * 3. Треккинг карточки в демо проекте
 * <p>
 * 4. Удаление демо проекта
 */



@ExtendWith(TestResultLogger.class)
public class TestDemoProject {

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
            "Создание демо проекта,Демо проект,true"
    })
    public void test1(String description, String title, String expected) {
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.goProjects();

            CreateProjectPage createProjectPage = new CreateProjectPage(driver);
            createProjectPage.createDemoProject(title);

            menu.goProjects();

            driver.navigate().refresh();

            assertEquals(Boolean.parseBoolean(expected), createProjectPage.isCreated(title));
        }catch (Exception e){
            TestHelper.takeScreenshot(description, driver);
            throw e;
        } finally {
            finallyMethod();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Создание карточки,Демо проект,DemoCard,Description,true"
    })
    public void test2(String description,String projectName, String title, String descriptionCard, boolean extend) {
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.goAgileBoard();

            AgileBoardPage agileBoardPage = new AgileBoardPage(driver);
            agileBoardPage.goToCardInProject(String.format("%s Доска", projectName));
            agileBoardPage.createCard(title, descriptionCard);


            assertEquals(extend, agileBoardPage.isCreatedCard(title));
        } catch (Exception e) {
            TestHelper.takeScreenshot(description,driver);
            throw e;
        } finally {
            finallyMethod();
        }
    }


    @ParameterizedTest
    @CsvSource({
            "Треккинг карточки в демо проекте,Демо проект,DemoCard,В обработке,true",
            "Треккинг карточки в демо проекте,Демо проект,DemoCard,Исправлена,true",
            "Треккинг карточки в демо проекте,Демо проект,DemoCard,Открыта,true",
    })
    public void test3(String description, String projectName, String title, String statusCard, boolean extend) {
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.goTasks();

            TaskPage taskPage = new TaskPage(driver);
            taskPage.setStateTask(title, statusCard);

            menu.goAgileBoard();

            AgileBoardPage agileBoardPage = new AgileBoardPage(driver);
            agileBoardPage.goToCardInProject(String.format("%s Доска", projectName));

            boolean actual = agileBoardPage.isSetState(title, statusCard);
            assertEquals(extend, actual);
        } catch (Exception e) {
            TestHelper.takeScreenshot(description, driver);
            throw e;
        } finally {
            finallyMethod();
        }
    }


    @ParameterizedTest
    @CsvSource({
            "Удаление демо проекта,Демо проект,true"
    })
    public void test4(String description, String projectName, boolean expected) {
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.goProjects();

            CreateProjectPage createProjectPage = new CreateProjectPage(driver);
            createProjectPage.goToSituationalMenu(projectName);

            CreateProjectPage.SituationalMenu situationalMenu = createProjectPage.new SituationalMenu();
            situationalMenu.deleteProject(projectName);

            driver.navigate().refresh();


            assertEquals(expected, !createProjectPage.isCreated(projectName));
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

