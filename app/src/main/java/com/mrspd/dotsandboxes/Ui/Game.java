package com.mrspd.dotsandboxes.Ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mrspd.dotsandboxes.Models.DirectionOfLine;
import com.mrspd.dotsandboxes.Models.Graph;
import com.mrspd.dotsandboxes.Models.Line;
import com.mrspd.dotsandboxes.Models.Player;
import com.mrspd.dotsandboxes.Models.Warrior;
import com.mrspd.dotsandboxes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Game extends View implements Observer {
    String Mode,GrideSize;
    protected static final float radius = (float) 16 / 824;
    protected static final float start = (float) 14 / 824;
    protected static final float add1 = (float) 18 / 824;
    protected static final float add2 = (float) 2 / 824;
    protected static final float add3 = (float) 14 / 824;
    protected static final float add4 = (float) 141 / 824;
    protected static final float add5 = (float) 159 / 824;
    protected static final float add6 = (float) 9 / 824;
    private ArrayList<Path> paths;
    protected Path fillpath;
    protected final int[] playerColors, playerBoxColors;
    protected Graph game;
    protected Line move;
    Line horizontal, vertical;
    protected Paint paint;
    protected PlayersStateView playersState;

    @SuppressLint("ClickableViewAccessibility")
    public Game(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        fillpath = new Path();
        paint = new Paint();

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                receiveInput(event);
                return false;
            }
        });

        playerColors = new int[]{getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.red),getResources().getColor(R.color.blue),getResources().getColor(R.color.purple)};
        playerBoxColors = new int[]{getResources().getColor(R.color.yellow1),
                getResources().getColor(R.color.red1),getResources().getColor(R.color.blue),getResources().getColor(R.color.purple)};
    }

    public void setPlayersState(PlayersStateView playersState) {
        this.playersState = playersState;
    }

    public void startGame(Player[] players, String Mode, String GideSize, Integer playerNum) {

        paths = new ArrayList<>();
        this.Mode = Mode;
        this.GrideSize = GideSize;
        if (GideSize.equals("3")) {
            game = new Graph(3, 3, players,playerNum);
            game.addObserver(this);
            new Thread() {
                @Override
                public void run() {
                    game.start();
                }
            }.start();
            postInvalidate();
        } else if (GideSize.equals("4")) {
            game = new Graph(4, 4, players,playerNum);
            game.addObserver(this);
            new Thread() {
                @Override
                public void run() {
                    game.start();
                }
            }.start();
            postInvalidate();
        } else {
            game = new Graph(5, 5, players,playerNum);
            game.addObserver(this);
            new Thread() {
                @Override
                public void run() {
                    game.start();
                }
            }.start();
            postInvalidate();
        }
    }

    @SuppressLint({"ResourceAsColor", "DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0x00FFFFFF);
        int min = Math.min(getWidth(), getHeight());
        float radius = Game.radius * min;
        float start = Game.start * min;
        float add1 = Game.add1 * min;
        float add2 = Game.add2 * min;
        float add4 = Game.add4 * min;
        float add5 = Game.add5 * min;
        float add6 = Game.add6 * min;

        //paint lines
        paint.setColor(0xFF000000);
        for (int i = 0; i < game.getHeight() + 1; i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                 horizontal = new Line(DirectionOfLine.HORIZONTAL, i, j);
                if (horizontal.equals(game.getLatestLine())) {
                    paint.setColor(R.color.lastline);
                } else if (game.isLineOccupied(horizontal)) {
                    if (game.getLineOccupier(horizontal) == 1)
                        paint.setColor(playerColors[0]);
                    else if (game.getLineOccupier(horizontal) == 2)
                        paint.setColor(playerColors[1]);
                    else if (game.getLineOccupier(horizontal) == 3)
                        paint.setColor(playerColors[2]);
                    else if (game.getLineOccupier(horizontal) == 4)
                        paint.setColor(playerColors[3]);

                } else {
                    paint.setColor(Color.WHITE);
                }
                fillpath.reset();
                fillpath.addRect(start + add5 * j + add1, start + add5 * i,
                        start + add5 * (j + 1), start + add5 * i + add1
                        , Path.Direction.CW);
                canvas.drawPath(fillpath, paint);
                paths.add(fillpath);
                 vertical = new Line(DirectionOfLine.VERTICAL, j, i);
                if (vertical.equals(game.getLatestLine())) {
                    paint.setColor(R.color.lastline);
                } else if (game.isLineOccupied(vertical)) {
                    if (game.getLineOccupier(vertical) == 1)
                        paint.setColor(playerColors[0]);
                    else if (game.getLineOccupier(vertical) == 2)
                        paint.setColor(playerColors[1]);
                    else if (game.getLineOccupier(horizontal) == 3)
                        paint.setColor(playerColors[2]);
                    else if (game.getLineOccupier(horizontal) == 4)
                        paint.setColor(playerColors[3]);
                } else {
                    paint.setColor(Color.WHITE);
                }


                fillpath.reset();
                fillpath.addRect(start + add5 * i, start + add5 * j
                        + add1, start + add5 * i + add1, start + add5
                        * (j + 1), Path.Direction.CW);
                canvas.drawPath(fillpath, paint);
                paths.add(fillpath);

            }
        }
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                paint.setColor(game.getBoxOccupier(j, i) == null ? Color.TRANSPARENT : playerBoxColors[Player.indexIn(game.getBoxOccupier(j, i), game.getPlayers())]);
                canvas.drawRect(start + add5 * i + add1 + add2, start
                        + add5 * j + add1 + add2, start + add5 * i + add1
                        + add4 - add2, start + add5 * j + add1 + add4
                        - add2, paint);
            }
        }
        paint.setColor(getResources().getColor(R.color.highlight));
        for (int i = 0; i < game.getHeight() + 1; i++) {
            for (int j = 0; j < game.getWidth() + 1; j++) {
                canvas.drawCircle(start + add6 + j * add5 + 1, start + add6 + i * add5 + 1,
                        radius, paint);
            }
        }

        invalidate();
    }

    public  void Undo(){
        paths.remove(paths.size() -1);
       game.removeLineOccupied();
        postInvalidate();
    }
    private void receiveInput(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return;

        float touchX = event.getX();
        float touchY = event.getY();
        int min = Math.min(getWidth(), getHeight());
        float start = Game.start * min;
        float add1 = Game.add1 * min;
        float add2 = Game.add2 * min;
        float add3 = Game.add3 * min;
        float add5 = Game.add5 * min;
        int d = -1, a = -1, b = -1;
        int gridsize = Integer.parseInt(GrideSize);
        for (int i = 0; i <gridsize+1; i++) {
            for (int j = 0; j < gridsize; j++) {


                final float v1 = start + add5 * j + add1 - add3;
                float v2 = start + add5 * i + add2 - add3;
                float v3 = start + add5 * i + add1 - add2 + add3;


                if (v1 <= touchX
                        && touchX <= (start + add5 * (j + 1) + add3)
                        && touchY >= v2
                        && touchY <= v3) {
                    d = 0;
                    a = i;
                    b = j;
                }
                if (v2 <= touchX
                        && touchX <= v3
                        && touchY >= v1
                        && touchY <= start + add5 * (j + 1) + add3) {
                    d = 1;
                    a = j;
                    b = i;
                }
            }
        }

        if ((a != -1) && (b != -1)) {
            DirectionOfLine directionOfLine;
            if (d == 0)
                directionOfLine = DirectionOfLine.HORIZONTAL;
            else
                directionOfLine = DirectionOfLine.VERTICAL;
            move = new Line(directionOfLine, a, b);
            try {
                ((Warrior) game.currentPlayer()).add(move);
            } catch (Exception e) {
                Log.e("Game", e.toString());
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        playersState.setCurrentPlayer(game.currentPlayer());
        Map<Player, Integer> player_occupyingBoxCount_map = new HashMap<>();
        for (Player player : game.getPlayers()) {
            player_occupyingBoxCount_map.put(player, game.getPlayerOccupyingBoxCount(player));
        }
        playersState.setPlayerOccupyingBoxesCount(player_occupyingBoxCount_map);

        Player winner = game.getWinner();
        if (winner != null) {
            playersState.setWinner(winner);
        }
    }
}
