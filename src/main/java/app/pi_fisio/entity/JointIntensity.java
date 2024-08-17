package app.pi_fisio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class JointIntensity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Joint joint;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Intensity intensity;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    @JsonBackReference
    private Person person;
}
