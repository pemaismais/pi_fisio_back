package app.pi_fisio.service;

import app.pi_fisio.dto.ExerciseDTO;
import app.pi_fisio.entity.*;
import app.pi_fisio.repository.ExerciseRepository;
import app.pi_fisio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    PersonRepository personRepository;

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
                .orElseThrow(() -> new NoSuchElementException("Could not find the exercise with id " + id));
        try {
            exercise.setId(id);
           return new ExerciseDTO(exerciseRepository.save(exercise));
        }catch(Exception e){
            throw new RuntimeException("Unable to update exercise", e);
        }
    }

    public void delete(Long id){
        if (!exerciseRepository.existsById(id)) {
            throw new NoSuchElementException("Could not find the exercise with id " + id);
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
                    .orElseThrow(() -> new NoSuchElementException("Could not find the exercise with id " + id));

        }catch(RuntimeException e){
            throw e;
        }
        catch(Exception e){
            throw new RuntimeException("Unable to reach exercise", e);
        }
    }

    public List<ExerciseDTO> findByJointAndIntensity(Joint joint, Intensity intensity){
        try{
            return exerciseRepository.findByJointAndIntensity(joint,intensity)
                    .stream()
                    .map(ExerciseDTO::new)
                    .toList();
        }catch(Exception e){
            throw new RuntimeException("Unable to reach exercises", e);
        }
    }


    public List<ExerciseDTO> findByPerson(Long personId) {
        // Buscar a pessoa pelo ID e lançar exceção se não for encontrada
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new NoSuchElementException("Could not find the person with id " + personId));

        List<JointIntensity> jointIntensities = person.getJointIntensities();

        // Verificar se a lista de intensidades articulares está vazia
        if (jointIntensities == null || jointIntensities.isEmpty()) {
            throw new NoSuchElementException("Person with no jointIntensities with id " + personId);
        }

        // Coletar todos os exercícios correspondentes às intensidades articulares
        return jointIntensities.stream()
                .flatMap(jointIntensity ->
                        exerciseRepository.findByJointAndIntensity(jointIntensity.getJoint(), jointIntensity.getIntensity())
                                .stream()
                                .map(ExerciseDTO::new)
                )
                .collect(Collectors.toList());
    }


}
