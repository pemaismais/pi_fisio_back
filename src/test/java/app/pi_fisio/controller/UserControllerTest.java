package app.pi_fisio.controller;

import app.pi_fisio.dto.UserDTO;
import app.pi_fisio.entity.*;
import app.pi_fisio.repository.UserRepository;
import app.pi_fisio.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserControllerTest {

    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {//create 201

        UserDTO user1 = new UserDTO(1L,"", "fulano", "@gmail.com", UserRole.USER, List.of("Portugues"), List.of(new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null), new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)));

        when(userService.create(any(UserDTO.class))).thenReturn(user1);
        ResponseEntity<?> response = userController.create(user1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println(response);
        System.out.println("Usuário:  " + user1);

    }

    @Test
    void createBad() {
        //bad bad 400, retornando quando o e-mail tiver vazio

        UserDTO user1 = new UserDTO( 1L,"", "joana", "", UserRole.USER, List.of("Portugues"), List.of(new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null), new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)));

        ResponseEntity<?> response = userController.create(user1);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println(response);

    }

    @Test
    void update() { //200
        UserDTO userDto = new UserDTO(1L,"",  "joansa", "@gmail.com", UserRole.ADMIN, List.of("Portugues"),
                List.of(new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)));


        ResponseEntity<?> response = userController.update(1L, userDto);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response);
    }

    @Test
    void updateBad() { //400
        UserDTO userDto = new UserDTO(1L,"",  "joansa", "", UserRole.ADMIN, List.of("Portugues"),
                List.of(new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)));


        ResponseEntity<?> response = userController.update(1L, userDto);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println(response);
    }


    @Test
    void delete() { //200 OK
        UserDTO user1 = new UserDTO( 1L,"", "joana", "@gmail.com", UserRole.USER, List.of("Portugues"), List.of(new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null), new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)));

        ResponseEntity<?> response = userController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response);
    }

    @Test
    void deleteBad() { //400 BAD
        UserDTO user1 = new UserDTO( null,"", "joana", "@gmail.com", UserRole.USER, List.of("Portugues"), List.of(new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null), new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)));

        ResponseEntity<?> response = userController.delete(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println(response);
    }

    @Test
    void getPersonById() {//200 ok
        UserDTO user1 = new UserDTO( 1L,"", "joana", "@gmail.com", UserRole.USER, List.of("Portugues"), List.of(new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null), new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)));

        ResponseEntity<?> response = userController.getPersonById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response);

    }


    @Test
    void getPersonByIdBad() {//400 ok
        UserDTO user1 = new UserDTO( null,"", "joana", "@gmail.com", UserRole.USER, List.of("Portugues"), List.of(new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null), new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)));

        ResponseEntity<?> response = userController.getPersonById(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println(response);

    }

    @Test//200 ok
    void getAll() throws Exception {


        UserDTO user1 = new UserDTO(1L,"",  "Joana", "joana@gmail.com", UserRole.USER, List.of("Portugues"),
                List.of(new JointIntensity(1L, Joint.SHOULDER, Intensity.HIGH, null),
                        new JointIntensity(2L, Joint.KNEE, Intensity.MEDIUM, null)));

        UserDTO user2 = new UserDTO(2L,"",  "Carlos", "carlos@gmail.com", UserRole.ADMIN, List.of("Portugues"),
                List.of(new JointIntensity(3L, Joint.LOWERBACK, Intensity.LOW, null),
                        new JointIntensity(4L, Joint.ANKLE, Intensity.HIGH, null)));

        UserDTO user3 = new UserDTO(3L,"",  "Mariaa", "maria@gmail.com", UserRole.USER, List.of("Portugues"),
                List.of(new JointIntensity(5L, Joint.HIP, Intensity.MEDIUM, null),
                        new JointIntensity(6L, Joint.ANKLE, Intensity.LOW, null)));

        UserDTO user4 = new UserDTO(4L,"",  "Pedro", "pedro@gmail.com", UserRole.USER, List.of("Portugues"),
                List.of(new JointIntensity(7L, Joint.CERVICAL, Intensity.HIGH, null),
                        new JointIntensity(8L, Joint.LOWERBACK, Intensity.MEDIUM, null)));


        when(userService.findAll()).thenReturn(List.of(user1, user2, user3, user4));
        ResponseEntity<List<UserDTO>> response = userController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().size());
        System.out.println(response);

    }
}