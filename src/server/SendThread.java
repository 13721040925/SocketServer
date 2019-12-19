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
				out.write(bt);// �����Ϣ
				out.flush();
			}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

	}
}
