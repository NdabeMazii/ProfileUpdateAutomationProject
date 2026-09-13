package Steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepsDef extends Base{

    @Given("i am on the login page")
    public void i_am_on_the_login_page() {
        homePage.clickLoginButton();
    }
    @And("i enter my email admin@gmail.com")
    public void i_enter_my_email_admin_gmail_com() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @And("i enter my password @{int}")
    public void i_enter_my_password(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("i click on the login button")
    public void i_click_on_the_login_button() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("i should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
