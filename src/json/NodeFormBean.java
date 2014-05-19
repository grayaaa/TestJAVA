package json;


import java.util.List;

public class NodeFormBean {
	private String strNodeName;
	private String strConfStatus;
	private String strDescription;

	private String strManageIP;
	private String strRackName;
	private String strRackType;
	private String strRackPosition;
	private String strHeight;

	private String strBladeChassis;
	private String strChassisType;

	private List<String> listRole;
	private Boolean bIsNN;
	private Boolean bIsSNN;
	private Boolean bIsDN;
	private Boolean bIsJN;
	private Boolean bIsJT;
	private Boolean bIsTT;
	private Boolean bIsResourceManager;
	private Boolean bIsNodeManager;
	private Boolean bIsJobHistoryServer;
	private Boolean bIsZK;
	private Boolean bIsHMaster;
	private Boolean bIsRegionServer;
	private Boolean bIsThrift;
	private Boolean bIsMetastore;
	private Boolean bIsHiveServer;
	private Boolean bIsHiveServer2;

	@Override
	public String toString() {

		return "NodeFormBean [strNodeName=" + strNodeName + ", strConfStatus=" + strConfStatus + ", strManageIP="
				+ strManageIP + ", strRackName=" + strRackName + ", listRole=" + listRole + "]";
	}

	public String getStrNodeName() {
		return strNodeName;
	}

	public void setStrNodeName(String strNodeName) {
		this.strNodeName = strNodeName;
	}

	public String getStrConfStatus() {
		return strConfStatus;
	}

	public void setStrConfStatus(String strConfStatus) {
		this.strConfStatus = strConfStatus;
	}

	public String getStrManageIP() {
		return strManageIP;
	}

	public void setStrManageIP(String strManageIP) {
		this.strManageIP = strManageIP;
	}

	public String getStrRackName() {
		return strRackName;
	}

	public void setStrRackName(String strRackName) {
		this.strRackName = strRackName;
	}

	public List<String> getListRole() {
		return listRole;
	}

	public void setListRole(List<String> listRole) {
		this.listRole = listRole;
	}

	public Boolean getbIsNN() {
		return bIsNN;
	}

	public void setbIsNN(Boolean bIsNN) {
		this.bIsNN = bIsNN;
	}

	public Boolean getbIsSNN() {
		return bIsSNN;
	}

	public void setbIsSNN(Boolean bIsSNN) {
		this.bIsSNN = bIsSNN;
	}

	public Boolean getbIsDN() {
		return bIsDN;
	}

	public void setbIsDN(Boolean bIsDN) {
		this.bIsDN = bIsDN;
	}

	public Boolean getbIsJN() {
		return bIsJN;
	}

	public void setbIsJN(Boolean bIsJN) {
		this.bIsJN = bIsJN;
	}

	public Boolean getbIsJT() {
		return bIsJT;
	}

	public void setbIsJT(Boolean bIsJT) {
		this.bIsJT = bIsJT;
	}

	public Boolean getbIsTT() {
		return bIsTT;
	}

	public void setbIsTT(Boolean bIsTT) {
		this.bIsTT = bIsTT;
	}

	public Boolean getbIsZK() {
		return bIsZK;
	}

	public void setbIsZK(Boolean bIsZK) {
		this.bIsZK = bIsZK;
	}

	public Boolean getbIsHMaster() {
		return bIsHMaster;
	}

	public void setbIsHMaster(Boolean bIsHMaster) {
		this.bIsHMaster = bIsHMaster;
	}

	public Boolean getbIsRegionServer() {
		return bIsRegionServer;
	}

	public void setbIsRegionServer(Boolean bIsRegionServer) {
		this.bIsRegionServer = bIsRegionServer;
	}

	public Boolean getbIsThrift() {
		return bIsThrift;
	}

	public void setbIsThrift(Boolean bIsThrift) {
		this.bIsThrift = bIsThrift;
	}

	public Boolean getbIsHiveServer() {
		return bIsHiveServer;
	}

	public void setbIsHiveServer(Boolean bIsHiveServer) {
		this.bIsHiveServer = bIsHiveServer;
	}

	public Boolean getbIsResourceManager() {
		return bIsResourceManager;
	}

	public void setbIsResourceManager(Boolean bIsResourceManager) {
		this.bIsResourceManager = bIsResourceManager;
	}

	public Boolean getbIsNodeManager() {
		return bIsNodeManager;
	}

	public void setbIsNodeManager(Boolean bIsNodeManager) {
		this.bIsNodeManager = bIsNodeManager;
	}

	public Boolean getbIsJobHistoryServer() {
		return bIsJobHistoryServer;
	}

	public void setbIsJobHistoryServer(Boolean bIsJobHistoryServer) {
		this.bIsJobHistoryServer = bIsJobHistoryServer;
	}

	public Boolean getbIsMetastore() {
		return bIsMetastore;
	}

	public void setbIsMetastore(Boolean bIsMetastore) {
		this.bIsMetastore = bIsMetastore;
	}

	public Boolean getbIsHiveServer2() {
		return bIsHiveServer2;
	}

	public void setbIsHiveServer2(Boolean bIsHiveServer2) {
		this.bIsHiveServer2 = bIsHiveServer2;
	}

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public String getStrRackType() {
		return strRackType;
	}

	public void setStrRackType(String strRackType) {
		this.strRackType = strRackType;
	}

	public String getStrRackPosition() {
		return strRackPosition;
	}

	public void setStrRackPosition(String strRackPosition) {
		this.strRackPosition = strRackPosition;
	}

	public String getStrHeight() {
		return strHeight;
	}

	public void setStrHeight(String strHeight) {
		this.strHeight = strHeight;
	}

	public String getStrBladeChassis() {
		return strBladeChassis;
	}

	public void setStrBladeChassis(String strBladeChassis) {
		this.strBladeChassis = strBladeChassis;
	}

	public String getStrChassisType() {
		return strChassisType;
	}

	public void setStrChassisType(String strChassisType) {
		this.strChassisType = strChassisType;
	}

}
