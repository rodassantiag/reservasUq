package co.edu.uniquindio.reservasUq.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Instalacion {

    private String id, nombre;
    private int aforo;
    private float costo;
    private List<Horario> horarios;

}
