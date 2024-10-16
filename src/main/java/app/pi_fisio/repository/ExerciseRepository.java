package app.pi_fisio.repository;

import app.pi_fisio.entity.Exercise;
import app.pi_fisio.entity.Intensity;
import app.pi_fisio.entity.Joint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends
        JpaRepository<Exercise, Long>,
        JpaSpecificationExecutor<Exercise> {

    @Query("SELECT e FROM Exercise e WHERE e.joint = :joint AND e.intensity = :intensity")
    public Optional<List<Exercise> > findByJointAndIntensity(Joint joint, Intensity intensity);
}
