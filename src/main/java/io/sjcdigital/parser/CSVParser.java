package io.sjcdigital.parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import io.sjcdigital.model.entities.DeathRegister;
import io.sjcdigital.model.entities.Person;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class CSVParser {

    static final String SEPARATOR = ",";
    static final String HEADER = String.join(SEPARATOR,
                                             "NAME",
                                             "AGE",
                                             "DEATHDAY",
                                             "BIRTHDAY",
                                             "FUNERAL",
                                             "BURIAL",
                                             "YEAR",
                                             "MONTH");

    static final String HEADER_REGISTERS = String.join(SEPARATOR,
                                                       "YEAR",
                                                       "MONTH",
                                                       "FUNERAL");

    static final String TEXT_ENCLOSER = "\"";

    public String parse(List<Person> list) {
        return buildCSV(HEADER, list.stream().map(this::personLine));
    }

    public String withYearsColumns(List<Person> list) {
        var years = list.stream().map(Person::getYearDeath).distinct().collect(toList());
        var newHeader = addToHeader(HEADER, years);
        return buildCSV(newHeader, list.stream().map(p -> lineWithYears(p, years)));
    }

    public String parseRegister(List<Person> list) {
        var registers = list.stream()
                            .map(DeathRegister::from).collect(toList());
        var years = registers.stream().map(DeathRegister::getYear).distinct().collect(toList());
        var newHeader = addToHeader(HEADER_REGISTERS, years);
        return buildCSV(newHeader, registers.stream().map(d -> lineWithYears(d, years)));
    }

    protected String lineWithYears(DeathRegister register, List<String> years) {
        var yearsValues = yearValues(register.getYear(), years);
        Object[] lineValues = Stream.concat(Arrays.stream(registerFields(register)),
                                            Arrays.stream(yearsValues))
                                    .toArray(Object[]::new);
        return line(lineValues);
    }

    protected String lineWithYears(Person p, List<String> years) {
        var yearsValues = yearValues(p.getYearDeath(), years);
        Object[] lineValues = Stream.concat(Arrays.stream(personFields(p)),
                                            Arrays.stream(yearsValues))
                                    .toArray(Object[]::new);
        return line(lineValues);
    }

    protected String personLine(Person p) {
        return line(personFields(p));
    }

    protected String line(Object... values) {
        return Arrays.stream(values).map(this::escapeField).collect(Collectors.joining(SEPARATOR));
    }

    protected String addToHeader(String header, List<String> headers) {
        return String.join(SEPARATOR, header, headers.stream().collect(joining(SEPARATOR)));
    }

    protected String escapeField(Object v) {
        if (v instanceof CharSequence) {
            String escaped = ((String) v).replaceAll(TEXT_ENCLOSER, "\\\\" + TEXT_ENCLOSER + "");
            return TEXT_ENCLOSER + escaped + TEXT_ENCLOSER;
        }
        return v.toString();
    }

    private Object[] registerFields(DeathRegister d) {
        return new Object[]{d.getYear(),
                            d.getMonth(),
                            d.getFuneralType()};
    }

    private Object[] personFields(Person p) {
        return new Object[]{p.getName(),
                            p.getAge(),
                            p.getDeathday(),
                            p.getBirthday(),
                            p.getFuneral(),
                            p.getBurial(),
                            p.getYearDeath(),
                            p.getMonthDeath()};
    }

    private String buildCSV(String header, Stream<String> body) {
        return Stream.concat(Stream.of(header), body)
                     .collect(Collectors.joining(System.lineSeparator()));
    }

    private String[] yearValues(String year, List<String> years) {
        var yearsValues = new String[years.size()];
        Arrays.fill(yearsValues, "0");
        int yearColumn = years.indexOf(year);
        yearsValues[yearColumn] = "1";
        return yearsValues;
    }

}