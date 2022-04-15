package selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BaseTest {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @BeforeEach
    void startDriver() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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

    public WebElement waitForVisibilityElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public WebElement waitForClickableElement(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public List<WebElement> getElementsBy(By by) {
        return driver.findElements(by);
    }

    public WebElement getElementBy(By by) {
        return driver.findElement(by);
    }

    public List<WebElement> getElementsByCss(WebElement element, String cssSelector) {
        return element.findElements(By.cssSelector(cssSelector));
    }

    public void getAutorized() {
        driver.get("http://localhost/litecart/admin/");

        fillInputByName("username", "admin");
        fillInputByName("password", "admin");

        driver.findElement(By.name("login")).click();
        waitForVisibilityElement(getElementBy(By.cssSelector("[title = Logout]")));
    }

    public void fillInputByName(String name, String textTo) {
        WebElement element = waitForVisibilityElement(driver.findElement(By.name(name)));
        element.sendKeys(textTo);
        String actualText = element.getAttribute("value");
        Assertions.assertEquals(actualText, textTo);
    }
}
