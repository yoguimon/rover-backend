package com.api.rover.controllers;

import com.api.rover.models.Obstacle;
import com.api.rover.services.ObstacleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/obstacle")
public class ObstacleController {
    @Autowired
    private ObstacleServiceImp obstacleServiceImp;
    @GetMapping
    public ResponseEntity<List<Obstacle>> getObstacles(){
        List<Obstacle> obstacles =  obstacleServiceImp.getAllObstacles();
        if (obstacles.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay obstaculos
        }

        return ResponseEntity.ok(obstacles);
    }
}
