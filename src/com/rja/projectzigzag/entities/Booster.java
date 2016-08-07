package com.rja.projectzigzag.entities;

import com.ra4king.gameutils.gameworld.GameComponent;

import java.awt.*;

/**
 * Created by Jacob on 8/6/2016.
 */
public class Booster extends GameComponent {
    private double acceleration;
    private double fuel;
    private boolean isEnabled;

    public Booster(double acceleration) {
        super(5, 5, 100, 100);
        this.acceleration = acceleration;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void updateFuel(double newFuel) { fuel += newFuel; }

    public void setEnabled () { isEnabled = true; }

    @Override
    public void update(long deltaTime) {

    }

    @Override
    public void draw(Graphics2D g) {
        if(isEnabled) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString("BOOSTER FUEL: " + String.format("%.02f", fuel), 5, 15);
        isEnabled = false;
    }
}