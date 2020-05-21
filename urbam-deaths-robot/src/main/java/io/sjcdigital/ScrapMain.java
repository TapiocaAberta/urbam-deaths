package io.sjcdigital;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.sjcdigital.service.SaveDataService;

/**
 * 
 * @author pedro-hos
 *
 */

@QuarkusMain
public class ScrapMain implements QuarkusApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScrapMain.class);
	
	@Inject
	SaveDataService fileService;
	
	/**
	 * ./mvnw compile quarkus:dev -Dquarkus.args='2019,2020,2018 json'
	 */
	
	@Override
	public int run(String... args) throws Exception {
		
		
		if (args.length == 0) {
			
			LOGGER.info("NO parameters. If you want, use: ./mvnw compile quarkus:dev -Dquarkus.args='2019,2020,2018 json\'");
			Quarkus.waitForExit();
		
		} else {
			
			LOGGER.info("Scrapping deaths from years: " + args[0] + " and saving in " + args[1] + " format");

			String[] years = args[0].split(",");
			String fileType = args[1];

			fileService.save(fileType, years);
		}
		
		return 0;
	}

}
