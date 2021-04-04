package ru.diolloyd.webuiat.lesson6;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthorizationPage {
    private static final String LOGIN_PAGE_URL = "https://trello.com/appSwitcherLogin";
    private final WebDriver driver;


    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Open login page")
    public AuthorizationPage openPage() {
        driver.get(LOGIN_PAGE_URL);
        return this;
    }

    @Step("Input login")
    public AuthorizationPage inputLogin(String login) {
//        driver.findElement(By.cssSelector("#username")).click();
        driver.findElement(By.cssSelector("#username")).sendKeys(login);
//        driver.findElement(By.cssSelector("#username")).submit();
        return this;
    }

    @Step("Input password")
    public AuthorizationPage inputPassword(String password) {
//        driver.findElement(By.cssSelector("#password")).click();
        driver.findElement(By.cssSelector("#password")).sendKeys(password);
//        driver.findElement(By.cssSelector("#password")).submit();
        return this;
    }

    @Step("Click button Enter")
    public AuthorizationPage submitLoginForm() {
        driver.findElement(By.cssSelector("#form-login")).submit();
        return this;
    }
}
