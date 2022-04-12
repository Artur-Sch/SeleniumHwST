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

public class SeleniumTests {
    private static WebDriver driver;
    private static WebDriverWait wait;

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
        List<WebElement> menus = getElementsBy(By.xpath("//li[@id='app-']"));
        for (int i = 1; i <= menus.size(); i++) {
            waitForVisibilityElement(getElementBy(By.cssSelector(String.format("ul#box-apps-menu > li#app-:nth-child(%d)", i)))).click();
            WebElement selectedElement = getElementBy(By.cssSelector("#box-apps-menu #app-.selected"));
            List<WebElement> menuElements = selectedElement.findElements(By.tagName("li"));
            for (int j = 2; j <= menuElements.size(); j++) {
                selectedElement = getElementBy(By.cssSelector("#box-apps-menu #app-.selected"));
                selectedElement.findElement(By.cssSelector(String.format("li:nth-child(%d)", j))).click();

            }
        }
    }

    /**
     * Задание 7. Сделайте сценарий, проверяющий наличие стикеров у товаров
     */
    @Test
    public void test7() {
        driver.navigate().to("http://localhost/litecart");
        WebElement mainPage = getElementBy(By.cssSelector("#main > div.middle > div.content"));

        List<WebElement> listDucks = getElementsByCss(mainPage, "[class ^= product]");
        int countAllStikers = getElementsByCss(mainPage, "[class ^= sticker]").size();
        Assertions.assertEquals(countAllStikers, listDucks.size());

        for (WebElement duck : listDucks) {
            int countSticker = getElementsByCss(duck, "[class ^= sticker]").size();
            Assertions.assertEquals(countSticker, 1);
        }
    }

    public WebElement waitForVisibilityElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
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