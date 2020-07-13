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

public class XssFetchUser extends BaseTest {
    private final By usernameTextBox = By.id("username");
    private final By passwordTextBox = By.id("password");
    private final By submitButton = By.xpath("/html/body/div/div/div/div/div[2]/form/fieldset/div[3]/div/button");
    private final By dropDownMenuButton = By.xpath("/html/body/nav/div/div[2]/ul/li[7]/a");
//    private final By dropDownMenuButton = By.xpath("//*[@id=\"navSetting\"]/a");
    private final By addUserButton = By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[7]/ul[1]/li[1]/a[1]");
    private final By actionButton  = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[2]/td[2]/div[1]/button[1]");
    private final By dropDownEditButton = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[2]/td[2]/div[1]/ul[1]/li[1]/a[1]");
    private final By editUserName = By.id("edituserName");
    private final By passwordField = By.id("editPassword");
    private final By saveChangesButton = By.id("editProductBtn");
    private final By closeButton = By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[5]/button[1]");
    private final By usernameTable = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[2]/td[1]");
    @Test
    public  void  testFetchUserTest() throws IOException {
        // 1. Login
        login("admin", "admin");
        // Wait for login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. Go to add user page
        driver.findElement(dropDownMenuButton).click();
        driver.findElement(addUserButton).click();
        driver.findElement(actionButton).click();
        driver.findElement(dropDownEditButton).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 3. Change username inserting a XSS attack vector
        driver.findElement(editUserName).clear();
        driver.findElement(editUserName).sendKeys("<h1><b>Sansa Stark</b></h1>");
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys("password");

        driver.findElement(saveChangesButton).click();
        driver.findElement(closeButton).click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 4 Assert that the payload was successfully mounted and take screenshots.
        String actualTitle = driver.findElement(usernameTable).getAttribute("innerHTML");
        String expectedTitle = "<h1><b>Sansa Stark</b></h1>";

        try{
            assertEquals(expectedTitle, actualTitle);
            System.out.println("Assertion passed, payload successfully mounted");
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/fetchUser_test_passed.png";
            File destination = new File(dest_path);
            System.out.println("Screenshot taken: " +dest_path);
            Files.move(screenshot, destination);
        }
        catch (AssertionError e){
            System.out.println("Assertion failed, check payload");
            e.printStackTrace();
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/fetchUser_test_failed.png";
            File destination = new File(dest_path);
            System.out.println("Screenshot taken: " +dest_path);
            Files.move(screenshot, destination);
        }
    }


    // === Support methods ===
    private void login(String username, String password) {
        driver.findElement(usernameTextBox).sendKeys(username);
        driver.findElement(passwordTextBox).sendKeys(password);
        driver.findElement(submitButton).click();
    }


    // === Method for reset ====

    @After
    public void reset() {

        // 2. Click on action button on the same page

        driver.findElement(actionButton).click();
        driver.findElement(dropDownEditButton).click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Reset username
        driver.findElement(editUserName).clear();
        driver.findElement(editUserName).sendKeys("Sansa Stark");
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys("admin");

        driver.findElement(saveChangesButton).click();
        driver.findElement(closeButton).click();
    }

}
