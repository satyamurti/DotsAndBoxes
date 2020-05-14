package com.mrspd.dotsandboxes.Ui;

import com.mrspd.dotsandboxes.Models.Player;

import java.util.Map;

public interface PlayersStateView {
    void setCurrentPlayer(Player player);

    void setPlayerOccupyingBoxesCount(Map<Player, Integer> player_occupyingBoxesCount_map);

    void setWinner(Player winner);
}
