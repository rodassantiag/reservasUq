package co.edu.uniquindio.reservasUq.controlador;

import co.edu.uniquindio.reservasUq.modelo.Horario;
import co.edu.uniquindio.reservasUq.modelo.Instalacion;
import co.edu.uniquindio.reservasUq.modelo.Persona;
import co.edu.uniquindio.reservasUq.modelo.Reserva;
import co.edu.uniquindio.reservasUq.modelo.enums.DiaSemana;
import co.edu.uniquindio.reservasUq.observador.ObservableReservas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class CrearReservaControlador implements Initializable {

    private Persona persona;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private TableColumn<Instalacion, String> colNombre, colAforo, colCosto;
    @FXML
    private TableView<Instalacion> instalacionTableView;
    @FXML
    private DatePicker fecha;
    @FXML
    private ComboBox<LocalTime> horaComboBox;
    private ObservableReservas observableReservas;

    private List<Reserva> reservas = new ArrayList<>();

    public CrearReservaControlador() {
        this.persona = controladorPrincipal.obtenerSesion();
    }

    public void crearReserva(ActionEvent actionEvent) throws Exception {
        try {
            Persona usuario = persona;
            Instalacion instalacion = instalacionTableView.getSelectionModel().getSelectedItem();
            LocalDate diaSeleccionado = fecha.getValue();
            LocalTime horaSeleccionada = horaComboBox.getValue();
            LocalDateTime fechaReservada = LocalDateTime.of(diaSeleccionado, horaSeleccionada);

            System.out.println(fechaReservada);

            Reserva reserva = controladorPrincipal.crearReserva(usuario, instalacion, fechaReservada);
            reservas.add(reserva);

            controladorPrincipal.crearAlerta("La instalación ha sido reservada exitosamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observableReservas.notificar();
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }







    public void cargarInstalaciones(){
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colAforo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAforo())));
        colCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCosto())));
        instalacionTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.getReservasUq().getInstalaciones()));
    }

    public void cargarHorasComboBox(Instalacion instalacion, LocalDate diaSeleccionado) {
        try {
            DiaSemana diaSemana = convertirADiaSemana(diaSeleccionado.getDayOfWeek());
            List<LocalTime> horasDisponibles = new ArrayList<>();

            for (Horario horario : instalacion.getHorarios()) {
                if (horario.getDia().equals(diaSemana)) {
                    horasDisponibles.addAll(horario.getHoras());
                }
            }

            if (horasDisponibles.isEmpty()) {
                controladorPrincipal.crearAlerta("No hay horarios disponibles para la instalación en el día seleccionado.", Alert.AlertType.WARNING);
                horaComboBox.setDisable(true);
                horaComboBox.setItems(FXCollections.observableArrayList());
            } else {
                horaComboBox.setDisable(false);
                horaComboBox.setItems(FXCollections.observableArrayList(horasDisponibles));
            }
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private DiaSemana convertirADiaSemana(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> DiaSemana.LUNES;
            case TUESDAY -> DiaSemana.MARTES;
            case WEDNESDAY -> DiaSemana.MIERCOLES;
            case THURSDAY -> DiaSemana.JUEVES;
            case FRIDAY -> DiaSemana.VIERNES;
            case SATURDAY -> DiaSemana.SABADO;
            case SUNDAY -> DiaSemana.DOMINGO;
        };
    }

    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(instalacionTableView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        horaComboBox.setDisable(true);
        cargarInstalaciones();
        fecha.valueProperty().addListener((observable, oldDate, newDate) -> {
            Instalacion instalacionSeleccionada = instalacionTableView.getSelectionModel().getSelectedItem();
            if (instalacionSeleccionada != null && newDate != null) {
                horaComboBox.setDisable(false);
                cargarHorasComboBox(instalacionSeleccionada, newDate);
            }
        });


    }

    public void inicializarObservable(ObservableReservas observableReservas){
        this.observableReservas = observableReservas;

    }
}
