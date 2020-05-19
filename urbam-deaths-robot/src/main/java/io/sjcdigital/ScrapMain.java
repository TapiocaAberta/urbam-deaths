package io.sjcdigital;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.sjcdigital.service.DeathsScrapper;
import io.sjcdigital.service.FileService;

@QuarkusMain
public class ScrapMain implements QuarkusApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScrapMain.class);
	
	@Inject
	DeathsScrapper scrapper;
	
	@Inject
	FileService fileService;
	
	/**
	 * ./mvnw compile quarkus:dev -Dquarkus.args='2019,2020,2018 json'
	 */
	
	@Override
	public int run(String... args) throws Exception {
		
		LOGGER.info("Scrapping deaths from years: " + args[0] + " and saving in " + args[1] + " format");
		
		String[] years = args[0].split(",");
		String fileType = args[1];
		
		if("json".equalsIgnoreCase(fileType)) {
			
			for (int i = 0; i < years.length; i++) {
				fileService.saveAsJsonFile(years[i], scrapper.getDeathsByYear(years[i]));
			}
			
		} else if("csv".equalsIgnoreCase(fileType)) {
			//TODO: implement csv
			LOGGER.error("CSV is not implemented yet!");
		}
		
		return 0;
	}

}
