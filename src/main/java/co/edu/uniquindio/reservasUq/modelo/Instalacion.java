package co.edu.uniquindio.reservasUq.modelo;

import co.edu.uniquindio.reservasUq.modelo.enums.TipoInstalacion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.ref.PhantomReference;
import java.util.List;

@Getter
@Setter
@Builder
public class Instalacion {
    private TipoInstalacion tipoInstalacion;
    private String id;
    private int aforo;
    private float costo;
    private List<Horario> horarios;

}
