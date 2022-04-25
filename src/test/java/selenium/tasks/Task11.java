package selenium.tasks;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.BaseTest;

import java.util.Locale;
import java.util.Random;

public class Task11 extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(Task11.class);
    private static final String URL = "http://localhost/litecart/en/create_account";

    /**
     * Задание 11. Сделайте сценарий регистрации пользователя
     */
    @Test
    public void test11() {
        driver.navigate().to(URL);

        LOG.info("Генерируем и заполняем данные для регистрации");
        getElementBy(By.name("firstname")).sendKeys(RandomString.make(5));
        getElementBy(By.name("lastname")).sendKeys(RandomString.make(5));
        getElementBy(By.name("address1")).sendKeys(RandomString.make(6));
        getElementBy(By.name("postcode")).sendKeys("14140");
        getElementBy(By.name("city")).sendKeys(RandomString.make(7));
        getElementBy(By.name("phone")).sendKeys("+1" + new Random().nextInt(7));
        String email = RandomString.make(7).toLowerCase(Locale.ROOT) + "@gmail.com";
        getElementBy(By.name("email")).sendKeys(email);
        String password = RandomString.make(8);
        getElementBy(By.name("password")).sendKeys(password);
        getElementBy(By.name("confirmed_password")).sendKeys(password);

        LOG.info("Выбираем US и California");
        WebElement countrySelect = waitForClickableElement(getElementBy(By.name("country_code")));
        String countryIndex = getElementBy(By.cssSelector("option[value = US]")).getAttribute("index");
        String script = String.format("arguments[0].selectedIndex = %s; arguments[0].dispatchEvent(new Event('change'))", countryIndex);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, countrySelect);

        new Select(getElementBy(By.cssSelector("select[name = zone_code]"))).selectByVisibleText("California");
        LOG.info("Нажимаем create_account");
        getElementBy(By.name("create_account")).click();

        LOG.info("Нажимаем выход");
        getElementBy(By.cssSelector("#box-account [href $= logout]")).click();

        LOG.info("Авторизуемся");
        waitForVisibilityElement(getElementBy(By.name("email"))).sendKeys(email);
        getElementBy(By.name("password")).sendKeys(password);
        getElementBy(By.name("login")).click();

        LOG.info("Нажимаем выход");
        waitForClickableElement(getElementBy(By.cssSelector("#box-account [href $= logout]"))).click();
    }
}
