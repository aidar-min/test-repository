import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/*      1) регистрация новой учётной записи с уникальным адресом
        2) выход(logout)
        3) повторный вход в только что созданную учётную запись,и ещё раз выход.
        4) Страна - United States,штат произвольный.Формат индекса-пять цифр.*/

public class NewCustomers {
    private static WebDriver driver;

    private static final String firstName = "Test";
    private static final String lastName = "Lasttest";
    public static final String address = "Test Street 10";
    public static final String postcode = "12345";
    public static final String city = "New York";
    public static final String country = "United States";
    public static final String phone = "1234567890";
    public static final String email = "test.lasttest" + System.currentTimeMillis() + "@gmail.com";
    public static final String password = "password";


    @Before
    public void start() {
        System.setProperty("webdriver.gecko.driver", "C:\\auto\\geckodriver.exe");
        driver = new FirefoxDriver(); //вызов браузера
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("Test is started");

    }

    @Test
    public void Task11Test() throws InterruptedException {
        driver.get("https://litecart.stqa.ru/en/");
        driver.findElement(By.cssSelector("#box-account-login tr:nth-child(5) a")).click();
        WebElement createAccount = driver.findElement(By.cssSelector("#create-account"));
//Entering values
        setField(createAccount, "firstname", firstName);
        setField(createAccount, "lastname", lastName);
        setField(createAccount, "address1", address);
        setField(createAccount, "postcode", postcode);
        setField(createAccount, "city", city);
//Country entry
        driver.findElement(By.cssSelector("span.select2-selection__arrow")).click();
        driver.findElement(By.xpath("//li[text()='" + country + "']")).click();
//email
        driver.findElement(By.cssSelector("input[type=email]")).sendKeys(email);

//Entering values
        //setField(createAccount, "email", email);
        setField(createAccount, "phone", phone);
        setField(createAccount, "password", password);
        setField(createAccount, "confirmed_password", password);

//Create an account
        createAccount.findElement(By.cssSelector("button[name=create_account]")).click();
        /*driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type=submit]")).click();*/

//logout
        logout();
//account login
        WebElement login = driver.findElement(By.cssSelector("#box-account-login"));
        setField(login, "email", email);
        setField(login, "password", password);
        login.findElement(By.cssSelector("button[name=login]")).click();
//logout
        logout();
    }

    private void logout() {
        driver.findElement(By.cssSelector("#box-account li:nth-child(4) a")).click();
    }

    private void setField(WebElement createAccount, String field, String data) {
        createAccount.findElement(By.cssSelector("input[name=" + field + "]")).sendKeys(data);
    }

    @After
    public void stop() {
        System.out.println("Test is ended");
        driver.quit();
        driver = null;
    }

}
