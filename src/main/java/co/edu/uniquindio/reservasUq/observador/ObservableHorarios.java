package co.edu.uniquindio.reservasUq.observador;

import co.edu.uniquindio.reservasUq.modelo.Horario;

import java.util.List;

public interface ObservableHorarios {
    void notificar(List<Horario> horariosNuevos);
}
