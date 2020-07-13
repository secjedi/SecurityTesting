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

public class XssFetchProduct extends BaseTest {
    private final By usernameTextBox = By.id("username");
    private final By passwordTextBox = By.id("password");
    private final By submitButton = By.xpath("/html/body/div/div/div/div/div[2]/form/fieldset/div[3]/div/button");
    private final By productButton = By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[4]/a[1]");
    private final By actionButton  = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[3]/td[8]/div[1]/button[1]");
    private final By dropDownEditButton = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[3]/td[8]/div[1]/ul[1]/li[1]/a[1]");
    private final By productInfo = By.xpath("//a[contains(text(),'Product Info')]");
    private final By editProductName = By.id("editProductName");
    private final By saveButton = By.id("editProductBtn");
    private final By closeButton = By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/form[1]/div[8]/button[1]");
    private final By productTable= By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[3]/td[2]");
    @Test
    public void xssFetchProductTest() throws IOException {
        // 1. Login
        login("admin", "admin");

        // Wait for login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 2. Go to product page and click on action button
        driver.findElement(productButton).click();
        driver.findElement(actionButton).click();
        driver.findElement(dropDownEditButton).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Change product name by inserting a XSS attack vector
        driver.findElement(productInfo).click();
        driver.findElement(editProductName).clear();
        driver.findElement(editProductName).sendKeys("<h1><b>Ipad</b></h1>");
        //<h1>Ipad</h1>

        driver.findElement(saveButton).click();
        driver.findElement(closeButton).click();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 4 Assert that the payload was successfully mounted and take screenshots.
        String actualTitle = driver.findElement(productTable).getAttribute("innerHTML");
        String expectedTitle = "<h1><b>Ipad</b></h1>";

        try{
            assertEquals(expectedTitle, actualTitle);
            System.out.println("Assertion passed, payload successfully mounted");
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/fetchProduct_test_passed.png";
            File destination = new File(dest_path);
            System.out.println("Screenshot taken: " +dest_path);
            Files.move(screenshot, destination);
        }
        catch (AssertionError | IOException e){
            System.out.println("Assertion failed, check payload");
            e.printStackTrace();
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/fetchProduct_test_failed.png";
            File destination = new File(dest_path);
            System.out.println("Screenshot taken: " +dest_path);
            Files.move(screenshot, destination);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //reset();
    }
    //=== Support methods ===
    private void login(String username, String password) {
        driver.findElement(usernameTextBox).sendKeys(username);
        driver.findElement(passwordTextBox).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    @After
    public void reset() {

        // 2. Go to product page and click on action button

        driver.findElement(productButton).click();
        driver.findElement(actionButton).click();
        driver.findElement(dropDownEditButton).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 3. Reset product name
        driver.findElement(productInfo).click();
        driver.findElement(editProductName).clear();
        driver.findElement(editProductName).sendKeys("Ipad");

        driver.findElement(saveButton).click();
        driver.findElement(closeButton).click();
    }
}
