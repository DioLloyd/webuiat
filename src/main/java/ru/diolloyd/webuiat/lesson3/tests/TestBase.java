package ru.diolloyd.webuiat.lesson3.tests;

import org.openqa.selenium.WebDriver;

public abstract class TestBase {
    private final WebDriver driver;

    abstract public void run();

    public TestBase(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
