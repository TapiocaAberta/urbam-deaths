package io.sjcdigital;

import javax.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.sjcdigital.service.DeathsScrapper;
import io.sjcdigital.service.FileService;

@QuarkusMain
public class ScrapMain implements QuarkusApplication {

	@Inject
	DeathsScrapper scrapper;
	
	@Inject
	FileService fileService;
	
	@Override
	public int run(String... args) throws Exception {
		
		fileService.saveJsonFile("2019", scrapper.getDeathsByYear("2019"));
		fileService.saveJsonFile("2020", scrapper.getDeathsByYear("2020"));
		
		return 10;
	}

}
