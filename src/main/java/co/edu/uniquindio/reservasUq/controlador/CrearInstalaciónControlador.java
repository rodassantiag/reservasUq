package co.edu.uniquindio.reservasUq.controlador;

import co.edu.uniquindio.reservasUq.modelo.Horario;
import co.edu.uniquindio.reservasUq.modelo.Instalacion;
import co.edu.uniquindio.reservasUq.modelo.enums.TipoInstalacion;
import co.edu.uniquindio.reservasUq.observador.ObservableHorarios;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
public class CrearInstalaciónControlador implements Initializable, ObservableHorarios {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private Instalacion instalacionSeleccionada;
    @FXML
    private ComboBox<TipoInstalacion> tipoInstalacionComboBox;
    @FXML
    private TextField txtAforo, txtCosto;
    @FXML
    private TableColumn<Instalacion, String> colId, colNombre, colAforo, colCosto;
    @FXML
    private TableView<Instalacion> instalacionTableView;
    @FXML
    private TableColumn<Horario, String> colDia, colHoraInicio, colHoraFin;
    @FXML
    private TableView<Horario> horarioTableView;

    private ObservableHorarios observableHorarios;


    public void agregarInstalacion(){
        try {
            TipoInstalacion tipoInstalacion = tipoInstalacionComboBox.getValue();
            String aforoTexto = txtAforo.getText();
            String costoTexto = txtCosto.getText();

            if (!aforoTexto.matches("\\d+") || aforoTexto.isBlank()) {
                throw new Exception("El aforo debe ser un número entero válido");
            }

            if (!costoTexto.matches("\\d+") ||costoTexto.isBlank()) {
                throw new Exception("Costo inválido");
            }

            int aforo = Integer.parseInt(aforoTexto);
            float costo = Float.parseFloat(costoTexto);

            List<Horario> horarios = horarioTableView.getItems();
            controladorPrincipal.crearInstalacion(tipoInstalacion, aforo, costo, horarios);
            instalacionTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.getReservasUq().getInstalaciones()));
            limpiarCampos();
            controladorPrincipal.crearAlerta("La instalación se ha creado exitosamente", Alert.AlertType.INFORMATION);


        } catch (Exception e) {
            e.printStackTrace();
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void irPanelAdmin() throws Exception{
        controladorPrincipal.navegarVentana("/panelAdmin.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(instalacionTableView);
    }

    public void limpiarCampos(){
        tipoInstalacionComboBox.setValue(null);
        txtAforo.clear();
        txtCosto.clear();
        horarioTableView.setItems(FXCollections.observableArrayList());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTipoInstalacion())));
        colAforo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAforo())));
        colCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCosto())));
        instalacionTableView.setItems(FXCollections.observableArrayList(controladorPrincipal.getReservasUq().getInstalaciones()));

        colDia.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDia())));
        colHoraInicio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getInicio())));
        colHoraFin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFin())));
        tipoInstalacionComboBox.setItems(FXCollections.observableArrayList(TipoInstalacion.values()));

    }

    public void abrirVentanaHorario(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = controladorPrincipal.navegarVentana("/crearHorario.fxml", "Horario");
            CrearHorarioControlador crearHorarioControlador = loader.getController();
            crearHorarioControlador.inicializarObservable(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    @Override
    public void notificar(List<Horario> nuevosHorarios) {
        ObservableList<Horario> horariosActuales = horarioTableView.getItems();
        ObservableList<Horario> horariosBuenos = FXCollections.observableArrayList();

        if(!horariosActuales.isEmpty()) {
            for (Horario horario1 : nuevosHorarios) {
                boolean repetido = false;
                for (Horario horario : horariosActuales) {
                    if (horario.getDia() == horario1.getDia()) {
                        repetido = true;
                    }
                }

                if(!repetido){
                    horariosBuenos.add(horario1);
                }

            }
            horariosActuales.addAll(horariosBuenos);
        }else{
            horariosActuales.addAll(nuevosHorarios);
        }

        horarioTableView.setItems(FXCollections.observableArrayList(horariosActuales));

    }



}
