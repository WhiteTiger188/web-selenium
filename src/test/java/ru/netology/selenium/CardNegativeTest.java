package ru.netology.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CardNegativeTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void startDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void engDriver() {
        driver.quit();
        driver = null;
    }

    @Test
    void emptyFieldAll() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.tagName("button")).click();

        String expectedText = "Поле обязательно для заполнения";
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        boolean actualColor = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid")).isDisplayed();

        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertTrue(actualColor);
    }

    @Test
    void emptyFieldName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79122518775");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String expectedText = "Поле обязательно для заполнения";
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        boolean actualColor = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid")).isDisplayed();

        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertTrue(actualColor);
    }

    @ParameterizedTest
    @CsvSource({
            "Dana",
            "Татьяна_Tarasova",
            "!Татьяна",
            "Татьяна101"
    })
    void invalidName(String name) {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(name);
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79122518775");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        boolean actualColor = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid")).isDisplayed();

        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertTrue(actualColor);
    }

    @Test
    void emptyFieldPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Татьяна");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String expectedText = "Поле обязательно для заполнения";
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        boolean actualColor = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid")).isDisplayed();

        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertTrue(actualColor);
    }

    @ParameterizedTest
    @CsvSource({
            "+791225187755",
            "+7912251877",
            "+7",
            "+",
            "89122518775",
            "@79122518775",
            "Дмитрий"
    })
    void invalidPhone(String namber) {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Татьяна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys(namber);
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();

        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        boolean actualColor = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid")).isDisplayed();

        Assertions.assertEquals(expectedText, actualText);
        Assertions.assertTrue(actualColor);
    }

    @Test
    void emptyFieldAgree() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Татьяна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79122518775");
        driver.findElement(By.tagName("button")).click();

        boolean actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed();

        Assertions.assertTrue(actual);
    }
}


