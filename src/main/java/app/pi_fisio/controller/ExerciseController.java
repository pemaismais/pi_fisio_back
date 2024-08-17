package app.pi_fisio.controller;

import app.pi_fisio.dto.ExerciseDTO;
import app.pi_fisio.entity.Joint;
import app.pi_fisio.entity.Intensity;
import app.pi_fisio.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    // create, delete e update do exercise tem que ter uma key/hash
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ExerciseDTO exerciseDTO) {
        try {
            ExerciseDTO response = exerciseService.create(exerciseDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.getId())
                    .toUri();
            return ResponseEntity.created(location).body(response);

        } catch (RuntimeException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ExerciseDTO exerciseDTO) {
        try {
            exerciseService.update(id, exerciseDTO);
            return ResponseEntity.ok("Exercise with the id: " + id + " has been updated!");
        } catch (NoSuchElementException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            exerciseService.delete(id);
            return ResponseEntity.ok("Exercise with the id: " + id + " has been deleted!");
        } catch (NoSuchElementException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An unexpected error occurred");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExerciseById(@PathVariable Long id) {
        try {
            ExerciseDTO response = exerciseService.findById(id);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<ExerciseDTO> response = exerciseService.findAll();
            return ResponseEntity.ok(response);
        } catch (RuntimeException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    // Pegar os exercicios recomendados baseado na Intensidade e Local de dor
    @GetMapping("/findByJointAndIntensity")
    public ResponseEntity<?> getByJointAndIntensity(@RequestParam Joint joint, @RequestParam Intensity intensity) {
        try {
            List<ExerciseDTO> response = exerciseService.findByJointAndIntensity(joint, intensity);
            return ResponseEntity.ok(response);
        } catch (RuntimeException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    // Pegar os exercicios recomendados baseado nas dores de pessoa
    @GetMapping("/getByPerson")
    public ResponseEntity<?> getByPerson(@RequestParam Long personId) {
        try {
            List<ExerciseDTO> response = exerciseService.findByPerson(personId);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
