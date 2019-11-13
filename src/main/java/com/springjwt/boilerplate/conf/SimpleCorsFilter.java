package com.springjwt.boilerplate.conf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SimpleCorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
	  HttpServletRequest request = (HttpServletRequest) req;
	  HttpServletResponse response = (HttpServletResponse) res;
	  log.info(request.getPathInfo());
      response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers",
        "Content-Type, Accept");
      response.setHeader("Access-Control-Allow-Headers",
          "Content-Type, Accept, X-Requested-With, remember-me, authorization");
      chain.doFilter(req, res);
	}

}
