package com.passionnambition.websvc;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.passionnambition.websvc.impl.AIMSSServiceImpl;

/**
 * Defines the components of a JAX-RS application 
 */
public class AIMSSApplication extends Application
{
    private Set<Object> singletons = new HashSet<Object>();
    
    /**
     * Defines the components of a JAX-RS application
     */
    public AIMSSApplication()
    {
        singletons.add(new AIMSSServiceImpl());
    }
    
    /**
     * Get a set of root resource and provider classes. The default lifecycle for resource class
     * instances is per-request. This method returns a list of classes we want to deploy into the
     * JAX-RS environment. NOTE: DO NOT modify the returned set (non-Javadoc)
     * 
     * @see javax.ws.rs.core.Application#getClasses()
     */
    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        //TODO:Add Custom exception and response messages 
        return classes;
    }
    
    /**
     * Get a set of root resource and provider instances. Fields and properties of returned
     * instances are injected with their declared dependencies (see {@link Context}) by the runtime
     * prior to use. This method returns actual instances that is created. (non-Javadoc)
     * 
     * @see javax.ws.rs.core.Application#getSingletons()
     */
    @Override
    public Set<Object> getSingletons()
    {
        return singletons;
    }
}

