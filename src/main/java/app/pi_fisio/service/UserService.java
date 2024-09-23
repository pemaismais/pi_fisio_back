package app.pi_fisio.service;

import app.pi_fisio.dto.UserDTO;
import app.pi_fisio.entity.JointIntensity;
import app.pi_fisio.entity.User;
import app.pi_fisio.infra.exception.UserNotFoundException;
import app.pi_fisio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserDTO create(UserDTO userDTO) {
        User user = new User(userDTO);
        // fazendo assim pq n ta fazendo automatico ;-;
        for (JointIntensity jointIntensity : user.getJointIntensities()) {
            jointIntensity.setUser(user);
        }
        return new UserDTO(userRepository.save(user));
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("id", id.toString());
        }
        User user = new User(userDTO);
        user.setId(id);
        return new UserDTO(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("id", id.toString());
        }
        userRepository.deleteById(id);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::new)
                .orElseThrow(() -> new UserNotFoundException("id", id.toString()));
    }

    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::new)
                .orElseThrow(() -> new UserNotFoundException("email", email));
    }


}
