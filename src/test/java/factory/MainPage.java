package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    private NavigationMenu menu;

    @FindBy()
    WebElement element;



    public MainPage(WebDriver driver) {
        menu = new NavigationMenu(driver);
        PageFactory.initElements(driver,this);
    }
}
