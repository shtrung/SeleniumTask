package factory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestHelper;

import java.time.Duration;
import java.util.List;

public class CreateTaskPage {

    private final WebDriverWait wait;

    private final WebDriver driver;

    @FindBy(css = "textarea[data-test='summary']")
    private WebElement titleField;

    @FindBy(xpath = "//div[@data-test='wysiwyg-editor-content']")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[@data-test='submit-button']")
    private WebElement createButton;

    @FindBy(xpath = "//button[@data-test='ring-dialog-close-button']")
    private WebElement dialogCloseButton;

    @FindBy(xpath = "//button[@data-test='ok-button']")
    private WebElement savedTaskButton;

    @FindBy(xpath = "//a[@target='_blank']")
    List<WebElement> nameTasks;

    public CreateTaskPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void createTask(String title, String description) {
        titleField.sendKeys(title);
        descriptionField.sendKeys(description);
        createButton.click();
    }

    public void createAndCloseCreatedTask(String title, String description) {
        titleField.sendKeys(title);
        descriptionField.sendKeys(description);
        createButton.click();

        WebElement goToTaskInProject = driver.findElement(By.xpath("//div[@class='id__d727']"));
        goToTaskInProject.click();
    }


    public boolean isCreated() {
        try {
            driver.findElement(By.xpath("//span[text()='только что']"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isCreatedTaskInProject(String taskName) {
        try {
            driver.findElement(By.xpath(String.format("//*[text()='%s']", taskName)));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }



}
