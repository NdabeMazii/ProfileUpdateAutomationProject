# Profile Update Automation Project

This project automates the profile-update journey on the Ndosi Simplified Automation website. It includes browser-based UI tests written with Cucumber, Selenium WebDriver, and TestNG, plus API tests written with Rest Assured and TestNG.

## What the tests cover

### UI scenario

The `@myProfile` scenario in `src/test/resources/features/myProfile.feature`:

1. Opens the login page and signs in.
2. Opens the menu and navigates to My Profile.
3. Opens Edit Profile and uploads `profilePhoto.jpg`.
4. Saves the change and checks the success alert.

The upload uses the HTML file input directly via Selenium `sendKeys`, so it does not need to interact with the operating-system file chooser.

### API scenario

`Tests.ProfileUpdateApiTest` exercises the following sequence:

| Operation | HTTP request | Expected status |
|---|---|---:|
| Log in | `POST /APIDEV/login` | 200 |
| Read profile | `GET /APIDEV/profile` | 200 |
| Update profile names | `PUT /APIDEV/profile` | 200 |
| Upload profile image | `POST /APIDEV/profile/image` | 200 |
| Read profile after upload | `GET /APIDEV/profile` | 200 |

The API builder extracts the login token and uses it for authenticated profile requests. The tests also check successful login and confirm that profile data, including an image field, is returned.

## Technology

- Java 21
- Maven
- Selenium WebDriver 4.28.0
- Cucumber 7.0.0 with its TestNG integration
- TestNG
- Rest Assured 5.5.0
- Allure TestNG adapter
- ExtentReports Cucumber adapter

## Project layout

```text
src/
└── test/
    ├── java/
    │   ├── Base/          API base URL configuration
    │   ├── Pages/         Selenium page objects
    │   ├── Payload/       API request payload builders
    │   ├── RequestBuilder/Rest Assured API calls
    │   ├── Runner/        Cucumber TestNG runner
    │   ├── Steps/         Cucumber step definitions and UI setup
    │   ├── Tests/         TestNG API tests
    │   └── Utils/         WebDriver setup
    └── resources/
        ├── features/      Cucumber feature files
        └── images/        UI/API test image
```

## Requirements

Install or configure the following before running the tests:

- JDK 21
- Maven 3.8 or later
- Google Chrome for the UI scenario
- ChromeDriver compatible with the installed Chrome version (Selenium Manager may resolve the driver automatically)
- Valid test-site credentials
- Allure command-line tool, only if generating or opening an Allure HTML report locally

Check the Java and Maven installations:

```bash
java -version
mvn -version
```

Run all commands from the repository root, where `pom.xml` is located.

## Configuration and credentials

### API credentials

The API suite reads credentials from Java system properties first, then from environment variables:

| Purpose | System property | Environment variable |
|---|---|---|
| Login email | `api.email` | `API_EMAIL` |
| Login password | `api.password` | `API_PASSWORD` |

On PowerShell, set the environment variables for the current terminal session:

```powershell
$env:API_EMAIL = "your-test-account-email"
$env:API_PASSWORD = "your-test-account-password"
mvn clean test
```

On macOS/Linux:

```bash
export API_EMAIL="your-test-account-email"
export API_PASSWORD="your-test-account-password"
mvn clean test
```

You can also supply the system properties directly to Maven:

```bash
mvn clean test -Dapi.email="your-test-account-email" -Dapi.password="your-test-account-password"
```

Do not commit real credentials to feature files, source code, or this README. The UI feature currently has example credentials in its Scenario Outline table; replace them with credentials for a dedicated test account and move them to a secure configuration mechanism before using the repository in a shared environment.

### API base URL

The default API base URL is `https://ndosiautomation.co.za`. Override it with the `api.baseUrl` system property or `API_BASE_URL` environment variable:

```bash
mvn test -Dapi.baseUrl="https://your-api-host"
```

### Profile image

