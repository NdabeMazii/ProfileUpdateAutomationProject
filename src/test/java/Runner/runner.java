package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners(AllureTestNg.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"Steps"},
        tags = "@myProfile",
        plugin = {"html:target/cucumber-report.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true,
        publish = false
)
public class runner extends AbstractTestNGCucumberTests {
}
