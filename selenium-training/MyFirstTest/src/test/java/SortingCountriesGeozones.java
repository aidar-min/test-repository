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


public class SortingCountriesGeozones {
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
    public void myFirstTest() {
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        System.out.println("1.a - проверить, что страны расположены в алфавитном порядке");
        this.checkSortCountriesList();
        System.out.println("1.b - для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке");
        this.checkSortZonesList();

    }

    public void checkSortCountriesList() {

        int indexColumnName = this.getNumberColumn("Name");
        List<WebElement> countries = driver.findElements(By.xpath("//table[@class='dataTable']//td[" + indexColumnName + "]"));

        ArrayList<String> getCountiesList = new ArrayList();
        ArrayList<String> sortCountiesList = new ArrayList();

        for (int i = 0; i < countries.size(); i++) {
            WebElement country = countries.get(i);
            String countryName = country.getText();
            getCountiesList.add(countryName);
        }
        System.out.println("getCountiesList = " + getCountiesList);

        for (String getCountry : getCountiesList) {
            sortCountiesList.add(getCountry);
        }
        Collections.sort(sortCountiesList);
        System.out.println("sortCountiesList = " + sortCountiesList);

        if (sortCountiesList.equals(getCountiesList)) {
            System.out.println("SUCCESS: Список отсортирован в алфавитном порядке");
        } else {
            System.out.println("ERROR: Список не в алфавитном порядке");
        }
    }

    public void checkSortZonesList() {
        int indexColumnZones = this.getNumberColumn("Zones");

        List quantityZonesList = driver.findElements(By.xpath("//table[@class='dataTable']//td[" + indexColumnZones + "]"));

        for (int z = 0; z < quantityZonesList.size(); z++) {
            int index = z + 1;
            WebElement countriesZone = driver.findElement(By.xpath("//tr[" + index + "]//td[" + indexColumnZones + "]"));
            String quantityZoneCountry = countriesZone.getText();
            int n = Integer.parseInt(quantityZoneCountry);

            if (n > 0) {
                WebElement countryOfZones = driver.findElement(By.xpath("//tr[" + index + "]//a[contains(@href,'edit_country') and not (contains(@title,'Edit'))]"));
                countryOfZones.click();

                List countryZonesList = driver.findElements(By.cssSelector("h2"));
                ArrayList<String> getZonesList = new ArrayList();
                ArrayList<String> sortZonesList = new ArrayList();

                for (int j = 0; j < countryZonesList.size(); j++) {
                    WebElement countryZone = (WebElement) countryZonesList.get(j);
                    String countryZoneName = countryZone.getAttribute("value");
                    getZonesList.add(countryZoneName);
                }
                System.out.println("getZonesList  = " + getZonesList);

                for (String getZone : getZonesList) {
                    sortZonesList.add(getZone);
                }

                Collections.sort(sortZonesList);
                System.out.println("sortZonesList  = " + sortZonesList);

                if (sortZonesList.equals(getZonesList)) {
                    System.out.println("SUCCESS: Список отсортирован в алфавитном порядке");
                } else {
                    System.out.println("ERROR: Список не в алфавитном порядке");
                }
                driver.navigate().back();
            }
        }
    }


    public int getNumberColumn(String name) {
        List<WebElement> tableHeader = driver.findElements(By.xpath("//table[@class='dataTable']//th"));
        int index = 1;
        for (WebElement header : tableHeader) {
            String columnName = header.getText();
            if (columnName.equalsIgnoreCase(name)) {
                return index;
            } else {
                index++;
            }
        }
        return index;
    }


    @After
    public void stop() {
        System.out.println("Test is ended");
        driver.quit();
        driver = null;
    }
}