package ca.mcgill.ecse428.unitrade.unitradebackend.acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/../../features" },
        glue = { "ca.mcgill.ecse428.unitrade.unitradebackend.acceptance" },
        plugin = { "pretty", "html:target/cucumber-reports" }
)
public class CucumberTest {
}
