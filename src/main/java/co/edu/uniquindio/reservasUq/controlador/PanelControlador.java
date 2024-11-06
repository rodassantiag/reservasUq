package co.edu.uniquindio.reservasUq.controlador;

import co.edu.uniquindio.reservasUq.modelo.Horario;
import co.edu.uniquindio.reservasUq.modelo.Persona;
import co.edu.uniquindio.reservasUq.modelo.Reserva;
import co.edu.uniquindio.reservasUq.observador.ObservableHorarios;
import co.edu.uniquindio.reservasUq.observador.ObservableReservas;
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
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class PanelControlador implements Initializable, ObservableReservas {

    private Persona persona;

    @FXML
    private Text nombre, tipoPersona;
    @FXML
    private TableColumn<Reserva, String> colId, colFecha, colInstalacion;
    @FXML
    private TableView<Reserva> reservaTableView;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public PanelControlador(){
        this.persona = controladorPrincipal.obtenerSesion();
    }

    public void cancelarReserva(ActionEvent actionEvent){
        try {
            Reserva reserva = reservaTableView.getSelectionModel().getSelectedItem();
            controladorPrincipal.cancelarReserva(reserva);
            controladorPrincipal.crearAlerta("La reserva ha sido cancelada", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.persona != null){
            this.nombre.setText(persona.getNombre());
            this.tipoPersona.setText(persona.getTipoUsuario().toString());
        }else {
            System.out.println("Es null");
        }

        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechaReservada())));
        colInstalacion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getInstalacion().getNombre())));
    }

    public void irCrearReserva() throws Exception {
       FXMLLoader loader =  controladorPrincipal.navegarVentana("/crearReserva.fxml", "Reserva");
       CrearReservaControlador crearReservaControlador = loader.getController();
       crearReservaControlador.inicializarObservable(this);
    }


    @Override
    public void notificar() {
        List<Reserva> listaConstltada = controladorPrincipal.obtenerReservas(Sesion.getInstancia().obtenerPersona().getCedula());
        reservaTableView.setItems(FXCollections.observableArrayList(listaConstltada));
    }
}
