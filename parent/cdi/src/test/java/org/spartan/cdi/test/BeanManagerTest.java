package org.spartan.cdi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.spartan.cdi.Container;
import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.inject.annotation.Inject;
import org.spartan.cdi.scope.component.Component;
import org.spartan.cdi.scope.component.Session;
import org.spartan.cdi.scope.service.Service;

public class BeanManagerTest {

	/**
	 * The bean manager
	 */
	private static final Container container = new Container();

	@BeforeClass
	public static void initializeContainer() throws Exception {
		container.initialize();
	}
	
	@Test
	public void testComponent() {
		Session session = new Session();
		Bean<ComponentTest> bean = container.manager().get(ComponentTest.class);

		ComponentTest test_1 = container.manager().getInjectedReference(bean, session);
		ComponentTest test_2 = container.manager().getInjectedReference(bean, session);

		assertNotNull(test_1);
		assertNotNull(test_2);

		assertNotNull(test_1.test);
		assertNotNull(test_2.test);
		
		assertEquals(test_1, test_2);
		assertEquals(test_1.test, test_2.test);
	}
	
	@Test
	public void testService() {
		Bean<ServiceTest> bean = container.manager().get(ServiceTest.class);

		ServiceTest test_1 = container.manager().getInjectedReference(bean, new Session());
		ServiceTest test_2 = container.manager().getInjectedReference(bean, new Session());

		assertNotNull(test_1);
		assertNotNull(test_2);
		
		assertEquals(test_1, test_2);
	}
	
	@Component
	private static class ComponentTest {
		@Inject
		ServiceTest test;
	}
	
	@Service
	private static class ServiceTest {
//		double random = Math.random();
	}

}
