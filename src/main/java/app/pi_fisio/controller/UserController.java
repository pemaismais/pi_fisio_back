package app.pi_fisio.controller;

import app.pi_fisio.dto.UserDTO;
import app.pi_fisio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class UserController {

    @Autowired
    UserService userService;

    // create, delete e update do person tem que ter uma key/hash
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO){
        try {
            UserDTO response = userService.create(userDTO);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try{
            return ResponseEntity.ok(userService.update(id, userDTO));
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
            userService.delete(id);
            return ResponseEntity.ok("User with the id: " + id +" has been deleted!");
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
            UserDTO response = userService.findById(id);
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
            List<UserDTO> response = userService.findAll();
            return ResponseEntity.ok(response);
        }catch(RuntimeException err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
        catch(Exception e){
            return  ResponseEntity.badRequest().build();
        }
    }


}
