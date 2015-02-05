package org.eclipse.jetty.embedded;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Hello world!
 *
 */
public class EmbeddedJerseyServer
    implements Runnable
{
    private int port;
    private Server jettyServer;

    public EmbeddedJerseyServer(int port)
    {
        this.port = port;
    }

    public void stop() throws Exception
    {
        this.jettyServer.stop();
    }

    @Override
    public void run()
    {
        this.jettyServer = new Server(this.port);
        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        ServletHolder servletHolder = new ServletHolder(new ServletContainer(new JerseyApplication()));
        servletHandler.addServlet(servletHolder, "/api/v1/*");
        jettyServer.setHandler(servletHandler);

        jettyServer.setDumpAfterStart(true);
        jettyServer.setDumpBeforeStop(true);

        try
        {
            jettyServer.start();
        }
        catch( InterruptedException e )
        {
            System.out.println("Jetty server interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        catch( Exception e )
        {
            System.err.println("Error running or stopping Jetty server");
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) throws InterruptedException
    {
        EmbeddedJerseyServer startApp = new EmbeddedJerseyServer(8080);
        Thread serverThread = new Thread(startApp);
        serverThread.run();
        serverThread.join();
    }
}
