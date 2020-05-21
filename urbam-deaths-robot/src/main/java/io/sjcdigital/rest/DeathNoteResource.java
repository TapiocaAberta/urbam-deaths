package io.sjcdigital.rest;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.sjcdigital.model.entity.CountDTO;
import io.sjcdigital.model.entity.Person;
import io.sjcdigital.model.repository.PersonRepository;
import io.sjcdigital.service.DeathsScrapper;
import io.sjcdigital.service.SaveDataService;

@ApplicationScoped
@Path("/api/deaths")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeathNoteResource {
	
	@Inject
	SaveDataService saveData;
	
	@Inject
	DeathsScrapper scrapper;
	
	@Inject
	PersonRepository repository;
	
	@GET
	@Path("/direto/")
	public Response findFuneralDiretoByYearAndMonth(@QueryParam("year") final String year, @QueryParam("month") final String month) {
		return Response.ok(repository.findFuneralDireto(year, month)).build();
	}
	
	@GET
	@Path("/direto/count")
	public Response countFuneralDiretoByYearAndMonthAndAge( @QueryParam("year") final String year,
													        @QueryParam("month") final String month,
													        @QueryParam("byAge") final boolean byAge ) {
		
		CountDTO count = new CountDTO();
		count.setFuneral("DIRETO");
		count.setMonth(month);
		count.setYear(year);
		count.setNonZeroAge(byAge);
		
		if(byAge) {
			count.setCount(repository.countFuneralDiretoByYearAndMonthAndAge(year, month));
		} else {
			count.setCount(repository.countFuneralDiretoByYearAndMonth(year, month));
		}
		
		return Response.ok(count).build();
	}
	
	@GET
	public Response getByYearAndMonth(@QueryParam("year") final String year, @QueryParam("month") final String month) {
		
		if(Objects.nonNull(year) && Objects.nonNull(month)) {
			return Response.ok(repository.findByYearAndMonth(year, month)).build();
		} else if (Objects.nonNull(year) ) {
			return Response.ok(repository.findByYear(year)).build();
		} else {
			return Response.ok(repository.findByMonth(month)).build();
		}
		
	}
	
	@GET
	public Response getAll() {
		
		List<Person> contact = Person.findAll().list();
		
		if(contact.isEmpty()) {
            throw new WebApplicationException("No deaths available", HttpURLConnection.HTTP_NOT_FOUND);
		}
		
		return Response.ok(contact).build();
		
	}
	
	@POST
	@Transactional
	public Response save(final List<String> years) {
		
		System.out.println(years.size());
		
		for (String year : years) {
			
			scrapper.getDeathsByYear(year).forEach((k, v) -> {
				v.forEach(p -> Person.persist(p));
			});
			
		}
		
		return Response.ok().build();
	}

}
