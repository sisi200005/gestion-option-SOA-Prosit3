package restRessource;

import entities.Option;
import metiers.OptionBusiness;

import javax.validation.constraints.Null;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Path("/option")
public class RestOption {
    public static OptionBusiness opB =new OptionBusiness();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOption(@QueryParam("domaine") String domain){
        List<Option> l;
        if (domain!= null)
             l=opB.getOptionsByDomaine(domain);
        else
            l=opB.getListeOptions();


        if (l.isEmpty())
            return Response.status(204).entity("No data found").build();
        else

            return Response.status(200).entity(l).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option option){

        if (opB.addOption(option))
            return Response.status(201).build();
        else
            return Response.status(404).build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteOption(@PathParam("id")int id){
        if (opB.deleteOption(id))
            return Response.status(200).build();
        else
            return Response.status(404).build();
    }
    @PUT
    @Consumes()
    @Path("/{id}")
    public Response modifOption(@PathParam("id")int id,Option option){

            Option op = opB.getOptionByCode(id);
        if(op != null ) {
            op.setCapacite(option.getCapacite());
            op.setCodeOption(option.getCodeOption());
            op.setCredits(option.getCredits());
            op.setDomaine(option.getDomaine());
            op.setLibelle(option.getLibelle());
            op.setResponsable(option.getResponsable());
            op.setSemestre(option.getSemestre());
            return Response.status(200).build();

        }
        return Response.status(404).build();

    }
    @GET
    @Path("/{id}")
    public Response getOptionbyid(@PathParam("id")int id){
        if (opB.getOptionByCode(id) != null)
            return Response.status(200).entity(opB.getOptionByCode(id)).build();
        else
            return Response.status(404).build();
    }


}
