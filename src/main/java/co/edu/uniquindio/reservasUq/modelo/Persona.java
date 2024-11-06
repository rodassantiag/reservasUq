package co.edu.uniquindio.reservasUq.modelo;

import co.edu.uniquindio.reservasUq.modelo.enums.Rol;
import co.edu.uniquindio.reservasUq.modelo.enums.TipoUsuario;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    private String nombre, cedula, correo, contrasena;
    private TipoUsuario tipoUsuario;
    private Rol rol;
}
