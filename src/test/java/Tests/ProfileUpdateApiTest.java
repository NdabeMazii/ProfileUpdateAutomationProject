package Tests;

import RequestBuilder.ApiRequestBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.equalTo;

public class ProfileUpdateApiTest {

    private String email;
    private String password;
    private Path imagePath;
    private ApiRequestBuilder api;
    private String firstName;
    private String lastName;

    @BeforeClass
    public void setup() {
        email = requiredSetting("api.email", "API_EMAIL");
        password = requiredSetting("api.password", "API_PASSWORD");
        imagePath = Path.of(System.getProperty(
                "api.profileImage",
                "src/test/resources/images/profilePhoto.jpg")).toAbsolutePath().normalize();
        if (!Files.isRegularFile(imagePath)) {
            throw new IllegalArgumentException("Profile image file does not exist: " + imagePath);
        }
        api = new ApiRequestBuilder();
    }

    @Test
    public void loginUserTest() {
        Response response = api.loginUserResponse(email, password);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "loginUserTest")
    public void getProfileTest() {
        Response response = api.getProfileResponse();
        response.then()
                .assertThat()
                .statusCode(200);

        firstName = firstNonBlank(
                response.jsonPath().getString("data.firstName"),
                response.jsonPath().getString("data.user.firstName"),
                response.jsonPath().getString("data.FirstName"));
        lastName = firstNonBlank(
                response.jsonPath().getString("data.lastName"),
                response.jsonPath().getString("data.user.lastName"),
                response.jsonPath().getString("data.LastName"));
        Assert.assertNotNull(firstName, "GET /APIDEV/profile should return firstName");
        Assert.assertNotNull(lastName, "GET /APIDEV/profile should return lastName");
    }

    @Test(dependsOnMethods = "getProfileTest")
    public void updateProfileTest() {
        api.updateProfileResponse(firstName, lastName)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "updateProfileTest")
    public void uploadProfileImageTest() {
        api.uploadProfileImageResponse(imagePath)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "uploadProfileImageTest")
    public void verifyUpdatedProfileTest() {
        Response response = api.getProfileResponse();
        response.then()
                .assertThat()
                .statusCode(200);

        String savedImage = firstNonBlank(
                response.jsonPath().getString("data.profilePicture"),
                response.jsonPath().getString("data.ProfilePicture"),
                response.jsonPath().getString("data.profileImage"),
                response.jsonPath().getString("data.ProfileImage"),
                response.jsonPath().getString("data.profileImageUrl"),
                response.jsonPath().getString("data.ProfileImageUrl"),
                response.jsonPath().getString("data.imageUrl"),
                response.jsonPath().getString("data.ImageUrl"));
        Assert.assertNotNull(savedImage, "Profile image should be present after upload");
    }

    private String requiredSetting(String propertyName, String environmentName) {
        String value = System.getProperty(propertyName);
        if (value == null || value.isBlank()) {
            value = System.getenv(environmentName);
        }
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Set system property " + propertyName + " or environment variable " + environmentName);
        }
        return value;
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }
}
