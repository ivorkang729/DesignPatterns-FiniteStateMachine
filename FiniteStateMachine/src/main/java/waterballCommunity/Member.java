package waterballCommunity;

import java.util.Objects;

public class Member {
	private String id;
	private Role role;
	
	public Member(String id, Role role) {
		this.id = id;
		this.role = role;
	}
	
	public String getId() {
		return id;
	}
	
	public Role getRole() {
		return role;
	}

	//用 id 識別
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Member member = (Member) obj;
		return Objects.equals(id, member.id);
	}

}
