package io.sjcdigital.rest;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
import javax.ws.rs.core.Response.Status;

import io.sjcdigital.model.dto.CountDTO;
import io.sjcdigital.model.dto.ScrapDTO;
import io.sjcdigital.model.entities.Months;
import io.sjcdigital.model.entities.Person;
import io.sjcdigital.model.repositories.PersonRepository;
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
	@Path("/direto/compare")
	public Response compareMonths() {
		
		List<CountDTO> values = new LinkedList<>();
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime lastYear = now.minusYears(1);
		
		int month = now.minusMonths(1).getMonthValue();
		
		while(month != 0) {
			
			Months monthEnum = Months.withValue(month);
			
			values.add(CountDTO.create().funeral("DIRETO")
									    .count(repository.countFuneralDiretoByYearAndMonthAndAge(String.valueOf(now.getYear()), monthEnum.name()))
									    .month(monthEnum.name())
									    .year(String.valueOf(now.getYear()))
									    .nonZeroAge(true)
									    .build());
			
			values.add(CountDTO.create().funeral("DIRETO")
									    .count(repository.countFuneralDiretoByYearAndMonthAndAge(String.valueOf(lastYear.getYear()), monthEnum.name()))
									    .month(monthEnum.name())
									    .year(String.valueOf(lastYear.getYear()))
									    .nonZeroAge(true)
									    .build());
			
			month--;
		}
		
		return Response.ok(values).build();
	}
	
	@GET
	@Path("/direto/count")
	public Response countFuneralDiretoByYearMonthAndAge( @QueryParam("year") final String year,
													     @QueryParam("month") final String month,
													     @QueryParam("byAge") final boolean byAge ) {
		
		CountDTO count = CountDTO.create()
								 .funeral("DIRETO")
								 .month(month)
								 .year(year)
								 .nonZeroAge(byAge)
								 .build();
		
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
	public Response save(final ScrapDTO scrap) {
		
		// curl -X POST "http://localhost:8080/api/deaths" -H "accept: */*" -H "Content-Type: application/json" 
		//-d "{\"months\":[\"5\"],\"years\":[\"2020\"]}"
		
		for (String year : scrap.getYears()) {
			
			scrapper.getDeathsByYearAndMonth(year, scrap.getMonths()).forEach((k, v) -> {
				v.forEach(p -> Person.persist(p));
			});
			
		}
		
		return Response.status(Status.CREATED).build();
	}

}
