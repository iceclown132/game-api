package org.spartan.cdi.test;

import org.junit.Test;
import org.spartan.cdi.Container;
import org.spartan.cdi.event.Observes;
import org.spartan.cdi.scope.component.Component;
import org.spartan.cdi.scope.component.Session;
import org.spartan.cdi.scope.service.Service;

public class ObserverTest {

	@Test
	public void test() throws Exception {
		Container container = new Container();
		container.initialize();
		
		container.event().select(String.class).fire("yes", new Session());
	}
	
	@Service
	public static class TestInstanceObserver {
		
		public void test(@Observes String object) {
			System.out.println("hello service " + object);
		}
		
	}

	public static class TestStaticObserver {
		
		public static void test(@Observes Double object) {
			System.out.println("hello static " + object);
		}
		
	}

	@Component
	public static class TestComponentObserver {
		
		public void test(@Observes Integer object) {
			System.out.println("hello component " + object);
		}
		
	}

}
