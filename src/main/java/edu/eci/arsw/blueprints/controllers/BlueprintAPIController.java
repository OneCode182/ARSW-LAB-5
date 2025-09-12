/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.apache.catalina.connector.Response;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
public class BlueprintAPIController {

    private final BlueprintsServices services;

    private final Logger logger = (Logger) LoggerFactory.getLogger(BlueprintAPIController.class);
    public BlueprintAPIController(BlueprintsServices services){
        this.services = services;
    }


    @GetMapping("/blueprints")
    public ResponseEntity<Response> getAllBlueprints() {
        Set<Blueprint> response =  services.getAllBlueprints();

        if(response.isEmpty()){
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, "");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Response) response);
        }

        return ResponseEntity.status(HttpStatus.OK).body((Response) response);
    }
    
    
}

