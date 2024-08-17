package app.pi_fisio.dto;

import app.pi_fisio.entity.JointIntensity;
import app.pi_fisio.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
        private Long id;
        private String name;
        private LocalDate birthDate;
        private String course;
        private List<JointIntensity> jointIntensities;

        public PersonDTO(Person person){
                BeanUtils.copyProperties(person, this);
        }
}
