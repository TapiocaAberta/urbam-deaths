package io.sjcdigital.model.repositories;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.sjcdigital.model.entities.Months;
import io.sjcdigital.model.entities.Person;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

	public long countFuneralDiretoByYearAndMonth(String year, String month) {
		return count("yearDeath = ?1 and monthDeath = ?2 and funeral = 'DIRETO'", year, month.toUpperCase());
	}
	
	public List<Person> findCurentMonthDiretoAndAgeMoreThanZedo() {
		LocalDateTime now = LocalDateTime.now();
		return list("yearDeath = ?1 and monthDeath = ?2 and funeral = 'DIRETO' and age > 0", String.valueOf(now.getYear()), 
																				 			 Months.withValue(now.getMonthValue()).name());
	}
	
	public List<Person> findByYear(String year) {
		return list("yearDeath", year);
	}
	
	public List<Person> findByYearAndMonth(String year, String month) {
		return list("yearDeath = ?1 and monthDeath = ?2", year, month.toUpperCase());
	}

	public List<Person> findByMonth(String month) {
		return list("monthDeath", month.toUpperCase());
	}

	public List<Person> findFuneralDireto(String year, String month) {
		return list("yearDeath = ?1 and monthDeath = ?2 and funeral = 'DIRETO'", year, month.toUpperCase());
	}
	
	public List<Person> findFuneralDireto(List<String> year, String month) {
		return list("yearDeath in (?1) and monthDeath = ?2 and funeral = 'DIRETO' and age > 0", year, month.toUpperCase());
	}

	public long countFuneralDiretoByYearAndMonthAndAge(String year, String month) {
		return count("yearDeath = ?1 and monthDeath = ?2 and age > 0 and funeral = 'DIRETO'", year, month.toUpperCase());
	}

    public List<Person> findBYears(List<String> years) {
        return list("yearDeath in (?1)", years);
    }
	
}
