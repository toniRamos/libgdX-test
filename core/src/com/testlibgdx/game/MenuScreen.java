/*
 * This file is part of Jump Don't Die
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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * This is the screen that you see when you enter the game. It has a button for playing the game.
 * When you press this button, you go to the game screen so that you can start to exampleMoveActor. This
 * screen was done by copying the code from GameOverScreen. All the cool comments have been
 * copy-pasted.
 */
public class MenuScreen extends BaseScreen {

    /** The stage where all the buttons are added. */
    private Stage stage;

    /** The skin that we use to set the style of the buttons. */
    private Skin skin;

    /** The exampleMoveActor button you use to jump to the game screen. */
    private TextButton exampleMoveActor, exampleTextureRegionScreen;

    private ScrollPane scrollPane;
    private Table scrollTable;

    public MenuScreen(final MainGame game) {
        super(game);
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);
        final Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        //Label
        final Label labelButton = new Label("Example move a actor ->  ", skin);
        final Label labelButton2 = new Label("Example texture region animation ->  ", skin);

        //Buttons
        exampleMoveActor = new TextButton("Play", skin);
        // Add capture listeners. Capture listeners have one method, changed, that is executed
        // when the button is pressed or when the user interacts somehow with the widget. They are
        // cool because they let you execute some code when you press them.
        exampleMoveActor.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Take me to the game screen!
                game.setScreen(game.exampleMoveActorScreen);
            }
        });

        exampleTextureRegionScreen = new TextButton("Play", skin);
        // Add capture listeners. Capture listeners have one method, changed, that is executed
        // when the button is pressed or when the user interacts somehow with the widget. They are
        // cool because they let you execute some code when you press them.
        exampleTextureRegionScreen.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Take me to the game screen!
                game.setScreen(game.exampleTextureRegionAnimationScreen);
            }
        });

        final Table scrollTable = new Table();
        scrollTable.row();
        scrollTable.add(labelButton);
        scrollTable.add(exampleMoveActor);
        scrollTable.row();
        scrollTable.add(labelButton2);
        scrollTable.add(exampleTextureRegionScreen);

        final ScrollPane scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();

        this.stage.addActor(table);
    }

    @Override
    public void show() {
        // Now this is important. If you want to be able to click the button, you have to make
        // the Input system handle input using this Stage. Stages are also InputProcessors. By
        // making the Stage the default input processor for this game, it is now possible to
        // click on buttons and even to type on input fields.
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        // When the screen is no more visible, you have to remember to unset the input processor.
        // Otherwise, input might act weird, because even if you aren't using this screen, you are
        // still using the stage for handling input.
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        // Dispose assets.
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.3f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
