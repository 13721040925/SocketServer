package server;

import java.io.DataOutputStream;
import java.net.Socket;


public class SendThread implements Runnable {
	private DataOutputStream out;
	private Socket socket;

	public SendThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			out = new DataOutputStream(socket.getOutputStream());

			byte[] bt = CommondName.getXyModel();
			if (bt != null) {
				out.write(bt);// 输出信息
				out.flush();
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
}
