package com.lezardino.mybank.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class DaoAspect {

    private static final Logger logger = LoggerFactory.getLogger(DaoAspect.class);

    @Around("execution(* com.lezardino.mybank.dao.*.*(..))")
    public Object around(final ProceedingJoinPoint joinpoint) throws Throwable {

        Signature signature = joinpoint.getSignature();

        final StringBuilder sb = new StringBuilder();
        sb.append(signature.toString());

        final Object[] args = joinpoint.getArgs();
        if (args.length > 0) {
            if (args.length == 1) {
                sb.append(" avec le parametre : (");
            }
            else {
                sb.append(" avec les parametres : (");
            }
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i < args.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
        }
        logger.debug("Debut methode : " + sb);
        Object obj = null;
        try {
            obj = joinpoint.proceed();
        }
        finally {
            logger.debug("Fin methode : " + signature.getName() + " retour = " + obj);
        }
        return obj;
    }
}
