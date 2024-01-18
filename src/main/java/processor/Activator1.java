package de.cas.camel.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator1 implements BundleActivator {
    private static final transient Log log = LogFactory.getLog(de.cas.camel.processor.Activator1.class.getName());

    public void start(BundleContext context) {
        log.info("MapO365ToLancom started");
    }

    public void stop(BundleContext context) {
        log.info("MapO365ToLancom stopped");
    }
}