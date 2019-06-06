package com.sport.supernathral.Utils;

import com.sport.supernathral.DataModel.GameData;

public class GameItem extends ListItem {

    private GameData gameData;


    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public int getType() {
        return TYPE_GAMES;
    }

}
