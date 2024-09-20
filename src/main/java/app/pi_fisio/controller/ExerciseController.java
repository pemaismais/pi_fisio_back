package app.pi_fisio.controller;

import app.pi_fisio.dto.ExerciseDTO;
import app.pi_fisio.entity.Intensity;
import app.pi_fisio.entity.Joint;
import app.pi_fisio.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

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
