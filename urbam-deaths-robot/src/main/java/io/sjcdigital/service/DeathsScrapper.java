package io.sjcdigital.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.sjcdigital.model.Months;
import io.sjcdigital.model.Person;

@ApplicationScoped
public class DeathsScrapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeathsScrapper.class);
	
	@ConfigProperty(name = "scrapper.agent")
	String agent;
	
	@ConfigProperty(name = "scrapper.url")
	String url;
	
	@ConfigProperty(name = "scrapper.timeout")
	int timeout;

	@ConfigProperty(name = "scrapper.data.month")
	String month;

	@ConfigProperty(name = "scrapper.data.year")
	String year;

	@ConfigProperty(name = "scrapper.data.name")
	String name;
	
	public Map<String, List<Person>> getDeathsByYear(final String year) {
		LOGGER.info("get informations for year: " + year);
		return getDeathPersonsInAYear(year);
	}

	private Map<String, List<Person>> getDeathPersonsInAYear(final String year) {
		
		Map<String, List<Person>> deathsInAYear = new HashMap<>();
		Months[] months = Months.values();
		
		for (int i = 0; i < months.length; i++) {
			Elements deathNoteElements = getDeathNote(year, months[i].getValue().toString());
			deathsInAYear.put(months[i].name().toLowerCase(), parseElementsToPerson(deathNoteElements));
		}
		
		return deathsInAYear;
	}


	private List<Person> parseElementsToPerson(final Elements deathNoteElements) {
		
		List<Person> persons = new LinkedList<Person>();
		
		for (Element element : deathNoteElements) {
			
			Elements row = element.select("div.row");
			
			persons.add(Person.builder()
							  .name(row.select("h3").text())
							  .age(row.select("span:contains(Idade:)").next("span").text())
							  .birthday(row.select("span:contains(Data de nascimento:)").next("span").text())
							  .deathday(row.select("span:contains(Data de falecimento:)").next("span").text())
							  .build());
		}
		
		return persons;
	}


	private Elements getDeathNote(final String year, final String month) {
		
		Elements elements = null; //bad, change to Optional maybe!
		
		try {
			
			Response response = getResponse(year, month);
			elements = response.parse().getElementsByClass("col-12");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return elements;
	}

	private Response getResponse(final String yearValue, final String monthValue) throws IOException {
		
		Response response = Jsoup.connect(url)
								.userAgent(agent)
								.timeout(timeout)
								.method(Method.POST)
								.data(month, monthValue)
								.data(year, yearValue)
								.data(name, "")
								.followRedirects(true)
								.execute();
		return response;
	}
	
}
