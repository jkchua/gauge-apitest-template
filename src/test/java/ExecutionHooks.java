import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;

public class ExecutionHooks {

    @BeforeSuite
    public void beforeSuite() {
        // Execute any code that is required before test suite execution.
    }

    @AfterSuite
    public void afterSuite() {
        // Execute any code that is required after test suite execution.
    }
}
