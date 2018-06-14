package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ImageResultPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(className = "q")
    WebElement allButton;

    @FindBy(className = "rg_l")
    List<WebElement> result;

    public String curentURL() {
        return webDriver.getCurrentUrl();
    }

    public void switchToSearchResultPage() {
        allButton.click();
    }

    public List<WebElement> searchResult() {
        return result;
    }

    public List<String> linkSearchResult() {
        List<String> result = new ArrayList<String>();
        for (WebElement webElement : searchResult()) {
            result.add(webElement.getAttribute("href"));
        }
        return result;
    }

    public ImageResultPage(WebDriver driver) {
        webDriver = driver;
        wait = new WebDriverWait(webDriver, 30);
        PageFactory.initElements(webDriver, this);
    }
}
