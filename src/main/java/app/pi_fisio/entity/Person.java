package app.pi_fisio.entity;


import app.pi_fisio.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // mudar pra not null talvez
    String name;

    @ManyToMany//(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person-exercise"
    )
    @JsonIgnoreProperties("people")
    List<Exercise> exercises;

    public Person(PersonDTO personDTO){
        BeanUtils.copyProperties(personDTO, this);
    }
}
