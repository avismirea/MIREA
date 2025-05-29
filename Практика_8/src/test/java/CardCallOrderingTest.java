import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class CallCallOrderingTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTest1() throws InterruptedException {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Шохинзода Мухаммадджон");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7(912)552-22-80");

        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        Thread.sleep(4000);

        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[4]/button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expectedText = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void shouldTest2() throws InterruptedException {

        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7(912)552-22-80");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        Thread.sleep(4000);

        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[4]/button")).click();
        Thread.sleep(4000);

        String actualText = driver.findElement(By.className("input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void shouldTest3() throws InterruptedException {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Muhammed");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7(912)552-22-80");

        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        Thread.sleep(4000);

        driver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[4]/button")).click();
        Thread.sleep(4000);

        String actualText = driver.findElement(By.className("input__sub")).getText().trim();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        Assertions.assertEquals(expectedText, actualText);
    }
}
