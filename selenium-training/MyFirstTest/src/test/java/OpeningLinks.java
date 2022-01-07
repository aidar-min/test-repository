import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

        driver.findElement(By.xpath("//tr[@class='row'][1]//a[contains(@href,'edit_country') and (contains(@title,'Edit'))]")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[contains(text(),' Edit Country')]"))));

        List links = driver.findElements(By.xpath("//i[@class='fa fa-external-link']"));
        for (int i = 0; i < links.size(); i++) {
            WebElement link = (WebElement) links.get(i);
            String originalWindows = driver.getWindowHandle();
            Set<String> existingWindowsBefore = driver.getWindowHandles();

            link.click();

            wait.until(driver -> !driver.getWindowHandles().equals(existingWindowsBefore));
            Set<String> existingWindowsAfter = driver.getWindowHandles();

            Iterator<String> iterator = existingWindowsAfter.iterator();
            while (iterator.hasNext()) {
                String newWindow = iterator.next();
                if (!originalWindows.equals(newWindow)) {
                    driver.switchTo().window(newWindow);
                    System.out.println(driver.switchTo().window(newWindow).getTitle());
                    driver.close();
                }
            }
            driver.switchTo().window(originalWindows);
        }

    }

    @After
    public void stop() {
        System.out.println("Test is ended");
        driver.quit();
        driver = null;
    }
}