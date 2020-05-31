package io.sjcdigital.service;

import java.util.Arrays;

import io.sjcdigital.model.builder.PersonBuilder;
import io.sjcdigital.model.entities.Person;
import org.junit.jupiter.api.Test;

import static io.sjcdigital.service.CSVFileService.TEXT_ENCLOSER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVFileServiceTest {

    private Person p1 = new PersonBuilder().name("john")
                                           .age(50)
                                           .birthday("01/01/2070")
                                           .burial("BURIAL1")
                                           .deathday("01")
                                           .funeral("FUNERAL1")
                                           .yearDeath("05")
                                           .monthDeath("01")
                                           .build();
    private Person p2= new PersonBuilder().name("mary")
            .age(60)
            .birthday("01/01/2060")
            .burial("BURIAL2")
            .deathday("02")
            .funeral("FUNERAL2")
            .yearDeath("06")
            .monthDeath("02")
            .build();
    private CSVFileService csvFileService = new CSVFileService();
    private String p1Line = "\"john\",50,\"01\",\"01/01/2070\",\"FUNERAL1\",\"BURIAL1\",\"05\",\"01\"";
    private String p2Line = "\"mary\",60,\"02\",\"01/01/2060\",\"FUNERAL2\",\"BURIAL2\",\"06\",\"02\"";
    private String expectedCSV = "NAME,AGE,DEATHDAY,BIRTHDAY,FUNERAL,BURIAL,YEAR,MONTH\n" +
                                 "\"john\",50,\"01\",\"01/01/2070\",\"FUNERAL1\",\"BURIAL1\",\"05\",\"01\"\n" +
                                 "\"mary\",60,\"02\",\"01/01/2060\",\"FUNERAL2\",\"BURIAL2\",\"06\",\"02\"";

    @Test
    public void escapeIntFieldTest() {
        var v1 = 10;
        assertEquals("10", csvFileService.escapeField(v1));
    }

    @Test
    public void surroundTextFieldTest() {
        var v1 = "test";
        assertEquals(enclose(v1), csvFileService.escapeField(v1));
    }

    @Test
    public void surroundAndEscapeTextFieldTest() {
        var v1 = "test" + TEXT_ENCLOSER + "test";
        var expected = enclose(v1.replaceAll(TEXT_ENCLOSER, "\\\\" + TEXT_ENCLOSER));
        assertEquals(expected, csvFileService.escapeField(v1));
    }

    @Test
    public void personLineTest() {
        assertEquals(p1Line, csvFileService.personLine(p1));
        assertEquals(p2Line, csvFileService.personLine(p2));
    }

    @Test
    public void buildCSVTest() {
        System.out.println(expectedCSV);
        assertEquals(expectedCSV, csvFileService.buildCSVContent(Arrays.asList(p1, p2)));

    }

    private String enclose(Object value) {
        return TEXT_ENCLOSER + value + TEXT_ENCLOSER;
    }

}