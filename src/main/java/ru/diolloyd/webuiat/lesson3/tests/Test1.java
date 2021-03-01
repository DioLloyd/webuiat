package ru.diolloyd.webuiat.lesson3.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Test1 extends TestBase {

    public static final String NOTE_TEXT = "Первый кейс\nПодключить драйвер\nОткрыть страницу\nСоздать заметку";

    public Test1(WebDriver driver) {
        super(driver);
    }

    public void run() {
        createNote();
        List<WebElement> notes = getDriver().findElements(By.cssSelector("#canvas .note"));
        assert (notes.size() == 1);
        String checkText = notes.get(0).getText();
        assert (checkText.equals(NOTE_TEXT));
    }

    private WebElement findActiveNote() {
        return getDriver().findElement(By.cssSelector(".note .nicEdit-selected"));
    }

    private void createNote() {
        getDriver().get("http://note.ly");
        getDriver().findElement(By.cssSelector(".note-add")).click();
        findActiveNote().sendKeys(NOTE_TEXT);
    }
}
