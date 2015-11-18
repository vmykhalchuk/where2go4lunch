package org.vmykhalchuk.where2go4lunch.to_delete;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

//@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "role" }))
@Deprecated
public class UserRoleOld implements GrantedAuthority {
	private static final long serialVersionUID = 6305388622762170561L;

	public static enum Role {
		USER("User"), ADMIN("Administrator");

		private String title;

		Role(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Role role;

	public UserRoleOld() {
		super();
	}

	public UserRoleOld(Role role) {
		super();
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getAuthority() {
		return role.name();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
