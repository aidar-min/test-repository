import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Menu {

    private static WebDriver driver;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\auto\\chromedriver.exe");
        driver = new ChromeDriver(); //вызов браузера
        System.out.println("Test is started");

    }

    @Test
    public void setup() {
        driver.get("http://localhost/litecart/admin/login.php");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //Loging
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        String actualUrl = "http://localhost/litecart/admin/";
        String expectedUrl = driver.getCurrentUrl();
        if (actualUrl.equalsIgnoreCase(expectedUrl)) {
            System.out.println("Successful login");
        } else {
            System.out.println("Test failed");
        }
        List<WebElement> elementMenuHaupt = driver.findElements(By.xpath("//*[@id=\"app-\"]/a"));
        int numberOfMenuElements = elementMenuHaupt.size();

        for (int i = 0; i < numberOfMenuElements; i++) {
            elementMenuHaupt = driver.findElements(By.xpath("//*[@id=\"app-\"]/a"));
            elementMenuHaupt.get(i).click();
            if (driver.findElement(By.xpath("//*[@id=\"content\"]/h1")).isDisplayed()==true) {
                System.out.println("pass");
            }
            else {
                System.out.println("false");
            }

            for (int j = 0; j < (driver.findElements(By.xpath("//*[@class=\"selected\"]//a")).size()); j++) {
                driver.findElements(By.xpath("//*[@class=\"selected\"]//a")).get(j).click();
                if (driver.findElement(By.xpath("//*[@id=\"content\"]/h1")).isDisplayed()==true) {
                    System.out.println("pass");
                }
                else {
                    System.out.println("false");
                }
            }

        }
    }

    @After
    public void stop() {
        System.out.println("Test is ended");
        driver.quit();
        driver = null;
    }
}