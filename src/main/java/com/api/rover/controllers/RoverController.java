package com.api.rover.controllers;

import com.api.rover.dtos.DireccionDto;
import com.api.rover.models.Rover;
import com.api.rover.services.RoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rover")
public class RoverController {
    @Autowired
    private RoverService roverService;
    @GetMapping
    public ResponseEntity<Rover> get(){
        Rover r = roverService.getRover();
        return ResponseEntity.ok(r);
    }
    @PutMapping("/direction")
    public ResponseEntity<String> setDirection(@RequestBody DireccionDto direccionDto){
        try {
            roverService.setDirection(direccionDto);
            return ResponseEntity.ok("Direction changed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
