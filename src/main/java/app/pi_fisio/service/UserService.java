package app.pi_fisio.service;

import app.pi_fisio.dto.JointIntensityDTO;
import app.pi_fisio.dto.UserDTO;
import app.pi_fisio.dto.UserPageDTO;
import app.pi_fisio.entity.Joint;
import app.pi_fisio.entity.JointIntensity;
import app.pi_fisio.entity.User;
import app.pi_fisio.helper.CopyPropertiesUtil;
import app.pi_fisio.infra.exception.UserNotFoundException;
import app.pi_fisio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

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

    public UserPageDTO findAll(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserDTO> users = userPage.get().map(UserDTO::new).toList();

        return new UserPageDTO(users, userPage.getTotalElements(), userPage.getTotalPages());
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

    public UserDTO updateJointIntensities(List<JointIntensityDTO> jointIntensitiesDTO, String jwt) throws Exception{
        String email = jwtService.validateToken(jwt);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email", email));

        List<JointIntensity> jointIntensities = user.getJointIntensities();
        jointIntensities.clear();

        for (JointIntensityDTO jointIntensityDTO : jointIntensitiesDTO ) {
            JointIntensity jointIntensity = new JointIntensity(
                    null,
                    jointIntensityDTO.joint(),
                    jointIntensityDTO.intensity(),
                    user);
            jointIntensities.add(jointIntensity);
            jointIntensity.setUser(user);
        }

        user.setJointIntensities(jointIntensities);
        return new UserDTO(userRepository.save(user));
    }


    public UserDTO patchUpdate(UserDTO userDTO,String jwt) throws Exception{
        String email = jwtService.validateToken(jwt);

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email", email));

        User patchUser = new User(userDTO);
        patchUser.setRole(null);
        patchUser.setId(null);

        if(!patchUser.getJointIntensities().isEmpty()){
            List<JointIntensity> currentUserJointIntensities = replaceJointIntensities(currentUser, patchUser);
            patchUser.setJointIntensities(currentUserJointIntensities);
        }

        CopyPropertiesUtil.copyNonNullProperties(patchUser, currentUser);

        return new UserDTO(userRepository.save(currentUser));
    }

    private static List<JointIntensity> replaceJointIntensities(User currentUser, User patchUser) {
        List<JointIntensity> currentUserJointIntensities = currentUser.getJointIntensities();
        currentUserJointIntensities.clear();

        for (JointIntensity jointIntensities : patchUser.getJointIntensities() ) {
            JointIntensity jointIntensity = new JointIntensity(
                    null,
                    jointIntensities.getJoint(),
                    jointIntensities.getIntensity(),
                    currentUser);
            currentUserJointIntensities.add(jointIntensity);
        }
        return currentUserJointIntensities;
    }
}
