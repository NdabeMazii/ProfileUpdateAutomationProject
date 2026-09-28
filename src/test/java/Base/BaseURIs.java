package Base;

public final class BaseURIs {

    public static final String baseURL = System.getProperty(
            "api.baseUrl",
            System.getenv().getOrDefault("API_BASE_URL", "https://ndosiautomation.co.za"));

    private BaseURIs() {
    }
}
