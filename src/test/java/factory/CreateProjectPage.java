package factory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestHelper;

import java.util.List;

public class CreateProjectPage {


    private final WebDriver driver;


    @FindBy(xpath = "//a[@href='projects/create']")
    private WebElement createProjectButton;

    @FindBy(xpath = "//div[@data-test='project']")
    private List<WebElement> projects;

    @FindBy(xpath = "//button[@data-test='template'][1]")
    private WebElement projectDefault;

    @FindBy(xpath = "//button[@data-test='template'][3]")
    private WebElement projectScrum;

    @FindBy(xpath = "//button[@data-test='template'][5]")
    private WebElement projectDemo;


    @FindBy(xpath = "//button[@data-test='accept-button']")
    private WebElement acceptButton;

    @FindBy(xpath = "//input[@data-test='project-name']")
    WebElement inputName;

    @FindBy(xpath = "//button[@data-test='create-button']")
    WebElement createButton;

    @FindBy(xpath = "//button[@data-test='skip']")
    WebElement skipButton;


    public CreateProjectPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void createDefaultProject(String projectName) {
        createProjectButton.click();
        TestHelper.scrollToElement(driver, projectDefault);
        projectDefault.click();
        acceptButton.click();
        inputName.sendKeys(projectName);
        createButton.click();
        skipButton.click();
    }

    public void createScrumProject(String projectName) {
        createProjectButton.click();
        TestHelper.scrollToElement(driver, projectScrum);
        projectScrum.click();
        acceptButton.click();
        inputName.sendKeys(projectName);
        createButton.click();
        skipButton.click();
    }

    public void createDemoProject(String projectName) {
        createProjectButton.click();
        TestHelper.scrollToElement(driver, projectDemo);
        projectDemo.click();
        acceptButton.click();
        inputName.sendKeys(projectName);
        createButton.click();
        skipButton.click();
    }

    public boolean isCreated(String projectName) {
        for (WebElement project : projects) {
            TestHelper.waitElement(driver, project);
            if (projectName.equals(project.getAttribute("data-test-name"))) return true;
        }
        return false;
    }

    public void goCreatedProject(String projectName) {
        for (WebElement project : projects) {
            if (projectName.equals(project.getAttribute("data-test-name"))) {
                WebElement createdProject = project.findElement(By.xpath("//div[@class='rowContent__f90b']"));
                TestHelper.waitElement(driver, createdProject);
                createdProject.click();
            }
        }
    }

    public void goToSituationalMenu(String projectName) {
        for (WebElement project : projects) {
            if (projectName.equals(project.getAttribute("data-test-name"))) {
                WebElement situationMenu = project.findElement(By.xpath("//div[@data-test='ring-dropdown project-app']"));
                situationMenu.click();
            }
        }

    }

    public class SituationalMenu {

        @FindBy(xpath = "//a[text()='Задачи']")
        private WebElement tasks;

        @FindBy(xpath = "//button[text()='Удалить']")
        private WebElement deleteProject;

        @FindBy(xpath = "//input[@value][2]")
        private WebElement inputProjectName;

        @FindBy(xpath = "//span[text()='Удалить проект']")
        private WebElement deleteProjectButton;

        @FindBy(xpath = "//div[@class='ring-ui-innerContainer_b659']")
        WebElement modalWindow;


        public SituationalMenu() {
            PageFactory.initElements(driver, this);
        }

        public void goToTaskInProject() {
            tasks.click();
        }

        public void deleteProject(String projectName) {
            deleteProject.click();
            WebElement input = modalWindow.findElement(By.cssSelector("input[data-enabled-shortcuts=esc]"));
            input.sendKeys(projectName);
            deleteProjectButton.click();
        }
    }

    public class CreateTaskInProject {

        @FindBy(xpath = "//a[@data-test='createIssueButton']")
        WebElement createTaskInProject;


        public CreateTaskInProject() {
            PageFactory.initElements(driver, this);
        }

        public void createTaskInProject() {
            createTaskInProject.click();
        }
    }
}