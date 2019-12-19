package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
	// 线程池50--固定
	private static ExecutorService threadPool = Executors.newFixedThreadPool(50);
	private static ServerSocket server;
	private static Map<String, Socket> channels = new HashMap<String, Socket>();

	static {
		try {
			server = new ServerSocket(8899);
			Accept ac = new Accept();
			Thread th1 = new Thread(ac);
			th1.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connect() {
		System.out.println(">>>>>>>>>>>>>>>>>>");
		System.out.println(channels);
		Socket so = null;
		for (Map.Entry<String, Socket> entry : channels.entrySet()) {
			if (entry.getKey().equals("127.0.0.1")) {
				System.out.println(">>>>>>>>>>>>>>>");
				so = entry.getValue();
				break;
			}
		}
		if (so != null) {
			System.out.println(so);
			SendThread sendThread = new SendThread(so);
			Thread th = new Thread(sendThread);
			th.start();
		}

	}
	public static void main(String[] args) {
		try {
			ServerMain s = new ServerMain();
			Thread.currentThread().sleep(1000 * 10);
			s.connect();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static class Accept implements Runnable {
		@Override
		public void run() {
			System.out.println(server);
			try {
				Socket socket = null;
				while ((socket = server.accept()) != null) {
					// socket = server.accept();
					RunnableImple run = new RunnableImple(socket);
					threadPool.execute(run);
					System.out.println("接受一个socket对象");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 接受Socket对象，处理该对象
	static class RunnableImple implements Runnable {
		public Socket socket;

		public RunnableImple(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			// 将获取的socket对象交给线程池
			SocketAddress address = socket.getRemoteSocketAddress();
			System.out.println(address);
			String ip = address.toString().split(":")[0].substring(1);
			channels.put(ip, socket);
			System.out.println(">>>>>>>>>>>>>>>>>>");
			System.out.println(channels);

		}
	}

}

