package reporting;

import com.aventstack.extentreports.ExtentTest;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ExtentCucumberAdapter {
	
	private static final com.aventstack.extentreports.ExtentReports extent =
            ExtentManager.getInstance();

    @Before
    public void beforeScenario(Scenario scenario) {

        ExtentTest test = extent.createTest(scenario.getName());
        ExtentTestManager.setTest(test);
    }

    @AfterStep
    public void afterStep(Scenario scenario) {

        if (scenario.isFailed()) {
            ExtentTestManager.getTest()
                    .fail("Step Failed: " + scenario.getName());
        } else {
            ExtentTestManager.getTest()
                    .pass("Step Passed");
        }
    }

    @After
    public void afterScenario(Scenario scenario) {

        if (scenario.isFailed()) {
            ExtentTestManager.getTest().fail("Scenario Failed");
        } else {
            ExtentTestManager.getTest().pass("Scenario Passed");
        }

        ExtentTestManager.unload();   // 🔥 important for parallel
    }

    // 🔥 Flush ONLY ONCE after all scenarios
    @AfterAll
    public static void tearDown() {
        extent.flush();
    }

}
