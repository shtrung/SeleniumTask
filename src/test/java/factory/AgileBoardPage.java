package factory;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestHelper;

public class AgileBoardPage {

    private final WebDriver driver;

    @FindBy(xpath = "//span[@class='c_anchor__cb9']")
    private WebElement searchProjectMenu;


    @FindBy(xpath = "//button[@data-test='Новая карточка']")
    private WebElement newCardButton;

    @FindBy(xpath = "//textarea[@data-test='summary']")
    private WebElement titleCard;

    @FindBy(xpath = "//div[@data-test='wysiwyg-editor-content']")
    private WebElement descriptionCard;

    @FindBy(xpath = "//button[@data-test='submit-button']")
    private WebElement createCardButton;

    @FindBy(xpath = "//td[@class='yt-agile-table__row__cell'][1]")
    private WebElement openTasks;

    @FindBy(xpath = "//td[@class='yt-agile-table__row__cell'][2]")
    private WebElement inProcessingTasks;

    @FindBy(xpath = "//td[@class='yt-agile-table__row__cell'][3]")
    private WebElement fixedTasks_subjectVerificationTask;

    @FindBy(xpath = "//td[@class='yt-agile-table__row__cell'][4]")
    private WebElement readyTask;

    public AgileBoardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void createCard(String title, String description) {
        TestHelper.sleep(3);
        newCardButton.click();
        titleCard.sendKeys(title);
        descriptionCard.sendKeys(description);
        createCardButton.click();
    }

    public boolean isCreatedCard(String title) {
        try {
            driver.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", title)));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void goToCardInProject(String projectName){
        searchProjectMenu.click();
        WebElement agileInProject = driver.findElement(By.xpath( String.format("//a[text()='%s']",projectName)));
        agileInProject.click();
    }

    public boolean isSetState(String cardName, String state) {
        try {
            switch (state) {
                case "Открыта" ->
                {
                    return   openTasks.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", cardName))).isDisplayed();
                }
                case "В обработке" ->
                {
                    return inProcessingTasks.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", cardName))).isDisplayed();
                }
                case "Исправлена", "Подлежит проверке" ->
                {
                    return fixedTasks_subjectVerificationTask.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", cardName))).isDisplayed();
                }
                case "Готово" ->
                {
                    return readyTask.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", cardName))).isDisplayed();
                }
            }
            return false;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
