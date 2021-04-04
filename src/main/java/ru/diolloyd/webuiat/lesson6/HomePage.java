package ru.diolloyd.webuiat.lesson6;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Create board")
    public BoardPage createBoard(String boardName) {
        driver.findElement(By.cssSelector("[data-test-id=create-board-tile]")).click();
        driver.findElement(By.cssSelector("[data-test-id=create-board-title-input]")).click();
        driver.findElement(By.cssSelector("[data-test-id=create-board-title-input]")).sendKeys(boardName);
        driver.findElement(By.cssSelector("[data-test-id=create-board-submit-button]")).click();
        BoardPage boardPage = new BoardPage(driver);
        boardPage.waitingForPageIsLoaded();
        return boardPage;
    }

    @Step("Get actual login")
    public String getActualLogin() {
        driver.findElement(By.cssSelector("[data-test-id=header-member-menu-button]")).click();
        String actualLogin = driver.findElement(By.cssSelector("[data-test-id=header-member-menu-popover] nav>ul>div>div:nth-child(2)>span")).getText();
        driver.findElement(By.cssSelector("[data-test-id=popover-close]")).click();
        return actualLogin;
    }
}
