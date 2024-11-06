package co.edu.uniquindio.reservasUq.controlador;

import co.edu.uniquindio.reservasUq.modelo.Persona;
import co.edu.uniquindio.reservasUq.modelo.enums.Rol;
import co.edu.uniquindio.reservasUq.modelo.enums.TipoUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControlador {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtContrasena;

    public void verificarPersona(){
        try {
            String correo = txtCorreo.getText();
            String contrasena = txtContrasena.getText();

            Persona personaValidada = controladorPrincipal.login(correo, contrasena);
            controladorPrincipal.guardarSesion(personaValidada);

            if (personaValidada.getRol().equals(Rol.ADMIN)){
                irPanelAdmin();
            } else {
                irPanelPrincipal();
            }


        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void irPanelAdmin() throws Exception {
        controladorPrincipal.navegarVentana("/panelAdmin.fxml", "Panel Administrador");
        cerrarVentana();
    }

    public void irPanelPrincipal() throws Exception {
        controladorPrincipal.navegarVentana("/panelPrincipal.fxml", "Panel Principal");
        cerrarVentana();
    }

    public void irInicio() throws Exception {
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        cerrarVentana();
    }

    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(txtContrasena);
    }
}
