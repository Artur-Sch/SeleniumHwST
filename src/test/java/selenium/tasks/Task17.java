package selenium.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.BaseTest;

import java.util.ArrayList;
import java.util.List;


public class Task17 extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(Task17.class);
    private final String URL_CATALOG = "http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1";

    /**
     * Задание 17. Проверьте отсутствие сообщений в логе браузера
     */
    @Test
    public void test17() {
        getAutorized();
        LOG.info("Открываем каталог, категорию, которая содержит товары (страница %s)", URL_CATALOG);
        driver.navigate().to(URL_CATALOG);
        LOG.info("Получаем список товаров");
        List<WebElement> listProduct = getElementsBy(By.cssSelector("a[href *='category_id=1&product_id']:not([title=Edit])"));
        List<String> namesProduct = getNameElements(listProduct);
        LOG.info("Переходим по списку товаров, проверяем остутсвие логов");
        for (String name : namesProduct) {
            waitForClickableElement(getElementBy(By.linkText(name))).click();
            Assertions.assertTrue(driver.manage().logs().get("browser").getAll().isEmpty());
            driver.navigate().back();
        }
    }

    private List<String> getNameElements(List<WebElement> elements) {
        List<String> names = new ArrayList<>();
        for (WebElement e : elements) {
            names.add(e.getText());
        }
        return names;
    }
}
