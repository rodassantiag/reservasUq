package co.edu.uniquindio.reservasUq.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Reserva {
    private String id;
    private Persona persona;
    private Instalacion instalacion;
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaReservada;
    private double costo;

}
