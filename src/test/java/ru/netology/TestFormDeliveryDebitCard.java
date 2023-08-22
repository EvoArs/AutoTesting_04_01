package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFormDeliveryDebitCard {
    private WebDriver driver;

    // настройка под драйвер
    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
//        Опция отключающаяя отображение работы браузера при запуске тестов
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    void testDebitCardPage() {
        //1. загрузка страницы
        driver.get("http://localhost:7777");

        //2. поиск и взаимодействие с элементами
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Эвоян Арсен");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79602358994");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
//        .trim() отрезает пробелы в тексте
//        Опция отображения работы браузера при запуске тестов
//        Thread.sleep(5000);
    }
}
