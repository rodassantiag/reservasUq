package co.edu.uniquindio.reservasUq.modelo;

import co.edu.uniquindio.reservasUq.modelo.enums.DiaSemana;
import lombok.*;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Horario {
    private DiaSemana dia;
    private LocalTime Inicio, fin;
    private List<LocalTime> horas;
}
