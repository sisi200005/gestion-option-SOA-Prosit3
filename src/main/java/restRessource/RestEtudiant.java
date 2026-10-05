package restRessource;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiant")
public class RestEtudiant {
    public static EtudiantBusiness etb = new EtudiantBusiness();
    public static OptionBusiness opb=new OptionBusiness();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiant(@QueryParam("codeOption")int codeOption){
        List < Etudiant> l;
        if (opb.getOptionByCode(codeOption)!=null)
            l=etb.getEtudiantsByOption(opb.getOptionByCode(codeOption));

        else
            l=etb.getAllEtudiants();


        if (l.isEmpty())
            return Response.status(204).entity("No data found").build();
        else

            return Response.status(200).entity(l).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Etudiant etudiant){

        if (etb.addEtudiant(etudiant))
            return Response.status(201).build();
        else
            return Response.status(404).build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteOption(@PathParam("id")String id){
        if (etb.deleteEtudiant(id))
            return Response.status(200).build();
        else
            return Response.status(404).build();
    }
    @PUT
    @Consumes()
    @Path("/{id}")
    public Response modifOption(@PathParam("id")String id,Etudiant etudiant){

        Etudiant et = etb.getEtudiantByIdentifiant(id);
        if(et != null ) {
            et.setOption(etudiant.getOption());
            et.setAnneeEtude(etudiant.getAnneeEtude());
            et.setEmail(etudiant.getEmail());
            et.setIdentifiant(etudiant.getIdentifiant());
            et.setNom(etudiant.getNom());
            et.setPrenom(etudiant.getPrenom());
            return Response.status(200).build();

        }
        return Response.status(404).build();

    }
    @GET
    @Path("/{id}")
    public Response getEtudiantbyid(@PathParam("id")String id){
        if (etb.getEtudiantByIdentifiant(id) != null)
            return Response.status(200).entity(etb.getEtudiantByIdentifiant(id)).build();
        else
            return Response.status(404).build();
    }
}
