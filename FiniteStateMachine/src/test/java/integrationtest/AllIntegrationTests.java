package integrationtest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ IntegrationTest01.class, IntegrationTest02.class, IntegrationTest03.class, IntegrationTest04.class, IntegrationTest05.class, IntegrationTest06.class, IntegrationTest07.class})
public class AllIntegrationTests {

}