package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSite {
    private WebDriver webDriver;
    private WebDriverWait wait;

    public MainPage mainPage() {
        return new MainPage(webDriver);
    }

    public SearchResultPage searchResultPage() {
        return new SearchResultPage(webDriver);
    }

    public ImageResultPage imageResultPage() {
        return new ImageResultPage(webDriver);
    }

    public GoogleSite(WebDriver driver) {
        this.webDriver = driver;
        wait = new WebDriverWait(webDriver, 30);
        PageFactory.initElements(webDriver, this);
    }
}
