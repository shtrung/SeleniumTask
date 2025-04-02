package factory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestHelper;

public class CreateArticlePage {

    private final WebDriver driver;

    @FindBy(xpath = "//a[@data-test='articlesCreateButtonContainer']")
    private WebElement createButton;

    @FindBy(css = "div.ring-ui-editableHeading_f4da input[data-test='summary']")
    private WebElement titleField;

    @FindBy(xpath = "//div[@data-test='wysiwyg-editor-content']")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[@data-test='publish-button']")
    private WebElement publishButton;


    public CreateArticlePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void createArticle(String title, String description, String projectName) {
        createButton.click();
        TestHelper.waitElement(driver,titleField);
        TestHelper.waitElement(driver,descriptionField);
        TestHelper.sleep(4);
        titleField.sendKeys(title);
        descriptionField.sendKeys(description);
        TestHelper.waitElement(driver,publishButton);
        publishButton.click();

        WebElement choiceProject = driver.findElement(By.xpath(String.format("//span[text()='%s']", projectName)));
        TestHelper.waitElement(driver,choiceProject);
        choiceProject.click();



    }
}
