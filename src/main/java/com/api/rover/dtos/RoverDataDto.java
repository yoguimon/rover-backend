package com.api.rover.dtos;

import com.api.rover.models.Rover;


public class RoverDataDto {
    private Rover rover;
    private int upOrdown;//forward or back

    public RoverDataDto() {
    }

    public RoverDataDto(Rover rover, int upOrdown) {
        this.rover = rover;
        this.upOrdown = upOrdown;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }

    public int getUpOrdown() {
        return upOrdown;
    }

    public void setUpOrdown(int upOrdown) {
        this.upOrdown = upOrdown;
    }
}
