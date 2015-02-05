package org.eclipse.jetty.embedded.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by stuartsmith on 2/4/2015.
 */
@Path("status")
public class Status
{
    @GET
    public String getStatus()
    {
        return "{ \"status\": \"OK\" }";
    }
}
