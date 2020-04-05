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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.testlibgdx.game.entities.PlayerActor;


/**
 * This is the main screen for the game. All the fun happen here.
 */
public class ExampleTextureRegionAnimationScreen extends BaseScreen {

    /** Stage instance for Scene2D rendering. */
    private Stage stage;

    /** Initial position of the camera. Required for reseting the viewport. */
    private Vector3 position;

    private PlayerActor actor;

    private TextureRegion texturePlayerNoMoved;

    private static final int lengthFramesActor = 6;

    private Texture allTextures;

    private TextureRegion[] actorFramesRight, actorFramesLeft;
    private Animation actorAnimationRight, actorAnimationLeft;

    private float duration;

    private int movement = 3;

    /**
     * Create the screen. Since this constructor cannot be invoked before libGDX is fully started,
     * it is safe to do critical code here such as loading assets and setting up the stage.
     * @param game
     */
    public ExampleTextureRegionAnimationScreen(MainGame game) {
        super(game);

        // Create a new Scene2D stage for displaying things.
        stage = new Stage(new FitViewport(640, 360));
        position = new Vector3(stage.getCamera().position);

        allTextures = game.getManager().get("daxbotsheet.png");

        TextureRegion[][] temp = TextureRegion.split(allTextures,
                allTextures.getWidth() / 4,
                allTextures.getHeight() / 4);

        actorFramesRight = new TextureRegion[lengthFramesActor];

        int count = 0;

        for (int row = 0; row < temp.length - 2; row++ ){
            for (int column = 1; column <= temp[row].length - 1; column++ ){
                actorFramesRight[count] = temp[row][column];
                count++;
            }
        }

        actorAnimationRight = new Animation<TextureRegion>(0.25f, actorFramesRight);

        temp = TextureRegion.split(allTextures,
                allTextures.getWidth() / 4,
                allTextures.getHeight() / 4);

        actorFramesLeft = new TextureRegion[lengthFramesActor];

        count = 0;
        for (int row = 0; row < temp.length - 2; row++ ){
            for (int column = 1; column <= temp[row].length - 1; column++ ){
                TextureRegion textureTmp = temp[row][column];
                textureTmp.flip(true, false);
                actorFramesLeft[count] = textureTmp;
                count++;
            }
        }

        actorAnimationLeft = new Animation<TextureRegion>(0.25f, actorFramesLeft);

        texturePlayerNoMoved = new TextureRegion((Texture) game.getManager().get("daxbotsheet_alone.png"));
        texturePlayerNoMoved.getRegionHeight();

        duration = 0f;
    }

    /**
     * This method will be executed when this screen is about to be rendered.
     * Here, I use this method to set up the initial position for the stage.
     */
    @Override
    public void show() {
        stage.getCamera().position.set(position);
        stage.getCamera().update();

        actor = new PlayerActor(texturePlayerNoMoved);
        actor.setSize(texturePlayerNoMoved.getRegionWidth(), texturePlayerNoMoved.getRegionHeight());
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


        if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            actor.setTexture(texturePlayerNoMoved);
            duration = 0;
        }else {
            duration += delta;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            actor.setTexture((TextureRegion) actorAnimationLeft.getKeyFrame(duration, true));
            actor.moveBy(-movement,0);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            actor.setTexture((TextureRegion) actorAnimationRight.getKeyFrame(duration, true));
            actor.moveBy(movement,0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            actor.moveBy(0,movement);
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            actor.moveBy(0,-movement);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            actor.setPosition(0, 0);
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
        allTextures.dispose();
    }
}
