package org.vmykhalchuk.where2go4lunch.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "restaurant")
	private List<LunchMenu> lunchMenuesList = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LunchMenu> getLunchMenuesList() {
		return lunchMenuesList;
	}

	public void setLunchMenuesList(List<LunchMenu> lunchMenuesList) {
		this.lunchMenuesList = lunchMenuesList;
	}

}
