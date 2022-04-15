package selenium.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.BaseTest;

import java.util.List;

public class Task7 extends BaseTest {

    /**
     * Задание 7. Сделайте сценарий, проверяющий наличие стикеров у товаров
     */
    @Test
    public void test7() {
        driver.navigate().to("http://localhost/litecart");
        WebElement mainPage = getElementBy(By.cssSelector("#main > div.middle > div.content"));

        List<WebElement> listDucks = getElementsByCss(mainPage, ".product");
        int countAllStikers = getElementsByCss(mainPage, ".sticker").size();
        Assertions.assertEquals(countAllStikers, listDucks.size());

        for (WebElement duck : listDucks) {
            int countSticker = getElementsByCss(duck, ".sticker").size();
            Assertions.assertEquals(countSticker, 1);
        }
    }
}
