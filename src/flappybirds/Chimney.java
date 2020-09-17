/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybirds;

import java.awt.Rectangle;
import pkg2dgamesframework.*;

/**
 *
 * @author FPT SHOP
 */
public class Chimney extends Objects{
    private Rectangle rect;
    private boolean isBehindBird = false;

    public Chimney() {
    }

    public Chimney(int x, int y, int w, int h) {
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);
    }

    public boolean isIsBehindBird() {
        return isBehindBird;
    }

    public void setIsBehindBird(boolean isBehindBird) {
        this.isBehindBird = isBehindBird;
    }
    
    public void update(){
        setPosX(getPosX()-2);
        rect.setLocation((int)getPosX(), (int)getPosY());
    }

    public Rectangle getRect() {
        return rect;
    }
}
