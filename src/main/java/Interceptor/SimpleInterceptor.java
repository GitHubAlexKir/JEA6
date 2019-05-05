package Interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
/**
 * @author Alex
 * Simpele interceptor met logger
 **/
public class SimpleInterceptor {
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Logger logger = LoggerFactory.getLogger(context.getClass());
        logger.info("Method Called: " + context.getMethod().getName());
        return context.proceed();
    }
}