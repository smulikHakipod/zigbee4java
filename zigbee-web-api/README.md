ZigBee Web API
===================

This Web API let the user control the ZigBee network using HTTP. In my vision, this API listens locally behind a
HTTP reverse proxy e.g Apache server. The Apache server redirects only the required traffic using .htaccess and
.htpasswd. The htpasswd ideally using a SSL private key (if possible) and of course the entire Apache server
is using only SSL.