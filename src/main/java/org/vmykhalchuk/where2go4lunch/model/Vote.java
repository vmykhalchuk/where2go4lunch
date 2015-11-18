package org.vmykhalchuk.where2go4lunch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "user_username", "date" }))
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private long version;

	@ManyToOne
	private User user;

	@ManyToOne
	private LunchMenu lunchMenu;

	private long tmstmp;

	/**
	 * YYYYMMDD
	 */
	private int date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LunchMenu getLunchMenu() {
		return lunchMenu;
	}

	public void setLunchMenu(LunchMenu lunchMenu) {
		this.lunchMenu = lunchMenu;
	}

	public long getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(long tmstmp) {
		this.tmstmp = tmstmp;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

}
