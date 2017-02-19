package in.eloksolutions.ayyappa.ayyappapp.beans;

import java.util.Date;

public class MemberDTO {
	private String memId;
	private String memName;
	private Date updatedTs;
	public String getMemberId() {
		return memId;
	}
	public void setMemberId(String memberId) {
		this.memId = memberId;
	}
	public String getMemberName() {
		return memName;
	}
	public void setMemberName(String memberName) {
		this.memName = memberName;
	}
	public Date getUpdatedTs() {
		return updatedTs;
	}
	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}
	
	

}
