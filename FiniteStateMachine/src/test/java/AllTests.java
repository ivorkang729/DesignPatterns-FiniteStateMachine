

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import integrationtest.AllIntegrationTests;
import unittest.AllUnitTests;

@RunWith(Suite.class)
@SuiteClasses({AllUnitTests.class, AllIntegrationTests.class})
public class AllTests {

}