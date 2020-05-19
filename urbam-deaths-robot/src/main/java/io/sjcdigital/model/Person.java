package io.sjcdigital.model;

public class Person {

	private String name;
	private String age;
	private String deathday;
	private String birthday;
	
	public static PersonBuilder builder() {
		return new PersonBuilder();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
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
}
