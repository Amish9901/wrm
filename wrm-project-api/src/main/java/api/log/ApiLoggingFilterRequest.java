package api.log;

import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Provider
public class ApiLoggingFilterRequest implements ContainerRequestFilter {

    private static String HOST;

    static {
        try {
            HOST = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            HOST = System.getenv("HOSTNAME");
        }
    }

    @Context
    private HttpServletRequest request;
    @Context
    private HttpHeaders headers;
    @Context
    private ResourceInfo resourceInfo;


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException{
        ApiLogMessage msg = new ApiLogMessage();
        msg.setHost(HOST);
        msg.setMethod(request.getMethod());
        msg.setPath(request.getRequestURI().substring(request.getContextPath().length()));
        msg.setQuery(request.getQueryString());
        msg.setRequestId(UUID.randomUUID().toString());
        msg.setRequestTime(System.currentTimeMillis());
        msg.setClient(request.getHeader("X-Forwarded-For"));
        request.setAttribute(Constants.ATTRIBUTE_ID_MESSAGE, msg);
        MDC.put(Constants.REQUEST_ID, msg.getRequestId());
        MDC.put(Constants.REQUEST_TIME, String.valueOf(msg.getRequestTime()));

//        msg.requestId = UUID.randomUUID().toString().replace("-", "");
//        request.setAttribute(Constants.ATTRIBUTE_ID_MESSAGE, msg);
//       // request.setAttribute(Constants.CAR_ID, msg);
//        msg.host = HOST;
//        msg.apiVersion = System.getProperty("SERVER-VERSION");
//        msg.path = request.getRequestURI().substring(request.getContextPath().length());
//        msg.pathTemplate = request.getMethod();
//        msg.pathTemplate += " " + getPathTemplate();
//        MDC.put(Constants.REQUEST_ID, msg.requestId);
//        MDC.put(Constants.REQUEST_TIME, String.valueOf(now.toEpochMilli()));

    }
//        private String getPathTemplate() {
//            Class<?> resourceClass = resourceInfo.getResourceClass();
//            Method resourceMethod = resourceInfo.getResourceMethod();
//            UriBuilder partialUriBuilder = UriBuilder.fromResource(resourceClass)
//                    .path(resourceMethod);
//            return partialUriBuilder.toTemplate();
//    }
}
