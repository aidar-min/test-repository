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

import static org.junit.Assert.assertEquals;


public class SortingCountriesGeozones<string> {
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
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        // а) проверяет, что страны расположенных в алфавитном порядке
        List<WebElement> listCountries = driver.findElements(By.cssSelector("tr.row td a:not([title=Edit])"));
        List<String> Countries = new ArrayList<>();
        List<String> textContents = new ArrayList<>();

        for (WebElement listCountry : listCountries) {
            String textContent = (String) listCountry.getAttribute("textContent");
            Countries.add((String) textContent);
            textContents.add((String) textContent);
        }
        Collections.sort(Countries);

        if (Countries.equals(textContents)==true) {
            System.out.println("sorting of countries is correct");
        }

        // б) для тех стран, у которых количество зон отлично от нуля -- открывает страницу этой страны и там проверяет, что геозоны расположены в алфавитном порядке

        List<WebElement> countryRow = driver.findElements(By.cssSelector("tr.row"));
        for (int i = 2; i <= countryRow.size(); i++) {
            if (!driver.findElement(By.cssSelector("tr:nth-child("+i+") > td:nth-child(6)")).getText().equals("0")) {

                driver.findElement(By.cssSelector(" tr:nth-child("+i+") > td:nth-child(5)> a ")).click();

                List<WebElement> listZones = driver.findElements(By.cssSelector("h2"));
                List<String> namesZones = new ArrayList<>();
                List<String> textZones = new ArrayList<>();
                for (WebElement listZona : listZones) {
                    String textZona = (String) listZona.getAttribute("value");
                    namesZones.add((String) textZona);
                    textZones.add((String) textZona);
                }
                Collections.sort(namesZones);
                if (namesZones.equals(textZones)==true){
                    System.out.println("sorting is correct");

                }
                driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
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