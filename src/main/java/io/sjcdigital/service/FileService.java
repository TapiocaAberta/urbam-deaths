package io.sjcdigital.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.sjcdigital.model.entities.Person;

/**
 * 
 * @author pesilva
 *
 */
public abstract class FileService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
	
	@ConfigProperty(name = "file.path")
	String path;
	
	public abstract void save(final String year, final Map<String, List<Person>> deaths);
	
	protected void createDirectoryIfDoesntExists(String directoryName) {
		
		LOGGER.info("Files will be save into " + directoryName + " if you need change it, replace the 'file.path' argument on application.properties file");
		
		File directory = new File(directoryName);
		
		if(!directory.exists()) {
			directory.mkdirs();
		}
	}

}
