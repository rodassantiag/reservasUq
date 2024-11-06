package co.edu.uniquindio.reservasUq.controlador;

import co.edu.uniquindio.reservasUq.modelo.enums.TipoUsuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroControlador implements Initializable {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private TextField txtNombre, txtCedula, txtCorreo;
    @FXML
    private ComboBox<TipoUsuario> tipoUsuarioComboBox;
    @FXML
    private PasswordField txtContrasena;

    public void registrarUsuario(){
        try {
            String nombre = txtNombre.getText();
            String cedula= txtCedula.getText();
            String correo = txtCorreo.getText();
            TipoUsuario tipoUsuario = tipoUsuarioComboBox.getValue();
            String contrasena = txtContrasena.getText();

            controladorPrincipal.registrarPersona(nombre, cedula, correo, contrasena, tipoUsuario);
            controladorPrincipal.crearAlerta("El usuario se ha creado exitosamente", Alert.AlertType.INFORMATION);
            irInicioSesion();


        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoUsuarioComboBox.setItems(FXCollections.observableArrayList(TipoUsuario.values()));
    }

    public void irInicioSesion() throws Exception {
        controladorPrincipal.navegarVentana("/login.fxml", "login");
        controladorPrincipal.cerrarVentana(txtContrasena);
    }

    public void irInicio() throws Exception {
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        cerrarVentana();
    }

    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(txtContrasena);
    }

}
