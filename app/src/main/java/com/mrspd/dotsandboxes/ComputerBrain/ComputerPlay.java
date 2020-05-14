package com.mrspd.dotsandboxes.ComputerBrain;

import com.mrspd.dotsandboxes.Models.DirectionOfLine;
import com.mrspd.dotsandboxes.Models.Line;
import com.mrspd.dotsandboxes.Models.Player;

import java.util.ArrayList;
import java.util.List;


public class ComputerPlay extends Player {

    private final Integer gridSize;
    private final ArrayList<Line> safeLines;
    private final ArrayList<Line> goodLines;
    private final ArrayList<Line> badLines;

    public ComputerPlay(String name, String gridSize) {
        super(name);
        this.gridSize = Integer.valueOf(gridSize);
        safeLines = new ArrayList<>();
        goodLines = new ArrayList<>();
        badLines = new ArrayList<>();
    }

    private Line nextMove() {
        if (goodLines.size() != 0) return getBestGoodLine();
        if (safeLines.size() != 0) return getRandomSafeLine();

        return getRandomBadLine();
    }

    public Line move() {
        initialiseLines();
        return nextMove();
    }

    private void initialiseLines() {
        goodLines.clear();
        badLines.clear();
        safeLines.clear();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!isHorizontalLineOccupied(i, j)) {
                    if (i == 0) {
                        switch (getBox(i, j).occupiedLineCount()) {
                            case 3:
                                goodLines.add(new Line(DirectionOfLine.HORIZONTAL, i, j));
                                break;
                            case 2:
                                badLines.add(new Line(DirectionOfLine.HORIZONTAL, i, j));
                                break;
                            case 1:
                            case 0:
                                safeLines.add(new Line(DirectionOfLine.HORIZONTAL, i, j));
                        }
                    } else if (i == 5) {
                        switch (getBox(i - 1, j).occupiedLineCount()) {
                            case 3:
                                goodLines.add(new Line(DirectionOfLine.HORIZONTAL, i, j));
                                break;
                            case 2:
                                badLines.add(new Line(DirectionOfLine.HORIZONTAL, i, j));
                                break;
                            case 1:
                            case 0:
                                safeLines.add(new Line(DirectionOfLine.HORIZONTAL, i, j));
                        }
                    } else {
                        if (getBox(i, j).occupiedLineCount() == 3
                                || getBox(i - 1, j).occupiedLineCount() == 3)
                            goodLines.add(new Line(DirectionOfLine.HORIZONTAL, i, j));

                        if (getBox(i, j).occupiedLineCount() == 2
                                || getBox(i - 1, j).occupiedLineCount() == 2)
                            badLines.add(new Line(DirectionOfLine.HORIZONTAL, i, j));

                        if (getBox(i, j).occupiedLineCount() < 2
                                && getBox(i - 1, j).occupiedLineCount() < 2)
                            safeLines.add(new Line(DirectionOfLine.HORIZONTAL, i, j));
                    }
                }

                if (!isVerticalLineOccupied(j, i)) {
                    if (i == 0) {
                        if (getBox(j, i).occupiedLineCount() == 3)
                            goodLines.add(new Line(DirectionOfLine.VERTICAL, j, i));
                    } else if (i == 5) {
                        if (getBox(j, i - 1).occupiedLineCount() == 3)
                            goodLines.add(new Line(DirectionOfLine.VERTICAL, j, i));
                    } else {
                        if (getBox(j, i).occupiedLineCount() == 3
                                || getBox(j, i - 1).occupiedLineCount() == 3)
                            goodLines.add(new Line(DirectionOfLine.VERTICAL, j, i));

                        if (getBox(j, i).occupiedLineCount() == 2
                                || getBox(j, i - 1).occupiedLineCount() == 2)
                            badLines.add(new Line(DirectionOfLine.VERTICAL, j, i));

                        if (getBox(j, i).occupiedLineCount() < 2
                                && getBox(j, i - 1).occupiedLineCount() < 2)
                            safeLines.add(new Line(DirectionOfLine.VERTICAL, j, i));
                    }
                }
            }
        }
    }

    private Box getBox(int row, int column) {
        return new Box(isVerticalLineOccupied(row, column), isHorizontalLineOccupied(row, column),
                isVerticalLineOccupied(row, column + 1), isHorizontalLineOccupied(row + 1, column));
    }

    private boolean isHorizontalLineOccupied(int row, int column) {
        return getGame().isLineOccupied(DirectionOfLine.HORIZONTAL, row, column);
    }

    private boolean isVerticalLineOccupied(int row, int column) {
        return getGame().isLineOccupied(DirectionOfLine.VERTICAL, row, column);
    }

    private Line getBestGoodLine() {
        return goodLines.get(0);
    }

    private Line getRandomSafeLine() {
        return getRandomLine(safeLines);
    }

    private Line getRandomBadLine() {
        return getRandomLine(badLines);
    }

    private Line getRandomLine(List<Line> list) {
        return list.get((int) (list.size() * Math.random()));
    }
}
