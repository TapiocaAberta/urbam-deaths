package io.sjcdigital.model.entities;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.sjcdigital.model.builder.PersonBuilder;

/**
 * 
 * @author pedro-hos
 *
 */

@Entity(name = "person")
public class Person extends PanacheEntity {

	private String name;
	private Integer age;
	private String deathday;
	private String birthday;
	private String funeral;
	private String burial;
	private String yearDeath;
	private String monthDeath;
	
	public static PersonBuilder create() {
		return new PersonBuilder();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDeathday() {
		return deathday;
	}

	public void setDeathday(String deathdate) {
		this.deathday = deathdate;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthdate) {
		this.birthday = birthdate;
	}

	public String getFuneral() {
		return funeral;
	}

	public void setFuneral(String funeral) {
		this.funeral = funeral;
	}

	public String getBurial() {
		return burial;
	}

	public void setBurial(String burial) {
		this.burial = burial;
	}

	public String getYearDeath() {
		return yearDeath;
	}

	public void setYearDeath(String yearDeath) {
		this.yearDeath = yearDeath;
	}

	public String getMonthDeath() {
		return monthDeath;
	}

	public void setMonthDeath(String monthDeath) {
		this.monthDeath = monthDeath;
	}
}
