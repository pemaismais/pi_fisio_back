package app.pi_fisio.controller;

import app.pi_fisio.dto.ExerciseDTO;
import app.pi_fisio.dto.ExerciseFilterDTO;
import app.pi_fisio.dto.ExercisePageDTO;
import app.pi_fisio.entity.Intensity;
import app.pi_fisio.entity.Joint;
import app.pi_fisio.queryfilters.ExerciseQueryFilter;
import app.pi_fisio.service.ExerciseService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExerciseDTO> create(@RequestBody ExerciseDTO exerciseDTO) throws Exception {
        ExerciseDTO response = exerciseService.create(exerciseDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ExerciseDTO exerciseDTO) throws Exception {
        exerciseService.update(id, exerciseDTO);
        return ResponseEntity.ok("Exercise with the id: " + id + " has been updated!");

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        exerciseService.delete(id);
        return ResponseEntity.ok("Exercise with the id: " + id + " has been deleted!");
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable Long id) throws Exception {
        ExerciseDTO response = exerciseService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExercisePageDTO> getAll
            (@RequestParam(defaultValue = "0") @PositiveOrZero int page,
             @RequestParam(defaultValue = "10") @Positive @Max(100) int size,
             @ModelAttribute ExerciseQueryFilter filter){

        ExercisePageDTO response = exerciseService.findAll(page, size, filter);
        return ResponseEntity.ok(response);
    }

    // Pegar os exercicios recomendados baseado na Intensidade e Local de dor
    @GetMapping("/findByJointAndIntensity")
    public ResponseEntity<List<ExerciseDTO>> getByJointAndIntensity(@RequestParam Joint joint, @RequestParam Intensity intensity) throws Exception {
            List<ExerciseDTO> response = exerciseService.findByJointAndIntensity(joint, intensity);
            return ResponseEntity.ok(response);
    }

    // Pegar os exercicios recomendados baseado nas dores de pessoa
    @GetMapping("/getByUser")
    public ResponseEntity<List<ExerciseDTO>> getByUser(@RequestParam Long userId) throws Exception {
        List<ExerciseDTO> response = exerciseService.findByUser(userId);
        return ResponseEntity.ok(response);
    }
}
