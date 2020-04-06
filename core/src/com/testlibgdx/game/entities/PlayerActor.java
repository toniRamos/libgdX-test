package com.testlibgdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by antonio on 04/04/2020.
 */

public class PlayerActor  extends Actor{
    /** The player texture. */
    private TextureRegion player;

    public PlayerActor(TextureRegion player) {
        this.player = player;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(this.getColor());
        batch.draw(this.player, getX(), getY(), getWidth(), getHeight());
    }

    public void setTexture (TextureRegion texture){
        this.player = texture;
    }
}
