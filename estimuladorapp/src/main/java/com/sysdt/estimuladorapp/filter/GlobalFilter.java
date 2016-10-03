package com.sysdt.estimuladorapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sysdt.estimuladorapp.dto.UsuarioDTO;
import com.sysdt.estimuladorapp.enums.TipoUsuarioEnum;

/**
 * Servlet Filter implementation class GlobalFilter
 */
@WebFilter(urlPatterns={
		"/", "/login", "/acceso", "/historial", "/adminmedico", "/adminpaciente", "/estimulacion", "/adminestimulador", }, 
		asyncSupported=true)
public class GlobalFilter implements Filter {

    /**
     * Default constructor. 
     */
    public GlobalFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Entro a FILTER");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String url = req.getRequestURI();
		System.out.println(url);
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		res.setDateHeader("Expires", 0);
		if(!url.contentEquals("/estimuladorapp/") && !url.contentEquals("/estimuladorapp/login")){
			HttpSession session = req.getSession(false);
			if(session == null){
				req.getRequestDispatcher("/accesoDenegado").forward(request, response);
			}else{
				UsuarioDTO usuarioDTO = (UsuarioDTO)session.getAttribute("usuarioDTO");
				if(usuarioDTO == null){
					req.getRequestDispatcher("/accesoDenegado").forward(request, response);
				}else if(usuarioDTO.getUsuario().getIdTipoUsuario() == TipoUsuarioEnum.MEDICO.getId() && url.contains("admin")){
					req.getRequestDispatcher("/accesoDenegado").forward(request, response);
				}else{
					chain.doFilter(request, response);
				}
				
			}
		}else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
