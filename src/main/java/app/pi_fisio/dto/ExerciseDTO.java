package app.pi_fisio.dto;


import app.pi_fisio.entity.Exercise;
import app.pi_fisio.entity.Intensity;
import app.pi_fisio.entity.Joint;
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

    private Long id;
    private String name;
    private String description;
    private String reps;
    private String videoUrl;
    private Joint joint;
    private Intensity intensity;
}