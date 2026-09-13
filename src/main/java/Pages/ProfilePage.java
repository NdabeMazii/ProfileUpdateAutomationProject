package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ProfilePage {

    WebDriver driver;

    @FindBy(xpath = "//h2[contains(text(),'\uD83D\uDC64 My Profile')]")
    WebElement profileHeading_xpath;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyProfilePageIsDisplayed() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(visibilityOf(profileHeading_xpath));
        profileHeading_xpath.click();
    }


}
