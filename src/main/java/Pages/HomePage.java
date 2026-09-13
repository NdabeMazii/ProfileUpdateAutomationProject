package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class HomePage {

    WebDriver driver;

    @FindBy(xpath = "//span[normalize-space()='Login']")
    WebElement loginButton_xpath;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLoginButton() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(visibilityOf(loginButton_xpath));
        loginButton_xpath.click();
    }
}
