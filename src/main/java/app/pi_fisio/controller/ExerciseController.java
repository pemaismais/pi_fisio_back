package app.pi_fisio.controller;

import app.pi_fisio.dto.ExerciseDTO;
import app.pi_fisio.entity.Intensity;
import app.pi_fisio.entity.Joint;
import app.pi_fisio.infra.exception.ExerciseNotFoundException;
import app.pi_fisio.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody ExerciseDTO exerciseDTO) throws Exception {
        ExerciseDTO response = exerciseService.create(exerciseDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);

    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ExerciseDTO exerciseDTO) throws Exception {
        exerciseService.update(id, exerciseDTO);
        return ResponseEntity.ok("Exercise with the id: " + id + " has been updated!");

    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        exerciseService.delete(id);
        return ResponseEntity.ok("Exercise with the id: " + id + " has been deleted!");
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getExerciseById(@PathVariable Long id) throws Exception {
        ExerciseDTO response = exerciseService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        List<ExerciseDTO> response = exerciseService.findAll();
        return ResponseEntity.ok(response);
    }

    // Pegar os exercicios recomendados baseado na Intensidade e Local de dor
    @GetMapping("/findByJointAndIntensity")
    public ResponseEntity<?> getByJointAndIntensity(@RequestParam Joint joint, @RequestParam Intensity intensity) throws Exception {
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
    public ResponseEntity<?> getByPerson(@RequestParam Long personId) throws Exception {
        List<ExerciseDTO> response = exerciseService.findByPerson(personId);
        return ResponseEntity.ok(response);
    }
}
