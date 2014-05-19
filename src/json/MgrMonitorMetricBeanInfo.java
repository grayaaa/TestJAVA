package json;


import com.google.gson.annotations.SerializedName;

public class MgrMonitorMetricBeanInfo {
	/**
	 * 剩余CPU
	 */
	@SerializedName("ParastorNodeCpuIdle")
	private Double dCPUIdle;
	/**
	 * 剩余内存
	 */
	@SerializedName("ParastorNodeMemFree")
	private Double dMemoryFree;
	/**
	 * 内存总量
	 */
	@SerializedName("ParastorNodeMemTotal")
	private Double dMemoryTotal;
	/**
	 * 所有IP列表
	 */
	private String strAllNetCard;
	/**
	 * 节点名
	 */
	private String strHAIP;
	@SerializedName("OfsNodeName")
	private String strNodeName;
	/**
	 * 节点显示名
	 */
	@SerializedName("OfsNodeDisplayName")
	private String strNodeDisplayName;
	/**
	 * 节点状态
	 */
	@SerializedName("ParastorNodeState")
	private String strNodeStatus;
	/**
	 * 管理IP
	 */
	@SerializedName("OfsNodeManageIP")
	private String strManageIP;
	/**
	 * 主盘总容量
	 */
	@SerializedName("OfsNode_master_capacity")
	private String strMainDiskTotalCapacity;
	/**
	 * 主盘已用容量
	 */
	@SerializedName("OfsNode_master_used_space")
	private String strMainDiskUsageCapacity;
	/**
	 * 日志盘总容量
	 */
	@SerializedName("OfsNode_jnl_capacity")
	private String strLogDiskTotalCapacity;
	/**
	 * 日志盘已用容量
	 */
	@SerializedName("OfsNode_jnl_used_space")
	private String strLogDiskUsageCapacity;
	/**
	 * HA状态
	 */
	@SerializedName("ParastorMgrHAStatus")
	private Object objHaStatus;
	/**
	 * 串口状态
	 */
	@SerializedName("ParastoroNodeNetSerialStatus")
	private Object objNetSerialStatus;
	/**
	 * 网络层NAL服务状态
	 */
	@SerializedName("OfsNodeState_ha_nal")
	private String strNetworkNALStatus;
	/**
	 * 网络通断Ping状态
	 */
	@SerializedName("OfsNodeState_ha_ping")
	private String strNetworkPingStatus;
	/**
	 * 物理节点id
	 */
	@SerializedName("OfsNodeid")
	private String strNodeID;
	/**
	 * 所属机柜
	 */
	@SerializedName("OfsCabinet")
	private String strCabinet;
	private String strCabinetLocation;

	public String getStrHAIP() {
		return strHAIP;
	}

	public void setStrHAIP(String strHAIP) {
		this.strHAIP = strHAIP;
	}

	public String getStrCabinetLocation() {
		return strCabinetLocation;
	}

	public void setStrCabinetLocation(String strCabinetLocation) {
		this.strCabinetLocation = strCabinetLocation;
	}

	public String getStrNodeID() {
		return strNodeID;
	}

	public void setStrNodeID(String strNodeID) {
		this.strNodeID = strNodeID;
	}

	public String getStrCabinet() {
		return strCabinet;
	}

	public void setStrCabinet(String strCabinet) {
		this.strCabinet = strCabinet;
	}

	public String getStrCabinetPosition() {
		return strCabinetPosition;
	}

	public void setStrCabinetPosition(String strCabinetPosition) {
		this.strCabinetPosition = strCabinetPosition;
	}

	/**
	 * 机柜位置
	 */
	@SerializedName("OfsNodeLoc")
	private String strCabinetPosition;

	public String getStrNodeName() {
		return strNodeName;
	}

	public void setStrNodeName(String strNodeName) {
		this.strNodeName = strNodeName;
	}

	public String getStrNodeStatus() {
		return strNodeStatus;
	}

	public void setStrNodeStatus(String strNodeStatus) {
		this.strNodeStatus = strNodeStatus;
	}

	public String getStrManageIP() {
		return strManageIP;
	}

	public void setStrManageIP(String strManageIP) {
		this.strManageIP = strManageIP;
	}

	public String getStrAllNetCard() {
		return strAllNetCard;
	}

	public void setStrAllNetCard(String strAllNetCard) {
		this.strAllNetCard = strAllNetCard;
	}

	public String getStrMainDiskTotalCapacity() {
		return strMainDiskTotalCapacity;
	}

	public void setStrMainDiskTotalCapacity(String strMainDiskTotalCapacity) {
		this.strMainDiskTotalCapacity = strMainDiskTotalCapacity;
	}

	public String getStrMainDiskUsageCapacity() {
		return strMainDiskUsageCapacity;
	}

	public void setStrMainDiskUsageCapacity(String strMainDiskUsageCapacity) {
		this.strMainDiskUsageCapacity = strMainDiskUsageCapacity;
	}

	public String getStrLogDiskTotalCapacity() {
		return strLogDiskTotalCapacity;
	}

	public void setStrLogDiskTotalCapacity(String strLogDiskTotalCapacity) {
		this.strLogDiskTotalCapacity = strLogDiskTotalCapacity;
	}

	public String getStrLogDiskUsageCapacity() {
		return strLogDiskUsageCapacity;
	}

	public void setStrLogDiskUsageCapacity(String strLogDiskUsageCapacity) {
		this.strLogDiskUsageCapacity = strLogDiskUsageCapacity;
	}

	public Object getObjHaStatus() {
		return objHaStatus;
	}

	public void setObjHaStatus(Object objHaStatus) {
		this.objHaStatus = objHaStatus;
	}

	public Object getObjNetSerialStatus() {
		return objNetSerialStatus;
	}

	public void setObjNetSerialStatus(Object objNetSerialStatus) {
		this.objNetSerialStatus = objNetSerialStatus;
	}

	public String getStrNetworkNALStatus() {
		return strNetworkNALStatus;
	}

	public void setStrNetworkNALStatus(String strNetworkNALStatus) {
		this.strNetworkNALStatus = strNetworkNALStatus;
	}

	public String getStrNetworkPingStatus() {
		return strNetworkPingStatus;
	}

	public void setStrNetworkPingStatus(String strNetworkPingStatus) {
		this.strNetworkPingStatus = strNetworkPingStatus;
	}

	public String getStrNodeDisplayName() {
		return strNodeDisplayName;
	}

	public void setStrNodeDisplayName(String strNodeDisplayName) {
		this.strNodeDisplayName = strNodeDisplayName;
	}

	public Double getdCPUIdle() {
		return dCPUIdle;
	}

	public void setdCPUIdle(Double dCPUIdle) {
		this.dCPUIdle = dCPUIdle;
	}

	public Double getdMemoryFree() {
		return dMemoryFree;
	}

	public void setdMemoryFree(Double dMemoryFree) {
		this.dMemoryFree = dMemoryFree;
	}

	public Double getdMemoryTotal() {
		return dMemoryTotal;
	}

	public void setdMemoryTotal(Double dMemoryTotal) {
		this.dMemoryTotal = dMemoryTotal;
	}

}
