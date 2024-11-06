module co.edu.uniquindio.reservasuq {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens co.edu.uniquindio.reservasUq to javafx.fxml;
    exports co.edu.uniquindio.reservasUq;
    exports co.edu.uniquindio.reservasUq.controlador;
    opens co.edu.uniquindio.reservasUq.controlador to javafx.fxml;
}