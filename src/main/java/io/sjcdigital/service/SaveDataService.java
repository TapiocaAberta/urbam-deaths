package io.sjcdigital.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Inject
    BeanManager beanManager;

    public void save(String fileType, String[] years, String[] months) {

        boolean hasMonth = months.length > 0;

        var fileService = forType(fileType);

        for (var year : years) {
            var deathsByYear = hasMonth ? scrapper.getDeathsByYearAndMonth(year, months) : scrapper.getDeathsByYear(year);
            fileService.save(year, deathsByYear);
        }

    }

    private FileService forType(String fileType) {
        switch (fileType) {
            case "csv":
                return csvService;
            case "json":
                return jsonService;
            default:
                LOGGER.info("File type not recognized: {}. Returning json type.", fileType);
                return jsonService;
        }
    }
    
    @Transactional
    public void saveYesterdayDeaths() {
    	
    	LOGGER.info("Starting cron save yesterday deaths ....");

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String yesterdayFormat = yesterday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        List<Person> yesterdayDeaths =  scrapper.getDeathPersonsByMonthAndYear(	String.valueOf(yesterday.getYear()), 
        																		Months.withValue(yesterday.getMonthValue()))
        																		.get(Months.withValue(yesterday.getMonthValue()).name().toLowerCase())
								        										.stream()
								        										.filter(p -> p.getDeathday().equals(yesterdayFormat))
								        										.collect(Collectors.toList());
        
        Person.persist(yesterdayDeaths);
        
    }

    //@Scheduled(cron = "{cron.expr}")
    @Transactional
    public void saveCurrentMonthAndYearData() {

        LOGGER.info("Starting cron ....");

        LocalDateTime now = LocalDateTime.now();

        scrapper.getDeathPersonsByMonthAndYear(String.valueOf(now.getYear()), Months.withValue(now.getMonthValue())).forEach((k, v) -> {
            v.forEach(p -> Person.persist(p));
        });

    }

}