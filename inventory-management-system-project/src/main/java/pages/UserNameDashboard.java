package pages;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseTest;

import static org.junit.Assert.assertEquals;

public class UserNameDashboard extends BaseTest {
    @Test
    public  void  testXssTest(){
        // 1. Login
        login("admin", "admin");
        // Wait for login
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. Go to setting page
        // WebElement settingButton = driver.findElement(By.cssSelector("#topNavSetting > a:nth-child(1)")); ElementNotVisibleException
        // settingButton.click();

        WebElement dropDownMenuButton = driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[7]/a"));
        dropDownMenuButton.click();
        WebElement settingButton = driver.findElement(By.cssSelector("#topNavSetting > a:nth-child(1)"));
        settingButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Change admin's username inserting a XSS attack vector
        WebElement usernameTextBox = driver.findElement(By.id("username"));
        usernameTextBox.clear();
        usernameTextBox.sendKeys("<h1>admin</h1>");
        WebElement changeUsernameButton = driver.findElement(By.id("changeUsernameBtn"));
        changeUsernameButton.click();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logout();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        login("<h1>admin</h1>", "admin");


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 5 Check the username in the dashboard
        WebElement dashboardUsernameSpan = driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div/a/span"));
        String innerHtml = dashboardUsernameSpan.getAttribute("innerHTML");

        assertEquals("<h1>admin</h1>", innerHtml);
    }

    // === Support methods ===
    private void login(String username, String password) {
        WebElement usernameTextBox = driver.findElement(By.id("username"));
        WebElement passwordTextBox = driver.findElement(By.id("password"));
        usernameTextBox.sendKeys(username);
        passwordTextBox.sendKeys(password);
        WebElement submitButton = driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/form/fieldset/div[3]/div/button"));
        submitButton.click();
    }

    private void logout() {
        WebElement dropDownMenuButton = driver.findElement(By.xpath("//*[@id=\"navSetting\"]/a"));
        dropDownMenuButton.click();
        WebElement logOutButton = driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[7]/ul/li[3]/a"));
        logOutButton.click();
    }

    // === Method for reset ====

    @After
    public void reset() {

        // 0. We are still in the dashboard

        // 1. Go to the setting page
        WebElement dropDownMenuButton = driver.findElement(By.xpath("//*[@id=\"navSetting\"]/a"));
        dropDownMenuButton.click();

        WebElement settingButton = driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[7]/ul/li[2]/a"));
        settingButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. Set the username back to admin
        WebElement usernameTextBox = driver.findElement(By.id("username"));
        usernameTextBox.clear();
        usernameTextBox.sendKeys("admin");
        WebElement changeUsernameButton = driver.findElement(By.id("changeUsernameBtn"));
        changeUsernameButton.click();
    }

}
