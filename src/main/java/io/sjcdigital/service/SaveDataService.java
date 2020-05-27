package io.sjcdigital.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.scheduler.Scheduled;
import io.sjcdigital.model.entities.Months;
import io.sjcdigital.model.entities.Person;

/**
 * 
 * @author pedro-hos
 *
 */
@ApplicationScoped
public class SaveDataService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SaveDataService.class);
	
	@Inject
	DeathsScrapper scrapper;
	
	@Inject
	JSONFileService jsonService;
	
	@Inject
	CSVFileService csvService;

	public void save(String fileType, String[] years, String[] months) {
		
		boolean hasMonth = months.length > 0;
		Map<String, List<Person>> deathsByYear = null;
		
		if ("json".equalsIgnoreCase(fileType)) {

			for (int i = 0; i < years.length; i++) {
				String year = years[i];
				deathsByYear = hasMonth ? scrapper.getDeathsByYearAndMonth(year, months) : scrapper.getDeathsByYear(year);
				jsonService.save(year, deathsByYear);
			}

		} else if ("csv".equalsIgnoreCase(fileType)) {

			for (int i = 0; i < years.length; i++) {
				String year = years[i];
				deathsByYear = hasMonth ? scrapper.getDeathsByYearAndMonth(year, months) : scrapper.getDeathsByYear(year);
				csvService.save(year, deathsByYear);
			}
			
		}

	}
	
	
	@Scheduled(cron = "{cron.expr}")
	public void saveCurrentMonthAndYearData() {
		
		LOGGER.info("Starting cron ....");
		
		LocalDateTime now = LocalDateTime.now();
		
		scrapper.getDeathPersonsByMonthAndYear(String.valueOf(now.getYear()), Months.withValue(now.getMonthValue())).forEach((k, v) -> {
			v.forEach(p -> Person.persist(p));
		});
		
	}

}
