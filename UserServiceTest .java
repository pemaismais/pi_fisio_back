package app.pi_fisio.service;

import app.pi_fisio.dto.UserDTO;
import app.pi_fisio.entity.*;
import app.pi_fisio.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    }

    @Test
    void buscarADM(){
        User user1 = User.builder()
                .id(1L)
                .name("Jose Aparecido")
                .email("@gmail.com")
                .course("Portugues")
                .role(UserRole.ADMIN)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("Fulano de Tal")
                .email("fulano@gmail.com")
                .course("Ingles")
                .role(UserRole.USER)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        User user3 = User.builder()
                .id(3L)
                .name("Joana ")
                .email("joana@gmail.com")
                .course("Ciencias")
                .role(UserRole.ADMIN)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));

        List<User> allUsers = userRepository.findAll();
        List<User> admins = allUsers.stream()
                .filter(user -> user.getRole() == UserRole.ADMIN)
                .toList();

        assertEquals(UserRole.ADMIN, admins.get(0).getRole());
        System.out.println(admins);
    }

    @Test
    void findAll(){
        User user1 = User.builder()
                .id(1L)
                .name("Jose Aparecido")
                .email("@gmail.com")
                .course("Portugues")
                .role(UserRole.ADMIN)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("Fulano de Tal")
                .email("fulano@gmail.com")
                .course("Ingles")
                .role(UserRole.USER)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        User user3 = User.builder()
                .id(3L)
                .name("Joana ")
                .email("joana@gmail.com")
                .course("Ciencias")
                .role(UserRole.ADMIN)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));
        List<User> usersList = userRepository.findAll();
        System.out.println(usersList);
    }

    @Test
    void deleteUsers(){
        User user1 = User.builder()
                .id(1L)
                .name("Jose Aparecido")
                .email("@gmail.com")
                .course("Portugues")
                .role(UserRole.ADMIN)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("Fulano de Tal")
                .email("fulano@gmail.com")
                .course("Ingles")
                .role(UserRole.USER)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();


        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        Assertions.assertThrows(RuntimeException.class, () -> userService.delete(1L));
        System.out.println("usuário foi deletado");
    }

    @Test
    void findByIdUser(){
        User user1 = User.builder()
                .id(2L)
                .name("Fulanos de Tal")
                .email("fulano@gmail.com")
                .course("Ingles")
                .role(UserRole.USER)
                .jointIntensities(List.of(
                        new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)))
                .build();

        when(userRepository.findById(2l)).thenReturn(Optional.of(user1));
        Optional<User> Iduser = userRepository.findById(2L);
        assertTrue(Iduser.isPresent());
        assertEquals(2L, Iduser.get().getId());
        System.out.println(Iduser);

    }


}