package com.davidbargados;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main implements ApplicationListener {

    private SpriteBatch batch;
    private Texture walkSheet;
    private FitViewport viewport;

    private float stateTime;

    // Direcciones
    private static final int IDLE = 0;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;

    private Rectangle up, down, left, right;

    Monigote monigote;

    @Override
    public void create() {
        viewport = new FitViewport(800, 500);
        batch = new SpriteBatch();

        monigote = new Monigote("Walk-Anim.png", 100, 100, 200f);
        stateTime = 0f;

        float w = viewport.getWorldWidth();
        float h = viewport.getWorldHeight();
        up = new Rectangle(0, h * 2f / 3f, w, h / 3f);
        down = new Rectangle(0, 0, w, h / 3f);
        left = new Rectangle(0, 0, w / 3f, h);
        right = new Rectangle(w * 2f / 3f, 0, w / 3f, h);
    }

    protected int virtual_joystick_control() {
        // iterar per multitouch
        // cada "i" és un possible "touch" d'un dit a la pantalla
        for(int i=0;i<10;i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                // traducció de coordenades reals (depen del dispositiu) a 800x480
                viewport.getCamera().unproject(touchPos);
                if (up.contains(touchPos.x, touchPos.y)) {
                    return UP;
                } else if (down.contains(touchPos.x, touchPos.y)) {
                    return DOWN;
                } else if (left.contains(touchPos.x, touchPos.y)) {
                    return LEFT;
                } else if (right.contains(touchPos.x, touchPos.y)) {
                    return RIGHT;
                }
            }
        return IDLE;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        float delta = Gdx.graphics.getDeltaTime();
        int dir = virtual_joystick_control();

        float posX = monigote.getX();
        float posY = monigote.getY();
        float tiempo = monigote.getStateTime();
        float vel = monigote.getSpeed();

        switch (dir) {
            case UP:
                monigote.setY(posY + vel * delta);
                monigote.setAnimActual(monigote.getAnimArriba());
                monigote.setStateTime(tiempo + delta);
                break;
            case DOWN:
                monigote.setY(posY - vel * delta);
                monigote.setAnimActual(monigote.getAnimAbajo());
                monigote.setStateTime(tiempo + delta);
                break;
            case LEFT:
                monigote.setX(posX - vel * delta);
                monigote.setAnimActual(monigote.getAnimIzquierda());
                monigote.setStateTime(tiempo + delta);
                break;
            case RIGHT:
                monigote.setX(posX + vel * delta);
                monigote.setAnimActual(monigote.getAnimDerecha());
                monigote.setStateTime(tiempo + delta);
                break;
            case IDLE:
                monigote.setStateTime(0);
                break;
        }

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        TextureRegion frame = monigote.getAnimActual().getKeyFrame(monigote.getStateTime(), true);

        batch.draw(frame, monigote.getX(), monigote.getY(), 160, 192);

        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        walkSheet.dispose();
    }
}