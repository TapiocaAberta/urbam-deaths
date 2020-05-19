package io.sjcdigital.model;

public class PersonBuilder {

	private Person person;
	
	public PersonBuilder() {
		this.person = new Person();
	}
	
	public PersonBuilder name(final String name) {
		this.person.setName(name);
		return this;
	}
	
	public PersonBuilder age(final String age) {
		this.person.setAge(age);
		return this;
	}
	
	public PersonBuilder birthday(final String birthday) {
		this.person.setBirthday(birthday);
		return this;
	}
	
	public PersonBuilder deathday(final String deathday) {
		this.person.setDeathday(deathday);
		return this;
	}
	
	public Person build() {
		return this.person;
	}
	
}
