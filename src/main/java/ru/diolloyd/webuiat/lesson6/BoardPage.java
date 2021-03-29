package ru.diolloyd.webuiat.lesson6;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BoardPage {
    private final WebDriver driver;
    private String url;

    public BoardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitingForPageIsLoaded(){
        getActualBoardName();
        url = driver.getCurrentUrl();
    }

    public String getActualBoardName() {
        driver.findElement(By.cssSelector("h1.js-board-editing-target"));  //fix StaleReferenceException
        WebElement element = driver.findElement(By.cssSelector("h1.js-board-editing-target"));
        return element.getAttribute("innerText");
    }

    @Step
    public void goToBoard(){
        driver.get(url);
        getActualBoardName();
    }

    @Step
    public void createList(String listName) {
        driver.findElement(By.cssSelector("#board")).click();
        driver.findElement(By.cssSelector(".js-add-list")).click();
        driver.findElement(By.cssSelector(".list-name-input")).click();
        driver.findElement(By.cssSelector(".list-name-input")).sendKeys(listName);
        driver.findElement(By.cssSelector(".list-name-input")).submit();
    }

    @Step
    public void deleteBoard() {
        driver.get(url);
        String display = driver.findElement(By.cssSelector(".board-menu")).getCssValue("display");
        if ("none".equals(display)) {
            driver.findElement(By.cssSelector(".js-show-sidebar")).click();
        }
        driver.findElement(By.cssSelector(".js-open-more")).click();
        driver.findElement(By.cssSelector(".js-close-board")).click();
        driver.findElement(By.cssSelector(".pop-over .js-confirm")).click();
    }

    @Step
    public void addCardToList(String listName, String cardText) {
        driver.findElement(By.xpath(String.format(
                "//h2[contains(@class,'list-header-name-assist') and child::text()='%s']" +
                        "/../..//a[contains(@class,'js-open-card-composer')]",
                listName))).click();
        driver.findElement(By.xpath("//textarea[contains(@class,'js-card-title')]")).click();
        driver.findElement(By.xpath("//textarea[contains(@class,'js-card-title')]")).sendKeys(cardText);
        driver.findElement(By.cssSelector("#board")).click();
    }

    public List<WebElement> findListsByName(String listName) {
        return driver.findElements(By.xpath(String.format(
                "//h2[contains(@class,'list-header-name-assist') and child::text()='%s']",
                listName)));
    }

    public List<WebElement> findCardsInList(String listName, String cardText) {
        return driver.findElements(By.xpath(
                "//h2[contains(@class,'list-header-name-assist') and child::text()='" + listName + "']" +
                        "/../..//*[contains(@class,'js-card-name') and text()='" + cardText + "']"));
    }
}
