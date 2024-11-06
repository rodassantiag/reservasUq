package co.edu.uniquindio.reservasUq.modelo;

import co.edu.uniquindio.reservasUq.modelo.enums.DiaSemana;
import co.edu.uniquindio.reservasUq.modelo.enums.Rol;
import co.edu.uniquindio.reservasUq.modelo.enums.TipoUsuario;
import co.edu.uniquindio.reservasUq.servicio.ServiciosReservas;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class ReservasUq implements ServiciosReservas {

    private ArrayList<Persona> personas;
    private ArrayList<Persona> admin;
    private ArrayList<Instalacion> instalaciones;
    private ArrayList<Reserva> reservas;
    List<Horario> horarios;

    public ReservasUq(){
        this.personas = new ArrayList<>();
        this.personas.add(Persona.builder()
                .nombre("Pepito")
                .cedula("123")
                .rol(Rol.USUARIO)
                .tipoUsuario(TipoUsuario.EXTERNO)
                .correo("pepito@gmail.com")
                .contrasena("123456")
                .build());
        this.admin = new ArrayList<>();
        this.admin.add(Persona.builder()
                .rol(Rol.ADMIN)
                .correo("pepito123")
                .contrasena("123")
                .build());

        this.instalaciones = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.horarios = new ArrayList<>();


        try {

            this.instalaciones.add(Instalacion.builder()
                    .nombre("Piscina")
                    .aforo(100)
                    .id("INS1")
                    .horarios(
                            List.of(Horario.builder()
                                    .dia(DiaSemana.LUNES)
                                    .Inicio(LocalTime.of(12, 0))
                                    .fin(LocalTime.of(18, 0))
                                    .horas(generarHoras(LocalTime.of(12, 0), LocalTime.of(18, 0)))
                                    .build())
                    ).build());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Persona login(String correo, String contrasena) throws Exception {

        for (Persona externo : personas) {
            if (externo.getCorreo().equals(correo) && externo.getContrasena().equals(contrasena)) {
                return externo;
            }
        }

        for (Persona administrador : admin) {
            if (administrador.getCorreo().equals(correo) && administrador.getContrasena().equals(contrasena)) {
                return administrador;
            }
        }

        throw new Exception("Credenciales incorrectas o usuario no encontrado");
    }

    @Override
    public void registrarPersona(String nombre, String cedula, String correo, String contrasena,
                                 TipoUsuario tipoUsuario) throws Exception{
        if (nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if (cedula.isBlank()){
            throw new Exception("La cédula es obligatoria");
        }

        if (!cedula.matches("\\d+")) {
            throw new Exception("Cédula inválida");
        }

        if (correo.isBlank()){
            throw new Exception("El correo es obligatorio");
        }

        String correoRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!correo.matches(correoRegex)) {
            throw new Exception("El formato del correo es inválido");
        }

        if (contrasena.isBlank()){
            throw new Exception("La contraseña es obligatoria");
        }

        if (contrasena.length() < 6){
            throw new Exception("La contraseña tiene que tener 6 caracteres como mínimo");
        }

        if (tipoUsuario == null){
            throw new Exception("El tipo es obligatorio");
        }

        if ((tipoUsuario == TipoUsuario.ESTUDIANTE && !correo.endsWith("@uqvirtual.edu.co")) ||
                ((tipoUsuario == TipoUsuario.ADMINISTRATIVO || tipoUsuario == TipoUsuario.DOCENTE) && !correo.endsWith("@uniquindio.edu.co")) ||
                (tipoUsuario == TipoUsuario.EXTERNO && (correo.endsWith("@uqvirtual.edu.co") || correo.endsWith("@uniquindio.edu.co")))) {
            throw new Exception("El correo no coincide con el tipo de usuario seleccionado");
        }

        Persona persona = Persona.builder()
                .nombre(nombre)
                .cedula(cedula)
                .correo(correo)
                .contrasena(contrasena)
                .tipoUsuario(tipoUsuario)
                .rol(Rol.USUARIO)
                .build();

        personas.add(persona);
    }

    @Override
    public void crearInstalacion(String nombre, int aforo, float costo, List<Horario> horarios)throws Exception{
        if (nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }
        if (aforo <= 0){
            throw new Exception("El aforo tiene que ser mayor a 0");
        }

        if (costo < 0){
            throw new Exception("El costo no puede ser menor a 0");
        }

        Instalacion instalacion = Instalacion.builder()
                .id(generarid())
                .nombre(nombre)
                .aforo(aforo)
                .costo(costo)
                .horarios(horarios)
                .build();
        instalaciones.add(instalacion);


    }

    public String generarid() {
        StringBuilder codigoRegistro = new StringBuilder();


        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int numero = random.nextInt(8);
            codigoRegistro.append(numero);
        }

        return codigoRegistro.toString();
    }

    @Override
    public List<LocalTime> generarHoras(LocalTime horaInicio, LocalTime horaFin) throws Exception {
        if (!horaInicio.isBefore(horaFin)) {
            throw new Exception("La hora de inicio debe ser menor a la hora de fin");
        }

        List<LocalTime> horas = new ArrayList<>();

        LocalTime horaActual = horaInicio;
        while (horaActual.isBefore(horaFin)) {
            horas.add(horaActual);
            horaActual = horaActual.plusHours(1);
        }

        return horas;
    }

    @Override
    public List<LocalTime> generarHorasParaComboBox() {
        List<LocalTime> horas = new ArrayList<>();

        LocalTime horaActual = LocalTime.of(8, 0);
        LocalTime horaFin = LocalTime.of(20, 0);

        while (!horaActual.isAfter(horaFin)) {
            horas.add(horaActual);
            horaActual = horaActual.plusHours(1);
        }

        return horas;
    }

    @Override
    public Reserva crearReserva(Persona persona, Instalacion instalacion, LocalDateTime fechaReservada) throws Exception{
        System.out.println(fechaReservada);
        if (fechaReservada.isBefore(LocalDateTime.now())){
            throw new Exception("La fecha de la reserva tiene que ser mayor a la fecha actual");
        }

        LocalDateTime fechaLimite = LocalDateTime.now().plusDays(2);
        if (fechaReservada.isBefore(fechaLimite)){
            throw new Exception("La reserva debe realizarse con al menos 2 días de anticipación.");
        }

        if (instalacion == null){
            throw new Exception("Eija una Instalacion");
        }

        if(!estaDisponible(instalacion.getId(), fechaReservada)){
            throw new Exception("Ya instalación ya está ocupada en el día y hora seleccionada.");
        }

        Reserva reserva = Reserva.builder()
                .id(generarid())
                .persona(persona)
                .instalacion(instalacion)
                .fechaReserva(LocalDateTime.now())
                .fechaReservada(fechaReservada)
                .build();

        System.out.println(reserva.getFechaReservada());

        reservas.add(reserva);
        return reserva;
    }



    @Override
    public void cancelarReserva(Reserva reserva) throws Exception {

        if (reserva == null){
            throw new Exception("Elija una reserva");
        }

        reservas.remove(reserva);
    }
    @Override
    public void obtenerreservasPersona(List<Reserva> reservas, LocalDateTime fecha) throws Exception{
        for (Reserva reserva : reservas){
            if (reserva.getFechaReservada().equals(fecha)){
                throw new Exception("Ya ha reservado una instalación en este mismo horario");
            }
        }
    }

    public boolean estaDisponible(String idInstalacion, LocalDateTime fecha){

        for(Reserva reserva: reservas){
            System.out.println(reserva.getInstalacion().getId());
            System.out.println(reserva.getFechaReserva());
            if(reserva.getInstalacion().getId().equals(idInstalacion) && reserva.getFechaReserva().equals(fecha)){
                return false;
            }
        }

        return true;

    }

    @Override
    public List<Reserva> obtenerReservas(String idPersona){

        List<Reserva> reservasPersona = new ArrayList<>();

        for (Reserva reserva: reservas){
            if(reserva.getPersona().getCedula().equals(idPersona)){
                reservasPersona.add(reserva);
            }
        }

        return reservasPersona;
    }

}










