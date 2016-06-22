package org.spartan.clock.test;


import org.junit.Test;
import org.spartan.cdi.Container;
import org.spartan.cdi.scope.service.Service;
import org.spartan.clock.Clock;

@Service
public class ClockTest {

	@Test
	public void test() throws Exception {
		Container container = new Container();
		container.initialize();

		Clock clock = container.manager().get(Clock.class).get(null, container.manager());
		clock.repeat(future -> System.out.println("die lijpe mocro flavor")).onError(exception -> System.out.println("what is up"));
		
//		while(true);
	}

}
