package app.pi_fisio.service;

import app.pi_fisio.dto.UserDTO;
import app.pi_fisio.entity.*;
import app.pi_fisio.infra.exception.UserNotFoundException;
import app.pi_fisio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Mock
    UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user1 = User.builder()
                .id(1L)
                .name("Jose Aparecido")
                .email("@gmail.com")
                .role(UserRole.ADMIN)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("Fulano de Tal")
                .email("fulano@gmail.com")
                .role(UserRole.USER)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        User user3 = User.builder()
                .id(3L)
                .name("Joana ")
                .email("joana@gmail.com")
                .classes(List.of("Portugues"))
                .role(UserRole.ADMIN)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(0L)).thenReturn(Optional.empty());
        when(userRepository.existsById(0L)).thenReturn(false);
        when(userRepository.findByEmail("email@valido.com")).thenReturn(Optional.of(user1));
        when(userRepository.findByEmail("email@invalido.com")).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user1);

    }

    @Test
    void buscarADM() {
        List<User> allUsers = userRepository.findAll();
        List<User> admins = allUsers.stream()
                .filter(user -> user.getRole() == UserRole.ADMIN)
                .toList();

        assertEquals(UserRole.ADMIN, admins.get(0).getRole());
        System.out.println(admins);
    }

    @Test
    void findAll() {
//        List<UserDTO> response = userService.findAll();
//        assertNotNull(response);
//        System.out.println(response);
    }

    @Test
    void deleteUsers() {
        // Com id valido
        assertDoesNotThrow(() -> userService.delete(1L));
        // Com id invalido
        assertThrows(UserNotFoundException.class, () -> userService.delete(0L));
    }

    @Test
    void findByIdUser() {
        // Com id valido!
        UserDTO response = userService.findById(1L);
        assertNotNull(response);
        // Com id invalido!
        assertThrows(UserNotFoundException.class, () -> userService.delete(0L));
    }

    @Test
    void findByEmail() {
        // Com email valido!
        UserDTO response = userService.findByEmail("email@valido.com");
        assertNotNull(response);
        // Com email invalido!
        assertThrows(UserNotFoundException.class, () -> userService.findByEmail("email@invalido.com"));
    }

    @Test
    void update() {
        UserDTO userdto = UserDTO.builder()
                .id(3L)
                .name("Joana ")
                .email("joana@gmail.com")
                .classes(List.of("3b"))
                .role(UserRole.ADMIN)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();
        // Com id valido!
        UserDTO response = userService.update(1L, userdto);
        assertNotNull(response);
        // Com id invalido!
        assertThrows(UserNotFoundException.class, () -> userService.update(0L, userdto));
    }

    @Test
    void create() {
        UserDTO userdto = UserDTO.builder()
                .id(3L)
                .name("Joana ")
                .email("joana@gmail.com")
                .classes(List.of("3b"))
                .role(UserRole.ADMIN)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        UserDTO response = userService.create(userdto);
        assertNotNull(response);
    }

}