package com.gsuslateproxy.gsuslateproxy.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping({"/gsu"})
public class ProxyController {
    public ProxyController(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }
    Logger logger= LoggerFactory.getLogger(ProxyController.class);

   // @Autowired
   // private final RestTemplate restTemplate;
    @Autowired
    private final  RestTemplate restTemplate;
    @Autowired
    private final Environment environment;

    @GetMapping({"/slate/{type}/{id}"})
    public JsonNode getResultFunds(@PathVariable String type, @PathVariable String id) throws Exception {
        String requestUrl ="";
        logger.info("getResult Method is Accessed");
        if(id.equalsIgnoreCase("all")) {
            requestUrl = this.environment.getProperty(type);
        }else{
            requestUrl = this.environment.getProperty(type)+"&key="+id;
        }
        if (requestUrl == null) {
            throw new Exception("No request url is specified for client " + type);
        }
        logger.info(requestUrl);
        //requestUrl = requestUrl;
        //System.out.println(requestUrl);

        // return restTemplate.getForObject(requestUrl,String.class);
        return restTemplate.getForObject(requestUrl, JsonNode.class);
       // return requestUrl;

    }

}
