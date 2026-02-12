package uce.citas.interfaces;

import java.util.List;

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
import uce.citas.application.DoctorService;
import uce.citas.application.representation.DoctorRepresentation;

@Path("/doctores")
public class DoctorResource {

    @Inject
    private DoctorService dService;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "admin" })
    public Response crear(DoctorRepresentation doctorRepresentation) {
        try {
            this.dService.crear(doctorRepresentation);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al crear doctor: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "admin" })
    public Response listar() {
        try {
            List<DoctorRepresentation> doctores = this.dService.listar();
            return Response.ok(doctores).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar doctores: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "admin" })
    public Response actualizar(@PathParam("id") Integer id, DoctorRepresentation doctorRepresentation) {
        try {
            this.dService.actualizar(id, doctorRepresentation);
            return Response.status(204).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error al actualizar doctor: " + e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "admin" })
    public Response actualizarParcial(@PathParam("id") Integer id, DoctorRepresentation doctorRepresentation) {
        try {
            this.dService.actualizarParcial(id, doctorRepresentation);
            return Response.status(204).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error al actualizar parcialmente doctor: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "admin" })
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            this.dService.eliminar(id);
            return Response.status(204).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error al eliminar doctor: " + e.getMessage()).build();
        }
    }

    

}
