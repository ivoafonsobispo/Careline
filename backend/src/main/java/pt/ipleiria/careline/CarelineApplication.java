package pt.ipleiria.careline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.dal.PersonRepository;
import pt.ipleiria.careline.entities.Person;

import java.util.List;

@SpringBootApplication
public class CarelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarelineApplication.class, args);
	}

}
