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
 * 1. Создание scrum проекта
 * <p>
 * 2. Создание задачи в scrum проекте
 * <p>
 * 3. Треккинг карточки в scrum проекте
 * <p>
 * 4. Удаление scrum проекта
 */

@ExtendWith(TestResultLogger.class)
public class TestScrumProject {

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
            "Создание scrum проекта,Скрам Проект,true"
    })
    public void test1(String description, String title, boolean expected) {
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.goProjects();

            driver.navigate().refresh();

            CreateProjectPage createProjectPage = new CreateProjectPage(driver);
            createProjectPage.createScrumProject(title);

            menu.goProjects();

            driver.navigate().refresh();

            assertEquals(expected, createProjectPage.isCreated(title));
        }catch (Exception e){
            TestHelper.takeScreenshot(description, driver);
            throw e;
        } finally {
            finallyMethod();
        }
    }


    @ParameterizedTest
    @CsvSource({
            "Создание задачи в scrum проекте,Скрам Проект,ScrumCard,Description,true"
    })
    public void test2(String description, String projectName, String title, String descriptionTask, boolean expected)  {
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.goProjects();

            CreateProjectPage createProjectPage = new CreateProjectPage(driver);
            createProjectPage.goToSituationalMenu(projectName);

            CreateProjectPage.SituationalMenu situationalMenu = createProjectPage.new SituationalMenu();
            situationalMenu.goToTaskInProject();

            CreateProjectPage.CreateTaskInProject createTaskInProject = createProjectPage.new CreateTaskInProject();
            createTaskInProject.createTaskInProject();

            CreateTaskPage taskPage = new CreateTaskPage(driver);
            taskPage.createAndCloseCreatedTask(title, descriptionTask);

            menu.goProjects();

            createProjectPage.goCreatedProject(projectName);

            boolean actual = taskPage.isCreatedTaskInProject(title);
            assertEquals(expected, actual);
        }catch (Exception e){
            TestHelper.takeScreenshot(description, driver);
            throw e;
        } finally {
            finallyMethod();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Треккинг карточки в scrum проекте,Скрам Проект,ScrumCard,В обработке,true",
            "Треккинг карточки в scrum проекте,Скрам Проект,ScrumCard,Подлежит проверке,true",
            "Треккинг карточки в scrum проекте,Скрам Проект,ScrumCard,Готово,true",
            "Треккинг карточки в scrum проекте,Скрам Проект,ScrumCard,Открыта,true"
    })
    public void test3(String description,String projectName, String title, String statusCard, boolean extend) {
        try {
            NavigationMenu menu = new NavigationMenu(driver);
            menu.goTasks();

            TaskPage taskPage = new TaskPage(driver);
            taskPage.setStateTask(title,statusCard);

            menu.goAgileBoard();

            AgileBoardPage agileBoardPage = new AgileBoardPage(driver);
            agileBoardPage.goToCardInProject(String.format("Разработка проекта %s",projectName));

            driver.navigate().refresh();

            boolean actual = agileBoardPage.isSetState(title,statusCard);
            assertEquals(extend,actual);
        } catch (Exception e) {
            TestHelper.takeScreenshot(description,driver);
            throw e;
        } finally {
            finallyMethod();
        }
    }


    @ParameterizedTest
    @CsvSource({
            "Удаление scrum проекта,Скрам Проект,true"
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


    public void finallyMethod() {
        driver.manage().deleteAllCookies();
        driver.quit();
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
}
