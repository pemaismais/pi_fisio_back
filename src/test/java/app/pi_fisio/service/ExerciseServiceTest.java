package app.pi_fisio.service;

import app.pi_fisio.dto.ExerciseDTO;
import app.pi_fisio.entity.*;
import app.pi_fisio.repository.ExerciseRepository;
import app.pi_fisio.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class ExerciseServiceTest {

    @Autowired
    ExerciseService exerciseService;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    ExerciseRepository exerciseRepository;

    @BeforeEach
    void setup(){
        // Pessoa
        Person person01 = new Person();
        person01.setId(1L);
        List<JointIntensity> jointIntensities = List.of(
                new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, person01),
                new JointIntensity(1L, Joint.KNEE, Intensity.MEDIUM, person01)
        );
        person01.setJointIntensities(jointIntensities);

        Mockito.when(personRepository.findById(1L))
                .thenReturn(Optional.of(person01));

        // Exercicios
        Exercise exercise01 = new Exercise(1L, "Exercise 01","Exercise 01 description", "3x", "https://videoUrl.com", Joint.SHOULDER, Intensity.HIGH);
        Exercise exercise02 = new Exercise(2L, "Exercise 02","Exercise 02 description", "6x", "https://videoUrl.com", Joint.SHOULDER, Intensity.HIGH);
        List<Exercise> shoulderHighExercises = List.of(exercise02,exercise01);

        Exercise exercise03 = new Exercise(3L, "Exercise 03","Exercise 03 description", "12x", "https://videoUrl.com", Joint.KNEE, Intensity.MEDIUM);
        List<Exercise> kneeMediumExercises = List.of(exercise03);

        Mockito.when(exerciseRepository.findByJointAndIntensity(Joint.SHOULDER,Intensity.HIGH))
                .thenReturn(shoulderHighExercises);

        Mockito.when(exerciseRepository.findByJointAndIntensity(Joint.KNEE,Intensity.MEDIUM))
                        .thenReturn(kneeMediumExercises);
    }

    @Test
    void findByPerson001(){
        List<ExerciseDTO> response = exerciseService.findByPerson(1L);

        Assertions.assertEquals(3 , response.size());

    }
}