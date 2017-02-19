package in.eloksolutions.ayyappa.ayyappapp.beans;

import java.util.ArrayList;
import java.util.List;

public class EventDTO {
	
	//for event
	private Integer id;
	private String eventName;
	private String fromDate;
	private String ftTime;
	private String location;
	private String description;
	private String eventPic;
	private String pincode;
	private String owner;
	private Integer memberCount;
	private String ownerName;
	private int status;
	private String fDate;
	private boolean member;
	private boolean past;
	private String areaName;
	private String city;
	private String state;
	private String country;
	//list of members
List<MemberDTO> mem=new ArrayList<MemberDTO>();
	
	public List<MemberDTO> getMem() {
	return mem;
}
public void setMem(List<MemberDTO> mem) {
	this.mem = mem;
}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEventPic() {
		return eventPic;
	}
	public void setEventPic(String eventPic) {
		this.eventPic = eventPic;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getFtTime() {
		return ftTime;
	}
	public void setFtTime(String ftTime) {
		this.ftTime = ftTime;
	}

	public String getfDate() {
		return fDate;
	}

	public void setfDate(String fDate) {
		this.fDate = fDate;
	}

	public Integer getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	public boolean getMember() {
		return member;
	}
	public void setMember(boolean isMember) {
		this.member = isMember;
	}
	public boolean getPast() {
		return past;
	}

	public void setIsPast(boolean isPast) {
		this.past = isPast;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "EventDTO{" +
				"id=" + id +
				", eventName='" + eventName + '\'' +
				", ftTime='" + ftTime + '\'' +
				", location='" + location + '\'' +
				", description='" + description + '\'' +
				", eventPic='" + eventPic + '\'' +
				", pincode=" + pincode +
				", owner=" + owner +
				", memberCount=" + memberCount +
				", ownerName='" + ownerName + '\'' +
				", fDate='" + fDate + '\'' +
				", isMember=" + member +
				", isPast=" + past +
				", mem=" + mem +
				'}';
	}
}
