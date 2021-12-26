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
    public <string> void myFirstTest() {
        driver.navigate().to("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> geoZonesList = driver.findElements(By.xpath("//tr[@class='row']//a[contains(@href,'geo_zone_id')and not (contains(@title,'Edit'))]"));

        for (int k = 0; k < geoZonesList.size(); k++) {
            int geoIndex = k + 1;
            WebElement geoZone = driver.findElement(By.xpath("//tr[@class='row'][" + geoIndex + "]//a[contains(@href,'geo_zone_id')and not (contains(@title,'Edit'))]"));
            geoZone.click();

            List<WebElement> geoZonesZonesList = driver.findElements(By.xpath("//table[@id='table-zones']//select[contains(@name,'zone_code')]//option[@selected='selected']"));
            ArrayList<String> getGeoZonesList = new ArrayList();
            ArrayList<String> sortGeoZonesList = new ArrayList();

            for (WebElement geoZonesZones : geoZonesZonesList) {
                String geoZonesName = geoZonesZones.getText();
                getGeoZonesList.add(geoZonesName);
            }
            System.out.println("getGeoZonesList = " + getGeoZonesList);
            for (String getGeoZone : getGeoZonesList) {
                sortGeoZonesList.add(getGeoZone);
            }

            Collections.sort(sortGeoZonesList);
            System.out.println("sortGeoZonesList = " + sortGeoZonesList);

            if (sortGeoZonesList.equals(getGeoZonesList)) {
                System.out.println("SUCCESS: Список отсортирован в алфавитном порядке");
            } else {
                System.out.println("ERROR: Список не в алфавитном порядке");
            }
            driver.navigate().back();
        }

    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
