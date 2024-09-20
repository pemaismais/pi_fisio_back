package app.pi_fisio.repository;

import app.pi_fisio.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {

    public Optional<Person> findByEmail(String email);
}
