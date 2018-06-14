import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.GoogleSite;

public class MyTest {
    WebDriver webDriver;
    GoogleSite googleSite;
    WebDriverWait wait;

    @Before
    public void setUp() {
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, 10, 500);

//       1 перейти на сайт http://google.com/ncr;
        googleSite = new GoogleSite(webDriver);
        webDriver.get("http://google.com/ncr");

//       2 выполнить поиск по слову "selenium";
        googleSite.mainPage().searchFor("selenium");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rso")));
    }

    @Test
    public void searchTest() {

//       3 ожидается, что первый результат в выдаче ведет на какую-то страницу сайта seleniumhq.org.
// Если в начале поисковой выдачи появились какие-то виджеты,
// то их не учитывать (виджет Википедии, виджет "Top stories" и т.п.);
        Assert.assertFalse(
                googleSite
                        .searchResultPage()
                        .curentURL().contains("tbm="));
        String firstLink = googleSite
                .searchResultPage()
                .linkSearchResults()
                .get(0);
        Assert.assertTrue(firstLink.contains("seleniumhq.org"));

//       4 перейти на вкладку "Images";
        googleSite.searchResultPage().switchSearchToImage();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rso")));
        Assert.assertTrue(
                googleSite
                        .imageResultPage()

                        .curentURL().contains("tbm=isch"));
//       5 ожидается, что первая картинка в выдаче как-либо относится к сайту seleniumhq.org;
        Assert.assertTrue(
                googleSite
                        .imageResultPage()
                        .linkSearchResult()
                        .get(0).contains("seleniumhq.org"));

//       6 вернуться на вкладку "All"
        googleSite.imageResultPage().switchToSearchResultPage();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rso")));
        Assert.assertFalse(
                googleSite
                        .searchResultPage()
                        .curentURL().contains("tbm="));

//       7 ожидается, что первый результат по-прежнему ведет на ту же страницу, что и на шаге 3
        Assert.assertTrue(
                googleSite
                        .searchResultPage()
                        .linkSearchResults()
                        .get(0).contains(firstLink));
    }

    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }

    }

}
