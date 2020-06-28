package pages;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseTest;

import static org.junit.Assert.assertEquals;

public class FetchUserUsername extends BaseTest {
    @Test
    public  void  testXssTest(){
        // 1. Login
        login("admin", "admin");
        // Wait for login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. Go to add user page
        WebElement dropDownMenuButton = driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[7]/a"));
        dropDownMenuButton.click();
        WebElement addUserButton = driver.findElement(By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[7]/ul[1]/li[1]/a[1]"));
        addUserButton.click();
        WebElement actionButton  = driver.findElement(By.xpath("//tr[1]//td[2]//div[1]//button[1]"));
        actionButton.click();
        WebElement dropDownEditButton = driver.findElement(By.id("editUserModalBtn"));
        dropDownEditButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 3. Change username inserting a XSS attack vector
        WebElement editUserName = driver.findElement(By.id("edituserName"));
        editUserName.clear();
        editUserName.sendKeys("<h1><b>Admin</b></h1>");
        WebElement passwordField = driver.findElement(By.id("editPassword"));
        passwordField.clear();
        passwordField.sendKeys("admin");

        WebElement saveOrderButton = driver.findElement(By.id("editProductBtn"));
        WebElement closeButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[5]/button[1]"));

        saveOrderButton.click();
        closeButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 4 Check the username in the dashboard
        WebElement usernameTable = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[1]/td[1]"));
        String innerHtml = usernameTable.getAttribute("innerHTML");

        assertEquals("<h1><b>Admin</b></h1>", innerHtml);
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


    // === Method for reset ====

    @After
    public void reset() {

        // 2. Click on action button on the same page

        WebElement actionButton = driver.findElement(By.xpath("//tr[1]//td[2]//div[1]//button[1]"));
        actionButton.click();
        WebElement dropDownEditButton = driver.findElement(By.id("editUserModalBtn"));
        dropDownEditButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Reset username
        WebElement editUserName = driver.findElement(By.id("edituserName"));
        editUserName.clear();
        editUserName.sendKeys("Admin");
        WebElement passwordField = driver.findElement(By.id("editPassword"));
        passwordField.clear();
        passwordField.sendKeys("admin");

        WebElement saveOrderButton = driver.findElement(By.id("editProductBtn"));
        WebElement closeButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/form[1]/div[5]/button[1]"));

        saveOrderButton.click();
        closeButton.click();
    }

}
