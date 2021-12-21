import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyFirstTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start () {
        driver = new ChromeDriver();
        wait = new WebDriverWait (driver, 10);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://www.bbc.com/");
        driver.findElement(By.id("orb-search-q")).sendKeys("England");
        driver.findElement(By.id("orb-search-button")).click();
        WebElement searchLink = driver.findElement(By.cssSelector("main#main-content li:first-child"));
            searchLink.click();

    }

    @After
    public void stop () {
        driver.quit();
        driver = null;
    }
}

