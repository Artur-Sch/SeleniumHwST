package selenium.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.BaseTest;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class Task13 extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(Task13.class);
    private static final String URL = "http://localhost/litecart/en/";

    /**
     * Задание 13. Сделайте сценарий работы с корзиной
     */
    @Test
    public void test13() {
        driver.navigate().to(URL);

        LOG.info("Получаем количесво товаров в корзине");
        int quantity = getQuantityInCart();

        LOG.info("Добавляем 3 товара в корзину");
        for (int i = quantity; i < (quantity + 3); i++) {
            getElementBy(By.cssSelector(".product")).click();
            int finalQuantity = i;

            List<WebElement> selectsSize = getElementsBy(By.name("options[Size]"));
            if(selectsSize.size() > 0){
                new Select(selectsSize.get(0)).selectByIndex(1);
            }

            waitForClickableElement(getElementBy(By.name("add_cart_product"))).click();
            wait.until((ExpectedCondition<Boolean>) driver -> getQuantityInCart() > finalQuantity);
            driver.navigate().to(URL);
        }
        quantity = getQuantityInCart();
        Assertions.assertEquals(quantity, 3, "В корзине не верное количество товара");

        LOG.info("Переходим в корзину");
        getElementBy(By.cssSelector("[href $= checkout].link")).click();

        LOG.info("Удаляем товары из корзины");

        while (quantity > 0) {
            int finalQuantity = quantity;
            ifShortcutIsPresentCLickFirst();
            getElementBy(By.name("remove_cart_item")).click();
            wait.until(stalenessOf(getElementBy(By.cssSelector("#order_confirmation-wrapper tr:nth-child(2)"))));
            quantity = getTotalOrderSum();
        }
        LOG.info("Ожидаем появления надписи, что корзина пуста");
        waitForVisibilityElement(getElementBy(By.xpath("//*[contains(text(), 'There are no items in your cart.')]")));
        Assertions.assertEquals(quantity, 0,"Корзина не пуста");
    }

    private int getQuantityInCart() {
        return Integer.parseInt(getElementBy(By.cssSelector("span.quantity")).getText());
    }

    private int getTotalOrderSum() {
        return getElementsBy(By.cssSelector("#order_confirmation-wrapper td:nth-child(1)")).stream()
                .filter(element -> element.getText().matches("\\d"))
                .mapToInt(element -> Integer.parseInt(element.getText()))
                .sum();
    }

    private void ifShortcutIsPresentCLickFirst() {
        List<WebElement> list = getElementsBy(By.cssSelector("#box-checkout-cart li:first-child > [href]"));
        if (list.size() > 0) {
            list.get(0).click();
        }
    }
}
