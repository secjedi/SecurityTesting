package pages;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseTest;

import static org.junit.Assert.assertEquals;

public class EditProductUsername extends BaseTest {
    @Test
    public  void  xssTest() throws InterruptedException {
        // 1. Login
        login("admin", "admin");

        // Wait for login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 2. Go to product page and click on action button
        WebElement productButton = driver.findElement(By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[4]/a[1]"));
        productButton.click();
        WebElement actionButton  = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[1]/td[8]/div[1]/button[1]"));
        actionButton.click();
        WebElement dropDownEditButton = driver.findElement(By.id("editProductModalBtn"));
        dropDownEditButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Change product name by inserting a XSS attack vector
        WebElement productInfo = driver.findElement(By.xpath("//a[contains(text(),'Product Info')]"));
        productInfo.click();
        WebElement editProductName = driver.findElement(By.id("editProductName"));
        editProductName.clear();
        editProductName.sendKeys("<h1>Samsung</h1>");

        WebElement saveButton = driver.findElement(By.id("editProductBtn"));
        WebElement closeButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/form[1]/div[8]/button[1]"));
        saveButton.click();
        closeButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 4 Check the product name in the product page
        WebElement prodTable= driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[1]/td[2]"));
        String innerHtml = prodTable.getAttribute("innerHTML");
        assertEquals("<h1>Samsung</h1>", innerHtml);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reset();
    }
    //=== Support methods ===
    private void login(String username, String password) {
        WebElement usernameTextBox = driver.findElement(By.id("username"));
        WebElement passwordTextBox = driver.findElement(By.id("password"));
        usernameTextBox.sendKeys(username);
        passwordTextBox.sendKeys(password);
        WebElement submitButton = driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/form/fieldset/div[3]/div/button"));
        submitButton.click();
    }

    @After
    public void reset() {

        // 2. Go to product page and click on action button

        WebElement productButton = driver.findElement(By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[4]/a[1]"));
        productButton.click();
        WebElement actionButton  = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[1]/td[8]/div[1]/button[1]"));
        actionButton.click();
        WebElement dropDownEditButton = driver.findElement(By.id("editProductModalBtn"));
        dropDownEditButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 3. Reset product name
        WebElement productInfo = driver.findElement(By.xpath("//a[contains(text(),'Product Info')]"));
        productInfo.click();
        WebElement editProductName = driver.findElement(By.id("editProductName"));
        editProductName.clear();
        editProductName.sendKeys("Samsung");

        WebElement saveButton = driver.findElement(By.id("editProductBtn"));
        WebElement closeButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/form[1]/div[8]/button[1]"));
        saveButton.click();
        closeButton.click();
    }
}
