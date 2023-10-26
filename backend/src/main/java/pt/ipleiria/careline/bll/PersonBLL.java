package pt.ipleiria.careline.bll;

import org.springframework.stereotype.Controller;
import pt.ipleiria.careline.dal.PersonRepository;
import pt.ipleiria.careline.dto.PersonDTO;
import pt.ipleiria.careline.entities.Person;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonBLL {
    private final PersonRepository personRepository;

    public PersonBLL(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public ArrayList<PersonDTO> getPersons() {
        List<Person> personList = personRepository.findAll();
        ArrayList<PersonDTO> personDTOList = new ArrayList<PersonDTO>();

        for (Person person : personList) {
            personDTOList.add(new PersonDTO(person.getName(), person.getEmail(), person.getAge()));
        }
        return personDTOList;
    }

    public void addPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setEmail(personDTO.getEmail());
        person.setAge(personDTO.getAge());
        personRepository.save(person);
    }

    public void deletePerson(Integer id) {
        personRepository.deleteById(id);
    }

    public void updatePerson(Integer id, PersonDTO person) {
        Person personToUpdate = personRepository.findById(id).orElseThrow();
        personToUpdate.setName(person.getName());
        personToUpdate.setEmail(person.getEmail());
        personToUpdate.setAge(person.getAge());
        personRepository.save(personToUpdate);
    }
}
