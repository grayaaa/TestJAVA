import java.net.InetAddress;

public class Host2IP {
	public static void main(String args[]) {
		String args1[] = args;
		int i = args1.length;
		for (int j = 0; j < i; j++) {
			String s = args1[j];
			try {
				InetAddress inetaddress = InetAddress.getByName(s);
				System.out.println(inetaddress.getHostAddress());
			} catch (Exception exception) {
				System.out.println();
			}
		}

	}
}
