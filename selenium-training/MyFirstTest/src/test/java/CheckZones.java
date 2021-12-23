import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CheckZones {
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
    // ввод логина / пароля на форме авторизации
    public <var> void myFirstTest() {
        driver.navigate().to ("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        for (int i = 2; i <= driver.findElements(By.cssSelector("tr.row")).size()+1; i++) {
            driver.findElement(By.cssSelector("tr:nth-child("+i+") > td:nth-child(3)>a")).click();

            List<WebElement> listZones = driver.findElements(By.cssSelector("td:nth-child(3)>select[name^='zones']"));
            List<String> SortZones = new ArrayList<>();
            List<String> Zones = new ArrayList<>();

            for (WebElement listZona : listZones) {

                if (listZona.isSelected() == true)
                {
                    var textZona = (var) listZona.getAttribute("text");
                    SortZones.add((String) textZona);
                    Zones.add((String) textZona);
                }

            }
            Collections.sort(SortZones);
            if (SortZones.equals(Zones)==true){
                System.out.println("sorting is correct");
            }
            driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        }

    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
