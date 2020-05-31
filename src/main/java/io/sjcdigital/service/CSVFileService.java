package io.sjcdigital.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.sjcdigital.model.entities.Person;

/**
 * 
 * @author pesilva
 *
 */
@ApplicationScoped
public class CSVFileService extends FileService {

    static final String CSV_HEADER = "NAME,AGE,DEATHDAY,BIRTHDAY,FUNERAL,BURIAL,YEAR,MONTH";
    static final String DEFAULT_SEPARATOR = ",";
    static final String TEXT_ENCLOSER = "\"";

    @Override
    public void save(String year, Map<String, List<Person>> deaths) {

        String directoryName = path + year + "/csv/";
        createDirectoryIfDoesntExists(directoryName);

        deaths.forEach((k, v) -> createCSVFile(directoryName, k, v));
    }

    private void createCSVFile(String directoryName, String month, List<Person> deathPersons) {

        String csvContent = buildCSVContent(deathPersons);

        try {

            Files.write(Paths.get(directoryName + month + ".csv"), csvContent.getBytes());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected String buildCSVContent(List<Person> deathPersons) {
        String header = CSV_HEADER;
        return Stream.concat(Stream.of(header),
                             deathPersons.stream()
                                         .map(this::personLine))
                     .collect(Collectors.joining(System.lineSeparator()));

    }

    protected String personLine(Person p) {
        return Stream.of(p.getName(),
                         p.getAge(),
                         p.getDeathday(),
                         p.getBirthday(),
                         p.getFuneral(),
                         p.getBurial(),
                         p.getYearDeath(),
                         p.getMonthDeath())
                     .map(this::escapeField)
                     .collect(Collectors.joining(DEFAULT_SEPARATOR));

    }

    protected String escapeField(Object v) {
        if (v instanceof CharSequence) {
            String escaped = ((String) v).replaceAll(TEXT_ENCLOSER, "\\\\" + TEXT_ENCLOSER + "");
            return TEXT_ENCLOSER + escaped + TEXT_ENCLOSER;
        }

        return v.toString();
    }

}
