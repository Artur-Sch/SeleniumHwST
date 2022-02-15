import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyFirstTest {
    private static WebDriver driver;

    @BeforeEach
    void startDriver() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
        driver = new ChromeDriver();
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
        driver.get("http://localhost/litecart/admin/");

        fillInputByName("username", "username");
        fillInputByName("password", "password");

        getClickDriverOnXpath("//button[@name='login']");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='login']")));
    }

    /**
     * Задание 6. Сделайте сценарий, проходящий по всем разделам админки
     */
    @Test
    public void test6() {
        driver.get("http://localhost/litecart/admin/");

        fillInputByName("username", "admin");
        fillInputByName("password", "admin");

        getClickDriverOnXpath("//button[@name='login']");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void getClickDriverOnXpath(String s) {
        driver.findElement(By.xpath(s)).click();
    }

    public void fillInputByName(String name, String textTo) {
        String fullXpath = String.format((".//*[@name='%s']"), name);
        WebElement element = driver.findElement(By.xpath(fullXpath));
        element.sendKeys(textTo);
        String actualText = element.getAttribute("value");
        Assertions.assertEquals(actualText, textTo);
    }
}