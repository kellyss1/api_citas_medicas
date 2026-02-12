package uce.citas.interfaces;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.citas.application.CitaService;
import uce.citas.application.representation.CitaRepresentation;

@Path("/citas")
public class CitaResource {
    
    @Inject
    private CitaService cService;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public Response crearCita(CitaRepresentation citaRepresentation) {
        try {
            this.cService.crearCita(citaRepresentation);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al crear cita: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public Response listarCitas() {
        return Response.ok(this.cService.listarCitas()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public Response actualizarCita(@PathParam("id") Integer id, CitaRepresentation citaRepresentation) {
        try {
            this.cService.actualizarCita(id, citaRepresentation);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar cita: " + e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public Response actualizarCitaParcial(@PathParam("id") Integer id, CitaRepresentation citaRepresentation) {
        try {
            this.cService.actualizarCitaParcial(id, citaRepresentation);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar cita: " + e.getMessage()).build();
        } 
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public Response eliminarCita(@PathParam("id") Integer id) {
        try {
            this.cService.eliminarCita(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al eliminar cita: " + e.getMessage()).build();
        }
    }
}
