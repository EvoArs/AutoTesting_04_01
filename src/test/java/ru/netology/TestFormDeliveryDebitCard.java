package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TestFormDeliveryDebitCard {
    private WebDriver driver;

    // Универсальный метод для генерации даты
    // где:
    // addDay - количество дней добавляемых к текущей дате
    // String pattern - шаблон для преобразование даты в строчный формат (ДД.ММ.ГГГГ)
    // LocalDate.now() - метод возвращающий текущую дату
    // plusDays(addDay) - метод плюсующий к текущей дате параметр addDay
    // format() - метод форматирующий дату в строку
    private String genDate(int addDay, String pattern) {
        return LocalDate.now().plusDays(addDay).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void testDebitCardPage() {
        //1. загрузка страницы
        open("http://localhost:9999");
        //2. Поиск города по первым двум буквам
        $("[data-test-id='city'] input").setValue("Са");
        $$("span.menu-item__control").findBy(text("Санкт-Петербург")).click();
        //3. Выбор даты (передаём плюс 4 дня и формат даты)
        String currentDate = genDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $("[data-test-id='date'] input").sendKeys(currentDate);
        //4. Ввод ФИО
        $("[data-test-id='name'] input").setValue("Эвоян Арсен Гагикович");
        //5. Ввод телефона
        $("[data-test-id='phone'] input").setValue("+79602358994");
        //6. Установка "галочку" согласия
        $("[data-test-id='agreement']").click();
        //7. Нажатие кнопки "Забронировать"
        $("button.button").click();
        //8. Фиксация всплывающих сообщений
        $("div.notification__title").shouldHave(text("Успешно!"),
                Duration.ofSeconds(15)).shouldBe(visible);
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + currentDate),
                Duration.ofSeconds(15)).shouldBe(visible);
    }
}