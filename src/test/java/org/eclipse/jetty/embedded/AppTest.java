package org.eclipse.jetty.embedded;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    public static final int timeout = 10000;
    public static final int serverPort = 8080;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigorous Test :-)
     */
    public void testApp() throws Exception
    {
        EmbeddedJerseyServer startApp = new EmbeddedJerseyServer(serverPort);
        Thread serverThread = new Thread(startApp);
        serverThread.start();

        System.out.println("Server started, starting test client");
        TestClient testClient = new TestClient(serverPort);
        Thread testThread = new Thread(testClient);
        testThread.start();

        testThread.join();
        serverThread.join(timeout);
        startApp.stop();
        assertTrue( testClient.testPassed() );
    }
}
