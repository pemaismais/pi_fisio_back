package app.pi_fisio.service;

import app.pi_fisio.dto.PersonDTO;
import app.pi_fisio.entity.JointIntensity;
import app.pi_fisio.entity.Person;
import app.pi_fisio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public PersonDTO create(PersonDTO personDTO) {
        try {
            Person person = new Person(personDTO);
            // fazendo assim pq n ta fazendo automatico ;-;
            for (JointIntensity jointIntensity : person.getJointIntensities()) {
                jointIntensity.setPerson(person);
            }

            return new PersonDTO(personRepository.save(person));
        } catch (Exception e) {
            throw new RuntimeException("Unable to create person", e);
        }
    }

    public PersonDTO update(Long id, PersonDTO personDTO) {
        Person person = new Person(personDTO);
        personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find the person with id " + id));
        try {
            person.setId(id);
            return new PersonDTO(personRepository.save(person));
        } catch (Exception e) {
            throw new RuntimeException("Unable to update person", e);
        }
    }

    public void delete(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException("Could not find the person with id " + id);
        }
        try {
            personRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete person", e);
        }
    }

    public List<PersonDTO> findAll() {
        try {
            return personRepository.findAll()
                    .stream()
                    .map(PersonDTO::new)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Unable to reach data", e);
        }
    }

    public PersonDTO findById(Long id) {
        try {
            return personRepository.findById(id)
                    .map(PersonDTO::new)
                    .orElseThrow(() -> new RuntimeException("Could not find the person with id " + id));

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to reach person", e);
        }
    }

    public PersonDTO findByEmail(String email) {
        try {
            return personRepository.findByEmail(email)
                    .map(PersonDTO::new)
                    .orElseThrow(() -> new RuntimeException("Could not find the person with email " + email));

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to reach person", e);
        }
    }

}
