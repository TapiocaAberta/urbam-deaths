package io.sjcdigital.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * 
 * @author pedro-hos
 *
 */
@ApplicationScoped
public class SaveDataService {
	
	@Inject
	DeathsScrapper scrapper;
	
	@Inject
	JSONFileService jsonService;
	
	@Inject
	CSVFileService csvService;

	public void save(String fileType, String[] years) {

		if ("json".equalsIgnoreCase(fileType)) {

			for (int i = 0; i < years.length; i++) {
				jsonService.save(years[i], scrapper.getDeathsByYear(years[i]));
			}

		} else if ("csv".equalsIgnoreCase(fileType)) {

			for (int i = 0; i < years.length; i++) {
				csvService.save(years[i], scrapper.getDeathsByYear(years[i]));
			}
			
		}

	}

}
