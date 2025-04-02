package factory;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestHelper;

import java.time.Duration;

public class NavigationMenu {

    private final WebDriverWait wait;

    private final WebDriver driver;

    @FindBy(xpath = "//a[@href='issues']")
    private WebElement issues;

    @FindBy(xpath = "//a[@href='dashboard']")
    private WebElement dashboard;

    @FindBy(css = "a[data-test='ring-link agile-boards-button']")
    private WebElement agiles;

    @FindBy(xpath = "//a[@href='reports']")
    private WebElement reports;

    @FindBy(xpath = "//div[@data-test='ring-dropdown show-more']")
    private WebElement dropdown;

    @FindBy(xpath = "//a[@href='projects']")
    private WebElement projects;

    @FindBy(xpath = "//a[@href='articles']")
    private WebElement articles;

    @FindBy(xpath = "//div[@data-test='ring-dropdown create']")
    private WebElement creates;

    @FindBy(xpath = "//button[@data-test='ring-link collapse-sidebar-button-button']")
    private WebElement collapseBar;

    @FindBy(xpath = "//button[@data-test='ring-tooltip expand-sidebar-button']")
    private WebElement expandBar;


    @FindBy(xpath = "//div[@data-test='ring-dropdown ring-profile']")
    private WebElement profile;

    @FindBy(xpath = "//button[text()='Выйти']")
    private WebElement exitButton;

    public NavigationMenu(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goDashboard() {
        dashboard.click();
    }

    public void goAgileBoard() {
        agiles.click();
    }

    public void goProjects() {
        dropdown.click();
        projects.click();
    }

    public void goArticles(){
        dropdown.click();
        articles.click();
    }

    public void logout(){
        profile.click();
        exitButton.click();
    }

    public void collapseBar(){
        collapseBar.click();
    }



    public void goReports() {
        reports.click();
    }

    public void goTasks() {
       issues.click();
    }


    public void createTask() {
        creates.click();
        WebElement task = driver.findElement(By.xpath("//div[@role='row'][2]"));
        wait.until(ExpectedConditions.elementToBeClickable(task));
        task.click();
    }

    public void createArticle() {
        creates.click();
        WebElement article = driver.findElement(By.xpath("//div[@role='row'][3]"));
        wait.until(ExpectedConditions.elementToBeClickable(article));
        article.click();
    }
}
