package ru.diolloyd.webuiat.lesson5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestBase {
    private final String LOGIN_PAGE_URL = "https://trello.com/appSwitcherLogin";
    private final String LOGIN = System.getenv("TRELLO_LOGIN");
    private final String PASSWORD = System.getenv("TRELLO_PASSWORD");
    private WebDriver driver;

    @BeforeAll
    static void setupWebDriverManager() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeTest() {
        setUpDriverSession();
    }

    @AfterEach
    void quitSession() {
        driver.quit();
    }

    @Test
    void testLogin() {
        login();
        driver.findElement(By.cssSelector("[data-test-id=header-member-menu-button]")).click();
        String actualLogin = driver.findElement(By.cssSelector("[data-test-id=header-member-menu-popover] nav>ul>div>div:nth-child(2)>span")).getText();
        assertEquals(LOGIN, actualLogin);
        driver.findElement(By.cssSelector("[data-test-id=popover-close]")).click();
    }

    @Nested
    public class AsAuthorizedUser {

        @BeforeEach
        void login() {
            testLogin();
        }

        @Test
        void createBoardTest() {
            String boardName = "AT Empty Board";
            createBoard(boardName);
            String actualBoardName = driver.findElement(By.cssSelector("h1.js-board-editing-target")).getAttribute("innerText");
            System.out.println(actualBoardName);
            assertEquals(boardName, actualBoardName);
        }

        @Nested
        public class ForNewBoard {
            String url;

            @BeforeEach
            void newBoard() {
                createBoard("AT Board");
                driver.findElement(By.cssSelector("h1.js-board-editing-target"));
                url = driver.getCurrentUrl();
            }

            @AfterEach
            void deleteBoard() {
                TestBase.this.deleteBoard(url);
            }

            @Nested
            class OnExistedBoard {
                @BeforeEach
                void goToBoard() {
                    driver.get(url);
                }

                @Test
                void createListTest() {
                    String listName = "ListForListTest";
                    createList(listName);
                    List<WebElement> expected = driver
                            .findElements(By.xpath(String.format(
                                    "//h2[contains(@class,'list-header-name-assist') and child::text()='%s']",
                                    listName)));
                    System.out.println("create list test " + expected.size());
                    assertTrue(expected.size() > 0);
                }

                @Test
                void addCardToListTest() {
                    String listName = "ListForCardTest";
                    String cardText = "testText";

                    createList(listName);
                    addCardToList(listName, cardText);

                    List<WebElement> expected = driver.findElements(By.xpath(
                            "//h2[contains(@class,'list-header-name-assist') and child::text()='" + listName + "']" +
                                    "/../..//*[contains(@class,'js-card-name') and text()='" + cardText + "']"));
                    System.out.println("add card test " + expected.size());
                    assertTrue(expected.size() > 0);
                }
            }
        }
    }

    private void addCardToList(String listName, String cardText) {
        driver.findElement(By.xpath(String.format(
                "//h2[contains(@class,'list-header-name-assist') and child::text()='%s']" +
                        "/../..//a[contains(@class,'js-open-card-composer')]",
                listName))).click();
        driver.findElement(By.xpath("//textarea[contains(@class,'js-card-title')]")).click();
        driver.findElement(By.xpath("//textarea[contains(@class,'js-card-title')]")).sendKeys(cardText);
        driver.findElement(By.cssSelector("#board")).click();
    }

    private void createList(String listName) {
        driver.findElement(By.cssSelector("#board")).click();
        driver.findElement(By.cssSelector(".js-add-list")).click();
        driver.findElement(By.cssSelector(".list-name-input")).click();
        driver.findElement(By.cssSelector(".list-name-input")).sendKeys(listName);
        driver.findElement(By.cssSelector(".list-name-input")).submit();
    }

    private void createBoard(String boardName) {
        driver.findElement(By.cssSelector("[data-test-id=create-board-tile]")).click();
        driver.findElement(By.cssSelector("[data-test-id=create-board-title-input]")).click();
        driver.findElement(By.cssSelector("[data-test-id=create-board-title-input]")).sendKeys(boardName);
        driver.findElement(By.cssSelector("[data-test-id=create-board-submit-button]")).click();
    }

    private void login() {
        driver.get(LOGIN_PAGE_URL);
        driver.findElement(By.cssSelector("#username")).click();
        driver.findElement(By.cssSelector("#username")).sendKeys(LOGIN);
        driver.findElement(By.cssSelector("#username")).submit();
        driver.findElement(By.cssSelector("#password")).click();
        driver.findElement(By.cssSelector("#password")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("#password")).submit();
    }

    private void deleteBoard(String url) {
        driver.get(url);
        String display = driver.findElement(By.cssSelector(".board-menu")).getCssValue("display");
        System.out.println("display " + display);
        if ("none".equals(display)) {
            driver.findElement(By.cssSelector(".js-show-sidebar")).click();
        }
        driver.findElement(By.cssSelector(".js-open-more")).click();
        driver.findElement(By.cssSelector(".js-close-board")).click();
        driver.findElement(By.cssSelector(".pop-over .js-confirm")).click();
    }

    private void setUpDriverSession() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }
}

