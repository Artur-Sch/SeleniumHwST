package selenium.tasks;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.BaseTest;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class Task12 extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(Task12.class);


    /**
     * Задание 12. Сделайте сценарий добавления товара
     */
    @Test
    public void test12() {
        getAutorized();
        LOG.info("Переходим в каталог");
        getElementBy(By.xpath("//span[contains(text(),'Catalog')]")).click();
        getElementBy(By.xpath("//a[contains(text(),'Add New Product')]")).click();

        LOG.info("Заполняем вкладку General");
        getElementBy(By.cssSelector("input[name='status'][value='1']")).click();

        String name = RandomString.make(7).toLowerCase(Locale.ROOT);
        getElementBy(By.name("name[en]")).sendKeys(name);

        getElementBy(By.cssSelector("input[data-name='Root']")).click();
        getElementBy(By.cssSelector("input[data-name='Rubber Ducks']")).click();

        getElementBy(By.name("quantity")).clear();
        getElementBy(By.name("quantity")).sendKeys("20");

        new Select(driver.findElement(By.name("sold_out_status_id"))).selectByVisibleText("Temporary sold out");

        File duckImage = new File("src/test/resources/duck.png");
        getElementBy(By.cssSelector("input[type='file']")).sendKeys(duckImage.getAbsolutePath());

        getElementBy(By.cssSelector("input[name = date_valid_from]")).sendKeys(getCurrentDate());
        getElementBy(By.cssSelector("input[name = date_valid_to]")).sendKeys(getFutureDate());

        LOG.info("Заполняем вкладку Information");
        getElementBy(By.xpath("//a[contains(text(),'Information')]")).click();

        new Select(waitForClickableElement(getElementBy(By.name("manufacturer_id")))).selectByVisibleText("ACME Corp.");
        getElementBy(By.name("short_description[en]")).sendKeys("short description product");

        LOG.info("Заполняем вкладку Prices");
        getElementBy(By.xpath("//a[contains(text(),'Prices')]")).click();
        waitForClickableElement(getElementBy(By.name("purchase_price"))).clear();
        getElementBy(By.name("purchase_price")).sendKeys("10");

        new Select(getElementBy(By.name("purchase_price_currency_code"))).selectByVisibleText("US Dollars");

        getElementBy(By.name("prices[USD]")).sendKeys("12");
        getElementBy(By.name("save")).click();

        LOG.info("Проверяем появление нашего товара");
        Assertions.assertNotNull(getElementBy(By.xpath(String.format("//*[contains(text(), '%s')]", name))));
    }

    private static String getCurrentDate() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private static String getFutureDate(){
        return LocalDateTime.now()
                .plusDays(120)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
