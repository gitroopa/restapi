package testRunner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.example.RestApiUtil;
import org.junit.runner.RunWith;
import org.testng.Reporter;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.Properties;

@RunWith(Cucumber.class)
@CucumberOptions(
//        monochrome = true,
        //features = {"classpath:features/"},
        features = {"src/test/resources/featurefiles/APItests_OpenWeatherMap.feature"},
//        plugin = {"pretty"},
        glue = {"stepDefs"},

//        plugin = {"pretty",
//                "html:target/cucumber-reports/cucumber.html",
//                "json:target/cucumber-reports/cucumber.json"
//        },
       plugin = {"pretty"},
        tags = "@SysTest1"
)
public class TestRunner {

}
