package pages;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseTest;

import static org.junit.Assert.assertEquals;

public class CategoryNameFetchCategories extends BaseTest {
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
        // 2. Go to category page and click on action button
        WebElement categoryButton = driver.findElement(By.xpath("//li[@id='navCategories']//a[1]"));
        categoryButton.click();
        WebElement actionButton  = driver.findElement(By.xpath("//button[@class='btn btn-default dropdown-toggle']"));
        actionButton.click();
        WebElement dropDownEditButton = driver.findElement(By.xpath("//a[@id='editCategoriesModalBtn']"));
        dropDownEditButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Change category name by inserting a XSS attack vector
        WebElement editCatName = driver.findElement(By.id("editCategoriesName"));
        editCatName.clear();
        editCatName.sendKeys("<h1><b>SmartPhone</b></h1>");

        WebElement saveCatButton = driver.findElement(By.id("editCategoriesBtn"));
        WebElement closeCatButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/form[1]/div[3]/button[1]/i[1]"));
        saveCatButton.click();
        closeCatButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4 Check the category name in the category page
        WebElement catTable= driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/table[1]/tbody[1]/tr[1]/td[1]"));
        String catLabel = catTable.getAttribute("innerHTML");
        assertEquals("<h1><b>SmartPhone</b></h1>", catLabel);


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
        // 2. Go to category page and click on action button
        WebElement categoryButton = driver.findElement(By.xpath("//li[@id='navCategories']//a[1]"));
        categoryButton.click();
        WebElement actionButton  = driver.findElement(By.xpath("//button[@class='btn btn-default dropdown-toggle']"));
        actionButton.click();
        WebElement dropDownEditButton = driver.findElement(By.xpath("//a[@id='editCategoriesModalBtn']"));
        dropDownEditButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. Reset category name
        WebElement editCatName = driver.findElement(By.id("editCategoriesName"));
        editCatName.clear();
        editCatName.sendKeys("SmartPhone");

        WebElement saveCatButton = driver.findElement(By.id("editCategoriesBtn"));
        WebElement closeCatButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/form[1]/div[3]/button[1]/i[1]"));
        saveCatButton.click();
        closeCatButton.click();

    }
}
