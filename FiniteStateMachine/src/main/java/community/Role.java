package community;

public enum Role {
	ADMIN("Admin"),
	MEMBER("Member");

	private final String displayName;

	Role(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
