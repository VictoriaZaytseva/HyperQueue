# HyperQueue
<h1>Description</h1>
<p>This is an unfinished HyperQueue. It does keep in-memory queue pf events and by connecting as a producer or consumer you will be able to
to enqueue events or move through the queue.
</p>
<h1>What wasn't implemented</h1>
<ul>
<li>User interface was unfinished, to see the responses please use the console log and the network tab in dev tools</li>
<li>More could have been done about synchronization, if I had the time</li>
<li>Testing! I wish I had a time to test it thoroughly, there are a few cases where the app might fail I am afraid</li>
</ul>
<h1>Installation:</h1>
<p>In conf folder edit application.conf put your url for client there instead of my default one 9090</p>
<p>Do the same thing in client config, put your server url there</p>
<p>For a test run you can use sbt, just sbt "run port_number" where port_number is your portnumber</p>
<p>Go to the client root page and try to push something</p>
