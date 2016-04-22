package edu.gatech.project3for6310.services;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		MultivaluedMap<String, Object> header=response.getHttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
        header.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        header.add("Access-Control-Allow-Credentials", "true");
        header.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        header.add("Access-Control-Max-Age", "1209600");
		return response;
	}

}
