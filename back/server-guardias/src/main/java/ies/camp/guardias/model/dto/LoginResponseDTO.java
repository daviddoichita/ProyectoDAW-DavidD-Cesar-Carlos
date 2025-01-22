package ies.camp.guardias.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponseDTO implements Serializable {

    private String token;
    private Long expiraEn;
}
