package pages;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseTest;

import static org.junit.Assert.assertEquals;

public class EditOrdersClientName extends BaseTest {
    @Test
    public  void  xssTest(){
        // 1. Login
        login("admin", "admin");

        // Wait for login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 2. Go to order page and click on action button
        WebElement dropDownMenuButton = driver.findElement(By.id("navOrder"));
        dropDownMenuButton.click();
        WebElement orderButton = driver.findElement(By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[5]/ul[1]/li[2]/a[1]"));
        orderButton.click();

        WebElement actionButton  = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[2]/div[2]/table[1]/tbody[1]/tr[1]/td[7]/div[1]/button[1]"));
        actionButton.click();
        WebElement dropDownEditButton = driver.findElement(By.id("editOrderModalBtn"));
        dropDownEditButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Change client's username by inserting a XSS attack vector
        WebElement editClientName = driver.findElement(By.id("clientName"));
        editClientName.clear();
        editClientName.sendKeys("<h1><b>Smith</b></h1>");


        WebElement saveOrderButton = driver.findElement(By.id("editOrderBtn"));
        saveOrderButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4 go back to page
        WebElement dropDownMenuBtn = driver.findElement(By.id("navOrder"));
        dropDownMenuBtn.click();
        WebElement orderBtn = driver.findElement(By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[5]/ul[1]/li[2]/a[1]"));
        orderBtn.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 5 Check the username in the order page
        WebElement orderTable= driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[2]/div[2]/table[1]/tbody[1]/tr[1]/td[3]"));
        String orderLabel = orderTable.getAttribute("innerHTML");
        assertEquals("<h1><b>Smith</b></h1>", orderLabel);

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
        // 2. Go to order page and click on action button
        WebElement dropDownMenuButton = driver.findElement(By.id("navOrder"));
        dropDownMenuButton.click();
        WebElement orderButton = driver.findElement(By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[5]/ul[1]/li[2]/a[1]"));
        orderButton.click();

        WebElement actionButton  = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[2]/div[2]/table[1]/tbody[1]/tr[1]/td[7]/div[1]/button[1]"));
        actionButton.click();
        WebElement dropDownEditButton = driver.findElement(By.id("editOrderModalBtn"));
        dropDownEditButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Reset client's username
        WebElement editClientName = driver.findElement(By.id("clientName"));
        editClientName.clear();
        editClientName.sendKeys("Smith");

        WebElement saveOrderButton = driver.findElement(By.id("editOrderBtn"));
        saveOrderButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4 go back to page

        WebElement dropDownMenuBtn = driver.findElement(By.id("navOrder"));
        dropDownMenuBtn.click();
        WebElement orderBtn = driver.findElement(By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[2]/ul[1]/li[5]/ul[1]/li[2]/a[1]"));
        orderBtn.click();
    }
}
