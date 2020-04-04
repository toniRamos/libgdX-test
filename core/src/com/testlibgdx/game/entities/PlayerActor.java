package com.testlibgdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by antonio on 04/04/2020.
 */

public class PlayerActor  extends Actor{
    /** The player texture. */
    private Texture player;

    public PlayerActor(Texture player) {
        this.player = player;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(player, getX(), getY(), getWidth(), getHeight());
    }
    ;
}
