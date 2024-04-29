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
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author Mauricio
 */
@Path("/backend")
@Produces(MediaType.APPLICATION_JSON)
public class CompetitorService {

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
    @Path("/cazadores")
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
        JSONObject rta = new JSONObject();
        Cargas ca = new Cargas();

        ca.setFecha(cargas.getFecha());
        ca.setUsuarioCarga(cargas.getUsuarioCarga());
        ca.setOrigenCiudad(cargas.getOrigenCiudad());
        ca.setDestinoCiudad(cargas.getDestinoCiudad());
        ca.setAlto(cargas.getAlto());
        ca.setLargo(cargas.getLargo());
        ca.setAncho(cargas.getAncho());
        ca.setPeso(cargas.getPeso());
        ca.setValorSeguro(cargas.getValorSeguro());

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
}
