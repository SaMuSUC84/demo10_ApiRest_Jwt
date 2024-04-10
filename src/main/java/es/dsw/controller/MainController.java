package es.dsw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {
	

	@GetMapping(value = {"/","/index"})
	public String idx() 
	{
		return "index";
	}
	 
	@GetMapping(value = { "/login" })
	synchronized public String login() 
	{
		return "login";
	}
	

}
