package com.api.rover.dtos;

import com.api.rover.models.Direction;
import lombok.Data;


public class DireccionDto {
    private String headPosition;//example LEFT
    private String actualDirection;// example WEST

    public DireccionDto() {
    }

    public DireccionDto(String headPosition, String actualDirection) {
        this.headPosition = headPosition;
        this.actualDirection = actualDirection;
    }

    public String getHeadPosition() {
        return headPosition;
    }

    public void setHeadPosition(String headPosition) {
        this.headPosition = headPosition;
    }

    public String getActualDirection() {
        return actualDirection;
    }

    public void setActualDirection(String actualDirection) {
        this.actualDirection = actualDirection;
    }
}
