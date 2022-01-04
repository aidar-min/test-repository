import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class OpeningLinks {
    private static WebDriver driver;

    @Before
    public void start() {
        /*System.setProperty("webdriver.chrome.driver", "C:\\auto\\chromedriver.exe");
        driver = new ChromeDriver(); //вызов браузера
        System.out.println("Test is started");*/
        System.setProperty("webdriver.gecko.driver", "C:\\auto\\geckodriver.exe");
        driver = new FirefoxDriver(); //вызов браузера
        System.out.println("Test is started");

    }

    @Test
    public void setup() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.xpath("//*[@id=\"content\"]/div/a")).click();
        String Locator = "//i[@class='fa fa-external-link']/parent::a";
        wait.until(presenceOfElementLocated(By.xpath(Locator)));
        String oldTab = driver.getWindowHandle(); //список открытых окон- главная страница с формой
        for (int i = 0; i < driver.findElements(By.xpath(Locator)).size(); i++) {
            driver.findElements(By.xpath(Locator)).get(i).click();
            ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
            newTab.remove(oldTab);
            // переключить фокус на новую вкладку
            driver.switchTo().window(newTab.get(0));
            driver.close();
            // переключить фокус обратно на старую вкладку
            driver.switchTo().window(oldTab);
            wait.until(titleIs("Add New Country | My Store"));
        }
    }

    @After
    public void stop() {
        System.out.println("Test is ended");
        driver.quit();
        driver = null;
    }
}
