
package snakegame;

import java.lang.Math;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class board extends JPanel implements ActionListener{
    private final int TOTAL_DOTS=30;
    private final int DOT_SIZE=10;
    
    private int apple_x;
    private int apple_y;
    private final int[] x=new int[TOTAL_DOTS];
    private final int[] y=new int[TOTAL_DOTS];
    private int dots;
    private Image Apple;
    private Image Dot;
    private Image Head;
    private Timer timer;
    
    private boolean inGame=true;
    private boolean leftDirection=false;   
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;

    
    board(){
       setPreferredSize(new Dimension(300,300));
       setBackground(Color.BLACK);
       setFocusable(true);
       
       LoadImages();
       initGame();
       addKeyListener(new TAdapter());
    }
    
    public void LoadImages(){
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        Apple=i1.getImage();
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        Dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        Head=i3.getImage();
    }
    
    public void initGame(){
        dots=3;
        for(int i=0;i<dots;i++){
           y[i]=50;
           x[i]=50-i*DOT_SIZE;
        }
        
        locateApple();
        
        timer=new Timer(140,this);
        timer.start();
        
    }
    
    public void locateApple(){
        int r=(int)(Math.random()*29);
         apple_x=r*DOT_SIZE;
             
         r=(int)(Math.random()*29);
         apple_y=r*DOT_SIZE;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);       
    }
    
    public void draw(Graphics g){
        if(inGame){
           g.drawImage(Apple,apple_x,apple_y,this);
        for(int i=0;i<dots;i++){
            if(i==0){
                g.drawImage(Head, x[i], y[i], this);
            }
            else{
                g.drawImage(Dot,x[i],y[i],this);
            }
        }
        
        Toolkit.getDefaultToolkit().sync();  
        }
        
        else{
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g){
        String msg="Game Over!";
        Font font=new Font("SAN SERIF", Font.BOLD,14);
        FontMetrics metrices= getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300-metrices.stringWidth(msg))/2, 300/2);
    }
    
    public void checkCollision(){
      for(int i=dots-1; i>0 ;i--){
          if((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
              inGame=false;
          }
      }
      
      if(x[0]>300 || x[0]<0 || y[0]>300 || y[0]<0){
          inGame=false;
      }
      
      if(!inGame){
          timer.stop();
      }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(inGame){
        checkCollision();
        checkApple();
        move();
        }
        repaint();
    }
    
    public void checkApple(){
        if((x[0]==apple_x) && (y[0]==apple_y)){
            dots++;
            locateApple();
        }
    }
    
    public void move(){
        for(int i=dots-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection){
           x[0]=x[0]-DOT_SIZE;
        }
        if(rightDirection){
           x[0]=x[0]+DOT_SIZE;
        }
        if(upDirection){
        y[0]=y[0]-DOT_SIZE;
        }
        if(downDirection){
        y[0]=y[0]+DOT_SIZE;
        }
    }
    
    public class TAdapter extends KeyAdapter{   
        @Override
        public void keyPressed(KeyEvent ke){
            int key= ke.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
               leftDirection=true;
               upDirection=false;
               downDirection=false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
               rightDirection=true;
               upDirection=false;
               downDirection=false;
            }
            if(key == KeyEvent.VK_UP && (!downDirection)){
               upDirection=true;
               leftDirection=false;
               rightDirection=false;
            }
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
               downDirection=true;
               leftDirection=false;
               rightDirection=false;
            }
        }
        
    }
    
}