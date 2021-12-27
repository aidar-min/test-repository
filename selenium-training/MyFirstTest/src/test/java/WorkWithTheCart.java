import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class WorkWithTheCart {
    private static WebDriver driver;
    public byte DEF_TIME = 20;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "C:\\auto\\chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("Test is started");


    }

    @Test
    public void Task13Test() {
        driver.get("https://litecart.stqa.ru/en/");
        WebDriverWait wait = new WebDriverWait(driver, DEF_TIME);
        for (int i = 0; i < 3; i++) {
            List<WebElement> ProdList = driver.findElements(By.cssSelector("#box-most-popular li"));
            ProdList.get(i).click();
            if (isElementsPresent(driver, By.cssSelector("select[name='options[Size]']"))) {
                Select select = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
                select.selectByVisibleText("Small");
            }
            driver.findElement(By.cssSelector(".quantity button[name=add_cart_product]")).click();
            int quantity = Integer.parseInt(driver.findElement(By.cssSelector("#cart .quantity")).getText());
            wait.until(ExpectedConditions.textToBe(By.cssSelector("#cart .quantity"), String.valueOf(quantity + 1)));
            driver.navigate().back();
        }
        driver.findElement(By.cssSelector("#cart > a.link")).click();

        int numberOfLines = driver.findElements(By.xpath("//*[@id=\"order_confirmation-wrapper\"]/table//td[@class= 'item']")).size();
        By locator = By.xpath("//*[@id=\"order_confirmation-wrapper\"]/table//tr[2]");
        for (int i = 0; i < numberOfLines; i++) {
            WebElement table = driver.findElement(locator);
            if (i != numberOfLines - 1) {
                driver.findElement(By.xpath("//*[@id=\"box-checkout-cart\"]/ul/li[1]")).click();
            }
            wait.until(visibilityOf(driver.findElement(By.cssSelector("button[name=remove_cart_item]")))).click();
            wait.until(stalenessOf(table));
            if (i == numberOfLines - 1) {
                isElementsNotPresent(driver, By.cssSelector(".dataTable"));
                System.out.println("Cart is empty");
            } else {
                wait.until(visibilityOfElementLocated(locator));
            }
        }
    }

    public boolean isElementsPresent(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            return driver.findElements(locator).size() > 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(DEF_TIME, TimeUnit.SECONDS);
        }
    }

    public boolean isElementsNotPresent(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            return driver.findElements(locator).size() == 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(DEF_TIME, TimeUnit.SECONDS);
        }
    }

    @After
    public void stop() {
        System.out.println("Test is ended");
        driver.quit();
        driver = null;
    }

}