package io.sjcdigital.parser;

import java.util.Arrays;
import java.util.List;

import io.sjcdigital.model.builder.PersonBuilder;
import io.sjcdigital.model.entities.Person;
import org.junit.jupiter.api.Test;

import static io.sjcdigital.parser.CSVParser.TEXT_ENCLOSER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVParserTest {

    private Person p1 = new PersonBuilder().name("john")
                                           .age(50)
                                           .birthday("01/01/2070")
                                           .burial("BURIAL1")
                                           .deathday("01")
                                           .funeral("DIRETO")
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
    
    private CSVParser csvParser = new CSVParser();

    private String p1Line = "\"john\",50,\"01\",\"01/01/2070\",\"DIRETO\",\"BURIAL1\",\"05\",\"01\"";
    private String p2Line = "\"mary\",60,\"02\",\"01/01/2060\",\"FUNERAL2\",\"BURIAL2\",\"06\",\"02\"";
    private String expectedCSV = "NAME,AGE,DEATHDAY,BIRTHDAY,FUNERAL,BURIAL,YEAR,MONTH\n" +
                                 "\"john\",50,\"01\",\"01/01/2070\",\"DIRETO\",\"BURIAL1\",\"05\",\"01\"\n" +
                                 "\"mary\",60,\"02\",\"01/01/2060\",\"FUNERAL2\",\"BURIAL2\",\"06\",\"02\"";

    private String header = "NAME,AGE,DEATHDAY,BIRTHDAY,FUNERAL,BURIAL,YEAR,MONTH";
    private String headerWithYears = header + ",05,06";
    private String p1LineWithYears = "\"john\",50,\"01\",\"01/01/2070\",\"DIRETO\",\"BURIAL1\",\"05\",\"01\",\"1\",\"0\"";
    private String p2LineWithYears = "\"mary\",60,\"02\",\"01/01/2060\",\"FUNERAL2\",\"BURIAL2\",\"06\",\"02\",\"0\",\"1\"";
    
    List<String> yearsHeaders = Arrays.asList("05", "06");
    
    
    private String registerCSV = "YEAR,MONTH,FUNERAL,05,06\n" +
            "\"05\",\"01\",\"DIRETO\",\"1\",\"0\"\n" +
            "\"06\",\"02\",\"OUTRO\",\"0\",\"1\"";
    
    @Test
    public void addToHeaderTest() {
        assertEquals(headerWithYears, csvParser.addToHeader(header, yearsHeaders));
    }
    
    @Test
    public void lineWithYearsTest() {
        assertEquals(p1LineWithYears, csvParser.lineWithYears(p1, yearsHeaders));
        assertEquals(p2LineWithYears, csvParser.lineWithYears(p2, yearsHeaders));
    }
    
    @Test
    public void registerCSVTest() {
        assertEquals(registerCSV, csvParser.parseRegister(Arrays.asList(p1, p2)));
    }
    
    @Test
    public void escapeIntFieldTest() {
        var v1 = 10;
        assertEquals("10", csvParser.escapeField(v1));
    }

    @Test
    public void surroundTextFieldTest() {
        var v1 = "test";
        assertEquals(enclose(v1), csvParser.escapeField(v1));
    }

    @Test
    public void surroundAndEscapeTextFieldTest() {
        var v1 = "test" + TEXT_ENCLOSER + "test";
        var expected = enclose(v1.replaceAll(TEXT_ENCLOSER, "\\\\" + TEXT_ENCLOSER));
        assertEquals(expected, csvParser.escapeField(v1));
    }

    @Test
    public void personLineTest() {
        assertEquals(p1Line, csvParser.personLine(p1));
        assertEquals(p2Line, csvParser.personLine(p2));
    }

    @Test
    public void buildCSVTest() {
        assertEquals(expectedCSV, csvParser.parse(Arrays.asList(p1, p2)));
    }

    private String enclose(Object value) {
        return TEXT_ENCLOSER + value + TEXT_ENCLOSER;
    }

}