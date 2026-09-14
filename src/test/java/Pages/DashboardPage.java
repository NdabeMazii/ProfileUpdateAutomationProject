package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class DashboardPage {

    WebDriver driver;

    @FindBy(xpath = "//span[normalize-space()='Welcome']")
    WebElement dashboardHeading_xpath;

    @FindBy(xpath = "//span[normalize-space()='Menu']")
    WebElement userMenuButton_xpath;

    @FindBy(xpath = "//span[normalize-space()='My Profile']")
    WebElement myProfileButton_xpath;

    public DashboardPage (WebDriver driver) {
        this.driver = driver;
    }

    public void verifyDashboardPageIsDisplayed() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(visibilityOf(dashboardHeading_xpath));
        dashboardHeading_xpath.click();
    }

    public void clickMenuButton() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(visibilityOf(userMenuButton_xpath));
        userMenuButton_xpath.click();
    }

    public void clickMyProfileButton() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(visibilityOf(myProfileButton_xpath));
        myProfileButton_xpath.click();
    }
}
