package org.acme.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.acme.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Role role;
}
