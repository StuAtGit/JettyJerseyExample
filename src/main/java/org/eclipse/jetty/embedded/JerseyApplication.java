package org.eclipse.jetty.embedded;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * Created by stuartsmith on 2/5/2015.
 */
public class JerseyApplication
    extends ResourceConfig
{
    public JerseyApplication()
    {
        try
        {
            packages("org.eclipse.jetty.embedded.resources");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        property(ServerProperties.TRACING, "ALL");
        System.out.println("***** Jersey Application started *****");
    }
}
