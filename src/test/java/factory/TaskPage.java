package factory;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestHelper;

import java.util.List;

public class TaskPage {

    private final WebDriver driver;

    @FindBy(xpath = "//section[@data-test='projects-section']")
    private WebElement projects;

    @FindBy(xpath = "//section[@data-test='tags-section']")
    private WebElement tags;

    @FindBy(xpath = "//section[@data-test='saved-searches-section']")
    private WebElement savedSearchesSection;

    @FindBy(xpath = "//div[@data-test='section-item'][3]")
    private WebElement goToCreatedByMyTask;

    @FindBy(xpath = "//div[@data-test='section-item'][4]")
    private WebElement goToCreatedTasksInProject;

    @FindBy(xpath = "//div[@class='summaryColumnWrapper__f90e']")
    private WebElement goToCreatedTask;

    @FindBy(xpath = "//tr[@class='tableRow__d7ef draggable__a3a9 row__ad7e ring-ui-row_db75']")
    private List<WebElement> createdTasks;

    @FindBy(xpath = "//span[@data-test='ring-tooltip field-value']")
    private WebElement stateTasks;

    @FindBy(xpath = "//div[@id='list-0-13ft:150-1']")
    private WebElement openTaskButton;

    @FindBy(xpath = "//div[@id='list-0-13ft:150-2']")
    private WebElement processingTasksButton;

    @FindBy(xpath = "//div[@id='list-0-13ft:150-7']")
    private WebElement fixedTasksButton;



    public TaskPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isVisibleInTasks(String title) {
        try {
            driver.findElement(By.xpath(String.format("//a[text()='%s']", title)));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void goToCreatedByMeTasks() {
        goToCreatedByMyTask.click();
    }

    public void selectionTask(String taskName) {
        goToCreatedByMyTask.click();
        for(WebElement task : createdTasks){
            if(task.getText().contains(taskName)){
                WebElement select = task.findElement(By.cssSelector("label[data-test='ring-checkbox']"));
                select.click();
            }
        }
    }

    public void setStateTask(String taskName,String stateTask){
        goToCreatedByMyTask.click();
        for(WebElement task : createdTasks){
            if(task.getText().contains(taskName)){
                WebElement statesTask = task.findElement(By.xpath("//span[@data-test='ring-tooltip field-value']"));
                statesTask.click();
                WebElement state = task.findElement(By.xpath(String.format("//span[@class='ring-ui-label_d0d5' and text()='%s']",stateTask)));
                TestHelper.waitElement(driver,state);
                state.click();
            }
        }
    }

    public void goToCreatedTaskInProject() {
        goToCreatedTasksInProject.click();
        goToCreatedTask.click();
    }


    public class SettingsTask {


        @FindBy(xpath = "//div[@class='hrLink__d416 fieldsExpander__c9b7']")
        private WebElement seeAllField;

        @FindBy(xpath = "//div[@aria-label='Приоритет']")
        private WebElement priorityTaskLevel;

        @FindBy(xpath = "//span[text()='Критическая']")
        private WebElement criticalLVL;

        @FindBy(xpath = "//span[text()='Неотложная']")
        private WebElement urgentLVL;

        @FindBy(xpath = "//span[text()='Серьезная']")
        private WebElement seriousLVL;

        @FindBy(xpath = "//span[text()='Обычная']")
        private WebElement normalLVL;

        @FindBy(xpath = "//span[text()='Незначительная']")
        private WebElement minorLVL;

        @FindBy(xpath = "//span[@class='closeButton__b605']")
        private WebElement closeButtonTaskInProject;

        @FindBy(xpath = "//button[@data-test='delete-item-button']")
        private WebElement deleteButton;

        @FindBy(xpath = "//button[@data-test='confirm-ok-button']")
        private WebElement acceptButton;

        @FindBy(css = "button.ring-ui-button_bc66")
        private WebElement showMoreButton;

        @FindBy(css = "div.ticketQuickView__acc0 ticketTableQuickView__be64")
        private WebElement settingsWindow;

        public SettingsTask() {
            PageFactory.initElements(driver, this);
        }

        public void setPriorityTask(String priorityLevel) {
            seeAllField.click();
            TestHelper.waitElement(driver, priorityTaskLevel);
            priorityTaskLevel.click();

            switch (priorityLevel) {
                case "Неотложная" -> urgentLVL.click();
                case "Критическая" -> criticalLVL.click();
                case "Серьезная" -> seriousLVL.click();
                case "Обычная" -> normalLVL.click();
                case "Незначительная" -> minorLVL.click();
            }
        }

        public void deleteTask(){
            deleteButton.click();
            acceptButton.click();
        }

        public boolean isSetLVLPriority(String priorityLevel) {
            try {
                driver.findElement(By.xpath(String.format("//span[@label='%s']", priorityLevel)));
            } catch (NoSuchElementException e) {
                return false;
            }
            return true;

        }

        public void closeSetTask() {
            closeButtonTaskInProject.click();
        }
    }
}