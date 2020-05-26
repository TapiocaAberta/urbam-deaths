package io.sjcdigital.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.sjcdigital.model.entity.Person;

/**
 * 
 * @author pesilva
 *
 */
@ApplicationScoped
public class CSVFileService extends FileService {
	
	private static final char DEFAULT_SEPARATOR = ',';

	@Override
	public void save(String year, Map<String, List<Person>> deaths) {
		
		String directoryName = path + year + "/csv/";
		createDirectoryIfDoesntExists(directoryName);
		
		deaths.forEach((k, v) -> createCSVFile(directoryName, k, v) );
	}

	private void createCSVFile(String directoryName, String month, List<Person> deathPersons) {
		
		StringBuilder csvContent = buildCSVContent(deathPersons);
		
		try {
			
			Files.write(Paths.get(directoryName + month + ".csv"), csvContent.toString().getBytes());
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private StringBuilder buildCSVContent(List<Person> deathPersons) {
		
		String header = "NAME,AGE,DEATHDAY,BIRTHDAY,FUNERAL,BURIAL\n";
		StringBuilder csvContent = new StringBuilder(header);
		
		for (Person person : deathPersons) {
			csvContent.append(person.getName());
			csvContent.append(DEFAULT_SEPARATOR);
			csvContent.append(person.getAge());
			csvContent.append(DEFAULT_SEPARATOR);
			csvContent.append(person.getDeathday());
			csvContent.append(DEFAULT_SEPARATOR);
			csvContent.append(person.getBirthday());
			csvContent.append(DEFAULT_SEPARATOR);
			csvContent.append(person.getFuneral());
			csvContent.append(DEFAULT_SEPARATOR);
			csvContent.append(person.getBurial());
			csvContent.append("\n");
		}
		
		return csvContent;
	}

}
