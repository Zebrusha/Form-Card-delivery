import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;



public class FormOrderingCardDeliveryTestV1 {

    private String generateDate(int addDays, String pattern){
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldFillOutForm (){
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");

        String planingDate = generateDate(3,"dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planingDate);

        $("[data-test-id=name] input").setValue("Вавилов Иван");
        $("[data-test-id=phone] input").setValue(("+79000000000"));
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[class=notification__content]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + planingDate));
    }
}
