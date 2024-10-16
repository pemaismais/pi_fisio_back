package app.pi_fisio.controller;

import app.pi_fisio.dto.ExercisePageDTO;
import app.pi_fisio.dto.UserDTO;
import app.pi_fisio.dto.UserPageDTO;
import app.pi_fisio.service.UserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("E-mail não pode estar vazio.");
        }

            UserDTO response = userService.create(userDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.getId())
                    .toUri();
            return ResponseEntity.created(location).body(response);

    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(userService.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {

       if (id == null || id.toString().isEmpty()){
           return ResponseEntity.badRequest().body("ID não pode ser inválido");
       }
        userService.delete(id);
        return ResponseEntity.ok("User with the id: " + id + " has been deleted!");
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getPersonById(@PathVariable Long id) {
        if (id == null || id.toString().isEmpty()){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserPageDTO> getAll(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int size
    ) throws Exception {
        UserPageDTO userPageDTO = userService.findAll(page,size);
        return ResponseEntity.ok(userPageDTO);
    }
}
