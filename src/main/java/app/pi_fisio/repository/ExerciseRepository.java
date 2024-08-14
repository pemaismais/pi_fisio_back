package app.pi_fisio.repository;

import app.pi_fisio.entity.Exercise;
import app.pi_fisio.entity.Joint;
import app.pi_fisio.entity.PainIntensity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  ExerciseRepository extends JpaRepository<Exercise,Long> {

    @Query("SELECT e FROM Exercise e WHERE e.joint = :joint AND e.painIntensity = :intensity")
    public List<Exercise> findByJointAndIntensity(Joint joint, PainIntensity intensity);
}
