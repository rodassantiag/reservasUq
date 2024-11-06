package co.edu.uniquindio.reservasUq.controlador;

import co.edu.uniquindio.reservasUq.modelo.Horario;
import co.edu.uniquindio.reservasUq.modelo.enums.DiaSemana;
import co.edu.uniquindio.reservasUq.observador.ObservableHorarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Getter
@Setter
public class CrearHorarioControlador implements Initializable {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private ComboBox<LocalTime> textHoraInicio, textHoraFin;

    @FXML
    private CheckBox cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes, cbSabado, cbDomingo;

    private ObservableList<CheckBox> checkBoxes;

    private ObservableHorarios observableHorarios;

    private List<Horario> horarios = new ArrayList<>();

    public void onAgregarHorario(ActionEvent actionEvent) throws Exception {
        try {

            LocalTime horaInicio = textHoraInicio.getValue();
            LocalTime horaFin = textHoraFin.getValue();

            if (horaInicio == null || horaFin == null){
                throw new Exception("las horas de inicio y de fin son obligatorias");
            }

            if (!cbLunes.isSelected() && !cbMartes.isSelected() && !cbMiercoles.isSelected() && !cbJueves.isSelected() && !cbViernes.isSelected()
                    && !cbSabado.isSelected() && !cbDomingo.isSelected()){
                throw new Exception("Elija al menos un día de la semana");
            }

            List<Horario> nuevosHorarios = new ArrayList<>();

            List<LocalTime> horas = controladorPrincipal.generarHoras(horaInicio, horaFin);

            for(int i = 0; i < checkBoxes.size(); i++){
                if(checkBoxes.get(i).isSelected()){

                    Horario horario = Horario.builder()
                            .dia(DiaSemana.values()[i])
                            .Inicio(horaInicio)
                            .fin(horaFin)
                            .horas(horas)
                            .build();



                    horarios.add(horario);
                    nuevosHorarios.add(horario);


                }

            }

            controladorPrincipal.crearAlerta("El horario se ha agregado Exitosamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observableHorarios.notificar(nuevosHorarios);

        } catch (Exception e) {
            e.printStackTrace();
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(cbDomingo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkBoxes = FXCollections.observableArrayList( List.of(cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes, cbSabado, cbDomingo) );
        textHoraInicio.setItems(FXCollections.observableArrayList(controladorPrincipal.generarHorasParaComboBox()));
        textHoraFin.setItems(FXCollections.observableArrayList(controladorPrincipal.generarHorasParaComboBox()));
    }

    public void inicializarObservable(ObservableHorarios observableHorarios){
        this.observableHorarios = observableHorarios;

    }
}
