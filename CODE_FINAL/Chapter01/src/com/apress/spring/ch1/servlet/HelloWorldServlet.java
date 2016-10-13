package com.apress.springosgi.ch1.servlet;

import com.apress.springosgi.ch1.hello.HelloWorld;


import java.io.IOException;



import javax.servlet.ServletException;

import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.osgi.util.tracker.ServiceTracker;



public class HelloWorldServlet extends HttpServlet {

    
  
    private static final long serialVersionUID = 42L;

   
















        private ServiceTracker serviceTracker;



        public HelloWorldServlet(ServiceTracker serviceTracker) {

                this.serviceTracker = serviceTracker;

        }



        protected void doGet(HttpServletRequest httpServletRequest,

                        HttpServletResponse httpServletResponse) throws ServletException,

                        IOException {

                httpServletResponse.setContentType("text/html");

                ServletOutputStream out = httpServletResponse.getOutputStream();

                out.println("<html><body>");



                HelloWorld service = (HelloWorld) serviceTracker

                                .getService();

                if (service != null) {

                        out.println(service.hello());

                }



                out.println("</body></html>");

                out.close();

        }


}