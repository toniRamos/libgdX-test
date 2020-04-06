/*
 * This file is part of Jump Don't Die.
 * Copyright (C) 2015 Dani Rodríguez <danirod@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.testlibgdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.testlibgdx.game.entities.PlayerActor;


/**
 * This is the main screen for the game. All the fun happen here.
 */
public class ExampleMoveActorScreen extends BaseScreen {

    /** Stage instance for Scene2D rendering. */
    private Stage stage;

    /** Initial position of the camera. Required for reseting the viewport. */
    private Vector3 position;

    private PlayerActor actor;

    private TextureRegion texturePlayer;

    /**
     * Create the screen. Since this constructor cannot be invoked before libGDX is fully started,
     * it is safe to do critical code here such as loading assets and setting up the stage.
     * @param game
     */
    public ExampleMoveActorScreen(MainGame game) {
        super(game);

        // Create a new Scene2D stage for displaying things.
        stage = new Stage(new FitViewport(640, 360));
        position = new Vector3(stage.getCamera().position);

        Texture tmp = game.getManager().get("daxbotsheet_alone.png");
        texturePlayer = new TextureRegion(tmp);
        texturePlayer.getRegionHeight();

    }

    /**
     * This method will be executed when this screen is about to be rendered.
     * Here, I use this method to set up the initial position for the stage.
     */
    @Override
    public void show() {
        stage.getCamera().position.set(position);
        stage.getCamera().update();

        actor = new PlayerActor(texturePlayer);
        actor.setSize(texturePlayer.getRegionWidth(), texturePlayer.getRegionHeight());
        actor.setPosition(0, 0);

        stage.addActor(actor);
    }

    /**
     * This method will be executed when this screen is no more the active screen.
     * I use this method to destroy all the things that have been used in the stage.
     */
    @Override
    public void hide() {
        stage.clear();
    }

    /**
     * This method is executed whenever the game requires this screen to be rendered. This will
     * display things on the screen. This method is also used to update the game.
     */
    @Override
    public void render(float delta) {
        // Do not forget to clean the screen.
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            actor.moveBy(-10,0);
        }else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            actor.moveBy(10,0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            actor.moveBy(0,10);
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            actor.moveBy(0,-10);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            actor.setPosition(0, 0);
            actor.addAction(new Actions().fadeIn(0));
        }

        //Move
        if(Gdx.input.isKeyPressed(Input.Keys.M)) {
            actor.addAction(new Actions().moveTo(150,150,1));
        }

        //Fade in and fade out
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if(actor.isVisible()){
                actor.addAction(
                    new Actions().sequence(
                        new Actions().fadeOut(0.5f),
                        new Actions().visible(false)
                    )
                );
            } else {
                actor.addAction(
                    new Actions().sequence(
                        new Actions().visible(true),
                        new Actions().fadeIn(0.5f)
                    )
                );
            }
        }

        //Touch screen o touche mouse move actor
        if(Gdx.input.isTouched()) {
            actor.addAction(
                new Actions().moveTo(
                        Gdx.input.getX()-actor.getWidth()/2,
                        Gdx.graphics.getHeight()-Gdx.input.getY()-(actor.getHeight()*1.25f),
                        1)
            );
        }

        // Update the stage. This will update the player speed.
        stage.act();

        // Render the screen. Remember, this is the last step!
        stage.draw();
    }

    /**
     * This method is executed when the screen can be safely disposed.
     * I use this method to dispose things that have to be manually disposed.
     */
    @Override
    public void dispose() {
        // Dispose the stage to remove the Batch references in the graphics card.
        stage.dispose();
    }

}
