//***********************************************************************************
//
// Systeme Technique RBRSVA
//
// (C) Copyright Bouygues Telecom 2015.
//
// Utilisation, reproduction et divulgation interdites
// sans autorisation ecrite de Bouygues Telecom.
//
// Projet        : RBRSVA
// Createur      : arandria
// Date creation : 1 juil. 2015
// version       : $Revision$
//
//************************************************************************************
package com.lezardino.mybank.filter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import java.io.IOException;

public class ContainerResponseFilter implements Filter {

    private static final Log LOGGER = LogFactory.getLog(ContainerResponseFilter.class);

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";

    private static final String ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER = "Access-Control-Allow-Credentials";

    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    private static final String ALLOWED_METHODS = "GET, POST, PUT, OPTIONS";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("Debut du ContainerResponseFilter");

        final HttpServletRequest requete = (HttpServletRequest) servletRequest;
        final HttpServletResponse reponse = (HttpServletResponse) servletResponse;

        String requestOrigin = requete.getHeader("origin");

        reponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, requestOrigin);
        reponse.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER, "false");
        reponse.setHeader(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);

        filterChain.doFilter(requete, reponse) ;

        LOGGER.debug("Fin du ContainerResponseFilter");

    }

    @Override
    public void destroy() {

    }
}
