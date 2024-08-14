package app.pi_fisio.dto;

import app.pi_fisio.entity.Exercise;
import app.pi_fisio.entity.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
        public PersonDTO(Person person){
                BeanUtils.copyProperties(person, this);
        }
        Long id;
        String name;
        List<Exercise> exercises;

}
