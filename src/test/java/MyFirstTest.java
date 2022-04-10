import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MyFirstTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeEach
    void startDriver() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        driver = new SafariDriver();
//        System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
//        FirefoxOptions options = new FirefoxOptions();
//        options.setBinary("/Applications/Firefox Nightly.app/Contents/MacOS/firefox-bin");
//        driver = new FirefoxDriver(options);
    }

    @AfterEach
    void closeDriver() {
        driver.close();
        driver.quit();
    }

    /**
     * Задание 1. Подготовьте инфраструктуру
     */
    @Test
    public void testStart() {
//    BeforeEach откроет
//    AfterEach закроет
    }

    /**
     * Задание 3. Сделайте сценарий логина
     */
    @Test
    public void test3() {
        getAutorized();
    }

    /**
     * Задание 6. Сделайте сценарий, проходящий по всем разделам админки
     */
    @Test
    public void test6() {
        getAutorized();

//        //span[@class='name']
//        //li[@id='app-']
    }

    private void getClickDriverOnXpath(String s) {
        driver.findElement(By.xpath(s)).click();
    }

    public WebElement waitForVisibilityElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public List<WebElement> getElementsByXpath(String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    public WebElement getElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }


    public void getAutorized() {
        driver.get("http://localhost/litecart/admin/");

        fillInputByName("username", "admin");
        fillInputByName("password", "admin");

        getClickDriverOnXpath("//button[@name='login']");
        waitForVisibilityElement(getElementByXpath("//*[@title='Logout']"));
    }

    public void fillInputByName(String name, String textTo) {
        String fullXpath = String.format((".//*[@name='%s']"), name);
        WebElement element = driver.findElement(By.xpath(fullXpath));
        element.sendKeys(textTo);
        String actualText = element.getAttribute("value");
        Assertions.assertEquals(actualText, textTo);
    }
}