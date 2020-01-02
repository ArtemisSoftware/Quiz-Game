package com.titan.quizgame.sliders.models;

public class Slider {

    private String text;
    private int colorActive, colorInactive, backgroundColor;
    private int screen;

    public Slider(String text, int colorActive, int colorInactive, int screen, int backgroundColor) {
        this.text = text;
        this.colorActive = colorActive;
        this.colorInactive = colorInactive;
        this.screen = screen;
        this.backgroundColor = backgroundColor;
    }

    public String getText() {
        return text;
    }

    public int getBackGround() {
        return backgroundColor;
    }

    public int getColorActive() {
        return colorActive;
    }

    public int getColorInactive() {
        return colorInactive;
    }

    public int getScreen() {
        return screen;
    }
}
