package ru.diolloyd.webuiat.lesson3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.diolloyd.webuiat.lesson3.tests.Test1;
import ru.diolloyd.webuiat.lesson3.tests.Test2;
import ru.diolloyd.webuiat.lesson3.tests.TestBase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2L, TimeUnit.SECONDS);

        List<TestBase> testList = Arrays.asList(new Test1(driver), new Test2(driver));

        for (TestBase test : testList) {
            try {
                test.run();
                System.out.println(test.getClass().getSimpleName() + " OK");
            } catch (Exception e) {
                System.out.println(test.getClass().getSimpleName() + " FAIL");
                e.printStackTrace();
            }
        }
        driver.quit();
    }
}
