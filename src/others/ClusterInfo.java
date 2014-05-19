package others;

public class ClusterInfo {
	public String strSecurityPolicy;
	public String strKerberosNode;
	public String strClusterID;

	public ClusterInfo() {
		init();
	}

	// 初始化
	public void init() {
		this.strSecurityPolicy = "1";
		this.strKerberosNode = "1";
		this.strClusterID = "1";
	}

	public String getStrSecurityPolicy() {
		return strSecurityPolicy;
	}

	public void setStrSecurityPolicy(String strSecurityPolicy) {
		this.strSecurityPolicy = strSecurityPolicy;
	}

	public String getStrKerberosNode() {
		return strKerberosNode;
	}

	public void setStrKerberosNode(String strKerberosNode) {
		this.strKerberosNode = strKerberosNode;
	}

	public String getStrClusterID() {
		return strClusterID;
	}

	public void setStrClusterID(String strClusterID) {
		this.strClusterID = strClusterID;
	}

	public static void main(String[] args) {
		ClusterInfo clusterInfo = new ClusterInfo();
		System.out.println(clusterInfo.getStrClusterID());
	}
}
