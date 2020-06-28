package utils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected static  WebDriver driver;
    protected static String URL = "http://localhost/inventory-management-system/";

    @Before
    public  void setUp(){
        System.setProperty("webdriver.chrome.driver", ".idea/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get(URL);
        //driver.manage().window().maximize();

    }
    @After
    public void tearDown(){
        driver.quit();
        driver = null;
    }
}
