package app.pi_fisio.repository;


import app.pi_fisio.entity.JointIntensity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JointIntensityRepository extends JpaRepository<JointIntensity, Long> {
}