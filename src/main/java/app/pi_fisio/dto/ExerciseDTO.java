package app.pi_fisio.dto;


import app.pi_fisio.entity.Exercise;
import app.pi_fisio.entity.Joint;
import app.pi_fisio.entity.PainIntensity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExerciseDTO {
    public ExerciseDTO(Exercise exercise){
        BeanUtils.copyProperties(exercise,this);
    }

    Long id;
    String name;
    String description;
    String reps;
    String videoUrl;
    Joint joint;
    PainIntensity painIntensity;
}