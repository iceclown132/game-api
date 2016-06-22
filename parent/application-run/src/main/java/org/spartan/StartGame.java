package org.spartan;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.spartan.cdi.Container;
import org.spartan.net.Service;
import org.spartan.net.netty.game.GameService;

public class StartGame {

	/**
	 * The address the service will bind to
	 */
	private static final SocketAddress game_host = new InetSocketAddress("localhost", 43594);

	/**
	 * The address the service will bind to
	 */
	@SuppressWarnings("unused")
	private static final SocketAddress monitor_host = new InetSocketAddress("localhost", 43593);
	
	/**
	 * The executor service for both the services
	 */
	private static final ExecutorService service = Executors.newFixedThreadPool(2);

	/**
	 * Application entry point
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Container container = new Container();
		container.initialize();
		
//		/*
//		 * Start the monitor service on its own thread
//		 */
//		service.submit(() -> {
//			Service monitor_service = new MonitorService(container);
//			monitor_service.startup();
//			monitor_service.bind(monitor_host);
//			return null;
//		});
		
		/*
		 * Start the game service on its own thread
		 */
		service.submit(() -> {
			Service game_service = new GameService(container);
			game_service.startup();
			game_service.bind(game_host);
			return null;
		});
	}

}
