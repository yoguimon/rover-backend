package com.api.rover.services;

import com.api.rover.dtos.DireccionDto;
import com.api.rover.models.Rover;

public interface RoverService {
    Rover getRover();

    void setDirection(DireccionDto direccionDto);
}
