package Payload;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PayloadBuilder {

    private PayloadBuilder() {
    }

    public static Map<String, String> loginUserPayload(String email, String password) {
        Map<String, String> loginUser = new LinkedHashMap<>();
        loginUser.put("email", email);
        loginUser.put("password", password);
        return loginUser;
    }

    public static Map<String, String> updateProfilePayload(String firstName, String lastName) {
        Map<String, String> updateProfile = new LinkedHashMap<>();
        updateProfile.put("firstName", firstName);
        updateProfile.put("lastName", lastName);
        return updateProfile;
    }
}
