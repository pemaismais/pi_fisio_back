package app.pi_fisio.entity;


import app.pi_fisio.dto.ExerciseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Join;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Exercise {

    public Exercise(ExerciseDTO exerciseDTO){
        BeanUtils.copyProperties(exerciseDTO,this);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String description;
    String reps;
    String videoUrl;
    Joint joint;
    PainIntensity painIntensity;

    @ManyToMany(mappedBy = "exercises")
    @JsonIgnoreProperties("exercises")
    List<Person> people;
}
