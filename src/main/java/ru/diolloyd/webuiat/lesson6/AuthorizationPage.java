package ru.diolloyd.webuiat.lesson6;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthorizationPage {
    private static final String LOGIN_PAGE_URL = "https://trello.com/appSwitcherLogin";
    private final WebDriver driver;


    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
    }

    public AuthorizationPage openPage() {
        driver.get(LOGIN_PAGE_URL);
        return this;
    }

    public AuthorizationPage inputLogin(String login) {
        driver.findElement(By.cssSelector("#username")).click();
        driver.findElement(By.cssSelector("#username")).sendKeys(login);
//        driver.findElement(By.cssSelector("#username")).submit();
        return this;
    }

    public AuthorizationPage inputPassword(String password) {
        driver.findElement(By.cssSelector("#password")).click();
        driver.findElement(By.cssSelector("#password")).sendKeys(password);
//        driver.findElement(By.cssSelector("#password")).submit();
        return this;
    }

    public AuthorizationPage submitLoginForm() {
        driver.findElement(By.cssSelector("#form-login")).submit();
        return this;
    }
}
