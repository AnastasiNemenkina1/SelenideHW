package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @Test
    public void positiveTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        String Date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(Date);
        $("[data-test-id='name'] input").setValue("Анастасия Гаврина");
        $("[data-test-id='phone'] input").setValue("+79102436802");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + Date));
    }

    @Test
    public void tastTwoTest(){
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item__control").findBy(Condition.text("Москва")).click();
        $(".input__control").click();
        String Date = generateData(7, "dd.MM.yyyy");
        $("[data-test-id='date'] input").click();
        if (!generateData(3,"MM").equals(generateData(7,"MM"))){
            $("[data-step='1']").click();
        };
        $$(".calendar__day").findBy(Condition.text(generateData(7,"dd"))).click();
        $("[data-test-id='name'] input").setValue("Анастасия Гаврина");
        $("[data-test-id='phone'] input").setValue("+79102436802");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + Date));
    }
}