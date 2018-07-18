import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.Graphics;
import java.awt.Image;
import java.applet.Applet;
import java.awt.Graphics; 
import java.awt.Image; 
import java.io.File; 
import java.io.IOException; 
import javax.imageio.ImageIO; 

public class Powder extends Applet implements Runnable, KeyListener,MouseMotionListener, MouseListener{
    boolean[] keyDown;
    int mx,my;
    int b = 0;
    int scale = 2;
    boolean isPress = false;
    // boolean water;
    boolean perm = false;
    boolean water = false;
    Dimension dim;
    boolean erase = false;
    boolean alter = false;
    boolean button;
    Graphics bg;
    int m;
    int z = 0;
    Image offscreen;
    Thread thread;
    Sand[][] grid = new Sand[601][1001];

    public void init(){
        setSize(1000, 600);
        setBackground(Color.black);
        dim = getSize();
        addKeyListener(this);
        keyDown = new boolean[100];
        addMouseMotionListener(this);
        addMouseListener(this);
        
        for(int i = 0; i < 1001; i++){
            for(int j = 0; j <601 ; j++){
                grid[j][i] = new Sand();

            }
        }
        for(int i = 0; i < 1001; i++){
            for(int j = 598; j <601 ; j++){
                grid[j][i].a = true;
            }
        }
        
        offscreen = createImage(dim.width, dim.height);
        bg = offscreen.getGraphics();
    }

    public void start() {
        thread = new Thread(this); thread.start();
    } 

    public void update(Graphics g){
        paint(g);
        
        if(isPress && !button){
            if(water){
                grid[my][mx].type = 2;
                grid[my+1][mx].type = 2;
                grid[my+1][mx+1].type = 2;
                grid[my][mx+1].type = 2;
                grid[my-1][mx].type = 2;
                grid[my-1][mx-1].type = 2;
                grid[my][mx-1].type = 2;
                
                grid[my][mx].a = true;
                grid[my+1][mx].a = true;
                grid[my+1][mx+1].a = true;
                grid[my][mx+1].a = true;
                grid[my-1][mx].a = true;
                grid[my-1][mx-1].a = true;
                grid[my][mx-1].a = true;
            }
            if(perm){
                grid[my][mx].type = 1;
                grid[my+1][mx].type = 1;
                grid[my+1][mx+1].type = 1;
                grid[my][mx+1].type = 1;
                grid[my-1][mx].type = 1;
                grid[my-1][mx-1].type = 1;
                grid[my][mx-1].type = 1;
                
                grid[my][mx].a = true;
                grid[my+1][mx].a = true;
                grid[my+1][mx+1].a = true;
                grid[my][mx+1].a = true;
                grid[my-1][mx].a = true;
                grid[my-1][mx-1].a = true;
                grid[my][mx-1].a = true;
            }
            else if(erase){
                grid[my][mx].type = 0;
                grid[my+1][mx].type = 0;
                grid[my+1][mx+1].type = 0;
                grid[my][mx+1].type = 0;
                grid[my-1][mx].type = 0;
                grid[my-1][mx-1].type = 0;
                grid[my][mx-1].type = 0;
                
                grid[my][mx].a = false;
                grid[my+1][mx].a = false;
                grid[my+1][mx+1].a = false;
                grid[my][mx+1].a = false;
                grid[my-1][mx].a = false;
                grid[my-1][mx-1].a = false;
                grid[my][mx-1].a = false;
            }else{
                grid[my][mx].a = true;
                grid[my+1][mx].a = true;
                grid[my+1][mx+1].a = true;
                grid[my][mx+1].a = true;
                grid[my-1][mx].a = true;
                grid[my-1][mx-1].a = true;
                grid[my][mx-1].a = true;
            }
        }   
    }

