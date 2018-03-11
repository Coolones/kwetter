package controllers;

import domain.Kweet;
import models.ResponseBody;
import services.KweetService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/kweet")
@Produces(MediaType.APPLICATION_JSON)
public class KweetController {
    @Inject
    private KweetService service;

    @GET
    @Path("/{id}")
    public Response getKweet(@PathParam("id") long id) {
        return Response.ok(service.getKweet(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postKweet(Kweet kweet) {
        if (kweet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Kweet postedKweet = service.postKweet(new Kweet(kweet.getText(), kweet.getUser()));
        if (postedKweet == null) {
            return Response.status(400).build();
        }

        return Response.ok(postedKweet).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editKweet(Kweet kweet) {
        Kweet editedKweet = service.editKweet(kweet);

        if (editedKweet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(editedKweet).build();
    }

    @GET
    @Path("/search/{text}")
    public Response searchKweets(@PathParam("text") String text) {
        return Response.ok(service.searchKweets(text)).build();
    }

    @GET
    @Path("/user/{id}")
    public Response getKweets(@PathParam("id") long id) {
        return Response.ok(service.getKweetsOfUser(id)).build();
    }

    @GET
    @Path("/trend/{trend}")
    public Response getKweetsByTrend(@PathParam("trend") String trend) {
        return Response.ok(service.getKweetsByTrend(trend)).build();
    }

    @GET
    @Path("/mention/{mention}")
    public Response getKweetsByMention(@PathParam("mention") String mention) {
        return Response.ok(service.getKweetsByMention(mention)).build();
    }

    @GET
    @Path("/trends")
    public Response getCurrentTrends() {
        return Response.ok(service.getTrends()).build();
    }

    @GET
    @Path("/timeline/{id}")
    public Response getTimeline(@PathParam("id") long id) {
        return Response.ok(service.getTimeline(id)).build();
    }

    @POST
    @Path("/like/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response likeKweet(Kweet kweet, @PathParam("userId") long userId) {
        boolean result = service.likeKweet(kweet, userId);
        return Response.ok(new ResponseBody(result, null)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteKweet(@PathParam("id") long id) {
        service.removeKweet(id);
        return Response.ok(new ResponseBody(true, null)).build();
    }
}
