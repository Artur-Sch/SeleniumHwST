package selenium.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.BaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Task8 extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(Task8.class);
    private final String URL_COUNTRIES = "http://localhost/litecart/admin/?app=countries&doc=countries";
    private final String URL_EDIT_COUNTRIES = "http://localhost/litecart/admin/?app=countries&doc=edit_country&country_code=";
    private List<String> countriesList = new ArrayList<>();
    private List<String> countriesCodeList = new ArrayList<>();
    private List<String> countriesSortedList = new ArrayList<>();

    /**
     * Задание 8. Проверить сортировку стран и геозон на странице стран
     */
    @Test
    public void test8() {
        getAutorized();
        LOG.info("Переходим на страницу стран");
        driver.navigate().to(URL_COUNTRIES);

        LOG.info("Получаем список элементов с таблицы стран");
        List<WebElement> countriesElements = getElementsBy(By.cssSelector("[name = countries_form] table .row"));

        LOG.info("Получаем список стран");
        for (WebElement countriesElement : countriesElements) {
            countriesList.add(countriesElement.findElement(By.cssSelector("a")).getText());

            // Проверяем наличие геозон
            int countZone = Integer.parseInt(countriesElement.findElements(By.cssSelector("td")).get(5).getText());
            if (countZone > 0) {
                // Если есть геозоны, сохраняем код страны
                countriesCodeList.add(countriesElement.findElements(By.cssSelector("td")).get(3).getText());
            }
        }
        LOG.info(String.format("Найдено %d стран с геозонами", countriesCodeList.size()));
        LOG.info("Проверяем сотрировку стран");

        countriesSortedList.addAll(countriesList);
        Collections.sort(countriesSortedList);
        Assertions.assertEquals(countriesSortedList, countriesList);

        LOG.info("Проверяем сотрировку геозон, на страницах стран");
        for (String code : countriesCodeList) {
            countriesList.clear();
            countriesSortedList.clear();

            driver.navigate().to(URL_EDIT_COUNTRIES + code);
            // Получаем список элементов геозон
            List<WebElement> countriesCodeElm = getElementsBy(By.cssSelector("#table-zones input[name *= 'name'][type='hidden']"));
            for (WebElement element : countriesCodeElm) {
                countriesList.add(element.getAttribute("value"));
            }
            // Проверяем сотрировку стран
            countriesSortedList.addAll(countriesList);
            Collections.sort(countriesSortedList);
            Assertions.assertEquals(countriesSortedList, countriesList);
        }

    }
}
