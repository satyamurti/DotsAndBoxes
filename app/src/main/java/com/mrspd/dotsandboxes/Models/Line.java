package com.mrspd.dotsandboxes.Models;

public class Line {
    private final DirectionOfLine directionOfLine;
    private final int row;
    private final int column;
    public Line(DirectionOfLine directionOfLine, int row, int column) {
        this.directionOfLine = directionOfLine;
        this.row = row;
        this.column = column;
    }
    public DirectionOfLine direction() {
        return directionOfLine;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        return row == line.row && column == line.column && directionOfLine == line.directionOfLine;
    }

    @Override
    public String toString() {
        return "directionOfLine:" + direction().toString() + "row:" + row + "column" + column;
    }
}



