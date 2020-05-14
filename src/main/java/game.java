import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

public class game {
    public static float rotX;
    public static float rotY;
    public static ArrayList<triangle> tris = new ArrayList<>();
    public static ArrayList<square> squares = new ArrayList<>();
    protected static ArrayList<String> keys = new ArrayList<String>();
    protected static float fpsCap = (1000);
    protected static Color colo = new Color(0, 0, 0);
    static display board = new display();
    static String dir = System.getProperty("user.dir") + File.separatorChar;
    static Random rand = new Random();
    static boolean sort = false;
    static keyListen keyInput = new keyListen();
    static float camX = 0;
    static float camY = 0;
    static float camZ = 0;
    
    public static void main(String[] args) {
        board.setup();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        String F = dir + "\\imgs\\" + "0x0_invis";
        ImageIcon ico = new ImageIcon(F);
        Image img = ico.getImage();
        Cursor c = toolkit.createCustomCursor(img, new Point(10, 10), "Hidden");
        
        F = game.dir + "\\imgs\\terrain\\" + "gravel.png";
        ico = new ImageIcon(F);
        img = ico.getImage();
        Color col = null;
        float[] x;
        float[] y;
        float[] z;

        /*x = new int[4];
        x[0]=-10;
        x[1]=10;
        x[2]=-10;
        x[3]=-10;
        y = new int[4];
        y[0]=10;
        y[1]=10;
        y[2]=10;
        y[3]=10;
        z = new int[4];
        z[0]=10;
        z[1]=-10;
        z[2]=-10;
        z[3]=10;
        col = new Color(0,255,0,0);
        tris.add(new triangle(x,y,z,img,col));

        x = new int[4];
        x[0]=-10;
        x[1]=10;
        x[2]=10;
        x[3]=-10;
        y = new int[4];
        y[0]=10;
        y[1]=10;
        y[2]=10;
        y[3]=10;
        z = new int[4];
        z[0]=10;
        z[1]=10;
        z[2]=-10;
        z[3]=10;
        col = new Color(255, 251,0,0);
        tris.add(new triangle(x,y,z,img,col));

        x = new int[4];
        x[0]=-10;
        x[1]=10;
        x[2]=-10;
        x[3]=-10;
        y = new int[4];
        y[0]=-10;
        y[1]=-10;
        y[2]=-10;
        y[3]=-10;
        z = new int[4];
        z[0]=10;
        z[1]=-10;
        z[2]=-10;
        z[3]=10;
        col = new Color(0, 26, 255,0);
        tris.add(new triangle(x,y,z,img,col));

        x = new int[4];
        x[0]=-10;
        x[1]=10;
        x[2]=10;
        x[3]=-10;
        y = new int[4];
        y[0]=-10;
        y[1]=-10;
        y[2]=-10;
        y[3]=-10;
        z = new int[4];
        z[0]=10;
        z[1]=10;
        z[2]=-10;
        z[3]=10;
        col = new Color(255, 0, 243,0);
        tris.add(new triangle(x,y,z,img,col));*/
        
        ArrayList<Integer> xPoints = new ArrayList<>();
        ArrayList<Integer> yPoints = new ArrayList<>();
        ArrayList<Integer> zPoints = new ArrayList<>();

        /*for (int X=0; X<=160; X++)
        {
            for (int Z=0; Z<=160; Z++)
            {
                xPoints.add(X*1);
                zPoints.add(Z*1);
                yPoints.add((int)((Math.cos(X)*Math.sin(Z))*10));
                board.draw();
            }
        }*/
        
        float Xpoint = 0;
        float Ypoint = 0;
        
        //for (int zPos=0; zPos<=16; zPos+=0)
        //{
        
        int zPos = 0;
        int position = 0;
        boolean back = false;
        boolean firstRun = true;
        
        //int tri=0;
        
        int sizeX = (32);
        int sizeZ = (32);
        
        float Zpoint = sizeZ * -0.5f;
        
        int square = 0;
        
        ArrayList<triangle> lastZ = new ArrayList<>();
        for (int pointZ = 0; pointZ <= sizeZ; pointZ += 1) {
            Xpoint = sizeX * -0.5f;
            for (int point = 0; point <= sizeX; point += 1) {
                x = new float[4];
                y = new float[4];
                z = new float[4];
                
                x[0] = Xpoint;
                x[1] = Xpoint;
                Xpoint += 1;
                x[2] = Xpoint;
                x[3] = Xpoint;
                
                z[0] = Zpoint;
                z[1] = Zpoint + 1;
                z[2] = Zpoint + 1;
                z[3] = Zpoint;
                
                if (square == 0) {
                    y[0] = 0;
                    y[1] = 0;
                    Ypoint = rand.nextInt(3) - 1.5f;
                    y[2] = Ypoint;
                    y[3] = Ypoint;
                } else if (point == sizeX + 1) {
                    float[] y2 = squares.get(square - sizeX - 1).PointsY;
                    float[] y3 = squares.get(square - 1).PointsY;
                    y[0] = y2[1];
                    Ypoint = rand.nextInt(3) - 1.5f;
                    y[1] = y2[0] + Ypoint;
                    y[2] = y2[0] + Ypoint;
                    //y2 = squares.get(square - 2).PointsY;
                    y[3] = y2[2];
                } else if (point == 0) {
                    float[] y2 = squares.get(square - sizeX - 1).PointsY;
                    float[] y3 = squares.get(square - 1).PointsY;
                    y[0] = y2[1];
                    Ypoint = rand.nextInt(3) - 1.5f;
                    y[1] = y2[1] + Ypoint;
                    y[2] = y2[2] + Ypoint;
                    //y2 = squares.get(square - 2).PointsY;
                    y[3] = y2[2];
                } else if (square >= sizeX + 1) {
                    float[] y2 = squares.get(square - sizeX - 1).PointsY;
                    float[] y3 = squares.get(square - 1).PointsY;
                    y[0] = y2[1];
                    Ypoint = rand.nextInt(3) - 1.5f;
                    y[1] = y3[2];
                    y[2] = ((y2[2] + Ypoint) + y3[3]) / 2;
                    //y2 = squares.get(square - 2).PointsY;
                    y[3] = y2[2];
                } else {
                    float[] y2 = squares.get(square - 1).PointsY;
                    y[0] = y2[3];
                    y[1] = y2[2];
                    Ypoint += rand.nextInt(3) - 1.5f;
                    y[2] = Ypoint;
                    y[3] = Ypoint;
                }
                
                //col = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
                col = new Color(0x0A4400);
                Color col2 = new Color(0x218300);
                
                squares.add(new square(x, y, z, img, col, img, col2));
                
                square += 1;
            }
            Zpoint += 1;
            Ypoint = 0;
        }
        
        sort = true;

        /*zPos+=2;
        Xpoint=0;

        ArrayList<triangle> lastZ2 = new ArrayList<>();

        for (int point=0; point<=sizeX; point+=1)
        {
            Zpoint = zPos;

            x = new float[4];
            x[0] = Xpoint;
            y = new float[4];
            //Ypoint = tris.get(point).getPointsY()[0];
            y[0] = Ypoint;
            z = new float[4];
            z[0] = Zpoint;

            Xpoint += 2;
            x[1] = Xpoint;
            //Xpoint += 1;
            x[2] = Xpoint;
            x[3] = 0;

            //Ypoint = tris.get(point).getPointsY()[0];
            //y[1] = Ypoint;
            //Ypoint = tris.get(point).getPointsY()[0];
            y[2] = Ypoint;
            y[3] = 0;

            y[0]=lastZ.get(point).getPointsY()[1];
            y[1]=lastZ.get(point).getPointsY()[2];
            y[2]=lastZ.get(point).getPointsY()[2];

            z[1] = Zpoint;
            z[2] = Zpoint - 2;
            z[3] = 0;

            col = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
            //col = new Color(255, 255, 255);

            lastZ2.add(new triangle(x, y, z, img, col));
            //tris.add(new triangle(x, y, z, img, col));
        }*/
        
        zPos -= 2;

        /*for (int zItterator=0; zItterator<=sizeZ; zItterator+=1)
        {
            Xpoint=0;
            for (int point=0; point<=sizeX; point+=1)
            {
                //tri+=1;
                Zpoint=zPos;

                x = new float[4];
                x[0]=Xpoint;
                y = new float[4];
                z = new float[4];
                z[0]=Zpoint;

                //Xpoint+=1;
                x[1]=Xpoint;
                Xpoint+=2;
                x[2]=Xpoint;
                x[3]=0;

                try {
                    Ypoint=lastZ2.get(point).getPointsY()[1];
                    y[0]=Ypoint;
                    Ypoint=lastZ2.get(point).getPointsY()[0];
                    y[1]=Ypoint;
                    Ypoint=lastZ2.get(point-1).getPointsY()[2];
                    //Ypoint+=(rand.nextInt(10)-5);
                    y[2]=Ypoint;
                    y[3]=0;
                } catch (IndexOutOfBoundsException e) {
                    Ypoint=lastZ2.get(point).getPointsY()[1];
                    y[0]=Ypoint;
                    Ypoint=lastZ2.get(point).getPointsY()[1];
                    y[1]=Ypoint;
                    Ypoint=lastZ2.get(point).getPointsY()[2];
                    //Ypoint+=(rand.nextInt(10)-5);
                    y[2]=Ypoint;
                    y[3]=0;
                }

                z[1]=Zpoint+(2);
                z[2]=Zpoint;
                z[3]=0;

                col = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                //col = new Color(0,0,0);

                lastZ.set(point, new triangle(x, y, z, img, col));
                tris.add(new triangle(x,y,z,img,col));

            }

            zPos+=2;
            Xpoint=0;

            for (int point=0; point<=sizeX; point+=1)
            {
                Zpoint = zPos;

                //tri+=1;

                x = new float[4];
                x[0] = Xpoint;
                y = new float[4];
                //Ypoint = tris.get(point).getPointsY()[0];
                y[0] = Ypoint;
                z = new float[4];
                z[0] = Zpoint;

                Xpoint += 2;
                x[1] = Xpoint;
                //Xpoint += 1;
                x[2] = Xpoint;
                x[3] = 0;

                //Ypoint = tris.get(point).getPointsY()[0];
                //y[1] = Ypoint;
                //Ypoint = tris.get(point).getPointsY()[0];
                y[2] = Ypoint;
                y[3] = 0;

                y[0]=lastZ.get(point).getPointsY()[1];
                y[2]=lastZ.get(point).getPointsY()[2];
                try {
                    y[1]=lastZ.get(point+1).getPointsY()[1]+1;
                } catch (IndexOutOfBoundsException e) {
                    y[1]=lastZ.get(point).getPointsY()[1];
                }

                z[1] = Zpoint;
                z[2] = Zpoint - 2;
                z[3] = 0;

                //col = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
                col = new Color(255, 255, 255);

                lastZ2.add(new triangle(x, y, z, img, col));
                tris.add(new triangle(x, y, z, img, col));
            }
        }*
         */
        
        System.out.println(tris.size());

        /*try {
            for(int point=0; point<=(zPoints.size()-2);point+=10)
            {
                int Color=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)).getRGB();
                int point2 = xPoints.indexOf((xPoints.get(point)+10));
                x = new int[4];
                y = new int[4];
                z = new int[4];

                //x[0]=xPoints.get(point);
                //x[1]=xPoints.get(point2);
                //x[2]=xPoints.get(point2);
                //x[3]=xPoints.get(point);

                //y[0]=yPoints.get(point);
                //y[1]=yPoints.get(point2);
                //y[2]=yPoints.get(point2);
                //y[3]=yPoints.get(point);

                //z[0]=zPoints.get(point);
                //z[1]=zPoints.get(point2);
                //z[2]=zPoints.get(point2);
                //z[3]=zPoints.get(point);

                x[0]=xPoints.get(point);
                x[1]=xPoints.get(point)+10;
                x[2]=xPoints.get(point)+10;
                x[3]=xPoints.get(point);

                z[0]=zPoints.get(point)+10;
                z[1]=zPoints.get(point)+10;
                z[2]=zPoints.get(point);
                z[3]=zPoints.get(point);

                y[0]=yPoints.get(point);
                y[1]=yPoints.get(point+1);
                y[2]=yPoints.get(point+2);
                y[3]=yPoints.get(point);

                col = new Color(Color);
                tris.add(new triangle(x,y,z,img,col));

                try {
                    x[0]=xPoints.get(point)+10;
                    x[1]=xPoints.get(point);
                    x[2]=xPoints.get(point);
                    x[3]=xPoints.get(point);

                    z[0]=zPoints.get(point);
                    z[1]=zPoints.get(point);
                    z[2]=zPoints.get(point)+10;
                    z[3]=zPoints.get(point);

                    y[0]=yPoints.get(point);
                    y[1]=yPoints.get(point-1);
                    y[2]=yPoints.get(point-2);
                    y[3]=yPoints.get(point);

                    col = new Color(Color);
                    tris.add(new triangle(x,y,z,img,col));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("OutOfBounds");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("OutOfBounds");
        }*/
        
        System.out.println(tris.size());
        
        //board.setCursor(c);
        
        board.addKeyListener(keyInput);
        board.addWindowListener(new WindowListener() {
            @Override
            public void windowActivated(WindowEvent windowEvent) {
                System.out.println("I am back!");
            }
            
            @Override
            public void windowClosed(WindowEvent windowEvent) {
            
            }
            
            @Override
            public void windowDeactivated(WindowEvent windowEvent) {
                System.out.println("Bring me back to the front!!!");
            }
            
            @Override
            public void windowDeiconified(WindowEvent windowEvent) {
                System.out.println("Hello again!");
            }
            
            @Override
            public void windowIconified(WindowEvent windowEvent) {
                System.out.println("Did I lose your attention?");
            }
            
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                System.out.println("Hello world!");
            }
            
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                board.open = false;
                Runtime.getRuntime().runFinalization();
                board.dispose();
                System.out.println("Process exited with code 0-Good to go!");
                Runtime.getRuntime().exit(0);
            }
        });
        
        while (board.open) {
            Point pos = MouseInfo.getPointerInfo().getLocation();
            rotX = pos.x / -40f;
            rotY = pos.y / 40f;
            board.repaint(0);
            try {
                Robot r = new Robot();
                colo = r.getPixelColor(pos.x, pos.y);
            } catch (AWTException e) {
            }

            /*try
            {
                for (int keyIndex = 0; keyIndex <= keys.size()-1; keyIndex++)
                {
                    if (keys.get(keyIndex).equals("keyText=W")) {
                        camX += Math.cos((rotX + rotY) / 2);
                        camZ += Math.sin((rotX + rotY) / 2);
                        camY += Math.cos((rotY) / 2);
                        System.out.println("L");
                    }
                    if (keys.contains("s"))
                    {
                        camX -= Math.cos((rotX + rotY) / 2);
                        camZ -= Math.sin((rotX + rotY) / 2);
                        camY -= Math.cos((rotY) / 2);
                    }
                }
            } catch (ConcurrentModificationException e) {}*/
            
            if (keys.contains("keyCode=87")) {
                camX -= 0.01;
            }
            
            if (keys.contains("keyCode=83")) {
                camX += 0.01;
            }
            
            if (keys.contains("keyCode=65")) {
                camY -= 0.01;
            }
            
            if (keys.contains("keyCode=68")) {
                camY += 0.01;
            }
            
            if (keys.contains("keyCode=81")) {
                camZ -= 0.01;
            }
            
            if (keys.contains("keyCode=69")) {
                camZ += 0.01;
            }
            
            if (camZ >= 0)
                camZ = 0;
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