The API test uses `src/test/resources/images/profilePhoto.jpg` by default. Set `api.profileImage` to use another image:

```bash
mvn test -Dapi.profileImage="src/test/resources/images/another-photo.jpg"
```

The UI scenario also references `profilePhoto.jpg` in the same resources image directory.

## Running tests

Run the complete Maven test suite:

```bash
mvn clean test
```

Run only the API TestNG suite:

```bash
mvn -Dtest=Tests.ProfileUpdateApiTest test
```

The API suite requires both API credentials to be set. If they are missing, setup fails and dependent API tests are skipped.

Run only the Cucumber UI runner:

```bash
mvn -Dtest=Runner.runner test
```

The Cucumber runner currently selects scenarios tagged `@myProfile`. The browser is configured as Chrome in `Steps.Base`.

## Reports and screenshots

### Test output and reports

Maven/Surefire writes test execution output under `target/surefire-reports/`. The Cucumber runner writes its HTML report to:

```text
target/cucumber-report.html
```

Allure test result files are written to the project-root `allure-results/` directory. Generate an Allure HTML report with the Allure CLI:

```bash
allure generate allure-results --clean -o allure-report
```

Serve/open the report locally:

```bash
allure open allure-report
```

Alternatively, run `allure serve allure-results` to generate and serve a temporary report. Opening the generated `index.html` directly with a `file://` URL may not load all report resources correctly.

The GitHub Actions workflow uploads the `allure-results/` directory as an artifact named `allure-results`; it currently does not generate or upload a ready-to-open HTML Allure report.

### Screenshots

The Cucumber `@After` hook captures a browser screenshot after each scenario and attaches it to the active Allure test result as a PNG. The profile-update step also captures the browser window while the success alert is still open and attaches it as **Profile update success alert** before accepting the alert. The browser is closed after the scenario screenshot is taken. Screenshots are part of the `allure-results/` output and appear in the scenario's attachments in the generated Allure report; they are not saved as separate image files in the source tree. Capturing the visible native browser alert requires a headed desktop session; screen capture is unavailable in a headless environment.

## GitHub Actions

The workflow is located at `.github/workflows/ProfileUpdateAutomationProject.yml`. It runs on pushes to `main` and can also be started manually using `workflow_dispatch`.

The workflow expects these repository Actions secrets:

- `API_EMAIL`
- `API_PASSWORD`

Add them in **Settings → Secrets and variables → Actions**. The workflow exposes them to the Maven test step as environment variables. Do not place their values directly in the workflow YAML.

After the workflow completes, download the `allure-results` artifact from the run’s **Artifacts** section. If the test step fails, use the Maven/Surefire output and reports to inspect the failure.

## Troubleshooting

| Symptom | Likely cause | What to check |
|---|---|---|
| `Set system property api.email or environment variable API_EMAIL` | API email is unset | Configure `API_EMAIL` or `-Dapi.email=...` in the local run configuration or GitHub Actions secret |
| Missing `API_PASSWORD` | API password is unset | Configure `API_PASSWORD` or `-Dapi.password=...` |
| API tests are skipped after setup fails | `@BeforeClass` could not initialize credentials or image | Read the first setup error and verify both credentials and the image file |
| UI upload cannot find its file | The supplied image name/path does not exist | Check `src/test/resources/images/` and the filename used by the feature |
| Selenium reports an incompatible browser driver | Browser and driver versions differ | Update Chrome or allow Selenium Manager to resolve the matching driver |
| Allure report is empty or missing | No results were generated, or the wrong results folder was used | Run the tests first and generate from the project-root `allure-results/` directory |
| UI scenario is not selected | Runner tag filter differs from the feature tag | Confirm that the scenario has the `@myProfile` tag |

## Notes

- `target/` and other generated build output should not be committed.
- Use a dedicated test account and a non-production environment for automated tests.
- Test account credentials included in a feature file should be replaced and rotated if they have been shared or committed.
