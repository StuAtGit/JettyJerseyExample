# JettyJerseyExample
Example of how to use Jersey with an embedded Jetty Server.<br/>
Created in response to:

https://bugs.eclipse.org/bugs/show_bug.cgi?id=459178

To see it in action, install git and maven and:

<pre>
git clone [repo]
cd repo
mvn test
</pre>

The test does run an embedded server on port 8080 and try to connect, so if you already have that port taken, it will fail. Requires at least java 7.
