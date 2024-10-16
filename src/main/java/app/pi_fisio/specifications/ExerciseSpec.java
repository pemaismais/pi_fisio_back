package app.pi_fisio.specifications;

import app.pi_fisio.entity.Exercise;
import app.pi_fisio.entity.Intensity;
import app.pi_fisio.entity.Joint;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.Set;

public class ExerciseSpec {

    public static Specification<Exercise> nameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(name)) return null;

            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Exercise> hasJoints(Set<Joint> joints) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(joints)) return null;

            return root.get("joint").in(joints);
        };
    }

    public static Specification<Exercise> hasIntensities(Set<Intensity> intensities) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(intensities)) return null;

            return root.get("intensity").in(intensities);
        };
    }
}
