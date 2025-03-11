package com.api.rover.services;

import com.api.rover.models.Obstacle;
import com.api.rover.repositories.ObstacleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ObstacleServiceImp {
    @Autowired
    private ObstacleRepository obstacleRepository;
    public List<Obstacle> getAllObstacles(){
        return obstacleRepository.findAll();
    }
}