    public void run(){

        while(true){    
            for(int i = 0; i < 600; i+=1){
                for(int j = 1; j <999; j+=2){
                    if(grid[i][j].alt == false){
                        if(grid[i][j].a == true && grid[i][j].type == 0){
                            int n = (int)(Math.random()*2);
                            if(grid[i+1][j].type == 2 && grid[i+1][j].a == true && n == 1){
                                grid[i+1][j].type = 0;
                                grid[i][j].type = 2;
                            }else{
                                int k = (int)(Math.random()*2);
                                if(grid[i+1][j+1].type == 2 && grid[i+1][j+1].a == true && k == 1){
                                    grid[i+1][j+1].type = 0;
                                    grid[i][j].type = 2;
                                }

                                else if(grid[i+1][j-1].type == 2 && grid[i+1][j-1].a == true){
                                    grid[i+1][j-1].type = 0;
                                    grid[i][j].type = 2;
                                }
                            }
                        }
                        if( grid[i][j].a == true && (grid[i][j].type == 0 || grid[i][j].type == 2)){
                            int n = Math.round((int)(Math.random()*2));
                            if(grid[i+1][j].a ==false && n==1){

                                grid[i][j].a =false;
                                grid[i+1][j].a =true;
                                grid[i+1][j].alt = true;
                                if(grid[i][j].type == 2){

                                    grid[i][j].type = 0;
                                    grid[i+1][j].type = 2;

                                }
                            }
                            else if(grid[i][j].type == 2){
                                int h = (int)(Math.random()*2);
                                if(h==1){
                                    if(grid[i][j+1].a == false){

                                        grid[i][j].a =false;
                                        grid[i][j+1].a =true;
                                        grid[i][j].type = 0;
                                        grid[i][j+1].type = 2;
                                        grid[i][j+1].alt = true;

                                    }
                                    else  if(grid[i][j-1].a == false){

                                        grid[i][j].a =false;
                                        grid[i][j-1].a =true;
                                        grid[i][j].type = 0;
                                        grid[i][j-1].type = 2;
                                        grid[i][j-1].alt = true;

                                    }

                                }
                                else{
                                    if(grid[i][j-1].a == false){

                                        grid[i][j].a =false;
                                        grid[i][j-1].a =true;
                                        grid[i][j].type = 0;
                                        grid[i][j-1].type = 2;
                                        grid[i][j-1].alt = true;

                                    }
                                    else if(grid[i][j+1].a == false){

                                        grid[i][j].a =false;
                                        grid[i][j+1].a =true;
                                        grid[i][j].type = 0;
                                        grid[i][j+1].type = 2;
                                        grid[i][j+1].alt = true;

                                    }

                                }
                            }
                            else{

                                int c = Math.round((int)(Math.random()*2));
                                if(c == 1){
                                    if(!grid[i+1][j-1].a){
                                        grid[i+1][j-1].a =true;
                                        grid[i][j].a =false;
                                        grid[i+1][j-1].alt = true;
                                        if(grid[i][j].type == 2){

                                            grid[i][j].type = 0;
                                            grid[i+1][j-1].type = 2;}
                                    }
                                    else if(!grid[i+1][j+1].a){
                                        grid[i+1][j+1].a = true;
                                        grid[i][j].a = false;grid[i+1][j+1].alt = true;
                                        if(grid[i][j].type == 2){

                                            grid[i][j].type = 0;
                                            grid[i+1][j+1].type = 2;}
                                    }
                                }

                                else{
                                    if(!grid[i+1][j+1].a){
                                        grid[i+1][j+1].a = true;
                                        grid[i][j].a = false;grid[i+1][j+1].alt = true;
                                        if(grid[i][j].type == 2){

                                            grid[i][j].type = 0;
                                            grid[i+1][j+1].type = 2;}
                                    }
                                    else
                                    if(!grid[i+1][j-1].a){
                                        grid[i+1][j-1].a =true;
                                        grid[i][j].a =false;
                                        grid[i+1][j-1].alt = true;
                                        if(grid[i][j].type == 2){

                                            grid[i][j].type = 0;
                                            grid[i+1][j-1].type = 2;}
                                    }
                                }
                            }

                        }
                        grid[i][j].alt = true;
                    }
                    else{
                        grid[i][j].alt = false;
                    }
                }

                for(int j = 2; j < 999; j+=2){
                    if(grid[i][j].alt == false){
                        if(grid[i][j].a == true && grid[i][j].type == 0){
                            int n = (int)(Math.random()*2);
                            if(grid[i+1][j].type == 2 && grid[i+1][j].a == true && n == 1){
                                grid[i+1][j].type = 0;
                                grid[i][j].type = 2;
                            }else{
                                int k = (int)(Math.random()*2);
                                if(grid[i+1][j+1].type == 2 && grid[i+1][j+1].a == true && k == 1){
                                    grid[i+1][j+1].type = 0;
                                    grid[i][j].type = 2;
                                }

                                else if(grid[i+1][j-1].type == 2 && grid[i+1][j-1].a == true){
                                    grid[i+1][j-1].type = 0;
                                    grid[i][j].type = 2;
                                }
                            }
                        }
                        if( grid[i][j].a == true && (grid[i][j].type == 0 || grid[i][j].type == 2)){
                            int n = Math.round((int)(Math.random()*2));
                            if(grid[i+1][j].a ==false && n==1){

                                grid[i][j].a =false;
                                grid[i+1][j].a =true;
                                grid[i+1][j].alt = true;
                                if(grid[i][j].type == 2){

                                    grid[i][j].type = 0;
                                    grid[i+1][j].type = 2;

                                }
                            }
                            else if(grid[i][j].type == 2){
                                int h = (int)(Math.random()*2);
                                if(h==1){
                                    if(grid[i][j+1].a == false){

                                        grid[i][j].a =false;
                                        grid[i][j+1].a =true;
                                        grid[i][j].type = 0;
                                        grid[i][j+1].type = 2;
                                        grid[i][j+1].alt = true;

                                    }
                                    else  if(grid[i][j-1].a == false){

                                        grid[i][j].a =false;
                                        grid[i][j-1].a =true;
                                        grid[i][j].type = 0;
                                        grid[i][j-1].type = 2;
                                        grid[i][j-1].alt = true;

                                    }

                                }
                                else{
                                    if(grid[i][j-1].a == false){

                                        grid[i][j].a =false;
                                        grid[i][j-1].a =true;
                                        grid[i][j].type = 0;
                                        grid[i][j-1].type = 2;
                                        grid[i][j-1].alt = true;

                                    }
                                    else if(grid[i][j+1].a == false){

                                        grid[i][j].a =false;
                                        grid[i][j+1].a =true;
                                        grid[i][j].type = 0;
                                        grid[i][j+1].type = 2;
                                        grid[i][j+1].alt = true;

                                    }

                                }
                            }
                            else{

                                int c = Math.round((int)(Math.random()*2));
                                if(c == 1){
                                    if(!grid[i+1][j-1].a){
                                        grid[i+1][j-1].a =true;
                                        grid[i][j].a =false;
                                        grid[i+1][j-1].alt = true;
                                        if(grid[i][j].type == 2){

                                            grid[i][j].type = 0;
                                            grid[i+1][j-1].type = 2;}
                                    }
                                    else if(!grid[i+1][j+1].a){
                                        grid[i+1][j+1].a = true;
                                        grid[i][j].a = false;grid[i+1][j+1].alt = true;
                                        if(grid[i][j].type == 2){

                                            grid[i][j].type = 0;
                                            grid[i+1][j+1].type = 2;}
                                    }
                                }

                                else{
                                    if(!grid[i+1][j+1].a){
                                        grid[i+1][j+1].a = true;
                                        grid[i][j].a = false;grid[i+1][j+1].alt = true;
                                        if(grid[i][j].type == 2){

                                            grid[i][j].type = 0;
                                            grid[i+1][j+1].type = 2;}
                                    }
                                    else
                                    if(!grid[i+1][j-1].a){
                                        grid[i+1][j-1].a =true;
                                        grid[i][j].a =false;
                                        grid[i+1][j-1].alt = true;
                                        if(grid[i][j].type == 2){

                                            grid[i][j].type = 0;
                                            grid[i+1][j-1].type = 2;}
                                    }
                                }
                            }

                        }
                        grid[i][j].alt = true;
                    }
                    else{
                        grid[i][j].alt = false;
                    }
                }

            }

            repaint();
            try{
                thread.sleep((1/50));
            }
            catch(InterruptedException e){
            }
        }
    }

