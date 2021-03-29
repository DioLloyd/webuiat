package ru.diolloyd.webuiat.lesson5;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.diolloyd.webuiat.lesson6.AuthorizationPage;
import ru.diolloyd.webuiat.lesson6.BoardPage;
import ru.diolloyd.webuiat.lesson6.HomePage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestBase {
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
    @Description(value = "Authorization")
    void testLogin() {
        new AuthorizationPage(driver)
                .openPage()
                .inputLogin(LOGIN)
                .submitLoginForm()
                .inputPassword(PASSWORD)
                .submitLoginForm();
        HomePage homePage = new HomePage(driver);
        String actualLogin = homePage.getActualLogin();
        assertEquals(LOGIN, actualLogin);
    }

    @Nested
    public class AsAuthorizedUser {

        @BeforeEach
        void login() {
            testLogin();
        }

        @Test
        @Description(value = "Create Board")
        void createBoardTest() {
            String boardName = "AT Empty Board";
            HomePage homePage = new HomePage(driver);
            BoardPage boardPage = homePage.createBoard(boardName);
            String actualBoardName = boardPage.getActualBoardName();
            assertEquals(boardName, actualBoardName);
        }

        @Nested
        public class ForNewBoard {
            BoardPage boardPage;

            @BeforeEach
            void newBoard() {
                String boardName = "AT Board";
                HomePage homePage = new HomePage(driver);
                boardPage = homePage.createBoard(boardName);
            }

            @AfterEach
            void deleteBoard() {
                boardPage.deleteBoard();
            }

            @Nested
            class OnExistedBoard {
                @BeforeEach
                void goToBoard() {
                    boardPage.goToBoard();
                }

                @Description(value = "Create List")
                @Test
                void createListTest() {
                    String listName = "ListForListTest";
                    boardPage.createList(listName);
                    List<WebElement> expected = boardPage.findListsByName(listName);
                    assertTrue(expected.size() > 0);
                }

                @Description("Add Card to List")
                @Test
                void addCardToListTest() {
                    String listName = "ListForCardTest";
                    String cardText = "testText";
                    boardPage.createList(listName);
                    boardPage.addCardToList(listName, cardText);
                    List<WebElement> expected = boardPage.findCardsInList(listName, cardText);
                    assertTrue(expected.size() > 0);
                }
            }
        }
    }

    private void setUpDriverSession() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }
}

