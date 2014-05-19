package json;
import com.google.gson.annotations.SerializedName;




public class GlobalStatusMetricBeanInfo {

	@SerializedName("OfsPfsState")
	private String strSystemStatus;

	@SerializedName("OfsPfs_ds_read_rate")
	private String strReadRate;

	@SerializedName("OfsPfs_ds_write_rate")
	private String strWriteRate;

//	@SerializedName("")
	private String strRepairProgress;

	@SerializedName("OfsPfs_used_space")
	private String strUsedSpace;

	@SerializedName("OfsPfs_free_space")
	private String strFreeSpace;

	@SerializedName("OfsPfs_capacity")
	private String strTotalCapacity;

//	@SerializedName("")
	private String strDiskSize;

	@SerializedName("OfsPfs_fs_nr")
	private String strFileSystem;

	@SerializedName("OfsPfs_dirs_nr")
	private String strDicSize;

	public String getStrSystemStatus() {
		return strSystemStatus;
	}

	public void setStrSystemStatus(String strSystemStatus) {
		this.strSystemStatus = strSystemStatus;
	}

	public String getStrReadRate() {
		return strReadRate;
	}

	public void setStrReadRate(String strReadRate) {
		this.strReadRate = strReadRate;
	}

	public String getStrWriteRate() {
		return strWriteRate;
	}

	public void setStrWriteRate(String strWriteRate) {
		this.strWriteRate = strWriteRate;
	}

	public String getStrRepairProgress() {
		return strRepairProgress;
	}

	public void setStrRepairProgress(String strRepairProgress) {
		this.strRepairProgress = strRepairProgress;
	}

	public String getStrUsedSpace() {
		return strUsedSpace;
	}

	public void setStrUsedSpace(String strUsedSpace) {
		this.strUsedSpace = strUsedSpace;
	}

	public String getStrFreeSpace() {
		return strFreeSpace;
	}

	public void setStrFreeSpace(String strFreeSpace) {
		this.strFreeSpace = strFreeSpace;
	}

	public String getStrTotalCapacity() {
		return strTotalCapacity;
	}

	public void setStrTotalCapacity(String strTotalCapacity) {
		this.strTotalCapacity = strTotalCapacity;
	}

	public String getStrDiskSize() {
		return strDiskSize;
	}

	public void setStrDiskSize(String strDiskSize) {
		this.strDiskSize = strDiskSize;
	}

	public String getStrFileSystem() {
		return strFileSystem;
	}

	public void setStrFileSystem(String strFileSystem) {
		this.strFileSystem = strFileSystem;
	}

	public String getStrDicSize() {
		return strDicSize;
	}

	public void setStrDicSize(String strDicSize) {
		this.strDicSize = strDicSize;
	}

}
