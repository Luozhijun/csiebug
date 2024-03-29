/**
 * 
 */
package csiebug.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.RequestUtils;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.StaticContentLoader;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.ng.filter.FilterHostConfig;
import org.apache.struts2.util.ClassLoaderUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

/**
 * url pattern match的除了靜態資源和wsdl路徑以外，其他都當作是Action的request
 * @author George_Tsai
 * @version 2009/12/5
 */
public class Struts2WebServiceDispatcher implements Filter {

    /**
     * Provide a logging instance.
     */
    private Logger log;

    /**
     * Provide ActionMapper instance, set by injection.
     */
    private ActionMapper actionMapper;

    /**
     * Provide FilterConfig instance, set on init.
     */
    private FilterConfig filterConfig;

    /**
     * Expose Dispatcher instance to subclass.
     */
    protected Dispatcher dispatcher;

    /**
     * Loads stattic resources, set by injection
     */
    protected StaticContentLoader staticResourceLoader;

    /**
     * Initializes the filter by creating a default dispatcher
     * and setting the default packages for static resources.
     *
     * @param filterConfig The filter configuration
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            this.filterConfig = filterConfig;

            initLogging();

            dispatcher = createDispatcher(filterConfig);
            dispatcher.init();
            dispatcher.getContainer().inject(this);

            staticResourceLoader.setHostConfig(new FilterHostConfig(filterConfig));
        } finally {
            ActionContext.setContext(null);
        }
    }

	private void initLogging() {
        String factoryName = filterConfig.getInitParameter("loggerFactory");
        if (factoryName != null) {
            try {
                @SuppressWarnings("rawtypes")
				Class cls = ClassLoaderUtils.loadClass(factoryName, this.getClass());
                LoggerFactory fac = (LoggerFactory) cls.newInstance();
                LoggerFactory.setLoggerFactory(fac);
            } catch (InstantiationException e) {
                System.err.println("Unable to instantiate logger factory: " + factoryName + ", using default");
            } catch (IllegalAccessException e) {
                System.err.println("Unable to access logger factory: " + factoryName + ", using default");
            } catch (ClassNotFoundException e) {
                System.err.println("Unable to locate logger factory class: " + factoryName + ", using default");
            }
        }

        log = LoggerFactory.getLogger(Struts2WebServiceDispatcher.class);

    }

    /**
     * Calls dispatcher.cleanup,
     * which in turn releases local threads and destroys any DispatchListeners.
     *
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        if (dispatcher == null) {
            log.warn("something is seriously wrong, Dispatcher is not initialized (null) ");
        } else {
            try {
                dispatcher.cleanup();
            } finally {
                ActionContext.setContext(null);
            }
        }
    }

    /**
     * Create a default {@link Dispatcher} that subclasses can override
     * with a custom Dispatcher, if needed.
     *
     * @param filterConfig Our FilterConfig
     * @return Initialized Dispatcher
     */
	protected Dispatcher createDispatcher(FilterConfig filterConfig) {
    	AssertUtility.notNull(filterConfig);
    	
        Map<String, String> params = new HashMap<String, String>();
        for (Enumeration<String> e = filterConfig.getInitParameterNames(); e.hasMoreElements();) {
            String name = e.nextElement();
            String value = filterConfig.getInitParameter(name);
            params.put(name, value);
        }
        return new Dispatcher(filterConfig.getServletContext(), params);
    }

    /**
     * Modify state of StrutsConstants.STRUTS_STATIC_CONTENT_LOADER setting.
     * @param staticResourceLoader val New setting
     */
    @Inject
    public void setStaticResourceLoader(StaticContentLoader staticResourceLoader) {
        this.staticResourceLoader = staticResourceLoader;
    }

    /**
     * Modify ActionMapper instance.
     * @param mapper New instance
     */
    @Inject
    public void setActionMapper(ActionMapper mapper) {
        actionMapper = mapper;
    }

    /**
     * Provide a workaround for some versions of WebLogic.
     * <p/>
     * Servlet 2.3 specifies that the servlet context can be retrieved from the session. Unfortunately, some versions of
     * WebLogic can only retrieve the servlet context from the filter config. Hence, this method enables subclasses to
     * retrieve the servlet context from other sources.
     *
     * @return the servlet context.
     */
    protected ServletContext getServletContext() {
        return filterConfig.getServletContext();
    }

