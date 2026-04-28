package fr.univlyon1.m1if10.eventify_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String email;
    private String mdp;
}
