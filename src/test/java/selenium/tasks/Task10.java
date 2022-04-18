package selenium.tasks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.BaseTest;

import java.time.Duration;
import java.util.List;

public class Task10 {
    private static final Logger LOG = LoggerFactory.getLogger(Task10.class);
    private static final String URL = "http://localhost/litecart";
    protected static WebDriver driver;

    void startDriver(String browser) {
        switch (browser) {
            case ("chrome"):
                System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
                driver = new ChromeDriver();
                break;
            case ("firefox"):
                System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
                driver = new FirefoxDriver();
                break;
            case ("safari"):
                driver = new SafariDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
    }

    @AfterEach
    void closeDriver() {
        LOG.info("Закрываем драйвер");
        driver.close();
        driver.quit();
    }

    /**
     * Задание 10. Проверить, что открывается правильная страница товара
     */
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox", "safari"})
    public void test10(String browser) {
        startDriver(browser);
        driver.navigate().to(URL);

        WebElement firstProduct = driver.findElement(By.cssSelector("#box-campaigns [class ^= 'product']:nth-child(1)"));
        String productName = firstProduct.findElement(By.className("name")).getText();
        WebElement productRegularPrice = firstProduct.findElement(By.className("regular-price"));
        WebElement productCampaignPrice = firstProduct.findElement(By.className("campaign-price"));
        LOG.info("Проверяем цену на основной странице");
        Assertions.assertTrue(isCrossedOutFont(productRegularPrice) && isGray(productRegularPrice));
        Assertions.assertTrue(isRed(productCampaignPrice) && isStrongFont(productCampaignPrice));
        Assertions.assertTrue(isFirstGreaterSecond(productCampaignPrice, productRegularPrice));

        String productRegularPriceValue = productRegularPrice.getText();
        String productCampaignPriceValue = productCampaignPrice.getText();

        firstProduct.click();

        WebElement productBox = driver.findElement(By.cssSelector("#box-product"));
        String productBoxName = productBox.findElement(By.className("title")).getText();
        WebElement productBoxRegularPrice = productBox.findElement(By.className("regular-price"));
        String productBoxRegularPriceValue = productBoxRegularPrice.getText();
        WebElement productBoxCampaignPrice = productBox.findElement(By.className("campaign-price"));
        String productBoxCampaignPricealue = productBoxCampaignPrice.getText();

        LOG.info("Проверяем цену на странице товара");
        Assertions.assertTrue(isCrossedOutFont(productBoxRegularPrice) && isGray(productBoxRegularPrice));
        Assertions.assertTrue(isRed(productBoxCampaignPrice) && isStrongFont(productBoxCampaignPrice));
        Assertions.assertTrue(isFirstGreaterSecond(productBoxCampaignPrice, productBoxRegularPrice));

        LOG.info("Проверяем название товара на главной странице  и странице товара");
        Assertions.assertEquals(productName, productBoxName);
        LOG.info("Проверяем цену на главной странице  и странице товара");
        Assertions.assertEquals(productRegularPriceValue, productBoxRegularPriceValue);
        Assertions.assertEquals(productCampaignPriceValue, productBoxCampaignPricealue);

    }

    private boolean isGray(WebElement element) {
        Color color = Color.fromString(element.getCssValue("color"));
        return color.getColor().getRed() == color.getColor().getGreen() && color.getColor().getGreen() == color.getColor().getBlue();
    }

    private boolean isCrossedOutFont(WebElement element) {
        return element.getTagName().equalsIgnoreCase("s");
    }

    private boolean isRed(WebElement element) {
        Color color = Color.fromString(element.getCssValue("color"));
        return color.getColor().getGreen() == 0 && color.getColor().getBlue() == 0;
    }

    private boolean isStrongFont(WebElement element) {
        return element.getTagName().equalsIgnoreCase("strong");
    }

    private boolean isFirstGreaterSecond(WebElement first, WebElement second) {
        double first_px = Double.parseDouble(first.getCssValue("font-size").replace("px", ""));
        double second_px = Double.parseDouble(second.getCssValue("font-size").replace("px", ""));
        return first_px > second_px;
    }

}
