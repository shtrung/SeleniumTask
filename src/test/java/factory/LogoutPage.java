package factory;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestHelper;

public class LogoutPage {

    @FindBy(xpath = "//div[@class='container__content ng-scope']")
    private WebElement loginWindow;

    private final WebDriver driver;

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public boolean isLogout(){
        try{
            TestHelper.waitElement(driver,loginWindow);
            return loginWindow.isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }
}
