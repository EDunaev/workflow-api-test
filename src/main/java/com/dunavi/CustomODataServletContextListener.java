package com.dunavi;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;

import com.sap.cloud.sdk.service.prov.v2.rt.api.dpc.name.provider.DPCNameFacade;
import com.sap.gateway.core.api.delegate.RuntimeDelegateFacade;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CustomODataServletContextListener implements ServletContextListener {
    
    private static final String PARAMETER_NAME = "package";

    private final ServletContextListener delegate;

    private final String parameterValue;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        delegate.contextInitialized(
                new ServletContextEvent(new Context(sce.getServletContext())));
        RuntimeDelegateFacade.registerDelegate(
                CustomRuntimeDelegate.class.getName(),
                new CustomRuntimeDelegate());
        DPCNameFacade.setDpcNameProvider(
                () -> CustomRuntimeDelegate.class.getName());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        this.delegate.contextDestroyed(sce);
    }

    @SuppressWarnings("deprecation")
    private class Context implements ServletContext {
        private final ServletContext delegate;

        public Context(ServletContext delegate) {

            this.delegate = delegate;
        }

        @Override
        public String getInitParameter(String name) {

            if (PARAMETER_NAME.equals(name)) {
                return parameterValue;
            } else {
                return delegate.getInitParameter(name);
            }
        }

        public String getContextPath() {

            return delegate.getContextPath();
        }

        public ServletContext getContext(String uripath) {

            return delegate.getContext(uripath);
        }

        public int getMajorVersion() {

            return delegate.getMajorVersion();
        }

        public int getMinorVersion() {

            return delegate.getMinorVersion();
        }

        public int getEffectiveMajorVersion() {

            return delegate.getEffectiveMajorVersion();
        }

        public int getEffectiveMinorVersion() {

            return delegate.getEffectiveMinorVersion();
        }

        public String getMimeType(String file) {

            return delegate.getMimeType(file);
        }

        public Set<String> getResourcePaths(String path) {

            return delegate.getResourcePaths(path);
        }

        public URL getResource(String path) throws MalformedURLException {

            return delegate.getResource(path);
        }

        public InputStream getResourceAsStream(String path) {

            return delegate.getResourceAsStream(path);
        }

        public RequestDispatcher getRequestDispatcher(String path) {

            return delegate.getRequestDispatcher(path);
        }

        public RequestDispatcher getNamedDispatcher(String name) {

            return delegate.getNamedDispatcher(name);
        }

        public Servlet getServlet(String name) throws ServletException {

            return delegate.getServlet(name);
        }

        public Enumeration<Servlet> getServlets() {

            return delegate.getServlets();
        }

        public Enumeration<String> getServletNames() {

            return delegate.getServletNames();
        }

        public void log(String msg) {

            delegate.log(msg);
        }

        public void log(Exception exception, String msg) {

            delegate.log(exception, msg);
        }

        public void log(String message, Throwable throwable) {

            delegate.log(message, throwable);
        }

        public String getRealPath(String path) {

            return delegate.getRealPath(path);
        }

        public String getServerInfo() {

            return delegate.getServerInfo();
        }

        public Enumeration<String> getInitParameterNames() {

            return delegate.getInitParameterNames();
        }

        public boolean setInitParameter(String name, String value) {

            return delegate.setInitParameter(name, value);
        }

        public Object getAttribute(String name) {

            return delegate.getAttribute(name);
        }

        public Enumeration<String> getAttributeNames() {

            return delegate.getAttributeNames();
        }

        public void setAttribute(String name, Object object) {

            delegate.setAttribute(name, object);
        }

        public void removeAttribute(String name) {

            delegate.removeAttribute(name);
        }

        public String getServletContextName() {

            return delegate.getServletContextName();
        }

        public ServletRegistration.Dynamic addServlet(String servletName, String className) {

            return delegate.addServlet(servletName, className);
        }

        public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {

            return delegate.addServlet(servletName, servlet);
        }

        public ServletRegistration.Dynamic addServlet(String servletName,
                                                      Class<? extends Servlet> servletClass) {

            return delegate.addServlet(servletName, servletClass);
        }

        public <T extends Servlet> T createServlet(Class<T> clazz)
                throws ServletException {

            return delegate.createServlet(clazz);
        }

        public ServletRegistration getServletRegistration(String servletName) {

            return delegate.getServletRegistration(servletName);
        }

        public Map<String, ? extends ServletRegistration> getServletRegistrations() {

            return delegate.getServletRegistrations();
        }

        public javax.servlet.FilterRegistration.Dynamic addFilter(
                String filterName, String className) {

            return delegate.addFilter(filterName, className);
        }

        public javax.servlet.FilterRegistration.Dynamic addFilter(
                String filterName, Filter filter) {

            return delegate.addFilter(filterName, filter);
        }

        public javax.servlet.FilterRegistration.Dynamic addFilter(
                String filterName, Class<? extends Filter> filterClass) {

            return delegate.addFilter(filterName, filterClass);
        }

        public <T extends Filter> T createFilter(Class<T> clazz)
                throws ServletException {

            return delegate.createFilter(clazz);
        }

        public FilterRegistration getFilterRegistration(String filterName) {

            return delegate.getFilterRegistration(filterName);
        }

        public Map<String, ? extends FilterRegistration> getFilterRegistrations() {

            return delegate.getFilterRegistrations();
        }

        public SessionCookieConfig getSessionCookieConfig() {

            return delegate.getSessionCookieConfig();
        }

        public void setSessionTrackingModes(
                Set<SessionTrackingMode> sessionTrackingModes) {

            delegate.setSessionTrackingModes(sessionTrackingModes);
        }

        public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {

            return delegate.getDefaultSessionTrackingModes();
        }

        public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {

            return delegate.getEffectiveSessionTrackingModes();
        }

        public void addListener(String className) {

            delegate.addListener(className);
        }

        public <T extends EventListener> void addListener(T t) {

            delegate.addListener(t);
        }

        public void addListener(Class<? extends EventListener> listenerClass) {

            delegate.addListener(listenerClass);
        }

        public <T extends EventListener> T createListener(Class<T> clazz)
                throws ServletException {

            return delegate.createListener(clazz);
        }

        public JspConfigDescriptor getJspConfigDescriptor() {

            return delegate.getJspConfigDescriptor();
        }

        public ClassLoader getClassLoader() {

            return delegate.getClassLoader();
        }

        public void declareRoles(String... roleNames) {

            delegate.declareRoles(roleNames);
        }

        public String getVirtualServerName() {

            return delegate.getVirtualServerName();
        }

        @Override
        public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {

            return delegate.addJspFile(servletName, jspFile);
        }

        @Override
        public int getSessionTimeout() {

            return delegate.getSessionTimeout();
        }

        @Override
        public void setSessionTimeout(int sessionTimeout) {

            delegate.setSessionTimeout(sessionTimeout);
        }

        @Override
        public String getRequestCharacterEncoding() {

            return delegate.getRequestCharacterEncoding();
        }

        @Override
        public void setRequestCharacterEncoding(String encoding) {

            delegate.setRequestCharacterEncoding(encoding);
        }

        @Override
        public String getResponseCharacterEncoding() {

            return delegate.getResponseCharacterEncoding();
        }

        @Override
        public void setResponseCharacterEncoding(String encoding) {

            delegate.setResponseCharacterEncoding(encoding);
        }
    }
}
