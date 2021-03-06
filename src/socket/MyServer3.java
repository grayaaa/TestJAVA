package socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

/*
 * 传输对象
 代码写完了，下面就需要产生keystore文件了，运行下面的命令
 [plain] view plaincopyprint?
 keytool -genkey -alias mysocket -keyalg RSA -keystore mysocket.jks
 在提示输入项中，密码项自己给定，其它都不改直接回车，这里我使用的密码是“mysocket”。
 运行Server
 [plain] view plaincopyprint?
 java -Djavax.net.ssl.keyStore=mysocket.jks -Djavax.net.ssl.keyStorePassword=mysocket com.googlecode.garbagecan.test.socket.ssl.MyServer
 运行Client
 [plain] view plaincopyprint?
 java -Djavax.net.ssl.trustStore=mysocket.jks  -Djavax.net.ssl.trustStorePassword=mysocket com.googlecode.garbagecan.test.socket.ssl.MyClient
 */
public class MyServer3 {
	private final static Logger logger = Logger.getLogger(MyServer3.class.getName());

	public static void main(String[] args) {
		try {
			ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
			ServerSocket server = factory.createServerSocket(10000);

			while (true) {
				Socket socket = server.accept();
				invoke(socket);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void invoke(final Socket socket) throws IOException {
		new Thread(new Runnable() {
			public void run() {
				ObjectInputStream is = null;
				ObjectOutputStream os = null;
				try {
					is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
					os = new ObjectOutputStream(socket.getOutputStream());

					Object obj = is.readObject();
					User user = (User) obj;
					System.out.println("user: " + user.getName() + "/" + user.getPassword());

					user.setName(user.getName() + "_new");
					user.setPassword(user.getPassword() + "_new");

					os.writeObject(user);
					os.flush();
				} catch (IOException ex) {
					logger.log(Level.SEVERE, null, ex);
				} catch (ClassNotFoundException ex) {
					logger.log(Level.SEVERE, null, ex);
				} finally {
					try {
						is.close();
					} catch (Exception ex) {
					}
					try {
						os.close();
					} catch (Exception ex) {
					}
					try {
						socket.close();
					} catch (Exception ex) {
					}
				}
			}
		}).start();
	}
}
