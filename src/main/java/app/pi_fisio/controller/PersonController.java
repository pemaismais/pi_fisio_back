package app.pi_fisio.controller;

import app.pi_fisio.dto.PersonDTO;
import app.pi_fisio.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    PersonService personService;

    // create, delete e update do person tem que ter uma key/hash
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PersonDTO personDTO){
        try {
            PersonDTO response = personService.create(personDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.getId())
                    .toUri();
            return ResponseEntity.created(location).body(response);

        }catch(RuntimeException err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
        catch(Exception e){
            return  ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        try{
            return ResponseEntity.ok(personService.update(id, personDTO));
        }catch(RuntimeException err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
        catch(Exception e){
            return  ResponseEntity.badRequest().body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            personService.delete(id);
            return ResponseEntity.ok("Person with the id: " + id +" has been deleted!");
        }catch(RuntimeException err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("An unexpected error occurred");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable Long id){
        try {
            PersonDTO response = personService.findById(id);
            return ResponseEntity.ok(response);
        }catch(RuntimeException err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
        catch(Exception e){
            return  ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            List<PersonDTO> response = personService.findAll();
            return ResponseEntity.ok(response);
        }catch(RuntimeException err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
        catch(Exception e){
            return  ResponseEntity.badRequest().build();
        }
    }


}
