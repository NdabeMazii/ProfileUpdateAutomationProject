package RequestBuilder;

import io.restassured.response.Response;

import java.nio.file.Path;

import static Base.BaseURIs.baseURL;
import static Payload.PayloadBuilder.loginUserPayload;
import static Payload.PayloadBuilder.updateProfilePayload;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ApiRequestBuilder {

    private String authToken;

    public Response loginUserResponse(String email, String password) {
        Response response = given()
                .baseUri(baseURL)
                .basePath("/APIDEV/login")
                .contentType(JSON)
                .body(loginUserPayload(email, password))
                .post();

        authToken = response.jsonPath().getString("data.token");
        return response;
    }

    public Response getProfileResponse() {
        return authenticatedRequest()
                .get("/APIDEV/profile");
    }

    public Response updateProfileResponse(String firstName, String lastName) {
        return authenticatedRequest()
                .contentType(JSON)
                .body(updateProfilePayload(firstName, lastName))
                .put("/APIDEV/profile");
    }

    public Response uploadProfileImageResponse(Path imagePath) {
        return authenticatedRequest()
                .multiPart("profileImage", imagePath.toFile(), "image/jpeg")
                .multiPart("replaceExisting", "true")
                .post("/APIDEV/profile/image");
    }

    private io.restassured.specification.RequestSpecification authenticatedRequest() {
        if (authToken == null || authToken.isBlank()) {
            throw new IllegalStateException("Login must succeed before calling authenticated profile endpoints");
        }
        return given()
                .baseUri(baseURL)
                .auth().oauth2(authToken);
    }
}
