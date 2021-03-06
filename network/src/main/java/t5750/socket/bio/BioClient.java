package t5750.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import t5750.socket.util.SocketUtil;

public class BioClient {
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			socket = new Socket(SocketUtil.INET_HOST, SocketUtil.PORT_8765);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			// 向服务器端发送数据
			out.println("接收到客户端的请求数据...");
			String response = in.readLine();
			System.out.println("Client: " + response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			socket = null;
		}
	}
}
