package co.edu.uniquindio.reservasUq.controlador;

import co.edu.uniquindio.reservasUq.modelo.Persona;
import co.edu.uniquindio.reservasUq.modelo.Reserva;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class PanelAdminControlador implements Initializable {

    private Persona persona;
    @FXML
    private TableView<Reserva> reservaTableView;
    @FXML
    private TableColumn<Reserva, String> colId, colFechaReserva, colNombrePersona, colIdPersona, colTipoPersona, colInstalacion,
             colFechaReservada, colCosto;
    @FXML
    private Text nombre, rol;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public PanelAdminControlador(){
        this.persona = controladorPrincipal.obtenerSesion();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.persona != null){
            this.nombre.setText(persona.getCorreo());
            this.rol.setText(persona.getRol().toString());
        }

        cargarValores();


    }

    public void cargarValores(){
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colFechaReserva.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaReserva())));
        colNombrePersona.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersona().getNombre()));
        colIdPersona.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersona().getCedula()));
        colTipoPersona.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPersona().getTipoUsuario())));
        colInstalacion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getInstalacion().getTipoInstalacion())));
        colFechaReservada.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaReservada())));
        colCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getInstalacion().getCosto())));
        reservaTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.getReservasUq().getReservas()));


    }

    public void irCrearInstalacion() throws Exception {
        controladorPrincipal.navegarVentana("/crearInstalacion.fxml", "Crear Instalación");
        controladorPrincipal.cerrarVentana(nombre);
    }

    public void  cerrarSesion() throws Exception {
        controladorPrincipal.eliminarSesion();
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(reservaTableView);
    }
}
