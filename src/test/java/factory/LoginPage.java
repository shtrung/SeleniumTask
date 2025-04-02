package factory;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestHelper;

public class LoginPage {

    @FindBy(xpath = "//input[@id='username']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@data-test='login-button']")
    private WebElement loginButton;

    private final WebDriver driver;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        try {
            loginButton.click();
        } catch (StaleElementReferenceException e) {
            loginButton = driver.findElement(By.xpath("//button[@data-test='login-button']"));
        }
    }


    public boolean isLogin() {
        try {
            WebElement element = driver.findElement(By.xpath("//div[@data-test='navigation-bar']"));
            TestHelper.waitElement(driver,element);
            return element.isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        }
    }


}
