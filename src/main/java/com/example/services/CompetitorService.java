/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Cargas;
import com.example.models.Usuario;
import com.example.models.VehiculoDTO;
import com.example.models.Vehiculos;
import com.example.models.cargaDTO;
import com.example.models.usuarioDTO;
import com.example.models.usuarioLOGIN;
import com.example.SingletonLogin;
import com.example.models.Conductor;
import com.example.models.ConductorDTO;
import com.example.models.Solicitud;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;

/**
 *
 * @author Mauricio
 */
@Path("/backend")
@Produces(MediaType.APPLICATION_JSON)
public class CompetitorService {

    String IdDC;
    @PersistenceContext(unitName = "CompetitorsPU")
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        try {
            entityManager
                    = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//********************** U S U A R I O S **************************************************
    @POST
    @Path("/registerUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(usuarioDTO usr) {
        JSONObject rta = new JSONObject();
        Usuario us = new Usuario();
        us.setName(usr.getName());
        us.setCorreo(usr.getCorreo());
        us.setTelefono(usr.getTelefono());
        us.setDireccion(usr.getDireccion());
        us.setDireccionTrabajo(usr.getDireccionTrabajo());
        us.setPass(usr.getPass());
        us.setTipoUsuario(usr.getTipoUsuario());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(us);
            entityManager.getTransaction().commit();
            entityManager.refresh(us);
            rta.put("usuario_id", us.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            us = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }

    @PUT
    @Path("/updateUser/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(usuarioDTO usr) {

        return Response.status(200).entity("Usuario con ID eliminado exitosamente.").build();
    }

    @DELETE
    @Path("/deleteUser/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response DeshabilitarUsuario(@PathParam("id") Long userId) {
        Usuario user = entityManager.find(Usuario.class, userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuario con ID " + userId + " no encontrado.").build();
        }

        // Eliminar el usuario
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
        return Response.status(200).entity("Usuario con ID " + userId + " eliminado exitosamente.").build();
    }

    @GET
    @Path("/getUser/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("id") Long userId) {
        Query q = entityManager.createQuery("select u from Usuario u WHERE u.id =:id");
        q.setParameter("id", userId);
        List<Usuario> us = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(us).build();

    }

    @GET
    @Path("/getAllUsr")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from Usuario u order by u.name ASC");
        List<Usuario> us = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(us).build();
    }

//********************** V E H I C U L O **************************************************
    @POST
    @Path("/addVe")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVehiculo(VehiculoDTO carrito) {
        JSONObject rta = new JSONObject();
        Vehiculos car = new Vehiculos();

        car.setPlaca(carrito.getPlaca());
        car.setMarca(carrito.getMarca());
        car.setModelo(carrito.getMarca());
        car.setTipoCarroceria(carrito.getTipoCarroceria());
        car.setCapacidadCar(carrito.getCapacidadCar());

        long id = carrito.getIdDueno();
        Query q = entityManager.createQuery("select u from Usuario u WHERE u.id = :id");
        q.setParameter("id", id);
        Usuario usr = (Usuario) q.getSingleResult();
        car.setDueno(usr);

        car.setConductor(null);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(car);
            entityManager.getTransaction().commit();
            entityManager.refresh(car);
            rta.put("vehiculo_id", car.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            car = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();

    }

    @PUT
    @Path("/updateVe/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarVehiculo(VehiculoDTO Vehiculo) {

        return Response.status(200).entity("Usuario con ID eliminado exitosamente.").build();
    }

    @DELETE
    @Path("/deleteVe/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response DeshabilitarVehiculo(@PathParam("id") Long userId) {
        Vehiculos car = entityManager.find(Vehiculos.class, userId);
        if (car == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuario con ID " + userId + " no encontrado.").build();
        }

        // Eliminar el usuario
        entityManager.getTransaction().begin();
        entityManager.remove(car);
        entityManager.getTransaction().commit();
        return Response.status(200).entity("Usuario con ID " + userId + " eliminado exitosamente.").build();
    }

    @GET
    @Path("/getAllVe")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVe() {
        Query q = entityManager.createQuery("select u from Vehiculos u order by u.placa ASC");
        List<Vehiculos> competitors = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(competitors).build();

    }

//********************** C A R G A S **************************************************
    @POST
    @Path("/registerCa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrdenCarga(cargaDTO cargas) {
        if (SingletonLogin.getInstance().getUid() != null && SingletonLogin.getInstance().getTipoUs() == 0) {
            JSONObject rta = new JSONObject();
            Cargas ca = new Cargas();
            ca.setFecha(cargas.getFecha());
            ca.setUsuarioCarga(SingletonLogin.getInstance().getUid());
            ca.setOrigenCiudad(cargas.getOrigenCiudad());
            ca.setDestinoCiudad(cargas.getDestinoCiudad());
            ca.setAlto(cargas.getAlto());
            ca.setLargo(cargas.getLargo());
            ca.setAncho(cargas.getAncho());
            ca.setPeso(cargas.getPeso());
            ca.setValorSeguro(cargas.getValorSeguro());
            ca.setActiva(true);
            ca.setAceptada(false);

            try {
                entityManager.getTransaction().begin();
                entityManager.persist(ca);
                entityManager.getTransaction().commit();
                entityManager.refresh(ca);
                rta.put("carga_numero", ca.getId());
            } catch (Throwable t) {
                t.printStackTrace();
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                ca = null;
            } finally {
                entityManager.clear();
                entityManager.close();
            }
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();

        } else {
            return Response.status(401)
                    .header("NotAuthorizedException", "*").entity("NotAuthorizedException").build();

        }
    }

    @PUT
    @Path("/updateCa/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCarga(cargaDTO cargas) {

        return Response.status(200).entity("Usuario con ID eliminado exitosamente.").build();
    }

    @DELETE
    @Path("/deleteCa/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response EliminarCarga(@PathParam("id") Long userId) {
        Cargas carga = entityManager.find(Cargas.class, userId);
        if (carga == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuario con ID " + userId + " no encontrado.").build();
        }

        entityManager.getTransaction().begin();
        entityManager.remove(carga);
        entityManager.getTransaction().commit();
        return Response.status(200).entity("Usuario con ID " + userId + " eliminado exitosamente.").build();

    }

    @GET
    @Path("/getCa/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarga(@PathParam("id") Long userId) {
        Query q = entityManager.createQuery("select u from Cargas u WHERE u.id =:id");
        q.setParameter("id", userId);
        List<Cargas> carga = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(carga).build();

    }
//********************** I N I C I A R  S E S I Ó N **************************************************

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(usuarioLOGIN login) {
        try {
            String direccion = login.getDireccion();
            String pass = login.getPass();

            Query q = entityManager.createQuery("SELECT c FROM Usuario c WHERE c.correo = :direccion AND c.pass = :pass");
            q.setParameter("direccion", direccion);
            q.setParameter("pass", pass);

            List<Usuario> usuarios = q.getResultList();

            if (!usuarios.isEmpty()) {
                for (Usuario u : usuarios) {
                    SingletonLogin.getInstance().setUid(String.valueOf(u.getId()));
                    SingletonLogin.getInstance().setTipoUs(u.getTipoUsuario());
                }
                return Response.status(200)
                        .header("Access-Control-Allow-Origin", "*")
                        .entity(usuarios.get(0))
                        .build();
            } else if (usuarios.isEmpty()) {
                q = entityManager.createQuery("SELECT c FROM Conductor c WHERE c.correo = :direccion AND c.pass = :pass");
                q.setParameter("direccion", direccion);
                q.setParameter("pass", pass);

                List<Usuario> usuarioC = q.getResultList();
                if (!usuarioC.isEmpty()) {
                    for (Usuario u : usuarios) {
                        SingletonLogin.getInstance().setUid(String.valueOf(u.getId()));
                        SingletonLogin.getInstance().setTipoUs(2);
                    }
                    return Response.status(200)
                            .header("Access-Control-Allow-Origin", "*")
                            .entity(usuarios.get(0))
                            .build();
                } else {
                    return Response.status(401)
                            .header("NotAuthorizedException", "*").entity("NotAuthorizedException").build();
                }
            }else{
                return Response.status(401)
                            .header("NotAuthorizedException", "*").entity("NotAuthorizedException").build();
            }
                    
        } catch (Exception e) {
            return Response.status(500)
                    .entity("Error al procesar la solicitud: " + e.getMessage())
                    .build();
        }

    }
//********************** C E R R A R  S E S I Ó N **************************************************

    @POST
    @Path("/exit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exit(usuarioLOGIN login) {
        try {
            if (!IdDC.isEmpty()) {
                IdDC = "";
                SingletonLogin.getInstance().setUid(null);
                SingletonLogin.getInstance().setTipoUs(null);
                return Response.status(200)
                        .header("Access-Control-Allow-Origin", "*")
                        .entity("se ha cerrado sesion con exito")
                        .build();
            } else {
                return Response.status(401).header("NotAuthorizedException", "*").entity("NotAuthorizedException").build();
            }
        } catch (Exception e) {
            return Response.status(500)
                    .entity("Error al procesar la solicitud: " + e.getMessage())
                    .build();
        }
    }

//********************** C O N S U L T A  Y  A P L I C A C I O N **************************************************
    @GET
    @Path("/getAllCarga")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCarga() {
        Query q = entityManager.createQuery("select u from Cargas u WHERE u.activa = true");
        List<Cargas> carga = q.getResultList();

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(carga).build();

    }

    @POST
    @Path("/AplicarCarga/{id}/{idv}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response aplicarCarga(@PathParam("id") Long CargaId,@PathParam("idv") Long VId) {
        if (SingletonLogin.getInstance().getTipoUs() == 1) {
            Query q = entityManager.createQuery("select u from Cargas u WHERE u.id =:id");
            q.setParameter("id", CargaId);
            Cargas CargaA = (Cargas) q.getSingleResult();

            long id = Long.parseLong(SingletonLogin.getInstance().getUid());
            Query qu = entityManager.createQuery("SELECT c FROM Usuario c WHERE c.id = :id");
            qu.setParameter("id", id);
            Usuario usr = (Usuario) qu.getSingleResult();

            
            Query qv= entityManager.createQuery("SELECT v FROM Vehiculos v WHERE v.id = :id");
            qu.setParameter("id", VId);
            Vehiculos ve = (Vehiculos) qu.getSingleResult();
            
            JSONObject rta = new JSONObject();
            Solicitud solicitud = new Solicitud();
            solicitud.setUsuario(usr);
            solicitud.setCarga(CargaA);
            solicitud.setVe(ve);
            solicitud.setEstado("P");

            try {
                entityManager.getTransaction().begin();
                entityManager.persist(solicitud);
                entityManager.getTransaction().commit();
                entityManager.refresh(solicitud);
                rta.put("solicitud creada", solicitud.getId());
            } catch (Throwable t) {
                t.printStackTrace();
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                solicitud = null;
            } finally {
                entityManager.clear();
                entityManager.close();
            }
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(CargaA).build();
        } else {
            return Response.status(401).header("NotAuthorizedException", "*").entity("NotAuthorizedException").build();
        }
    }

//********************** V E R  S O L I C I T U D E S  Y  A C E P T A R L A S **************************************************
    @GET
    @Path("/verSolicitudes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verSolicitudes() {
        if (SingletonLogin.getInstance().getUid() != null && SingletonLogin.getInstance().getTipoUs() == 0) {
            String usrId = SingletonLogin.getInstance().getUid();
            Query q = entityManager.createQuery("SELECT s FROM Solicitud s WHERE s.carga.idUsCarga = :usrId");
            q.setParameter("usrId", usrId);

            List<Solicitud> solicitud = q.getResultList();

            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(solicitud).build();

        } else {
            return Response.status(401).header("NotAuthorizedException", "*").entity("NotAuthorizedException").build();
        }

    }

    @PUT
    @Path("/aceptarCarga/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response aceptarSolicitud(@PathParam("id") Long solId) {
        //SingletonLogin.getInstance().getTipoUs() == 0 && SingletonLogin.getInstance().getUid() != null
        if (SingletonLogin.getInstance().getTipoUs() == 0 && SingletonLogin.getInstance().getUid() != null) {
            Query q = entityManager.createQuery("SELECT s FROM Solicitud s WHERE s.id = :id");
            q.setParameter("id", solId);
            Solicitud solicitud = (Solicitud) q.getSingleResult();
            if (solicitud == null) {
                return Response.status(404).header("Error", "Solicitud no encontrada").build();
            }

            JSONObject rta = new JSONObject();
            solicitud.setEstado("A");

            Cargas carga = solicitud.getCarga();
            Query query = entityManager.createQuery("SELECT s FROM Solicitud s WHERE s.carga = :carga");
            query.setParameter("carga", carga);
            List<Solicitud> solicitudes = query.getResultList();

            try {
                entityManager.getTransaction().begin();
                entityManager.merge(solicitud);
                for (Solicitud s : solicitudes) {
                    if (!s.equals(solicitud)) {
                        s.setEstado("R");
                        entityManager.merge(s);
                    }
                }
                entityManager.getTransaction().commit();
                entityManager.refresh(solicitud);
                rta.put("solicitud creada", solicitud.getId());
            } catch (Throwable t) {
                t.printStackTrace();
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                solicitud = null;
            } finally {
                entityManager.clear();
                entityManager.close();
            }
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(solicitud).build();
        } else {
            return Response.status(401).header("NotAuthorizedException", "*").entity("NotAuthorizedException").build();
        }
    }

    @PUT
    @Path("/cancela/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prueba(@PathParam("id") Long cargaId) {
        Query q = entityManager.createQuery("SELECT c FROM Carga c WHERE c.id = :id");
        q.setParameter("id", cargaId);
        List<Cargas> cargas = q.getResultList();
        try {
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            for (Cargas c : cargas) {
                c.setActiva(false);
                entityManager.merge(c);
            }

        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(cargas).build();
    }

//private void enviarNotificacion(Usuario usr, String mensaje) {
//    // Configuración del servidor de correo electrónico y credenciales de autenticación
//    final String correoRemitente = "convergentesgrupocuatro@gmail.com";
//    final String contraseñaRemitente = "JeissonA2001.";
//    
//    // Propiedades para la sesión de correo
//    Properties props = new Properties();
//    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.smtp.host", "smtp-relay.gmail.com");
//    props.put("mail.smtp.port", "587");
//    
//    Authenticator authenticator = new Authenticator() {
//        protected PasswordAuthentication getPasswordAuthentication() {
//            return new PasswordAuthentication(correoRemitente, contraseñaRemitente);
//        }
//    };
//    Session session = Session.getInstance(props, authenticator);
//    
//    try {
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(correoRemitente));
//        
//        String correoDestinatario = usr.getCorreo();
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestinatario));
//        
//        message.setSubject("Notificación de solicitud aceptada");
//        message.setText(mensaje);
//        
//     
//        Transport.send(message);
//        System.out.println("Correo electrónico enviado correctamente a " + correoDestinatario);
//    } catch (MessagingException e) {
//        e.printStackTrace();
//        System.err.println("Error al enviar correo electrónico");
//    }
//}
//********************** V E R  S O L I C I T U D E S  A C E P **************************************************
    @GET
    @Path("/verSolicitudesA")
    @Produces(MediaType.APPLICATION_JSON)
    public Response solDueV() {
        if (SingletonLogin.getInstance().getUid() != null && SingletonLogin.getInstance().getTipoUs() == 1) {
            long usrId = Long.valueOf(SingletonLogin.getInstance().getUid());
            Query q = entityManager.createQuery("SELECT s FROM Solicitud s WHERE s.usuario.id = :usrId AND s.estado = :estado");
            q.setParameter("usrId", usrId);
            q.setParameter("estado", "A");

            List<Solicitud> solicitud = q.getResultList();

            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(solicitud).build();

        } else {
            return Response.status(401).header("NotAuthorizedException", "*").entity("NotAuthorizedException").build();
        }

    }

