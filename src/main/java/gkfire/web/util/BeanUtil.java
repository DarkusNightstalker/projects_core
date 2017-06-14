package gkfire.web.util;

import java.io.InputStream;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

public class BeanUtil {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.
                getCurrentInstance().
                getExternalContext().getRequest();
    }

    public static void exceuteJS(String js) {
        RequestContext.getCurrentInstance().execute(js);
    }

    public static String getParameter(String parameterName) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return params.get(parameterName);
    }

    public static boolean isAjaxRequest() {
        return FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest();
    }

    public static RequestContext getRequestContext() {
        return RequestContext.getCurrentInstance();
    }

    public static ServletContext getContext() {
        return (ServletContext) getExternalContext().getContext();
    }

    public static InputStream getResourceAsStream(String path) {
        return FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(path);
    }

    public static FacesContext getCurrentInstance() {
        return FacesContext.getCurrentInstance();
    }

    public static String getRealPath(String path) {
        return FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);
    }

    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static Object getSessionAttribute(String key) {
        return getRequest().getSession().getAttribute(key);
    }

    public static void setSessionAttribute(String key, Object value) {
        getRequest().getSession().setAttribute(key, value);
    }

    public static void removeSessionAttribute(String key) {
        getRequest().getSession().removeAttribute(key);
    }

    public static void invalidateSession() {
        getRequest().getSession().invalidate();
    }

    public static void logout() throws ServletException {
        getRequest().logout();
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }
}
