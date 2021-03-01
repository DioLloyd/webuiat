package ru.diolloyd.webuiat.lesson3.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Test2 extends TestBase {
    private static final String EMAIL = "test123@gmail.com";

    public Test2(WebDriver driver) {
        super(driver);
    }

    public void run() {
        authorization();
        String emailCheck = getDriver().findElement(By.cssSelector(".user-email-display")).getText();
        assert (emailCheck.equals(EMAIL));
    }

    private void authorization() {
        getDriver().get("http://note.ly");
        getDriver().findElement(By.cssSelector(".not-logged-in")).click();
        getDriver().findElement(By.cssSelector("#login-email")).click();
        getDriver().findElement(By.cssSelector("#login-email")).sendKeys("test123@gmail.com");
        getDriver().findElement(By.cssSelector(".newInputPass")).click();
        getDriver().findElement(By.cssSelector("#login-password")).sendKeys("Student2020!");
        getDriver().findElement(By.cssSelector("input[type=submit]")).click();
        getDriver().findElement(By.cssSelector(".note-add")).click();
    }
}
