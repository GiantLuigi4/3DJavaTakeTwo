import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;

public class display extends JFrame {
    public static BufferedImage textures = null;
    //    private static can canv = new can();
    protected static float frameRate;
    private static display storage = null;
    public boolean open = true;
    
    //    private static class can extends JComponent
//    {
    public void paint(Graphics g) {
        //g.setColor(new Color(0, 158, 208));
        
        frameRate += 1;
        //System.out.println(frameRate);
        if (frameRate >= 1000 - game.fpsCap) {
            textures = new BufferedImage(game.board.getWidth(), game.board.getHeight(), BufferedImage.TYPE_INT_RGB);
            BufferedImage bimig = new BufferedImage(game.board.getWidth(), game.board.getHeight(), BufferedImage.TYPE_INT_RGB);
            
            Graphics bim = bimig.getGraphics();
            bim.setColor(new Color(0, 158, 208));
            bim.fillRect(0, 0, game.board.getWidth(), game.board.getHeight());

                /*//BufferedImage img=null;
                try {
                    img = new Robot().createScreenCapture(game.board.getBounds());
                } catch (AWTException e)
                {}

                g.drawImage(img.getScaledInstance(img.getWidth(),img.getHeight(),0),0,0,null);*/
            //g.fillRect(0,0,game.board.getWidth(),game.board.getHeight());

                /*try {
                    for (triangle tri : game.tris) {
                        triangle draw = tri;
                        draw.getDrawPos(game.rotX, game.rotY, 0, 0, 0);
                        draw.drawTri(g);
                    }
                }catch(ConcurrentModificationException e)
                {

                }*/
            
            //TODO:viewplane
            
            try {
                if (game.sort) {
                    for (int i = 0; i <= game.squares.size() - 1; i += 1) {
                        for (int index = 1; index <= game.squares.size() - 1; index += 1) {
                            square squ = game.squares.get(index);
                            square squ2 = game.squares.get(index - 1);
                            squ.getDistanceFromCam(game.camX, game.camY, game.camZ);
                            squ2.getDistanceFromCam(game.camX, game.camY, game.camZ);
                            if (squ.distanceTotal < squ2.distanceTotal) {
                                game.squares.set(index - 1, squ);
                                game.squares.set(index, squ2);
                            } else {
                                game.squares.set(index - 1, squ2);
                                game.squares.set(index, squ);
                            }
                        }
                    }
                }
                
                ArrayList<Float> angles1 = new ArrayList<Float>();
                ArrayList<Float> angles2 = new ArrayList<Float>();
                for (int index = 0; index < game.squares.size(); index++) {
                    angles1.add((game.squares.get(index).getAngle1(game.camX, game.camY, game.camZ) / 1000f));
                    angles2.add((game.squares.get(index).getAngle2(game.camX, game.camY, game.camZ) / 1000f));
                    //game.squares.get(index).setShading(new Color(((int)(1/angles1.get((index*1))))),new Color(((int)(1/angles2.get((index*1))))));
                    game.squares.get(index).setShading(new Color(0, 0, 0, 0), new Color(0, 0, 0, 0));
                    
                    //int angle1 = (int)(angles1.get(index).floatValue());
                    //int angle2 = (int)(angles2.get(index).floatValue());
                    if (index != angles1.indexOf(angles1.get(index))
                            || index != angles2.indexOf(angles2.get(index))
                    ) {
                        //game.squares.get(index).setShading(new Color(0x4C007A),new Color(0xFDF8FF));
                        game.squares.get(index).setShading(new Color(0, 0, 0, (255 / 2)), new Color(0, 0, 0, (255 / 2)));
                    }
                }
                
                angles1.clear();
                angles2.clear();
                for (int index = 0; index < game.squares.size(); index++) {
                    angles1.add((game.squares.get(index).getAngle1Invert(game.camX, game.camY, game.camZ) / 1f));
                    angles2.add((game.squares.get(index).getAngle2Invert(game.camX, game.camY, game.camZ) / 1f));
                    //game.squares.get(index).setShading(new Color(((int)(1/angles1.get((index*1))))),new Color(((int)(1/angles2.get((index*1))))));
                    //game.squares.get(index).setShading(new Color(0,0,0,0),new Color(0,0,0,0));
                    
                    //int angle1 = (int)(angles1.get(index).floatValue());
                    //int angle2 = (int)(angles2.get(index).floatValue());
                    if (index != angles1.indexOf(angles1.get(index))
                            || index != angles2.indexOf(angles2.get(index))
                    ) {
                        //game.squares.get(index).setShading(new Color(0x4C007A),new Color(0xFDF8FF));
                        game.squares.get(index).setShading(new Color(0, 0, 0, (255 / 2)), new Color(0, 0, 0, (255 / 2)));
                    }
                }
                
                for (square squ : game.squares) {
                    //game.rotY=0;
                    squ.setShading(new Color(128, 0, 0, 255), new Color(0, 128, 0, 255));
                    square draw = squ;
                    //draw.getDrawPos(game.rotX, game.rotY, 0, 0, 0);
                    draw.drawSquare(bimig, game.rotX, game.rotY, game.camX, game.camY, game.camZ);
                }
                //bimig.getGraphics().drawImage(textures.getScaledInstance(textures.getWidth(),textures.getHeight(),BufferedImage.SCALE_FAST),0,0,null);
            } catch (ConcurrentModificationException e) {
            
            }
            
            //g.drawString(""+game.rotX,0,12);
            //g.drawString(""+game.rotY,0,24);
            
            //System.out.println(game.board.getHeight()/(Runtime.getRuntime().totalMemory()/Runtime.getRuntime().freeMemory()));

                /*try {
                    triangle tri = game.tris.get(0);
                    tri.getDrawPos(game.rotX,game.rotY,0,0,0);
                    tri.drawTri(g);
                    tri = game.tris.get(1);
                    tri.getDrawPos(game.rotX,game.rotY,0,0,0);
                    tri.drawTri(g);
                }catch (IndexOutOfBoundsException e) {
                    System.out.println("OutOfBounds");
                }*/
//                g.dispose();

//                g.setColor(new Color(0,0,255));
//                g.fillRect(9,0,10, game.board.getHeight());
//                g.setColor(game.colo);
//                g.fillRect(11,2,6, (game.board.getHeight()/(int)(Runtime.getRuntime().totalMemory()/Runtime.getRuntime().freeMemory()))-8);
//                g.setColor(new Color(0, 158, 208));
            g.drawImage(bimig.getScaledInstance(game.board.getWidth(), game.board.getHeight(), BufferedImage.SCALE_SMOOTH), 0, 0, null);
            
            frameRate = 0;
        }
    }
//    }
    
    public void setup() {
//        this.add(this.canv);
        this.setSize(800, 800);
        this.setLocation(0, 0);
        this.validate();
        this.setVisible(true);
    }
    
    public void draw() {
//        canv.repaint();
    }
}
