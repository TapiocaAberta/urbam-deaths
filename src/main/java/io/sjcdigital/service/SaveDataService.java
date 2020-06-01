package io.sjcdigital.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import io.quarkus.scheduler.Scheduled;
import io.sjcdigital.model.entities.Months;
import io.sjcdigital.model.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        Map<String, List<Person>> deathsByYear = null;

        FileService fileService = forType(fileType);

        for (int i = 0; i < years.length; i++) {
            String year = years[i];
            deathsByYear = hasMonth ? scrapper.getDeathsByYearAndMonth(year, months) : scrapper.getDeathsByYear(year);
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

    //@Scheduled(cron = "{cron.expr}")
    public void saveCurrentMonthAndYearData() {

        LOGGER.info("Starting cron ....");

        LocalDateTime now = LocalDateTime.now();

        scrapper.getDeathPersonsByMonthAndYear(String.valueOf(now.getYear()), Months.withValue(now.getMonthValue())).forEach((k, v) -> {
            v.forEach(p -> Person.persist(p));
        });

    }

}