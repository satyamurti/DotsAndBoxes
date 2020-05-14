package com.mrspd.dotsandboxes.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Graph extends Observable {
    private Player[] players;
    private int currentPlayerIndex, PlayersNum;
    private double width;
    private double height;
    private Player[][] occupied;
    private List<List<Integer>> occupied1;
    private int[][] horizontalLines;
    private int[][] verticalLines;
    private ArrayList<Integer> row;
    private ArrayList<Integer> column;
    private ArrayList<Line> movee;
    private ArrayList<DirectionOfLine> directionOfLine;
    private Line latestLine;

    public Graph(double width, double height, Player[] players, Integer PlayersNum) {
        this.width = width;
        this.height = height;
        this.players = players;
        this.PlayersNum = PlayersNum;

        row = new ArrayList<>();
        column = new ArrayList<>();
        directionOfLine = new ArrayList<>();
        movee = new ArrayList<>();

        occupied = new Player[(int) height][(int) width];
        occupied1 = new ArrayList<List<Integer>>();

        horizontalLines = new int[(int) (height + 1)][(int) width];
        verticalLines = new int[(int) height][(int) (width + 1)];

        addPlayersToGame(players);
        currentPlayerIndex = 0;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getWidth() {
        return (int) width;
    }

    public int getHeight() {
        return (int) height;
    }

    public Line getLatestLine() {
        return latestLine;
    }

    private void addPlayersToGame(Player[] players) {
        for (Player player : players) {
            player.addToGame(this);
        }
    }

    public void start() {
        while (!isGameFinished()) {
            addMove(currentPlayer().move());
            setChanged();
            notifyObservers();
        }
    }

    private void addMove(Line move) {
        movee.add(move);
        if (isLineOccupied(move)) {
            return;
        }
        boolean newBoxOccupied = OccupyBox(move);
        setLineOccupied(move);
        latestLine = move;
        if (!newBoxOccupied)
            toNextPlayer();
    }

    public Player currentPlayer() {
        return players[currentPlayerIndex];
    }

    public boolean isLineOccupied(DirectionOfLine directionOfLine, int row, int column) {
        return isLineOccupied(new Line(directionOfLine, row, column));
    }

    public boolean isLineOccupied(Line line) {

        switch (line.direction()) {
            case HORIZONTAL:
                return (horizontalLines[line.row()][line.column()] == 1
                        || horizontalLines[line.row()][line.column()] == 2
                        || horizontalLines[line.row()][line.column()] == 3
                        || horizontalLines[line.row()][line.column()] == 4
                );
            case VERTICAL:
                return (verticalLines[line.row()][line.column()] == 1
                        || verticalLines[line.row()][line.column()] == 2
                        || verticalLines[line.row()][line.column()] == 3
                        || verticalLines[line.row()][line.column()] == 4);
        }
        throw new IllegalArgumentException(line.direction().toString());
    }

    public int getLineOccupier(Line line) {
        switch (line.direction()) {
            case HORIZONTAL:
                return horizontalLines[line.row()][line.column()];
            case VERTICAL:
                return verticalLines[line.row()][line.column()];
        }
        throw new IllegalArgumentException(line.direction().toString());
    }

    public Player getBoxOccupier(int row, int column) {
        return occupied[row][column];
    }

    public int getPlayerOccupyingBoxCount(Player player) {
        int count = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (getBoxOccupier(i, j) == player)
                    count++;
            }
        }
        return count;
    }

    private boolean OccupyBox(Line move) {
        boolean rightOccupied = tryToOccupyRightBox(move);
        boolean underOccupied = tryToOccupyUnderBox(move);
        boolean upperOccupied = tryToOccupyUpperBox(move);
        boolean leftOccupied = tryToOccupyLeftBox(move);
        return leftOccupied || rightOccupied || upperOccupied || underOccupied;
    }

    private void RemoveBox(Line move) {
        boolean rightRemoved = tryToRemoveRightBox(move);
        boolean underRemoved = tryToRemoveUnderBox(move);
        boolean upperRemoved = tryToRemoveUpperBox(move);
        boolean leftRemoved = tryToRemoveLeftBox(move);
        setChanged();
        notifyObservers();
    }

    private void setLineOccupied(Line line) {
        row.add(line.row());
        column.add(line.column());
        directionOfLine.add(line.direction());
        switch (line.direction()) {
            case HORIZONTAL:
                horizontalLines[line.row()][line.column()] = currentPlayerIndex + 1;
                break;
            case VERTICAL:
                verticalLines[line.row()][line.column()] = currentPlayerIndex + 1;
                break;
        }
    }

    public void removeLineOccupied() {
        if (directionOfLine.get(directionOfLine.size() - 1) == DirectionOfLine.HORIZONTAL) {
            if ((row.size() > 1) && (column.size() > 1)) {
                latestLine = null;
                RemoveBox(movee.get(movee.size() - 1));
                horizontalLines[row.get(row.size() - 1)][column.get(column.size() - 1)] = 0;
                row.remove(row.size() - 1);
                movee.remove(movee.size() - 1);
                column.remove(column.size() - 1);
                directionOfLine.remove(directionOfLine.size() - 1);

            }
        } else {
            if ((row.size() > 1) && (column.size() > 1)) {
                latestLine = null;
                RemoveBox(movee.get(movee.size() - 1));
                verticalLines[row.get(row.size() - 1)][column.get(column.size() - 1)] = 0;
                row.remove(row.size() - 1);
                movee.remove(movee.size() - 1);
                column.remove(column.size() - 1);
                directionOfLine.remove(directionOfLine.size() - 1);

            }
        }

    }

    private void setBoxOccupied(int row, int column, Player player) {
        occupied[row][column] = player;
    }

    private void removeBoxOccupied(int row, int column, Player player) {
        occupied[row][column] = null;
    }

    private boolean tryToOccupyUpperBox(Line move) {
        if (move.direction() != DirectionOfLine.HORIZONTAL || move.row() <= 0)
            return false;
        if (isLineOccupied(DirectionOfLine.HORIZONTAL, move.row() - 1, move.column())
                && isLineOccupied(DirectionOfLine.VERTICAL, move.row() - 1, move.column())
                && isLineOccupied(DirectionOfLine.VERTICAL, move.row() - 1, move.column() + 1)) {
            setBoxOccupied(move.row() - 1, move.column(), currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToRemoveUpperBox(Line move) {
        if (move.direction() != DirectionOfLine.HORIZONTAL || move.row() <= 0)
            return false;
        if (isLineOccupied(DirectionOfLine.HORIZONTAL, move.row() - 1, move.column())
                && isLineOccupied(DirectionOfLine.VERTICAL, move.row() - 1, move.column())
                && isLineOccupied(DirectionOfLine.VERTICAL, move.row() - 1, move.column() + 1)) {
            removeBoxOccupied(move.row() - 1, move.column(), currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyUnderBox(Line move) {
        if (move.direction() != DirectionOfLine.HORIZONTAL || move.row() >= (height))
            return false;
        if (isLineOccupied(DirectionOfLine.HORIZONTAL, move.row() + 1, move.column())
                && isLineOccupied(DirectionOfLine.VERTICAL, move.row(), move.column())
                && isLineOccupied(DirectionOfLine.VERTICAL, move.row(), move.column() + 1)) {
            setBoxOccupied(move.row(), move.column(), currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToRemoveUnderBox(Line move) {
        if (move.direction() != DirectionOfLine.HORIZONTAL || move.row() >= (height))
            return false;
        if (isLineOccupied(DirectionOfLine.HORIZONTAL, move.row() + 1, move.column())
                && isLineOccupied(DirectionOfLine.VERTICAL, move.row(), move.column())
                && isLineOccupied(DirectionOfLine.VERTICAL, move.row(), move.column() + 1)) {
            removeBoxOccupied(move.row(), move.column(), currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyLeftBox(Line move) {
        if (move.direction() != DirectionOfLine.VERTICAL || move.column() <= 0)
            return false;
        if (isLineOccupied(DirectionOfLine.VERTICAL, move.row(), move.column() - 1)
                && isLineOccupied(DirectionOfLine.HORIZONTAL, move.row(), move.column() - 1)
                && isLineOccupied(DirectionOfLine.HORIZONTAL, move.row() + 1, move.column() - 1)) {
            setBoxOccupied(move.row(), move.column() - 1, currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToRemoveLeftBox(Line move) {
        if (move.direction() != DirectionOfLine.VERTICAL || move.column() <= 0)
            return false;
        if (isLineOccupied(DirectionOfLine.VERTICAL, move.row(), move.column() - 1)
                && isLineOccupied(DirectionOfLine.HORIZONTAL, move.row(), move.column() - 1)
                && isLineOccupied(DirectionOfLine.HORIZONTAL, move.row() + 1, move.column() - 1)) {
            removeBoxOccupied(move.row(), move.column() - 1, currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyRightBox(Line move) {
        if (move.direction() != DirectionOfLine.VERTICAL || move.column() >= (width))
            return false;
        if (isLineOccupied(DirectionOfLine.VERTICAL, move.row(), move.column() + 1)
                && isLineOccupied(DirectionOfLine.HORIZONTAL, move.row(), move.column())
                && isLineOccupied(DirectionOfLine.HORIZONTAL, move.row() + 1, move.column())) {
            setBoxOccupied(move.row(), move.column(), currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToRemoveRightBox(Line move) {
        if (move.direction() != DirectionOfLine.VERTICAL || move.column() >= (width))
            return false;
        if (isLineOccupied(DirectionOfLine.VERTICAL, move.row(), move.column() + 1)
                && isLineOccupied(DirectionOfLine.HORIZONTAL, move.row(), move.column())
                && isLineOccupied(DirectionOfLine.HORIZONTAL, move.row() + 1, move.column())) {
            removeBoxOccupied(move.row(), move.column(), currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private void toNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    private boolean isGameFinished() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (getBoxOccupier(i, j) == null)
                    return false;
            }
        }
        return true;
    }

    public Player getWinner() {
        if (!isGameFinished()) {
            return null;
        }

        int[] playersOccupyingBoxCount = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            playersOccupyingBoxCount[i] = getPlayerOccupyingBoxCount(players[i]);
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        switch (PlayersNum) {

            case 3:
                if (playersOccupyingBoxCount[0] > playersOccupyingBoxCount[2]
                        && playersOccupyingBoxCount[0] > playersOccupyingBoxCount[1])
                    return players[0];
                else if (playersOccupyingBoxCount[1] > playersOccupyingBoxCount[0]
                        && playersOccupyingBoxCount[1] > playersOccupyingBoxCount[2])
                    return players[1];
                else
                    return players[2];

            case 4:

                if (playersOccupyingBoxCount[0] > playersOccupyingBoxCount[2]
                        && playersOccupyingBoxCount[0] > playersOccupyingBoxCount[1]
                        && playersOccupyingBoxCount[0] > playersOccupyingBoxCount[3])
                    return players[0];
                else if (playersOccupyingBoxCount[1] > playersOccupyingBoxCount[0]
                        && playersOccupyingBoxCount[1] > playersOccupyingBoxCount[2]
                        && playersOccupyingBoxCount[1] > playersOccupyingBoxCount[3])
                    return players[1];
                else if (playersOccupyingBoxCount[2] > playersOccupyingBoxCount[0]
                        && playersOccupyingBoxCount[2] > playersOccupyingBoxCount[1]
                        && playersOccupyingBoxCount[2] > playersOccupyingBoxCount[3])
                    return players[2];
                else
                    return players[3];

            default:
                if (playersOccupyingBoxCount[0] > playersOccupyingBoxCount[1])
                    return players[0];
                else {
                    return players[1];
                }
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}
