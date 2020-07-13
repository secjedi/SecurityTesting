package pages;

import com.google.common.io.Files;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.*;
import utils.BaseTest;
import java.io.File;
import java.io.IOException;


import static org.junit.Assert.assertEquals;

public class XssDashboard extends BaseTest {
    private final By usernameTextBox = By.id("username");
    private final By passwordTextBox = By.id("password");
    private final By submitButton = By.xpath("/html/body/div/div/div/div/div[2]/form/fieldset/div[3]/div/button");
    private final By dropDownMenuButton = By.xpath("//*[@id=\"navSetting\"]/a");
    private final By logOutButton = By.xpath("/html/body/nav/div/div[2]/ul/li[7]/ul/li[3]/a");
    private final By settingButton = By.xpath("/html/body/nav/div/div[2]/ul/li[7]/ul/li[2]/a");
    private final By dropDownBtn = By.xpath("/html/body/nav/div/div[2]/ul/li[7]/a");
    private final By settingBtn = By.cssSelector("#topNavSetting > a:nth-child(1)");
    private final By usernameField = By.id("username");
    private final By changeUsernameButton = By.id("changeUsernameBtn");
    private final By dashboardUsernameSpan = By.xpath("/html/body/div/div[1]/div/div/div/a/span");

    @Test
    public  void  testDashboardXss() throws IOException {
        // 1. Login
        login("admin", "admin");
        // Wait for login
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. Go to setting page
        driver.findElement(dropDownBtn).click();
        driver.findElement(settingBtn).click();

        // Sleep for 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Change admin's username by inserting XSS payload
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys("<h1><b>admin</b></h1>");
        driver.findElement(changeUsernameButton).click();

        // Sleep for 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logout();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        login("<h1><b>admin</b></h1>", "admin");

        // Wait for 10 seconds to notice the effect of payload
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4 Assert that the payload was successfully mounted and take screenshots.
        String actualTitle = driver.findElement(dashboardUsernameSpan).getAttribute("innerHTML");
        String expectedTitle = "<h1><b>admin</b></h1>";

        try{
            assertEquals(expectedTitle, actualTitle);
            System.out.println("Assertion passed, payload successfully mounted");
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/dashboard_test_passed.png";
            File destination = new File(dest_path);
            System.out.println("Screenshot taken: " +dest_path);
            Files.move(screenshot, destination);
        }
        catch (AssertionError e){
            System.out.println("Assertion failed, check payload");
            e.printStackTrace();
            TakesScreenshot camera = (TakesScreenshot)driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String dest_path = ".idea/resources/screenshots/dashboard_test_failed.png";
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

    private void logout() {
        driver.findElement(dropDownMenuButton).click();
        driver.findElement(logOutButton).click();
    }

    // === Method for reset ====

    @After
    public void reset() {

        // 0. We are still in the dashboard

        // 1. Go to the setting page
        driver.findElement(dropDownMenuButton).click();
        driver.findElement(settingButton).click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. Set the username back to admin
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys("admin");
        driver.findElement(changeUsernameButton).click();
    }

}
