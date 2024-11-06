package co.edu.uniquindio.reservasUq.controlador;

import co.edu.uniquindio.reservasUq.modelo.*;
import co.edu.uniquindio.reservasUq.modelo.enums.DiaSemana;
import co.edu.uniquindio.reservasUq.modelo.enums.TipoUsuario;
import co.edu.uniquindio.reservasUq.servicio.ServiciosReservas;
import co.edu.uniquindio.reservasUq.utils.Sesion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
public class ControladorPrincipal implements ServiciosReservas {

    private Horario horario;

    private final ReservasUq reservasUq;

    public static ControladorPrincipal INSTANCIA;

    public ControladorPrincipal(){
        reservasUq = new ReservasUq();
    }

    public static ControladorPrincipal getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ControladorPrincipal();
        }
        return INSTANCIA;
    }

    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana) throws Exception {

        // Cargar la vista
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
        Parent root = loader.load();

        // Crear la escena
        Scene scene = new Scene(root);

        // Crear un nuevo escenario (ventana)
        Stage stage = new Stage();
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.setTitle(tituloVentana);

        // Mostrar la nueva ventana
        stage.show();

        return loader;
    }

    public void crearAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    public void cerrarVentana(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public Persona login(String correo, String contrasena) throws Exception {
        return reservasUq.login(correo, contrasena);
    }

    @Override
    public void registrarPersona(String nombre, String cedula, String correo, String contrasena, TipoUsuario tipoUsuario) throws Exception {
        reservasUq.registrarPersona(nombre, cedula, correo, contrasena, tipoUsuario);
    }

    @Override
    public void crearInstalacion(String nombre, int aforo, float costo, List<Horario> horarios) throws Exception {
        reservasUq.crearInstalacion(nombre, aforo, costo, horarios);
    }

    @Override
    public List<LocalTime> generarHoras(LocalTime horaInicio, LocalTime horaFin) throws Exception {
        return reservasUq.generarHoras(horaInicio, horaFin);
    }

    @Override
    public List<LocalTime> generarHorasParaComboBox() {
        return reservasUq.generarHorasParaComboBox();
    }

    @Override
    public Reserva crearReserva(Persona persona, Instalacion instalacion, LocalDateTime fechaReservada) throws Exception {
        return reservasUq.crearReserva(persona, instalacion, fechaReservada);
    }

    @Override
    public void cancelarReserva(Reserva reserva) throws Exception {
        reservasUq.cancelarReserva(reserva);
    }

    @Override
    public void obtenerreservasPersona(List<Reserva> reservas, LocalDateTime fecha) throws Exception {
        reservasUq.obtenerreservasPersona(reservas, fecha);
    }

    @Override
    public List<Reserva> obtenerReservas(String idPersona) {
        return reservasUq.obtenerReservas(idPersona);
    }


    public void guardarSesion(Persona persona){
        Sesion sesion = Sesion.getInstancia();
        sesion.asignarPersona(persona);
    }

    public Persona obtenerSesion(){
        return Sesion.getInstancia().obtenerPersona();
    }

    public void eliminarSesion(){
        Sesion.getInstancia().asignarPersona(null);
    }
}
