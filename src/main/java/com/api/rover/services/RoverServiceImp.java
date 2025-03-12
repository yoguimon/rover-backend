package com.api.rover.services;

import com.api.rover.dtos.DireccionDto;
import com.api.rover.dtos.RoverDataDto;
import com.api.rover.models.Direction;
import com.api.rover.models.Obstacle;
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
    @Autowired
    private ObstacleServiceImp obstacleServiceImp;
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
    @Transactional
    public void setDirection(DireccionDto direccionDto) {
        Rover rover = getRover();
        String res = updateDirection(direccionDto);
        rover.setDirection(Direction.valueOf(res));
        roverRepository.save(rover);
    }

    @Override
    @Transactional
    public String move(RoverDataDto roverDataDto) {
        Rover rover = getRover();
        List<Obstacle> obstacles = obstacleServiceImp.getAllObstacles();
        int moveStep = (roverDataDto.getUpOrdown() == 1) ? 5 : -5; // 5 for forward, -5 for back
        // Calculamos la nueva posición sin modificar directamente el rover
        int newX = rover.getX();
        int newY = rover.getY();
        switch (rover.getDirection()) {
            case NORTH:
                newY -= moveStep;
                break;
            case EAST:
                newX += moveStep;
                break;
            case SOUTH:
                newY += moveStep;
                break;
            case WEST:
                newX -= moveStep;
                break;
        }
        // Verificamos si la nueva posición esta dentro de los limites
        if (newX < 2 || newX > 92 || newY < 0 || newY > 85) {
            return "Posición fuera del mapa";
        }
        for(Obstacle ob : obstacles){
            if (ob.getX()==newX&&ob.getY()==newY){
                return "Colision con un obstaculo";
            }
        }
        // Si es válida, actualizamos y guardamos el rover
        rover.setX(newX);
        rover.setY(newY);
        roverRepository.save(rover);
        return "Rover movido exitosamente";
    }

    public String updateDirection(DireccionDto direccionDto){
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
