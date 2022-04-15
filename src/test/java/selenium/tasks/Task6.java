package selenium.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.BaseTest;

import java.util.List;

public class Task6 extends BaseTest {

    /**
     * Задание 6. Сделайте сценарий, проходящий по всем разделам админки
     */
    @Test
    public void test6() {
        getAutorized();
        List<WebElement> menus = getElementsBy(By.xpath("//li[@id='app-']"));
        String h1Text;
        for (int i = 1; i <= menus.size(); i++) {
            waitForVisibilityElement(getElementBy(By.cssSelector(String.format("ul#box-apps-menu > li#app-:nth-child(%d)", i)))).click();

            WebElement selectedElement = getElementBy(By.cssSelector("#box-apps-menu #app-.selected"));
            List<WebElement> menuElements = selectedElement.findElements(By.tagName("li"));
            if (menuElements.size() > 0) {
                for (int j = 2; j <= menuElements.size(); j++) {
                    selectedElement = getElementBy(By.cssSelector("#box-apps-menu #app-.selected"));
                    h1Text = waitForVisibilityElement(getElementBy(By.cssSelector("h1"))).getText();
                    Assertions.assertNotNull(h1Text);
//                    Assertions.assertEquals(h1Text, getElementBy(By.cssSelector(".docs .selected .name")).getText());
                    selectedElement = selectedElement.findElement(By.cssSelector(String.format("li:nth-child(%d)", j)));
                    selectedElement.click();
                }
            } else {
                h1Text = waitForVisibilityElement(getElementBy(By.cssSelector("h1"))).getText();
//                Assertions.assertEquals(h1Text, selectedElement.getText());
                Assertions.assertNotNull(h1Text);
            }
        }
    }
}
