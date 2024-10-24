package app.pi_fisio.dto;

import app.pi_fisio.entity.Intensity;
import app.pi_fisio.entity.Joint;

public record JointIntensityDTO(Joint joint, Intensity intensity) {
}
