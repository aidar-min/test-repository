import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckBrowserLogs {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        //ждем 20 секунд, чтобы найти элемент
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    // 1) зайти в админку
    public void myFirstTest() {
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        // 2) открыть каталог, категорию, которая содержит товары (страница http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1)
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");

        // 3) последовательно открывать страницы товаров и проверять, не появляются ли в логе браузера сообщения (любого уровня)
        List<WebElement> products = driver.findElements(By.xpath("//table[@class='dataTable']//tr[@class='row']//a[contains(@href,'product_id') and not (contains(@title,'Edit'))]"));
        ArrayList<String> productsList = new ArrayList();

        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
            String productTitle = product.getText();
            productsList.add(productTitle);
        }

        for (String productName : productsList) {
            WebElement productLink = driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]"));
            productLink.click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1"))));
            driver.manage().logs().get("browser").forEach(l -> {
                System.out.println(l);
                Assert.assertTrue(l.equals(""));
            });
            driver.navigate().back();
        }
    }

    @After
    public void stop() {
        System.out.println("Test is ended");
        driver.quit();
        driver = null;
    }
}
