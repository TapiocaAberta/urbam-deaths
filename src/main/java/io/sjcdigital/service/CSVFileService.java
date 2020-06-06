package io.sjcdigital.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.sjcdigital.model.entities.Person;
import io.sjcdigital.parser.CSVParser;

/**
 * 
 * @author pesilva
 *
 */
@ApplicationScoped
public class CSVFileService extends FileService {

    @Inject
    CSVParser parser;

    @Override
    public void save(String year, Map<String, List<Person>> deaths) {
        var directoryName = path + year + "/csv/";
        createDirectoryIfDoesntExists(directoryName);
        deaths.forEach((k, v) -> createCSVFile(directoryName, k, v));
    }

    private void createCSVFile(String directoryName, String month, List<Person> deathPersons) {
        var csvContent = parser.parse(deathPersons);
        try {
            Files.write(Paths.get(directoryName + month + ".csv"), csvContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}