    public void paint(Graphics g) {
        bg.clearRect(0,0,dim.width, dim.height);
        bg.setColor(Color.darkGray);
        if(perm)
            bg.setColor(Color.red);

        bg.fillRect(0, 0, 60, 60);
        bg.setColor(Color.blue);
        if(water)
            bg.setColor(Color.red);
        bg.fillRect(120, 0, 60, 60);
        bg.setColor(Color.cyan);
        if(erase)
            bg.setColor(Color.red);
        bg.fillRect(60, 0, 60, 60);
        // bg.fillRect(mx-1, my, 2, 100);
        for(int i = 0; i < 600; i++){
            for(int j = 1000; j > 0; j--){
                if(grid[i][j].a){
                    bg.setColor(new Color(255, 200, 0));
                    if(grid[i][j].type == 1)
                        bg.setColor(new Color(106, 106, 106));
                    else if(grid[i][j].type == 2)
                        bg.setColor(new Color(49, 185, 237));
                    bg.fillRect(j, i, 1, 1);

                }
            }

        }
        g.setColor(Color.white);
        
        Font f = new Font("impact", Font.BOLD,20) ;
        g.setFont(f);
        g.drawString("Fixed",5,20);
        g.drawString("Erase",65,20);
        g.drawString("Water",125,20);
        if(perm){
            g.drawString("ON",25,40);
        }
        if(erase){
            g.drawString("ON",85,40);
        }
        if(water){
            g.drawString("ON",145,40);
        }
        
        g.drawImage(offscreen, 0, 0, this);

    }

