package org.eclipse.jetty.embedded;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * Created by stuartsmith on 2/5/2015.
 */
public class TestClient
    implements Runnable
{
    private int serverPort;
    private boolean testPassed;

    public TestClient( int serverPort )
    {
        this.serverPort = serverPort;
        this.testPassed = false;
    }

    public boolean testPassed()
    {
        return this.testPassed;
    }

    @Override
    public void run()
    {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet getStatus = new HttpGet("/api/v1/status" );
        try
        {
            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(getStatus);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("response status: " + statusCode);
            if (statusCode == HttpStatus.SC_OK)
            {
                this.testPassed = true;
            }
            else
            {
                System.err.println("Endpoint returned error code: " + statusCode);
                System.err.println(response.getStatusLine().getReasonPhrase());
            }
            if (response.getEntity() != null)
            {
                System.out.println("response body: " + EntityUtils.toString(response.getEntity()));
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

    }
}