    @GET
    @Path("/rutaSolicitudesA/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearRuta(@PathParam("id") Long solId) {
        if (SingletonLogin.getInstance().getUid() != null && SingletonLogin.getInstance().getTipoUs() == 1) {
            Query q = entityManager.createQuery("SELECT s FROM Solicitud s WHERE s.id = :solId");
            q.setParameter("solId", solId);
            Solicitud solicitud = (Solicitud) q.getSingleResult();
            String apiKey = "AIzaSyDsseri8lGQRqrj1-Jx3i3Q3b1lTxUy_I0";
            Cargas carga = solicitud.getCarga();
            String origen = carga.getOrigenCiudad();
            String destino = carga.getDestinoCiudad();

            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(solicitud).build();

        } else {
            return Response.status(401).header("NotAuthorizedException", "*").entity("NotAuthorizedException").build();
        }

    }

    @POST
    @Path("/registerConductor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createConductor(ConductorDTO cond) {
        JSONObject rta = new JSONObject();
        Conductor Cond = new Conductor();
        Cond.setCorreo(cond.getCorreo());
        Cond.setNombre(cond.getNombre());
        Cond.setPass(cond.getPass());
        Cond.setTelefono(cond.getTelefono());
        long idV = cond.getIdvehiculo();
        Query q = entityManager.createQuery("SELECT v FROM Vehiculos v WHERE v.id = :id");
        q.setParameter("id", idV);
        Vehiculos v = (Vehiculos) q.getSingleResult();

        Cond.setVehiculo(v);

        v.setConductor(Cond);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(Cond);
            entityManager.merge(v);
            entityManager.getTransaction().commit();
            entityManager.refresh(Cond);
            rta.put("Conductor_id", Cond.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            Cond = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }

    @GET
    @Path("/ViajesARealizar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response VerViajes(ConductorDTO cond) {
        
        JSONObject rta = new JSONObject();
        Query q = entityManager.createQuery("SELECT v FROM Vehiculos v WHERE v.conductor = :id");
        Vehiculos v = (Vehiculos) q.getSingleResult();
        
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }
}
