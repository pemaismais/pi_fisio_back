package app.pi_fisio.service;

import app.pi_fisio.dto.ExerciseDTO;
import app.pi_fisio.entity.Exercise;
import app.pi_fisio.entity.Joint;
import app.pi_fisio.entity.PainIntensity;
import app.pi_fisio.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;

    public ExerciseDTO create(ExerciseDTO exerciseDTO) {
        try {
            Exercise exercise = new Exercise(exerciseDTO);
            return new ExerciseDTO(exerciseRepository.save(exercise));
        }catch(Exception e){
            throw new RuntimeException("Unable to create exercise", e);
        }
    }

    public ExerciseDTO update(Long id,ExerciseDTO exerciseDTO){
        Exercise exercise = new Exercise(exerciseDTO);
        exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find the exercise with id " + id));
        try {
            exercise.setId(id);
           return new ExerciseDTO(exerciseRepository.save(exercise));
        }catch(Exception e){
            throw new RuntimeException("Unable to update exercise", e);
        }
    }

    public void delete(Long id){
        if (!exerciseRepository.existsById(id)) {
            throw new RuntimeException("Could not find the exercise with id " + id);
        }
        try{
            exerciseRepository.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException("Unable to delete exercise", e);
        }
    }

    public List<ExerciseDTO> findAll(){
        try{
            return exerciseRepository.findAll()
                    .stream()
                    .map(ExerciseDTO::new)
                    .toList();
        }catch(Exception e){
            throw new RuntimeException("Unable to reach exercises", e);
        }
    }

    public ExerciseDTO findById(Long id){
        try{
            return exerciseRepository.findById(id)
                    .map(ExerciseDTO::new)
                    .orElseThrow(() -> new RuntimeException("Could not find the exercise with id " + id));

        }catch(RuntimeException e){
            throw e;
        }
        catch(Exception e){
            throw new RuntimeException("Unable to reach exercise", e);
        }
    }

    public List<ExerciseDTO> findByJointAndIntensity(Joint joint, PainIntensity intensity){
        try{
            return exerciseRepository.findByJointAndIntensity(joint,intensity)
                    .stream()
                    .map(ExerciseDTO::new)
                    .toList();
        }catch(Exception e){
            throw new RuntimeException("Unable to reach exercises", e);
        }
    }


}
