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

import io.sjcdigital.model.Person;

@ApplicationScoped
public class DeathsScrapper {
	
	@ConfigProperty(name = "scrapper.agent")
	private String agent;
	
	@ConfigProperty(name = "scrapper.url")
	private String url;
	
	@ConfigProperty(name = "scrapper.timeout")
	private int timeout;

	@ConfigProperty(name = "scrapper.data.month")
	private String month;

	@ConfigProperty(name = "scrapper.data.year")
	private String year;

	@ConfigProperty(name = "scrapper.data.name")
	private String name;
	
	public Map<String, List<Person>> getDeathsByYear(final String year) {
		return getDeathsPersonsInAYear(year);
	}

	private Map<String, List<Person>> getDeathsPersonsInAYear(final String year) {
		
		Map<String, List<Person>> deathsInAYear = new HashMap<>();
		
		for(Integer month = 1; month <= 12; month++) {
			
			Elements deathNoteElements = getDeathNote(year, month.toString());
			deathsInAYear.put(month.toString(), parseElementsToPerson(deathNoteElements));
		}
		
		return deathsInAYear;
	}


	private List<Person> parseElementsToPerson(Elements deathNoteElements) {
		
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
