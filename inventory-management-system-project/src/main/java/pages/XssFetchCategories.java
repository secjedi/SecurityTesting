package pages;

import com.google.common.io.Files;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import utils.BaseTest;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class XssFetchCategories extends BaseTest {
    private final By usernameTextBox = By.id("username");
    private final By passwordTextBox = By.id("password");
    private final By submitButton = By.xpath("/html/body/div/div/div/div/div[2]/form/fieldset/div[3]/div/button");
    private final By categoryButton = By.xpath("//li[@id='navCategories']//a[1]");
    private final By actionButton = By.xpath("//tr[3]//td[3]//div[1]//button[1]");
    private final By dropDownEditButton =By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[3]/td[3]/div[1]/ul[1]/li[1]/a[1]");
    private final By editCategoryName = By.id("editCategoriesName");
    private final By saveCategoryButton = By.id("editCategoriesBtn");
    private final By closeCategoryButton = By.xpath("//div[@class='modal-footer editCategoriesFooter']//button[1]");
    private final By categoryTable = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[3]/td[1]");
    @Test
    public  void  xssFetchCategoriesTest() throws IOException {
        // 1. Login
        login("admin", "admin");

        // Wait for login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 2. Go to category page and click on action button
        driver.findElement(categoryButton).click();
        driver.findElement(actionButton).click();
        driver.findElement(dropDownEditButton).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Change category name by inserting a XSS attack vector
        driver.findElement(editCategoryName).clear();
        driver.findElement(editCategoryName).sendKeys("<h1><b>Tablets</b></h1>");

        driver.findElement(saveCategoryButton).click();;
        driver.findElement(closeCategoryButton).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4 Assert that the payload was successfully mounted and take screenshots.
        String actualTitle = driver.findElement(categoryTable).getAttribute("innerHTML");
        String expectedTitle = "<h1><b>Tablets</b></h1>";

        try{
            assertEquals(expectedTitle, actualTitle);
            System.out.println("Assertion passed, payload successfully mounted");
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/fetchCategories_test_passed.png";
            File destination = new File(dest_path);
            System.out.println("Screenshot taken: " +dest_path);
            Files.move(screenshot, destination);
        }
        catch (AssertionError e){
            System.out.println("Assertion failed, check payload");
            e.printStackTrace();
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/fetchCategories_test_failed.png";
            File destination = new File(dest_path);
            System.out.println("Screenshot taken: " +dest_path);
            Files.move(screenshot, destination);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        reset();


    }
    //=== Support methods ===
    private void login(String username, String password) {
        driver.findElement(usernameTextBox).sendKeys(username);
        driver.findElement(passwordTextBox).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    @After
    public void reset() {
        driver.findElement(categoryButton).click();
        driver.findElement(actionButton).click();
        driver.findElement(dropDownEditButton).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Reset category name
        driver.findElement(editCategoryName).clear();
        driver.findElement(editCategoryName).sendKeys("Tablets");

        driver.findElement(saveCategoryButton).click();
        driver.findElement(closeCategoryButton).click();

    }
}
