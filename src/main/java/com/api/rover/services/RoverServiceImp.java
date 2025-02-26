package com.api.rover.services;

import com.api.rover.dtos.DireccionDto;
import com.api.rover.models.Direction;
import com.api.rover.models.Rover;
import com.api.rover.repositories.RoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Arrays;

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
        String res = updateDirecion(direccionDto);
        Rover rover = roverRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rover not found"));
        rover.setDirection(Direction.valueOf(res));
        roverRepository.save(rover);
    }
    public String updateDirecion(DireccionDto direccionDto){
        List<String> pos = Arrays.asList("NORTH","EAST","SOUTH","WEST");
        String res="";
        switch (direccionDto.getHeadPosition()){
            case "LEFT":
                int aux1 = pos.indexOf(direccionDto.getActualDirection());
                if(pos.size()-1==aux1){
                    res = pos.get(0);
                }else{
                    res = pos.get(aux1+1);
                }
                break;
            case "RIGHT":
                int aux2 = pos.indexOf(direccionDto.getActualDirection());
                if(aux2==0){
                    res = pos.get(pos.size()-1);
                }else{
                    res = pos.get(aux2-1);
                }
                break;
        }
        return res;
    }



}
