package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class TestHelper {

    private static Stream<String> stream;


    public static Stream<Arguments> additionProviderLogin() throws IOException {
        String filePath = "src/test/resources/addition_cases_Login.csv";
        stream = Files.lines(Paths.get(filePath));
        return stream.skip(1) // Пропускаем заголовок
                .map(line -> line.split(","))
                .map(arr -> Arguments.of(
                        arr[0], // Описание
                        arr[1], // Первое значение
                        arr[2], // Второе значение
                        arr[3]  // Ожтдаемы результат авторизации (true/false)
                ));
    }

    public static String[] login() {
        try (CSVReader reader = new CSVReader(new FileReader("src/test/resources/login_data.csv"))) {
            return reader.readNext();
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitElement(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void jsSendKeys(WebDriver driver, WebElement element, String value){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format("arguments[0].value='%s';",value), element);

    }



    public static void jsClick(WebDriver driver, WebElement element){
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    public static void takeScreenshot(String description, WebDriver driver) {
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File src = screenshot.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(src, new File(description+"_screenshot.png"));
        } catch (IOException ignored) {

        }
    }

    public static void randomScroll(WebDriver driver) {
        double dob = Math.random() * (15.66 - 4.81) + 4.81;
        String maskScroll = String.format("window.scrollBy(0, %f);", dob);
        ((JavascriptExecutor) driver).executeScript(maskScroll);
    }

    public static void clickAction(WebDriver driver, WebElement elementToClick){
        Actions actions = new Actions(driver);
        actions.moveToElement(elementToClick).doubleClick().perform();
    }

    public static void setZoomLevel(WebDriver driver, double zoomLevel) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom = '" + zoomLevel + "';");
    }

    public static void switchTo(WebDriver driver) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
    }

    public static void scrollToElement(WebDriver driver,WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void fullDownloadWait(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete")
        );

        wait.until(
                webDriver -> Objects.requireNonNull(((JavascriptExecutor) webDriver).executeScript("return (window.performance.getEntriesByType('resource').length > 0) && (window.performance.getEntriesByType('resource').every(r => r.responseEnd > 0))"))
        );

        try {
            WebElement anchor = driver.findElement(By.xpath("//div[@id='app']"));
            waitElement(driver,anchor);
        }catch (NoSuchElementException ignored){}


    }





    @Deprecated
    public static void sleep(int seconds){
        try{
            Thread.sleep(seconds * 1000L);
        }catch (InterruptedException ignored){

        }
    }

    public static void fullScreenWindow(WebDriver driver){
        driver.manage().window().maximize();
    }

    public static void close() {
        if (stream != null) {
            stream.close();
        }
    }
}
