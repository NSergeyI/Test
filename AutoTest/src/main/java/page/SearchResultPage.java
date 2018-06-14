package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(className = "q")
    List<WebElement> typeSearchButtons;

    @FindBy(xpath = "//a[contains(text(),'More')]")
    WebElement moreButton;

    @FindBy(xpath = "//*[@id='rso']/div/div/div/div/div/h3/a")
    List<WebElement> result;

    public SearchResultPage(WebDriver driver) {
        this.webDriver = driver;
        wait = new WebDriverWait(webDriver, 30);
        PageFactory.initElements(webDriver, this);
    }

    public String curentURL() {
        return webDriver.getCurrentUrl();
    }

    public WebElement imageButton() {
        for (int i = 0; i < typeSearchButtons.size(); i++) {
            if (typeSearchButtons.get(i).getAttribute("href").contains("tbm=isch")) {
                if (i >= 4) {
                    moreButton.click();
                }
                return typeSearchButtons.get(i);
            }
        }
        return null;
    }

    public void switchSearchToImage() {
        imageButton().click();
    }

    public List<WebElement> searchResults() {
        return result;
    }

    public List<String> linkSearchResults() {
        List<String> resultsLink = new ArrayList<String>();
        for (WebElement webElement : searchResults()) {
            resultsLink.add(webElement.getAttribute("href"));
        }
        return resultsLink;
    }

}
