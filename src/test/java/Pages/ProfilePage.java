package Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ProfilePage {

    WebDriver driver;

    @FindBy(xpath = "//h2[contains(text(),'\uD83D\uDC64 My Profile')]")
    WebElement profileHeading_xpath;

    @FindBy(xpath = "//button[contains(text(),'✏\uFE0F Edit Profile')]")
    WebElement editProfileButton_xpath;

    @FindBy(id = "profilePicture")
    WebElement fileInput;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyProfilePageIsDisplayed() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(visibilityOf(profileHeading_xpath));
        profileHeading_xpath.click();
    }

    public void clickEditProfileButton() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(visibilityOf(editProfileButton_xpath));
        editProfileButton_xpath.click();
    }

    public void uploadNewProfilePicture(String fileName) {
        Path imagesDirectory = Path.of("src", "test", "resources", "images")
                .toAbsolutePath()
                .normalize();
        Path imagePath = imagesDirectory.resolve(fileName).normalize();
        if (!imagePath.startsWith(imagesDirectory) || !Files.isRegularFile(imagePath)) {
            throw new IllegalArgumentException("Profile picture not found in test resources: " + imagePath);
        }
        if (!"file".equalsIgnoreCase(fileInput.getAttribute("type"))) {
            throw new IllegalStateException("The profile picture element is not a file input");
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].removeAttribute('hidden');"
                        + "arguments[0].style.display='block';"
                        + "arguments[0].style.visibility='visible';",
                fileInput);
        fileInput.sendKeys(imagePath.toString());
    }

}
