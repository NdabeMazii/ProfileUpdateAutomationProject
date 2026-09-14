package Steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepsDef extends Base{

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

    @And("i click on the choose photo button")
    public void i_click_on_the_choose_photo_button() {
        profilePage.clickChoosePhotoButton();
    }

}
