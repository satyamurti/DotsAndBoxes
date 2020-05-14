package com.mrspd.dotsandboxes.Ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mrspd.dotsandboxes.R;
import com.mrspd.dotsandboxes.ComputerBrain.ComputerPlay;
import com.mrspd.dotsandboxes.Models.Player;
import com.mrspd.dotsandboxes.Models.Warrior;

import java.util.ArrayList;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class GameActivity extends AppCompatActivity implements PlayersStateView {
    protected Game game;
    private ImageView playerprofile;
    protected TextView playernaame, occupiedBoxes;
    Player[] players;
    Integer p1, p2, p3, p4;
    String playerName1, playerName2, playerName3, playerName4, GridSize, Mode;
    ArrayList<Integer> PlayerProfiles;
    Integer[] playersOccupying;
    Integer playerNum = -1, Hack = -1;
    Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        game = (Game) findViewById(R.id.gameView);
        game.setPlayersState((PlayersStateView) this);
        Intent intent = getIntent();
        PlayerProfiles = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        p1 = bundle.getInt("profileId1");
        p2 = bundle.getInt("profileId2");
        p3 = bundle.getInt("profileId3");
        p4 = bundle.getInt("profileId4");
        playerNum = bundle.getInt("PlayerNum");
        Mode = bundle.getString("Mode");
        GridSize = bundle.getString("GridSize");
        playerName1 = bundle.getString("player1Name");
        playerName2 = bundle.getString("player2Name");
        playerName3 = bundle.getString("player3Name");
        playerName4 = bundle.getString("player4Name");
        //link  ids
        playernaame = (TextView) findViewById(R.id.tvName);
        playerprofile = (ImageView) findViewById(R.id.igPlayerProfile);
        occupiedBoxes = (TextView) findViewById(R.id.occupiedBoxes);
        startGame();


    }

    private void startGame() {
        Log.d("MrSPd", "startGame: " + Mode + " ");
        if (Mode.equals("Solo")) {
            players = new Player[]{new Warrior(playerName1), new ComputerPlay("CompGuru", GridSize)};
            playersOccupying = new Integer[]{0, 0};
            game.startGame(players, Mode, GridSize, playerNum);
            updateState();
        } else {
            switch (playerNum) {
                case 2:
                    players = new Player[]{new Warrior(playerName1), new Warrior(playerName2)};
                    playersOccupying = new Integer[]{0, 0};
                    Hack = 1;

                    break;
                case 3:
                    players = new Player[]{new Warrior(playerName1), new Warrior(playerName2), new Warrior(playerName3)};
                    playersOccupying = new Integer[]{0, 0, 0};
                    Hack = 2;

                    break;
                default:
                    players = new Player[]{new Warrior(playerName1), new Warrior(playerName2), new Warrior(playerName3), new Warrior(playerName4)};
                    playersOccupying = new Integer[]{0, 0, 0, 0};
                    Hack = 3;

                    break;
            }
            game.startGame(players, Mode, GridSize, playerNum);
            updateState();
        }
    }

    public void updateState() {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (Hack == 1) {
                    if (currentPlayer == players[0]) {
                        playernaame.setText(playerName1);
                        playerprofile.setImageResource(p1);
                        occupiedBoxes.setText("" + playersOccupying[0]);
                    } else if (currentPlayer == players[1]) {
                        playernaame.setText(playerName2);
                        playerprofile.setImageResource(p2);
                        occupiedBoxes.setText("" + playersOccupying[1]);
                    } else {
                        playernaame.setText(playerName1);
                        playerprofile.setImageResource(p1);
                    }
                } else if (Hack == 2) {
                    if (currentPlayer == players[0]) {
                        playernaame.setText(playerName1);
                        playerprofile.setImageResource(p1);
                        occupiedBoxes.setText("" + playersOccupying[0]);
                    } else if (currentPlayer == players[1]) {
                        playernaame.setText(playerName2);
                        playerprofile.setImageResource(p2);
                        occupiedBoxes.setText("" + playersOccupying[1]);
                    } else if (currentPlayer == players[2]) {
                        playernaame.setText(playerName3);
                        playerprofile.setImageResource(p3);
                        occupiedBoxes.setText("" + playersOccupying[2]);
                    } else {
                        playernaame.setText(playerName1);
                        playerprofile.setImageResource(p1);
                    }
                } else if (Hack == 3) {
                    if (currentPlayer == players[0]) {
                        playernaame.setText(playerName1);
                        playerprofile.setImageResource(p1);
                        occupiedBoxes.setText("" + playersOccupying[0]);
                    } else if (currentPlayer == players[1]) {
                        playernaame.setText(playerName2);
                        playerprofile.setImageResource(p2);
                        occupiedBoxes.setText("" + playersOccupying[1]);
                    } else if (currentPlayer == players[2]) {
                        playernaame.setText(playerName3);
                        playerprofile.setImageResource(p3);
                        occupiedBoxes.setText("" + playersOccupying[2]);
                    } else if (currentPlayer == players[3]) {
                        playernaame.setText(playerName3);
                        playerprofile.setImageResource(p4);
                        occupiedBoxes.setText("" + playersOccupying[3]);
                    } else {
                        playernaame.setText(playerName1);
                        playerprofile.setImageResource(p1);
                    }
                }

            }
        });
    }

    @Override
    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
        updateState();
    }

    @Override
    public void setPlayerOccupyingBoxesCount(Map<Player, Integer> player_occupyingBoxesCount_map) {
        if (Hack == 1) {
            playersOccupying[0] = (player_occupyingBoxesCount_map.get(players[0]));
            playersOccupying[1] = (player_occupyingBoxesCount_map.get(players[1]));
        } else if (Hack == 2) {
            playersOccupying[0] = (player_occupyingBoxesCount_map.get(players[0]));
            playersOccupying[1] = (player_occupyingBoxesCount_map.get(players[1]));
            playersOccupying[2] = (player_occupyingBoxesCount_map.get(players[2]));
        } else if (Hack == 3) {
            playersOccupying[0] = (player_occupyingBoxesCount_map.get(players[0]));
            playersOccupying[1] = (player_occupyingBoxesCount_map.get(players[1]));
            playersOccupying[2] = (player_occupyingBoxesCount_map.get(players[2]));
            playersOccupying[3] = (player_occupyingBoxesCount_map.get(players[3]));
        }
        updateState();
    }

    @Override
    public void setWinner(final Player winner) {
        runOnUiThread(new Runnable() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void run() {
                Toasty.normal(GameActivity.this, "Game Over!!!! \uD83D\uDE0E ").show();
                Bundle bundle = new Bundle();

                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                bundle.putString("winnername", winner.getName());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });
    }

    public void Undo(View view) {
        game.Undo();
    }
}