package jackson;

import java.util.List;

public class TestVO {
	private String voName;
	private List<Person> pers;

	public TestVO() {
	}

	public TestVO(String voName, List<Person> pers) {
		super();
		this.voName = voName;
		this.pers = pers;
	}

	public String getVoName() {
		return voName;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

	public List<Person> getPers() {
		return pers;
	}

	public void setPers(List<Person> pers) {
		this.pers = pers;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TestVO other = (TestVO) obj;
		if (pers == null) {
			if (other.pers != null) {
				return false;
			}
		} else if (pers.size() != other.pers.size()) {
			return false;
		} else {
			for (int i = 0; i < pers.size(); i++) {
				if (!pers.get(i).equals(other.pers.get(i))) {
					return false;
				}
			}
		}
		if (voName == null) {
			if (other.voName != null) {
				return false;
			}
		} else if (!voName.equals(other.voName)) {
			return false;
		}
		return true;
	}
}