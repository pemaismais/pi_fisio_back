package app.pi_fisio.service;

import app.pi_fisio.dto.UserDTO;
import app.pi_fisio.entity.JointIntensity;
import app.pi_fisio.entity.User;
import app.pi_fisio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserDTO create(UserDTO userDTO) {
        try {
            User user = new User(userDTO);
            // fazendo assim pq n ta fazendo automatico ;-;
            for (JointIntensity jointIntensity : user.getJointIntensities()) {
                jointIntensity.setUser(user);
            }

            return new UserDTO(userRepository.save(user));
        } catch (Exception e) {
            throw new RuntimeException("Unable to create person", e);
        }
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find the user with id " + id));
        try {
            user.setId(id);
            return new UserDTO(userRepository.save(user));
        } catch (Exception e) {
            throw new RuntimeException("Unable to update user", e);
        }
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Could not find the person with id " + id);
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete person", e);
        }
    }

    public List<UserDTO> findAll() {
        try {
            return userRepository.findAll()
                    .stream()
                    .map(UserDTO::new)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Unable to reach data", e);
        }
    }

    public UserDTO findById(Long id) {
        try {
            return userRepository.findById(id)
                    .map(UserDTO::new)
                    .orElseThrow(() -> new RuntimeException("Could not find the person with id " + id));

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to reach person", e);
        }
    }

    public UserDTO findByEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                    .map(UserDTO::new)
                    .orElseThrow(() -> new RuntimeException("Could not find the person with email " + email));

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to reach person", e);
        }
    }

}