    public void keyPressed( KeyEvent e ){
        keyDown[e.getKeyCode()] = true;

    }

    public void keyTyped( KeyEvent e ){

    }

    public void keyReleased( KeyEvent e ){
        keyDown[e.getKeyCode()] = false;

    }

    public void mouseMoved( MouseEvent e ){
        mx = e.getX();
        my = e.getY();
        if(mx > 180 || my > 60){
            button = false;
        }
        else{
            button = true;
        }
    }

    public void mouseDragged( MouseEvent e ){
        //drag++;
        mx = e.getX();
        my = e.getY();
        if(mx > 180 || my > 60){
            button = false;
        }
        else{
            button = true;
        }
        if(button == false){
            if(water)
            {grid[my][mx].type = 2;
                grid[my+1][mx].type = 2;
                grid[my+1][mx+1].type = 2;
                grid[my][mx+1].type = 2;
                //grid[my][mx].type = 1;
                grid[my-1][mx].type = 2;
                grid[my-1][mx-1].type = 2;
                grid[my][mx-1].type = 2;
                grid[my][mx].a = true;
                grid[my+1][mx].a = true;
                grid[my+1][mx+1].a = true;
                grid[my][mx+1].a = true;
                //grid[my][mx].a = true;
                grid[my-1][mx].a = true;
                grid[my-1][mx-1].a = true;
                grid[my][mx-1].a = true;
            }
            if(perm)
            {grid[my][mx].type = 1;
                grid[my+1][mx].type = 1;
                grid[my+1][mx+1].type = 1;
                grid[my][mx+1].type = 1;
                //grid[my][mx].type = 1;
                grid[my-1][mx].type = 1;
                grid[my-1][mx-1].type = 1;
                grid[my][mx-1].type = 1;
                grid[my][mx].a = true;
                grid[my+1][mx].a = true;
                grid[my+1][mx+1].a = true;
                grid[my][mx+1].a = true;
                //grid[my][mx].a = true;
                grid[my-1][mx].a = true;
                grid[my-1][mx-1].a = true;
                grid[my][mx-1].a = true;
            }
            else if(erase){
                grid[my][mx].type = 0;
                grid[my+1][mx].type = 0;
                grid[my+1][mx+1].type = 0;
                grid[my][mx+1].type = 0;
                //grid[my][mx].type = 1;
                grid[my-1][mx].type = 0;
                grid[my-1][mx-1].type = 0;
                grid[my][mx-1].type = 0;
                grid[my][mx].a = false;
                grid[my+1][mx].a = false;
                grid[my+1][mx+1].a = false;
                grid[my][mx+1].a = false;
                //grid[my][mx].a = true;
                grid[my-1][mx].a = false;
                grid[my-1][mx-1].a = false;
                grid[my][mx-1].a = false;
            }else{
                grid[my][mx].a = true;
                grid[my+1][mx].a = true;
                grid[my+1][mx+1].a = true;
                grid[my][mx+1].a = true;
                //grid[my][mx].a = true;
                grid[my-1][mx].a = true;
                grid[my-1][mx-1].a = true;
                grid[my][mx-1].a = true;}
        }
    }

