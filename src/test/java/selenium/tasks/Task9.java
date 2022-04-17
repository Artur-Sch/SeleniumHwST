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


public class Task9 extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(Task9.class);
    private final String URL_GEO_ZONES = "http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";
    private List<String> geoZonesUrls = new ArrayList<>();
    private List<String> geoZones = new ArrayList<>();
    private List<String> sortedGeoZones = new ArrayList<>();

    /**
     * Задание 9. Проверить сортировку геозон на странице геозон
     */
    @Test
    public void test9() {
        getAutorized();
        LOG.info("Переходим на страницу геозон");
        driver.navigate().to(URL_GEO_ZONES);
//        #table-zones select[name *= 'zone_code']
//        form[name='geo_zones_form'] a[title='Edit']

        LOG.info("Получаем список адресов для редактирования геозон");
        List<WebElement> geoZonesElms = getElementsBy(By.cssSelector("form[name='geo_zones_form'] a[title='Edit']"));
        for (WebElement elms : geoZonesElms) {
         geoZonesUrls.add(elms.getAttribute("href"));
        }

        LOG.info(String.format("Найдено %d геозон", geoZonesUrls.size()));
        LOG.info("Переходим в геозону и проверяем сортировку");
        for (String geoZonesUrl : geoZonesUrls) {
            driver.navigate().to(geoZonesUrl);

            geoZones.clear();
            sortedGeoZones.clear();

           geoZonesElms = getElementsBy(By.cssSelector("#table-zones select[name *= 'zone_code'] [selected]"));
            for (WebElement element : geoZonesElms) {
                geoZones.add(element.getText());
            }

            LOG.info("Проверяем сотрировку геозон, на странице геозоны");
            sortedGeoZones.addAll(geoZones);
            Collections.sort(sortedGeoZones);
            Assertions.assertEquals(sortedGeoZones, geoZones);
        }
    }
}
