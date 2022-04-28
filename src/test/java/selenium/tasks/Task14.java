package selenium.tasks;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.BaseTest;

import java.util.List;


public class Task14 extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(Task14.class);
    private final String URL_COUNTRIES = "http://localhost/litecart/admin/?app=countries&doc=countries";

    /**
     * Задание 14. Проверьте, что ссылки открываются в новом окне
     */
    @Test
    public void test14() {
        getAutorized();
        LOG.info("Переходим на страницу стран");
        driver.navigate().to(URL_COUNTRIES);

        LOG.info("Переходим на страницу редактирования");
        getElementBy(By.cssSelector("#content [href $= edit_country]")).click();

        LOG.info("Получаем список ссылок с открытием в новом окне");
        List<WebElement> links = driver.findElements(By.cssSelector("#content [href ^= http]"));

        LOG.info("Получаем идентификатор текущего окона");
        String mainWindow = driver.getWindowHandle();

        LOG.info("Переходим по полученным ссылкам, открываем, закрываем");
        for (WebElement link : links) {
            link.click();
            String newWindow = wait.until((ExpectedCondition<String>) d -> d.getWindowHandles()
                    .stream()
                    .filter(window -> !window.equals(mainWindow))
                    .findFirst()
                    .orElse("null"));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }
}