    public void mouseClicked( MouseEvent e ){
        mx = e.getX();
        my = e.getY();
        if(mx > 180 || my > 60){
            button = false;
        }
        else{
            button = true;
        }
        if(button == false){
            if(water)
            {grid[my][mx].type = 2;
                grid[my+1][mx].type = 2;
                grid[my+1][mx+1].type = 2;
                grid[my][mx+1].type = 2;
                //grid[my][mx].type = 1;
                grid[my-1][mx].type = 2;
                grid[my-1][mx-1].type = 2;
                grid[my][mx-1].type = 2;
                grid[my][mx].a = true;
                grid[my+1][mx].a = true;
                grid[my+1][mx+1].a = true;
                grid[my][mx+1].a = true;
                //grid[my][mx].a = true;
                grid[my-1][mx].a = true;
                grid[my-1][mx-1].a = true;
                grid[my][mx-1].a = true;
            }
            if(perm)
            {grid[my][mx].type = 1;
                grid[my+1][mx].type = 1;
                grid[my+1][mx+1].type = 1;
                grid[my][mx+1].type = 1;
                //grid[my][mx].type = 1;
                grid[my-1][mx].type = 1;
                grid[my-1][mx-1].type = 1;
                grid[my][mx-1].type = 1;
                grid[my][mx].a = true;
                grid[my+1][mx].a = true;
                grid[my+1][mx+1].a = true;
                grid[my][mx+1].a = true;
                //grid[my][mx].a = true;
                grid[my-1][mx].a = true;
                grid[my-1][mx-1].a = true;
                grid[my][mx-1].a = true;
            }
            else if(erase){
                grid[my][mx].type = 0;
                grid[my+1][mx].type = 0;
                grid[my+1][mx+1].type = 0;
                grid[my][mx+1].type = 0;
                //grid[my][mx].type = 1;
                grid[my-1][mx].type = 0;
                grid[my-1][mx-1].type = 0;
                grid[my][mx-1].type = 0;
                grid[my][mx].a = false;
                grid[my+1][mx].a = false;
                grid[my+1][mx+1].a = false;
                grid[my][mx+1].a = false;
                //grid[my][mx].a = true;
                grid[my-1][mx].a = false;
                grid[my-1][mx-1].a = false;
                grid[my][mx-1].a = false;
            }else{
                grid[my][mx].a = true;
                grid[my+1][mx].a = true;
                grid[my+1][mx+1].a = true;
                grid[my][mx+1].a = true;
                //grid[my][mx].a = true;
                grid[my-1][mx].a = true;
                grid[my-1][mx-1].a = true;
                grid[my][mx-1].a = true;}
        }
        if(mx < 60 && my < 60 && !water && !erase){
            perm = !perm;
        }
        if(mx < 120 && my < 60 && mx > 60 && !water && !perm){
            erase = !erase;
        }
        if(mx < 180 && my < 60 && mx > 120 && !perm && !erase){
            water = !water;
        }

    }

    public void mousePressed( MouseEvent e ){
        isPress = true;

    }

    public void mouseEntered( MouseEvent e ){
        //drag++;
        //mx = e.getX();
        //my = e.getY();
        // grid[my][mx] = true;

    }

    public void mouseExited( MouseEvent e ){
        // grid[my][mx] = true;

    }

    public void mouseReleased( MouseEvent e ){
        // grid[my][mx] = true;
        isPress = false;

    }
}

class Sand{
    boolean a = false;//exists on screen
    boolean alt = false;//checked in current sweep across screen
    int type = 0;//default sand, water, cement, or erase
    public void d(){
        a = false;
        type = 0;
    }
}

   