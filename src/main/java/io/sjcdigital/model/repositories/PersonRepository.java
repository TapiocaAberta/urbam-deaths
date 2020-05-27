package io.sjcdigital.model.repositories;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.sjcdigital.model.entities.Person;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

	public long countFuneralDiretoByYearAndMonth(String year, String month) {
		return count("yearDeath = ?1 and monthDeath = ?2 and funeral = 'DIRETO'", year, month);
	}
	
	public List<Person> findByYear(String year) {
		return list("yearDeath", year);
	}
	
	public List<Person> findByYearAndMonth(String year, String month) {
		return list("yearDeath = ?1 and monthDeath = ?2", year, month);
	}

	public List<Person> findByMonth(String month) {
		return list("monthDeath", month);
	}

	public List<Person> findFuneralDireto(String year, String month) {
		return list("yearDeath = ?1 and monthDeath = ?2 and funeral = 'DIRETO'", year, month);
	}

	public long countFuneralDiretoByYearAndMonthAndAge(String year, String month) {
		return count("yearDeath = ?1 and monthDeath = ?2 and age > 0 and funeral = 'DIRETO'", year, month);
	}
	
}
