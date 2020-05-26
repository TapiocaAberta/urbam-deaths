package io.sjcdigital.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.sjcdigital.model.entity.Person;

/**
 * 
 * @author pedro-hos
 *
 */
@ApplicationScoped
public class JSONFileService extends FileService{
	
	@Override
	public void save(final String year, final Map<String, List<Person>> deaths) {
		
		String directoryName = path + year + "/json/";
		createDirectoryIfDoesntExists(directoryName);
		
		deaths.forEach((k, v) -> createJsonFile(directoryName, k, v) );
	}

	private void createJsonFile(String directoryName, String month, List<Person> deathPersons) {
		
		try {
			
			Files.write(Paths.get(directoryName + month + ".json"), new ObjectMapper().writeValueAsString(deathPersons).getBytes());
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
