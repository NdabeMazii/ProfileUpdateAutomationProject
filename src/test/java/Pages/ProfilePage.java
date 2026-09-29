package Pages;

import io.qameta.allure.Allure;
import io.qameta.allure.AttachmentOptions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import javax.imageio.ImageIO;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ProfilePage {

    WebDriver driver;
    private Rectangle browserWindowBounds;

    @FindBy(xpath = "//h2[contains(text(),'\uD83D\uDC64 My Profile')]")
    WebElement profileHeading_xpath;

    @FindBy(xpath = "//button[contains(text(),'✏\uFE0F Edit Profile')]")
    WebElement editProfileButton_xpath;

    @FindBy(id = "profilePicture")
    WebElement fileInput;

    @FindBy(xpath = "//button[contains(text(),'\uD83D\uDCBE Save Changes')]")
    WebElement saveChangesButton_xpath;

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

    public void clickSaveChangesButton() {
        new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(visibilityOf(saveChangesButton_xpath));
        org.openqa.selenium.Point windowPosition = driver.manage().window().getPosition();
        org.openqa.selenium.Dimension windowSize = driver.manage().window().getSize();
        browserWindowBounds = new Rectangle(
                windowPosition.getX(),
                windowPosition.getY(),
                windowSize.getWidth(),
                windowSize.getHeight());
        saveChangesButton_xpath.click();
    }

    public void verifyProfileUpdatedAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        try {
            attachAlertScreenshot();
            Assert.assertEquals(alert.getText(), "Profile updated successfully!");
        } finally {
            alert.accept();
        }
    }

    private void attachAlertScreenshot() {
        if (browserWindowBounds == null) {
            throw new IllegalStateException("Browser window bounds were not captured before the alert opened");
        }

        try {
            BufferedImage screenshot = new Robot().createScreenCapture(browserWindowBounds);
            ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
            if (!ImageIO.write(screenshot, "png", imageBytes)) {
                throw new IllegalStateException("No PNG writer is available for the alert screenshot");
            }
            Allure.attachment(
                    "Profile update success alert",
                    "image/png",
                    new ByteArrayInputStream(imageBytes.toByteArray()),
                    AttachmentOptions.withFileExtension(".png"));
        } catch (AWTException | IOException e) {
            throw new IllegalStateException("Unable to capture the profile update alert screenshot", e);
        }
    }

}
