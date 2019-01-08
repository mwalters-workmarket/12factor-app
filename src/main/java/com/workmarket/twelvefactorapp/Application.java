package com.workmarket.twelvefactorapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

@RestController
class PersonController {

	private final PersonService personService;

	public PersonController(PersonService personService){
		this.personService = personService;
	}

	@GetMapping("/person/{name}")
	public Person findByName(@PathVariable("name") String name){
		return personService.getByName(name);
	}


}

@Service
class PersonService {

	private final PersonRepository personRepository;

	public PersonService(PersonRepository personRepository){
		this.personRepository = personRepository;
	}

	public Person getByName(final String name) {
		Person person = personRepository.findByName(name);
		if (person == null){
			throw new PersonNotFoundException();
		} else {
			return person;
		}
	}
}

@Entity
class Person {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String name;


	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}


}

@Repository
interface PersonRepository extends JpaRepository<Person, Long>{

	Person findByName(String name);

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class PersonNotFoundException extends RuntimeException{

}


@Component
@ConfigurationProperties(prefix="cool.feature")
@Validated
class CoolFeatureConfiguration {

	@NotNull
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}


}