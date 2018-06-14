package page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(id = "lst-ib")
    WebElement searchInputFiled;

    public MainPage(WebDriver driver) {
        webDriver = driver;
        wait = new WebDriverWait(webDriver,30);
        PageFactory.initElements(webDriver, this);
    }

    public void searchFor(String text){
        searchInputFiled.clear();
        searchInputFiled.sendKeys(text);
        searchInputFiled.sendKeys(Keys.RETURN);
    }
}
