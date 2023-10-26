package pt.ipleiria.careline.api;

import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.bll.PersonBLL;
import pt.ipleiria.careline.dto.PersonDTO;

import java.util.ArrayList;

@RequestMapping("api/v1/person")
@RestController
public class PersonAPI {

    private final PersonBLL personBLL;

    public PersonAPI(PersonBLL personBLL) {
        this.personBLL = personBLL;
    }

    @GetMapping
    public ArrayList<PersonDTO> getPersons() {
        return personBLL.getPersons();
    }

    @PostMapping
    public void addPerson(@RequestBody PersonDTO request) {
        personBLL.addPerson(request);
    }

    @DeleteMapping("{personId}")
    public void deletePerson(@PathVariable("personId") Integer id) {
        personBLL.deletePerson(id);
    }

    @PutMapping("{personId}")
    public void updatePerson(@PathVariable("personId") Integer id, @RequestBody PersonDTO person) {
        personBLL.updatePerson(id, person);
    }
}
