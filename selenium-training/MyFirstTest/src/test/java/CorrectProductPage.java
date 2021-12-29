import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;

public class CorrectProductPage {

    private static WebDriver driver;
    private WebDriverWait wait;

    @Test
    public void testChrome() {
        System.setProperty("webdriver.chrome.driver", "C:\\auto\\chromedriver.exe");
        driver = new ChromeDriver(); //вызов браузера
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        check();
    }

    @Test
    public void testFirefox() {
        System.setProperty("webdriver.gecko.driver", "C:\\auto\\geckodriver.exe");
        driver = new FirefoxDriver(); //вызов браузера
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        check();
    }

    public <var> void check() {
        driver.get("http://localhost/litecart/en/");

        //characteristics on the main page
        WebElement CampaignsOne = driver.findElement(By.xpath("//*[@id=\"box-campaigns\"]/div/ul/li[1]"));
        String NameCampaign = CampaignsOne.findElement(By.xpath(".//div[@class=\"name\"]")).getAttribute("textContent"); //name-Yellow Duck - Main Page
        String OldPriceMain = CampaignsOne.findElement(By.xpath("//s")).getAttribute("textContent"); //old price - Main Page
        String NewPriceMain = CampaignsOne.findElement(By.xpath("//strong[@class=\"campaign-price\"]")).getAttribute("textContent"); //new price - Main Page
        String catalogPriceCrossedOut = CampaignsOne.findElement(By.xpath(".//div[@class='price-wrapper']//s")).getCssValue("text-decoration");
        String VarOldPriceMainColor = CampaignsOne.findElement(By.xpath("//s")).getCssValue("color"); // color- #666 OR #777
        String VarNewPriceMainColor = CampaignsOne.findElement(By.xpath("//strong[@class=\"campaign-price\"]")).getCssValue("color"); //color- #c00
        String VarOldMainFontSize = CampaignsOne.findElement(By.xpath("//s")).getCssValue("font-size");
        String VarNewMainFontSize = CampaignsOne.findElement(By.xpath("//strong[@class=\"campaign-price\"]")).getCssValue("font-size");
        String catalogPriceColor = CampaignsOne.findElement(By.xpath(".//div[@class='price-wrapper']//s")).getCssValue("color");
        String[] arrayCatalogPriceColor = (catalogPriceColor.replaceAll("\\D+", " ").trim()).split(" ");
        int[] catalogPriceColors = new int[arrayCatalogPriceColor.length];

        CampaignsOne.click(); //product page

        //characteristics on the product page
        WebElement ProductPage = driver.findElement(By.xpath("//*[@id=\"box-product\"]"));
        String NameProductPage = ProductPage.findElement(By.xpath("//h1")).getAttribute("textContent"); //name-Yellow Duck - Product Page
        String OldPriceProductPage = ProductPage.findElement(By.xpath("//s")).getAttribute("textContent"); //old price - Product Page
        String NewPriceProductPage = ProductPage.findElement(By.xpath("//strong[@class=\"campaign-price\"]")).getAttribute("textContent"); //new price - Product Page
        String VarOldPriceProductPageColor = ProductPage.findElement(By.xpath("//s")).getCssValue("color"); // color- #666 OR #777- Product Page
        String VarNewPriceProductPageColor = ProductPage.findElement(By.xpath("//strong[@class=\"campaign-price\"]")).getCssValue("color"); //color- #c00 - Product Page
        String VarOldProductPageFontSize = ProductPage.findElement(By.xpath("//s")).getCssValue("font-size");
        String VarNewProductPageFontSize = ProductPage.findElement(By.xpath("//strong[@class=\"campaign-price\"]")).getCssValue("font-size");
        String catalogSaleWeight = ProductPage.findElement(By.xpath(".//div[@class='price-wrapper']//strong")).getTagName();


        // а-name
        assertEquals(NameCampaign, NameProductPage);
        // б-old and new price
        assertEquals(OldPriceMain, OldPriceProductPage);
        assertEquals(NewPriceMain, NewPriceProductPage);
        // в-old-gray and strikethrough "s"
        checkColorGrey(VarOldPriceMainColor, driver);
        checkColorGrey(VarOldPriceProductPageColor, driver);
        Assert.assertTrue(catalogPriceCrossedOut.contains("line-through"));
        Assert.assertTrue(catalogPriceColors[0] == catalogPriceColors[1] && catalogPriceColors[1] == catalogPriceColors[2]);
        String cardPriceCrossedOut = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//s")).getCssValue("text-decoration");
        Assert.assertTrue(cardPriceCrossedOut.contains("line-through"));
        // г-new oily- "strong" and red
        checkColorRed(VarNewPriceMainColor, driver);
        checkColorRed(VarNewPriceProductPageColor, driver);
        Assert.assertTrue(catalogSaleWeight.equalsIgnoreCase("strong"));
        String cardSaleWeight = driver.findElement(By.xpath("//div[@class='information']//div[@class='price-wrapper']//strong")).getTagName();
        Assert.assertTrue(cardSaleWeight.equalsIgnoreCase("strong"));
        // д-new larger than normal
        checkFontSize(VarOldMainFontSize, VarNewMainFontSize);
        checkFontSize(VarOldProductPageFontSize, VarNewProductPageFontSize);

    }

    public static boolean checkColorGrey(String colorOldRGB, WebDriver driver) {
        if (driver instanceof ChromeDriver) {
            colorOldRGB = colorOldRGB.substring(5, colorOldRGB.length() - 1);
            System.out.println(colorOldRGB);
            String colorR = colorOldRGB.split(", ")[0];
            String colorG = colorOldRGB.split(", ")[1];
            String colorB = colorOldRGB.split(", ")[2];
            return colorR.equals(colorG) && colorG.equals(colorB);
        } else if (driver instanceof FirefoxDriver) {
            colorOldRGB = colorOldRGB.substring(4, colorOldRGB.length() - 1);
            System.out.println(colorOldRGB);
            String colorR = colorOldRGB.split(", ")[0];
            String colorG = colorOldRGB.split(", ")[1];
            String colorB = colorOldRGB.split(", ")[2];
            return colorR.equals(colorG) && colorG.equals(colorB);
        } else
            throw new RuntimeException("driver class not found");

    }

    public static boolean checkColorRed(String colorNewRGB, WebDriver driver) {
        if (driver instanceof ChromeDriver) {
            colorNewRGB = colorNewRGB.substring(5, colorNewRGB.length() - 1);
            System.out.println(colorNewRGB);
            String colorG = colorNewRGB.split(", ")[1];
            String colorB = colorNewRGB.split(", ")[2];
            return colorG.equals("0") && colorB.equals("0");
        } else if (driver instanceof FirefoxDriver) {
            colorNewRGB = colorNewRGB.substring(4, colorNewRGB.length() - 1);
            System.out.println(colorNewRGB);
            String colorG = colorNewRGB.split(", ")[1];
            String colorB = colorNewRGB.split(", ")[2];
            return colorG.equals("0") && colorB.equals("0");
        } else
            throw new RuntimeException("driver class not found");

    }
    private static boolean checkFontSize(String oldPriceFontSize, String newPriceFontSize) {
        Double oldFontSize = Double.parseDouble(oldPriceFontSize.replace("px", ""));
        Double newFontSize = Double.parseDouble(newPriceFontSize.replace("px", ""));
        return newFontSize > oldFontSize;
    }

    @After
    public void stop() {
        System.out.println("Test is ended");
        driver.quit();
        driver = null;
    }

}

