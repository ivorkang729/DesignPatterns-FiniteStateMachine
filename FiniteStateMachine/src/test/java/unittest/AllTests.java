package unittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UnitTest01.class, UnitTest02.class, UnitTest03.class, UnitTest04.class, UnitTest05.class,
        UnitTest06.class, UnitTest07.class, UnitTest08.class, UnitTest09.class })
public class AllTests {

}