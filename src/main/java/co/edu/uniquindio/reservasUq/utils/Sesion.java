package co.edu.uniquindio.reservasUq.utils;

import co.edu.uniquindio.reservasUq.modelo.Persona;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sesion {

    private Persona persona;

    private static Sesion INSTANCIA;

    private Sesion(){}

    public static Sesion getInstancia(){
        if(INSTANCIA == null){
            INSTANCIA = new Sesion();
        }
        return INSTANCIA;
    }

    public void asignarPersona(Persona persona){
        INSTANCIA.setPersona(persona);
    }

    public Persona obtenerPersona(){
        return INSTANCIA.getPersona();
    }
}
