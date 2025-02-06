package com.api.rover.services;

import com.api.rover.dtos.DireccionDto;
import com.api.rover.models.Direction;
import com.api.rover.models.Rover;
import com.api.rover.repositories.RoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoverServiceImp implements RoverService{
    @Autowired
    RoverRepository roverRepository;
    @Override
    @Transactional
    public Rover getRover() {
        Rover rover = roverRepository.findAll().get(0);
        return rover;
    }

    @Override
    public void setDirection(DireccionDto direccionDto) {
        Rover rover = roverRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rover not found"));
        rover.setDirection(direccionDto.getNewDirection());
        roverRepository.save(rover);
    }
}
