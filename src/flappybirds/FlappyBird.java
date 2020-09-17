/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybirds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import pkg2dgamesframework.*;

/**
 *
 * @author FPT SHOP
 */
public class FlappyBird extends GameScreen{
    private BufferedImage birdimage;
    private Animation bird_anim;
    private Bird bird;
    private Ground ground;
    private ChimneyGroup chimneygroup;
    
    public static float g = 0.22f;
    private final int BEGIN_SCREEN = 0;
    private final int GAMEPLAY_SCREEN = 1;
    private final int GAMEOVER_SCREEN = 2;
    private int CURRENT_SCREEN = BEGIN_SCREEN;
    private int Point = 0;
    
    public FlappyBird(){
        super(800, 600);
        try {
            birdimage = ImageIO.read(new File("Assets/bird_sprite.png"));
        } catch (IOException ex) {
            Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bird_anim = new Animation(70);
        AFrameOnImage f;
        f = new AFrameOnImage(0, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(120, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        bird_anim.AddFrame(f);
        
        bird = new Bird(350, 250, 50, 50);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                switch(CURRENT_SCREEN){
                    case BEGIN_SCREEN:{
                        CURRENT_SCREEN = GAMEPLAY_SCREEN;
                        break;
                    }
                    case GAMEPLAY_SCREEN:{
                        if(bird.getIslive()) bird.fly();
                        break;
                    }
                    case GAMEOVER_SCREEN:{
                        CURRENT_SCREEN = BEGIN_SCREEN;
                        break;
                    }
                    default:break;
                }
            }
        });
        
        ground = new Ground();
        chimneygroup = new ChimneyGroup();
        BeginGame();
    }
    
    private void resetGame(){
        bird.setPos(350, 250);
        bird.setVt(0);
        bird.setIslive(true);
        Point = 0;
        chimneygroup.reset();
    }

    @Override
    public void GAME_UPDATE(long deltaTime) {
        switch(CURRENT_SCREEN){
            case BEGIN_SCREEN:{
                resetGame();
                break;
            }
            case GAMEPLAY_SCREEN:{
                if(bird.getIslive())    bird_anim.Update_Me(deltaTime);
                bird.update(deltaTime);
                ground.update();
                chimneygroup.update();
                for(int i = 0; i < ChimneyGroup.Size; ++i){
                    if(bird.getRect().intersects(chimneygroup.getChimney(i).getRect())){
                        if(bird.getIslive()) bird.bupSound.play();
                        bird.setIslive(false);
                        
                        //CURRENT_SCREEN = GAMEOVER_SCREEN;
                    }
                    
                    if(i % 2 == 0){
                        if(bird.getIslive()&&bird.getPosX() > (chimneygroup.getChimney(i).getPosX()+bird.getW()) && !chimneygroup.getChimney(i).isIsBehindBird()){
                            ++Point;
                            bird.getMoneySound.play();
                            chimneygroup.getChimney(i).setIsBehindBird(true);
                        }
                    }
                }
                
                if(bird.getPosY() + bird.getH() > ground.getY1()) CURRENT_SCREEN = GAMEOVER_SCREEN;
                break;
            }
            case GAMEOVER_SCREEN:{
                break;
            }
            default: break;
        }
        
    }

    @Override
    public void GAME_PAINT(Graphics2D g2) {
        g2.setColor(Color.decode("#b8daef"));
        g2.fillRect(0, 0, MASTER_WIDTH, MASTER_HEIGHT);
        chimneygroup.paint(g2);
        ground.Paint(g2);
        
        if(bird.isIsflying()) bird_anim.PaintAnims((int)bird.getPosX(), (int)bird.getPosY(), birdimage, g2, 0, 100);
        else{
            bird_anim.PaintAnims((int)bird.getPosX(), (int)bird.getPosY(), birdimage, g2, 0, 0);
        }
        
        
        switch(CURRENT_SCREEN){
            case BEGIN_SCREEN:{
                g2.setColor(Color.red);
                g2.drawString("Press space to play game", 200, 300);
                break;
            }
            case GAMEOVER_SCREEN:{
                g2.setColor(Color.red);
                g2.drawString("Press space to turn back begin screen", 200, 300);
                break;
            }
            default: break;
        }
        
        g2.setColor(Color.red);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Point: "  +Point, 20, 50);
    }

    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        if(Event == KEY_PRESSED){
            switch(CURRENT_SCREEN){
                case BEGIN_SCREEN:{
                    CURRENT_SCREEN = GAMEPLAY_SCREEN;
                    break;
                }
                case GAMEPLAY_SCREEN:{
                    if(bird.getIslive()) bird.fly();
                    break;
                }
                case GAMEOVER_SCREEN:{
                    CURRENT_SCREEN = BEGIN_SCREEN;
                    break;
                }
                default:break;
            }
        }
    }
    
    public static void main(String[] args){
        new FlappyBird();
    }

    
}
