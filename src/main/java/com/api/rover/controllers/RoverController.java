package com.api.rover.controllers;

import com.api.rover.dtos.DireccionDto;
import com.api.rover.dtos.RoverDataDto;
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
    public Rover get(){
        Rover rover = roverService.getRover();
        return rover;
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
    @PutMapping("/move")
    public ResponseEntity<String> moveRover(@RequestBody RoverDataDto roverDataDto){
        try {
            String result = roverService.move(roverDataDto);
            if (result.equals("Posición fuera del mapa")||result.equals("Colision con un obstaculo")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
