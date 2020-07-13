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

public class XssOrdersClientName extends BaseTest {
    private final By usernameTextBox = By.id("username");
    private final By passwordTextBox = By.id("password");
    private final By submitButton = By.xpath("/html/body/div/div/div/div/div[2]/form/fieldset/div[3]/div/button");
    private final By dropDownMenuButton = By.xpath("//*[@id=\"navSetting\"]/a");
    private final By orderButton = By.xpath
            ("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[5]/a[1]");
    private final By orderManageButton = By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[5]/ul[1]/li[2]/a[1]");
    private final By actionButton  = By.xpath("//tr[5]//td[7]//div[1]//button[1]");
    private final By dropDownEditButton = By.xpath("/html[1]/body[1]/div[1]/div[2]/div[2]/div[2]/table[1]/tbody[1]/tr[5]/td[7]/div[1]/ul[1]/li[1]/a[1]");
    private final By editClientName = By.id("clientName");
    private final By saveOrderButton = By.id("editOrderBtn");
    private final By orderTable= By.xpath("/html[1]/body[1]/div[1]/div[2]/div[2]/div[2]/table[1]/tbody[1]/tr[5]/td[3]");
    @Test
    public  void  xssOrdersClientNameTest() throws IOException {
        // 1. Login
        login("admin", "admin");

        // Wait for login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 2. Go to order page and click on action button
        driver.findElement(orderButton).click();
        driver.findElement(orderManageButton).click();

        driver.findElement(actionButton).click();
        driver.findElement(dropDownEditButton).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Change client's username by inserting a XSS payload
        driver.findElement(editClientName).clear();
        driver.findElement(editClientName).sendKeys("<h1><b>John Snow</b></h1>");
        driver.findElement(saveOrderButton).click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4 go back to page
        driver.findElement(orderButton).click();
        driver.findElement(orderManageButton).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 5 Check the username in the order page
        String actualTitle = driver.findElement(orderTable).getAttribute("innerHTML");
        String expectedTitle = "<h1><b>John Snow</b></h1>";

        try{
            assertEquals(expectedTitle, actualTitle);
            System.out.println("Assertion passed, payload successfully mounted");
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/ordersClientName_test_passed.png";
            File destination = new File(dest_path);
            System.out.println("Screenshot taken: " +dest_path);
            Files.move(screenshot, destination);
        }
        catch (AssertionError e){
            System.out.println("Assertion failed, check payload");
            e.printStackTrace();
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/ordersClientContact_test_failed.png";
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
        // 2. Go to order page and click on action button
        driver.findElement(orderButton).click();
        driver.findElement(orderManageButton).click();

        driver.findElement(actionButton).click();
        driver.findElement(dropDownEditButton).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Reset client's username
        driver.findElement(editClientName).clear();
        driver.findElement(editClientName).sendKeys("John Snow");
        driver.findElement(saveOrderButton).click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4 go back to page

        driver.findElement(orderButton).click();
        driver.findElement(orderManageButton).click();
    }
}
