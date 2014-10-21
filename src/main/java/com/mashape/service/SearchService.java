package com.mashape.service;

import com.google.inject.Inject;
import com.mashape.common.Constants;
import com.mashape.domain.Task;
import com.mashape.interfaces.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by yxzhao on 10/20/14.
 */

@Path("/query")
public class SearchService {
    private static final Logger LOG
            = LoggerFactory.getLogger(SearchService.class);

    private final Searcher searcher;

    @Inject
    public SearchService(final Searcher searcher) {
        this.searcher = searcher;
    }

    @GET
    @Path("/{keyword}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response searchByString(@PathParam("keyword") final String keyword) throws Exception {
        LOG.info("Searching index for keyword : " + keyword);
        Iterable<Task> tasks = searcher.search(keyword);
        GenericEntity<Iterable<Task>> entity = new GenericEntity<Iterable<Task>>(tasks) {
        };

        return Response.status(Response.Status.OK).entity(entity).build();
    }

    @GET
    @Path("/{field}/{keyword}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public final Response searchByField(
            @PathParam("field") final String field,
            @PathParam("keyword") final String keyword) throws Exception {

        Iterable<Task> tasks = searcher.searchByField(Constants.Fileds.forValue(field), keyword);
        GenericEntity<Iterable<Task>> entity = new GenericEntity<Iterable<Task>>(tasks) {
        };

        return Response.status(Response.Status.OK).entity(entity).build();
    }
}
