package app.pi_fisio.service;

import app.pi_fisio.dto.ExerciseDTO;
import app.pi_fisio.entity.*;
import app.pi_fisio.infra.exception.ExerciseNotFoundException;
import app.pi_fisio.infra.exception.NoJointIntensitiesException;
import app.pi_fisio.infra.exception.UserNotFoundException;
import app.pi_fisio.repository.ExerciseRepository;
import app.pi_fisio.repository.UserRepository;
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
    UserRepository userRepository;

    public ExerciseDTO create(ExerciseDTO exerciseDTO) throws Exception {
        Exercise exercise = new Exercise(exerciseDTO);
        return new ExerciseDTO(exerciseRepository.save(exercise));
    }

    public ExerciseDTO update(Long id, ExerciseDTO exerciseDTO) throws Exception{
        Exercise exercise = new Exercise(exerciseDTO);
        exerciseRepository.findById(id)
                .orElseThrow(ExerciseNotFoundException::new);

        exercise.setId(id);
        return new ExerciseDTO(exerciseRepository.save(exercise));
    }

    public void delete(Long id)  {
        if (!exerciseRepository.existsById(id)){
            throw new ExerciseNotFoundException();
        }
        exerciseRepository.deleteById(id);
    }

    public List<ExerciseDTO> findAll() {
        return exerciseRepository.findAll()
                .stream()
                .map(ExerciseDTO::new)
                .toList();
    }

    public ExerciseDTO findById(Long id) throws ExerciseNotFoundException {
        return exerciseRepository.findById(id)
                .map(ExerciseDTO::new)
                .orElseThrow(ExerciseNotFoundException::new);
    }
    public List<ExerciseDTO> findByJointAndIntensity(Joint joint, Intensity intensity) throws Exception {
        return exerciseRepository.findByJointAndIntensity(joint, intensity)
                .map(exercises -> exercises.stream()
                        .map(ExerciseDTO::new)
                        .toList())
                .orElseThrow(ExerciseNotFoundException::new);
    }

    public List<ExerciseDTO> findByPerson(Long personId) throws Exception {
        User user = userRepository.findById(personId)
                .orElseThrow(() -> new UserNotFoundException("id", personId.toString()));

        List<JointIntensity> jointIntensities = user.getJointIntensities();

        if (jointIntensities == null || jointIntensities.isEmpty()) {
            throw new NoJointIntensitiesException("User has no joint intensities.");
        }

        List<ExerciseDTO> list = new ArrayList<>();
        for (JointIntensity jointIntensity : jointIntensities) {
            list.addAll(findByJointAndIntensity(jointIntensity.getJoint(), jointIntensity.getIntensity()));
        }
        return list;
    }

}
