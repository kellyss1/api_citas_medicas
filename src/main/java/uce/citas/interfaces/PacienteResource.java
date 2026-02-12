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
import jakarta.ws.rs.core.UriInfo;
import uce.citas.application.PacienteService;
import uce.citas.application.representation.LinkDto;
import uce.citas.application.representation.PacienteRepresentation;

@Path("/pacientes")
public class PacienteResource {
    
    @Inject
    private PacienteService pService;

    @Inject
    private UriInfo uriInfo;

    @POST
    @Path("")
    @RolesAllowed({ "admin" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(PacienteRepresentation pacienteRepresentation) {
        try {
            this.pService.crear(pacienteRepresentation);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al crear paciente: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("")
    @RolesAllowed({ "admin" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        try {
            List<PacienteRepresentation> pacientes = this.pService.listar();
            pacientes.forEach(this::construirLinks);
            return Response.ok(pacientes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar pacientes: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "admin" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPorId(@PathParam("id") Integer id) {
        try {
            PacienteRepresentation paciente = this.pService.listarPorId(id);
            if (paciente != null) {
                return Response.ok(this.construirLinks(paciente)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Paciente no encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener paciente: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/citas")
    @RolesAllowed({ "admin" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarCitas(@PathParam("id") Integer id) {
        try {
            return Response.ok(this.pService.listarCitasPorId(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar citas del paciente: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "admin" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") Integer id, PacienteRepresentation pacienteRepresentation) {
        try {
            this.pService.actualizar(id, pacienteRepresentation);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar paciente: " + e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed({ "admin" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarParcial(@PathParam("id") Integer id, PacienteRepresentation pacienteRepresentation) {
        try {
            this.pService.actualizarParcial(id, pacienteRepresentation);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar parcialmente paciente: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "admin" })
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            this.pService.eliminar(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al eliminar paciente, debe eliminar las citas primero: " + e.getMessage()).build();
        }
    }


    private PacienteRepresentation construirLinks(PacienteRepresentation pacienteRepresentation) {
        // Enlaces HATEOAS para el paciente
        String baseUri = this.uriInfo.getBaseUriBuilder()
            .path(PacienteResource.class)
            .path(String.valueOf(pacienteRepresentation.getId()))
            .build()
            .toString();
        String citasUri = this.uriInfo.getBaseUriBuilder()
            .path(PacienteResource.class)
            .path(String.valueOf(pacienteRepresentation.getId()))
            .path("citas")
            .build()
            .toString();
        pacienteRepresentation.setLink(List.of(
            new LinkDto(baseUri, "self"),
            new LinkDto(citasUri, "citas")));
        return pacienteRepresentation;
    }

}