    /**
     * Expose the FilterConfig instance.
     *
     * @return Our FilterConfit instance
     */
    protected FilterConfig getFilterConfig() {
        return filterConfig;
    }

    /**
     * Wrap and return the given request, if needed, so as to to transparently
     * handle multipart data as a wrapped class around the given request.
     *
     * @param request  Our ServletRequest object
     * @param response Our ServerResponse object
     * @return Wrapped HttpServletRequest object
     * @throws ServletException on any error
     */
    protected HttpServletRequest prepareDispatcherAndWrapRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        Dispatcher du = Dispatcher.getInstance();

        // Prepare and wrap the request if the cleanup filter hasn't already, cleanup filter should be
        // configured first before struts2 dispatcher filter, hence when its cleanup filter's turn,
        // static instance of Dispatcher should be null.
        if (du == null) {

            Dispatcher.setInstance(dispatcher);

            // prepare the request no matter what - this ensures that the proper character encoding
            // is used before invoking the mapper (see WW-9127)
            dispatcher.prepare(request, response);
        } else {
            dispatcher = du;
        }

        try {
            // Wrap request first, just in case it is multipart/form-data
            // parameters might not be accessible through before encoding (ww-1278)
            request = dispatcher.wrapRequest(request, getServletContext());
        } catch (IOException e) {
            String message = "Could not wrap servlet request with MultipartRequestWrapper!";
            log.error(message, e);
            throw new ServletException(message, e);
        }

        return request;
    }
	
	/**
     * Process an action or handle a request a static resource.
     * <p/>
     * The filter tries to match the request to an action mapping.
     * If mapping is found, the action processes is delegated to the dispatcher's serviceAction method.
     * If action processing fails, doFilter will try to create an error page via the dispatcher.
     * <p/>
     * Otherwise, if the request is for a static resource,
     * the resource is copied directly to the response, with the appropriate caching headers set.
     * <p/>
     * If the request does not match an action mapping, or a static resource page,
     * then it passes through.
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    	AssertUtility.notNull(req);
    	AssertUtility.notNull(res);
    	AssertUtility.notNull(chain);
    	
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        ServletContext servletContext = getServletContext();
        
        if(RequestUtils.getServletPath(request).equals("/services") || RequestUtils.getServletPath(request).startsWith("/services/")) {
        	//web service的話,不透過struts2處理了
	     	chain.doFilter(request, response);
	        return;
        } else if(RequestUtils.getServletPath(request).equals("/")) {
        	//導到welcome頁(也就是index action)
        	response.sendRedirect(response.encodeRedirectURL(StringUtility.cleanCRLF(request.getRequestURI()) + "index"));
            return;
        }

        String timerKey = "FilterDispatcher_doFilter: ";
        try {

            ValueStack stack = dispatcher.getContainer().getInstance(ValueStackFactory.class).createValueStack();
            ActionContext ctx = new ActionContext(stack.getContext());
            ActionContext.setContext(ctx);

            UtilTimerStack.push(timerKey);
            request = prepareDispatcherAndWrapRequest(request, response);
            ActionMapping mapping;
            try {
                mapping = actionMapper.getMapping(request, dispatcher.getConfigurationManager());
            } catch (Exception ex) {
                log.error("error getting ActionMapping", ex);
                dispatcher.sendError(request, response, servletContext, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex);
                return;
            }
            
            if (mapping == null) {
                // there is no action in this request, should we look for a static resource?
                String resourcePath = RequestUtils.getServletPath(request);
                
                if ("".equals(resourcePath) && null != request.getPathInfo()) {
                    resourcePath = request.getPathInfo();
                }
                
                if (staticResourceLoader.canHandle(resourcePath)) {
                    staticResourceLoader.findStaticResource(resourcePath, request, response);
                } else {
                    // this is a normal request, let it pass through
                    chain.doFilter(request, response);
                }
                // The framework did its job here
                return;
            }

            dispatcher.serviceAction(request, response, servletContext, mapping);

        } finally {
            try {
            	// should we clean up yet?
                Integer count = (Integer) req.getAttribute("__cleanup_recursion_counter");
                if (count != null && count > 0 && log.isDebugEnabled()) {
                    log.debug("skipping cleanup counter="+count);
                } else {
	                // always dontClean up the thread request, even if an action hasn't been executed
	                ActionContext.setContext(null);
	                Dispatcher.setInstance(null);
                }
            } finally {
                UtilTimerStack.pop(timerKey);
            }
        }
    }
}
