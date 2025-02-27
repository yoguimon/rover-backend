package com.api.rover.services;

import com.api.rover.dtos.DireccionDto;
import com.api.rover.dtos.RoverDataDto;
import com.api.rover.models.Direction;
import com.api.rover.models.Rover;
import com.api.rover.repositories.RoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoverServiceImp implements RoverService{
    @Autowired
    private RoverRepository roverRepository;
    private static final List<String> DIRECTIONS = List.of("NORTH", "EAST", "SOUTH", "WEST");
    @Override
    @Transactional
    public Rover getRover() {
        return roverRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rover not found"));
    }

    @Override
    public void setDirection(DireccionDto direccionDto) {
        Rover rover = getRover();
        String res = updateDirecion(direccionDto);
        rover.setDirection(Direction.valueOf(res));
        roverRepository.save(rover);
    }

    @Override
    public void move(RoverDataDto roverDataDto) {
        Rover rover = getRover();
        int moveStep = (roverDataDto.getUpOrdown() == 1) ? 5 : -5; // 5 for forward, -5 for back
        switch (rover.getDirection()) {
            case NORTH:
                rover.setY(rover.getY() - moveStep);
                break;
            case EAST:
                rover.setX(rover.getX() + moveStep);
                break;
            case SOUTH:
                rover.setY(rover.getY() + moveStep);
                break;
            case WEST:
                rover.setX(rover.getX() - moveStep);
                break;
        }
        roverRepository.save(rover);
    }

    public String updateDirecion(DireccionDto direccionDto){
        int currentIndex = DIRECTIONS.indexOf(direccionDto.getActualDirection());
        if (currentIndex == -1) {
            throw new IllegalArgumentException("Invalid direction: " + direccionDto.getActualDirection());
        }
        int newIndex = currentIndex;
        switch (direccionDto.getHeadPosition().toUpperCase()) {
            case "LEFT":
                newIndex = (currentIndex + 1) % DIRECTIONS.size();
                break;
            case "RIGHT":
                newIndex = (currentIndex - 1 + DIRECTIONS.size()) % DIRECTIONS.size();
                break;
            default:
                throw new IllegalArgumentException("Invalid head position: " + direccionDto.getHeadPosition());
        }
        return DIRECTIONS.get(newIndex);
    }



}
