package app.pi_fisio.dto;

import app.pi_fisio.entity.JointIntensity;
import app.pi_fisio.entity.User;
import app.pi_fisio.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
        private Long id;
        private String name;
        private String email;
        private UserRole role;
        private String course;
        private List<JointIntensity> jointIntensities;
        
        public UserDTO(User user){
                BeanUtils.copyProperties(user, this);
        }
}
