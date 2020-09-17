/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybirds;

import java.awt.Rectangle;
import java.io.File;
import pkg2dgamesframework.*;

/**
 *
 * @author FPT SHOP
 */
public class Bird extends Objects{
    
    private float vt = 0;
    private boolean isflying = false;
    private Rectangle rect; 
    private boolean islive = true;
    public SoundPlayer flapSound, bupSound, getMoneySound;

    public boolean getIslive() {
        return islive;
    }

    public void setIslive(boolean islive) {
        //if(this.islive && !islive)  bupSound.play();
        this.islive = islive;
    }
    
    public Bird(int x, int y, int w, int h){
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);
        
        flapSound = new SoundPlayer(new File("Assets/flap.wav"));
        bupSound = new SoundPlayer(new File("Assets/bup.wav"));
        getMoneySound = new SoundPlayer(new File("Assets/getmoney.wav"));
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setVt(float vt) {
        this.vt = vt;
    }
    
    public void update(long deltaTime){
        vt+= FlappyBird.g;
        this.setPosY(this.getPosY()+vt);
        this.rect.setLocation((int)this.getPosX(), (int)this.getPosY());
        if(vt < 0){
            isflying = true;
        }
        else{
            isflying = false;
        }
    }
    
    public void fly(){
        vt = -5;
        flapSound.play();
    }

    public boolean isIsflying() {
        return isflying;
    }
}
