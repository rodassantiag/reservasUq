package co.edu.uniquindio.reservasUq.servicio;

import co.edu.uniquindio.reservasUq.modelo.Horario;
import co.edu.uniquindio.reservasUq.modelo.Instalacion;
import co.edu.uniquindio.reservasUq.modelo.Persona;
import co.edu.uniquindio.reservasUq.modelo.Reserva;
import co.edu.uniquindio.reservasUq.modelo.enums.DiaSemana;
import co.edu.uniquindio.reservasUq.modelo.enums.TipoUsuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ServiciosReservas {

    Persona login(String correo, String contrasena) throws Exception;

    void registrarPersona(String nombre, String cedula, String correo, String contrasena,
                          TipoUsuario tipoUsuario) throws Exception;

    void crearInstalacion(String nombre, int aforo, float costo, List<Horario> horarios)throws Exception;

    List<LocalTime> generarHoras(LocalTime horaInicio, LocalTime horaFin) throws Exception;

    List<LocalTime> generarHorasParaComboBox();

    Reserva crearReserva(Persona persona, Instalacion instalacion, LocalDateTime fechaReservada) throws Exception;

    void cancelarReserva(Reserva reserva) throws Exception;

    void obtenerreservasPersona(List<Reserva> reservas, LocalDateTime fecha) throws Exception;

    List<Reserva> obtenerReservas(String idPersona);
}
