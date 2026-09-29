package Steps;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.AttachmentOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class StepsDef extends Base{

    @After
    public void attachScreenshotToAllure(Scenario scenario) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.attachment(
                    "Screenshot - " + scenario.getName(),
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    AttachmentOptions.withFileExtension(".png"));
        } finally {
            driver.quit();
        }
    }

    @Given("i am on the login page")
    public void i_am_on_the_login_page() {
        homePage.clickLoginButton();
        loginPage.verifyLoginPageIsDisplayed();
    }

    @And("i enter my email (.*)$")
    public void i_enter_my_email(String email) {
        loginPage.enterEmail(email);
    }

    @And("i enter my password (.*)$")
    public void i_enter_my_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("i click on the login button")
    public void i_click_on_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("i should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        dashboardPage.verifyDashboardPageIsDisplayed();
    }

    @And("i click on the menu button")
    public void i_click_on_the_menu_button() {
        dashboardPage.clickMenuButton();
    }

    @And("i click on the my profile button")
    public void i_click_on_the_my_profile_button() {
        dashboardPage.clickMyProfileButton();
    }

    @Then("i should see my profile page")
    public void i_should_see_my_profile_page() {
        profilePage.verifyProfilePageIsDisplayed();
    }

    @And("i click on the edit profile button")
    public void i_click_on_the_edit_profile_button() {
        profilePage.clickEditProfileButton();
    }

    @And("i upload a new profile picture {string}")
    public void i_upload_a_new_profile_picture(String fileName) {
        profilePage.uploadNewProfilePicture(fileName);
    }

    @And("i click on the save changes button")
    public void i_click_on_the_save_changes_button() {
        profilePage.clickSaveChangesButton();
    }

    @Then("i should see the profile update success alert")
    public void verify_the_profile_picture_is_updated() {
        profilePage.verifyProfileUpdatedAlert();
    }

}
