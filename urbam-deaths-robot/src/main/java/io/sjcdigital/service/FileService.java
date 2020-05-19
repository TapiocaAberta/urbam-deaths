package io.sjcdigital.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.sjcdigital.model.Person;

@ApplicationScoped
public class FileService {
	
	@ConfigProperty(name = "json.file.path")
	private String path;
	
	public void saveJsonFile(final String year, final Map<String, List<Person>> deaths) {
		
		String directoryName = path + year + "/";
		
		File directory = new File(directoryName);
		
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		deaths.forEach((k, v) -> {
			
			try {
				
				Files.write(Paths.get(directoryName + k + ".json"), new ObjectMapper().writeValueAsString(v).getBytes());
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
	}

}
