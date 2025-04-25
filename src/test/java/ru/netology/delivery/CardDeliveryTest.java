package ru.netology.delivery;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class CardDeliveryTest {

    @BeforeAll
    static void setupAll() {

        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.timeout = 15000;
        Configuration.browserSize = "1920x1080";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        Configuration.browserCapabilities = options;
    }

    @Test
    void shouldSubmitValidForm() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Москва");
        String deliveryDate = generateDate(3);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Анастасия Гаврина");
        $("[data-test-id=phone] input").setValue("+79102436802");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();

        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + deliveryDate));
    }

    private String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}