# gzipfilter

J2EE GZIP Servlet Filter

## Usage
set up filter and filter-mapping in your __web.xml__

> Filter both request and response
```xml
<filter>
	<filter-name>gzip</filter-name>
	<filter-class>tk.blizz.gzipfilter.GZIPFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>gzip</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
```

> Filter all request
```xml
...
<filter>
	<filter-name>gzipRequest</filter-name>
	<filter-class>tk.blizz.gzipfilter.GZIPRequestFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>gzipRequest</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
...
```

> Filter response with jsps
```xml
...
<filter>
	<filter-name>gzipResponse</filter-name>
	<filter-class>tk.blizz.gzipfilter.GZIPResponseFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>gzipResponse</filter-name>
	<url-pattern>*.jsp</url-pattern>
</filter-mapping>
...
```

