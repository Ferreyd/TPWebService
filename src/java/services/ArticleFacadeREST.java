/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Article;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 *
 * @author nicolas
 */
@Stateless
@Path("article")
public class ArticleFacadeREST extends AbstractFacade<Article> {

    @PersistenceContext(unitName = "TPWebServicesPU")
    private EntityManager em;

    @Context
    UriInfo uriInfo;

    public ArticleFacadeREST() {
        super(Article.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Article entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Article entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Article find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Article> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Article> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public Response createFromFormulaire(@FormParam("titre") String titre,
            @FormParam("contenu") String contenu) {
        Article a = new Article(titre, contenu);
        super.create(a);

        // Create URI for Response    
        UriBuilder b = uriInfo.getBaseUriBuilder();
        URI u = null;
        try {
            u = new URI(b.path(ArticleFacadeREST.class).build() + "/" + a.getId());
            System.out.println("Donnée créée avec comme URI : " + u);
            System.out.println("Dans create titre: " + a.getTitre() + " content: " + a.getContenu());

        } catch (URISyntaxException ex) {
            Logger.getLogger(ArticleFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.created(u).build();
    }
}
