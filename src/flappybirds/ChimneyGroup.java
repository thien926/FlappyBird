/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybirds;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pkg2dgamesframework.*;

/**
 *
 * @author FPT SHOP
 */
public class ChimneyGroup {
    private QueueList<Chimney> chimneys;
    private BufferedImage chimneyImage, chimneyImage2;
    public static int Size = 6;
    private int topY = -365;
    private int bottomY = 220;

    public ChimneyGroup(QueueList<Chimney> chimneys) {
        this.chimneys = chimneys;
    }
    
    public Chimney getChimney(int i){
        return  chimneys.get(i);
    }
    
    private int getRandomY(){
        Random random = new Random();
        int a;
        do{
            a = random.nextInt(8);
        }
        while(a < 1);
        return a*35;
    }

    public ChimneyGroup() {
        try {
            chimneyImage = ImageIO.read(new File("Assets/chimney.png"));
            chimneyImage2= ImageIO.read(new File("Assets/chimney_.png"));
        } catch (IOException ex) {
            Logger.getLogger(ChimneyGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
        reset();
    }
    
    public void reset(){
        chimneys = new QueueList<Chimney>();
        
        Chimney cn;
            
        for(int i = 0; i < Size/2; ++i){
            int deltaY = getRandomY();
            cn = new Chimney(830+i*300, bottomY+deltaY, 74, 400);
            chimneys.push(cn);
            
            cn = new Chimney(830+i*300, topY+deltaY, 74, 400);
            chimneys.push(cn);
        }
    }
    
    public void update(){
        for(int i = 0; i < Size; i++){
            chimneys.get(i).update();
        }
        
        if(chimneys.get(0).getPosX() < -74){
            Chimney cn;
            int deltaY = getRandomY();
            cn = chimneys.pop();
            cn.setPosX(chimneys.get(4).getPosX()+300);
            cn.setPosY(bottomY+deltaY);
            cn.setIsBehindBird(false);
            chimneys.push(cn);
            System.out.print("Denta: "+deltaY+" Dưới: " +cn.getPosY()+ " Trên: ");
            
            cn = chimneys.pop();
            cn.setPosX(chimneys.get(4).getPosX());
            cn.setPosY(topY+deltaY);
            cn.setIsBehindBird(false);
            chimneys.push(cn);
            System.out.println(cn.getPosY()+ " ");
        }
    }
    
    public void paint(Graphics2D g2){
        for(int i = 0; i < 6; ++i){
            if(i % 2 == 0) g2.drawImage(chimneyImage, (int)chimneys.get(i).getPosX(), (int)chimneys.get(i).getPosY(), null);
            else g2.drawImage(chimneyImage2, (int)chimneys.get(i).getPosX(), (int)chimneys.get(i).getPosY(), null);
        }
    }
    
}
