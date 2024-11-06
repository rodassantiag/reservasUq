package co.edu.uniquindio.reservasUq.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InicioControlador {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private Button boton;

    public void irLogin() throws Exception {
        controladorPrincipal.navegarVentana("/login.fxml", "login");
        controladorPrincipal.cerrarVentana(boton);
    }

    public void irRegistro() throws Exception {
        controladorPrincipal.navegarVentana("/registro.fxml", "Registro");
        controladorPrincipal.cerrarVentana(boton);
    }
}
