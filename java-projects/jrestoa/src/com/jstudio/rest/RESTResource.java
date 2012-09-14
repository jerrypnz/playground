package com.jstudio.rest;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RESTResource
{
	public void init();

	public void create(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException;

	public void get(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException;

	public void update(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException;

	public void delete(Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException;
}
