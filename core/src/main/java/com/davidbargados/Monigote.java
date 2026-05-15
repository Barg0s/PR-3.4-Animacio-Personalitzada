package com.davidbargados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

    public class Monigote {

        private float x, y;
        private float speed;
        private float stateTime;
        private Texture walkSheet;

        private Animation<TextureRegion> animAbajo;
        private Animation<TextureRegion> animArriba;
        private Animation<TextureRegion> animIzquierda;
        private Animation<TextureRegion> animDerecha;
        private Animation<TextureRegion> animActual;

        public Monigote(String path, float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.stateTime = 0f;

            this.walkSheet = new Texture(Gdx.files.internal(path));
            this.animAbajo     = crearAnimacion(0, 40, 48);
            this.animDerecha = crearAnimacion(2, 40, 48); // Fila 2: Izquierda
            this.animArriba    = crearAnimacion(4, 40, 48); // Fila 4: Arriba
            this.animIzquierda   = crearAnimacion(6, 40, 48); // Fila 6: Derecha

            this.animActual = animAbajo;
        }

        private Animation<TextureRegion> crearAnimacion(int fila, int ancho, int alto) {
            TextureRegion[] frames = new TextureRegion[4];
            for (int i = 0; i < 4; i++) {
                frames[i] = new TextureRegion(walkSheet, i * ancho, fila * alto, ancho, alto);
            }
            return new Animation<>(0.15f, frames);
        }


        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public void setStateTime(float stateTime) {
            this.stateTime = stateTime;
        }

        public void setAnimActual(Animation<TextureRegion> anim) {
            this.animActual = anim;
        }

        public float getX(){
            return x;
        }

        public float getY(){
            return y;
        }

        public float getSpeed(){
            return speed;
        }

        public float getStateTime(){
            return stateTime;
        }

        public Animation<TextureRegion> getAnimAbajo(){
            return animAbajo;
        }

        public Animation<TextureRegion> getAnimArriba(){
            return animArriba;
        }

        public Animation<TextureRegion> getAnimIzquierda(){
            return animIzquierda;
        }

        public Animation<TextureRegion> getAnimDerecha(){
            return animDerecha;
        }

        public Animation<TextureRegion> getAnimActual(){
            return animActual;
        }

    }