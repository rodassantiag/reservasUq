package co.edu.uniquindio.reservasUq.controlador;

import co.edu.uniquindio.reservasUq.modelo.Persona;
import co.edu.uniquindio.reservasUq.modelo.Reserva;
import co.edu.uniquindio.reservasUq.observador.Observable;
import co.edu.uniquindio.reservasUq.utils.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class PanelControlador implements Initializable, Observable {

    private Persona persona;

    @FXML
    private Text nombre, tipoPersona;
    @FXML
    private TableColumn<Reserva, String> colId, colFecha, colInstalacion, colCosto;
    @FXML
    private TableView<Reserva> reservaTableView;

    private ObservableList<Reserva> observableList;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public PanelControlador(){
        this.persona = controladorPrincipal.obtenerSesion();
    }

    public void cancelarReserva(ActionEvent actionEvent) {
        Reserva reserva = reservaTableView.getSelectionModel().getSelectedItem();
        if (reserva != null){
            controladorPrincipal.cancelarReserva(reserva.getId());
            actualizarTabla();
            controladorPrincipal.crearAlerta("La Reserva se ha cancelado exitosamente", Alert.AlertType.INFORMATION);
        }

    }

    private void actualizarTabla() {
        List<Reserva> listaConsultatada = controladorPrincipal.obtenerReservas(Sesion.getInstancia().obtenerPersona().getCedula());
        observableList.setAll(listaConsultatada);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.persona != null){
            this.nombre.setText(persona.getNombre());
            this.tipoPersona.setText(persona.getTipoUsuario().toString());
        }

        List<Reserva> listaConsultatada = controladorPrincipal.obtenerReservas(Sesion.getInstancia().obtenerPersona().getCedula());


        observableList = FXCollections.observableArrayList(listaConsultatada);
        observableList.setAll(new ArrayList<>(listaConsultatada));
        reservaTableView.setItems(observableList);

        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaReservada())));
        colInstalacion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getInstalacion().getTipoInstalacion())));
        colCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCosto())));
    }

    public void irCrearReserva() throws Exception {
       FXMLLoader loader =  controladorPrincipal.navegarVentana("/crearReserva.fxml", "Reserva");
       CrearReservaControlador crearReservaControlador = loader.getController();
       crearReservaControlador.inicializarObservable(this);
    }

    public void cerrarSesion() throws Exception {
        controladorPrincipal.eliminarSesion();
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(reservaTableView);

    }


    @Override
    public void notificar() {
        List<Reserva> listaConsultatada = controladorPrincipal.obtenerReservas(Sesion.getInstancia().obtenerPersona().getCedula());
        reservaTableView.setItems(FXCollections.observableArrayList(listaConsultatada));
    }
}
