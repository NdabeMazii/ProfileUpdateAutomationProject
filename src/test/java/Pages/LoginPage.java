package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class LoginPage {

    WebDriver driver;

    @FindBy(xpath = "//h2[@id='login-heading']")
    WebElement loginHeading_xpath;

    @FindBy(id = "login-email")
    WebElement loginEmail_id;

    @FindBy(id = "login-password")
    WebElement loginPassword_id;

    @FindBy(id = "login-submit")
    WebElement loginSubmitButton_id;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyLoginPageIsDisplayed() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(visibilityOf(loginHeading_xpath));
        loginHeading_xpath.click();
    }

    public void enterEmail(String email) {
        loginEmail_id.sendKeys(email);
    }

    public void enterPassword(String password) {
        loginPassword_id.sendKeys(password);
    }

    public void clickLoginButton() {
        loginSubmitButton_id.click();
    }

}
