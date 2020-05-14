import org.w3c.dom.css.Rect;

import javax.tools.JavaCompiler;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class triangle {
    public Color shade = null;
    private float[] pointsX = new float[4];
    private float[] pointsY = new float[4];
    private float[] pointsZ = new float[4];
    private Image texture = null;
    private Color col = null;
    private BufferedImage bufferedTexture = null;
    private double rotation = 0;
    private double averageX = 0;
    private double averageY = 0;
    private double averageZ = 0;
    
    private boolean onScreen = true;
    
    private double averageOnScreenX = 0;
    private double averageOnScreenY = 0;
    
    private int[] drawPointsX = new int[4];
    private int[] drawPointsY = new int[4];
    private boolean shouldDraw = true;
    
    public triangle(float[] pointsX, float[] pointsY, float[] pointsZ, Image texture, Color shade) {
        this.pointsY = pointsX;
        this.pointsZ = pointsY;
        this.pointsX = pointsZ;
        this.texture = texture;
        this.col = shade;
        try {
            bufferedTexture = toBufferedImage(texture);
        } catch (NullPointerException e) {
            bufferedTexture = null;
        }
        //bufferedTexture = new BufferedImage(texture.getWidth(null), texture.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    }
    
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        
        // Return the buffered image
        return bimage;
    }
    
    public float[] getPointsY() {
        return pointsZ;
    }
    
    public float[] getPointsX() {
        return pointsY;
    }
    
    public float[] getPointsZ() {
        return pointsX;
    }
    
    public void getDrawPos(float rotX, float rotY, float camX, float camY, float camZ) {
        double cosRotX = Math.cos(rotX);
        double sinRotX = Math.sin(rotX);
        double cosRotY = Math.cos(rotY);
        double sinRotY = Math.sin(rotY);
        
        float offX = game.board.getWidth() / 2;
        float offY = game.board.getHeight() / 2;
        
        averageX = pointsX[0] + pointsX[1] + pointsX[2];
        averageY = pointsY[0] + pointsY[1] + (pointsY[2] * -1);
        averageZ = pointsZ[0] + pointsZ[1] + pointsZ[2];
        
        float X = pointsX[0];
        float Y = pointsY[0];
        float Z = pointsZ[0];
        
        //Robot r = new Robot().createScreenCapture();
        
        int AMP = 30;
        
        double posX = getX(X, Y, Z, camX, camY, camZ, sinRotX, sinRotY, cosRotY, cosRotX, AMP);
        double posY = getY(X, Y, Z, camX, camY, camZ, sinRotX, sinRotY, cosRotY, cosRotX, AMP);
        //double posX = getXAngle(X,Y,Z,rotX);
        //double posY = getYAngle(X,Y,Z,rotX);
        posX += offX;
        posY += offY;
        
        drawPointsX[0] = (int) posX;
        drawPointsY[0] = (int) posY;
        
        X = pointsX[1];
        Y = pointsY[1];
        Z = pointsZ[1];
        double posX2 = getX(X, Y, Z, camX, camY, camZ, sinRotX, sinRotY, cosRotY, cosRotX, AMP);
        double posY2 = getY(X, Y, Z, camX, camY, camZ, sinRotX, sinRotY, cosRotY, cosRotX, AMP);
        //double posX2 = getXAngle(X,Y,Z,rotX);
        //double posY2 = getYAngle(X,Y,Z,rotX);
        posX2 += offX;
        posY2 += offY;
        drawPointsX[1] = (int) posX2;
        drawPointsY[1] = (int) posY2;
        
        rotation = Math.atan2(posY2 - posY, posX2 - posX);
        
        X = pointsX[2];
        Y = pointsY[2];
        Z = pointsZ[2];
        posX = getX(X, Y, Z, camX, camY, camZ, sinRotX, sinRotY, cosRotY, cosRotX, AMP);
        posY = getY(X, Y, Z, camX, camY, camZ, sinRotX, sinRotY, cosRotY, cosRotX, AMP);
        //posX = getXAngle(X,Y,Z,rotX);
        //posY = getYAngle(X,Y,Z,rotX);
        posX += offX;
        posY += offY;
        drawPointsX[2] = (int) posX;
        drawPointsY[2] = (int) posY;
        
        X = (int) averageX;
        Y = (int) averageY;
        Z = (int) averageZ;
        posX = getX(X, Y, Z, camX, camY, camZ, sinRotX, sinRotY, cosRotY, cosRotX, AMP);
        posY = getY(X, Y, Z, camX, camY, camZ, sinRotX, sinRotY, cosRotY, cosRotX, AMP);
        //posX = getXAngle(X,Y,Z,rotX);
        //posY = getYAngle(X,Y,Z,rotX);
        //posX+=offX;
        posY += offY;
        averageOnScreenX = posX;
        averageOnScreenY = posY;

        /*if((
                !((drawPointsX[0]+drawPointsX[1]+drawPointsX[2])/3<=game.board.getWidth()&&
                (drawPointsX[0]+drawPointsX[1]+drawPointsX[2])/3>=0))
                || !shouldDraw)
        {
            onScreen=false;
        } else {
            onScreen=true;
        }*/
        
        drawPointsX[3] = drawPointsX[0];
        drawPointsY[3] = drawPointsY[0];
    }
    
    private int getX(float X, float Y, float Z, float camX, float camY, float camZ, double sinRotX, double sinRotY, double cosRotY, double cosRotX, float AMP) {
        float X2 = X - camY;
        float Y2 = Y - camX;
        float Z2 = (1 / (Z - camZ)) * 200;
        
        
        float scale = 0;
        try {
            scale = 1 - (1 / Z2);
        } catch (ArithmeticException e) {
        }
        
        float avgPos = (X2 + Y2 + Z2) / 3;
        float avgPosHoriz = (X2 + Z2) / 2;
        float amp = 1;
        try {
            amp = (1 / (1 - (1 / Z2))) * ((float) (cosRotY));
        } catch (ArithmeticException e) {
        }
        X -= camX;
        Y -= camY;
        Z += camZ;
        
        //int point = (int)Math.floor(((((cosRotX) * (X+amp)) - (sinRotX * Y)))*scale * AMP);
        //point += (int)Math.floor(((((cosRotX) * X) - (sinRotX * Y))) * AMP);
        //point += (int)Math.floor(((((cosRotX) * X) - (sinRotX * Y))) * AMP);
        //point /= 3;
        int point = (int) Math.floor(((((cosRotX) * (X + amp)) - (sinRotX * (Y + amp)))) * (scale) * AMP);
        //if(Z2>=camZ)
        //    shouldDraw=false;
        //else
        //    shouldDraw=true;
        //int point = getXRev(X,Y,Z,camX,camY,camZ,sinRotX,sinRotY,cosRotY,cosRotX,AMP);
        return point;
    }
    
    private int getY(float X, float Y, float Z, float camX, float camY, float camZ, double sinRotX, double sinRotY, double cosRotY, double cosRotX, float AMP) {
        float X2 = Y - camX;
        float Y2 = X - camY;
        float Z2 = Z - camZ;
        
        float avgPos = (X + Y + Z) / 3;
        float avgPosHoriz = (X + Z) / 2;
        float amp = 1;
        try {
            amp = 1 - (1 / Z2);
        } catch (ArithmeticException e) {
        }
        
        X -= camX;
        Y -= camY;
        Z -= camZ;
        
        int point = (int) Math.floor(((cosRotY) * ((((cosRotX) * Y)) + ((sinRotX) * X)) + ((Z * (sinRotY)))) * AMP);
        //int point = getXRev(X,Y,Z,camX,camY,camZ,sinRotX,sinRotY,cosRotY,cosRotX,AMP);
        return point;
    }
    
    private int getXAngle(float x, float y, float z, float rotX) {
        x -= game.camX;
        y -= game.camY;
        z -= game.camZ;
        
        double angleY = Math.tan(Math.sqrt((x * x) + (z * z)) * y);
        double angleX = (Math.atan2(x, y)) - rotX;
        
        if (Math.abs(angleX) <= 3) {
            angleX = 3;
            onScreen = false;
        } else if (Math.abs(angleX) >= 5) {
            //angleX=50;
            angleX = 5;
        } else {
            shouldDraw = true;
            onScreen = true;
        }
        
        return (int) (((Math.cos(angleX) * game.board.getWidth() * 2.5) + game.board.getWidth() * 1.5f) * 1.15);
        // return (int)((Math.asin(angleX)*game.board.getWidth()*0.25));
    }
    
    private int getYAngle(float x, float z, float y, float rotX) {
        x -= game.camX;
        y -= game.camY;
        z -= game.camZ;
        
        double angleY = Math.atan2(Math.sqrt((x * x) + (z * z)), y);
        double angleX = Math.atan2(x, z) - rotX;
        return (int) ((Math.sin(angleY) * game.board.getHeight() * -1)) + 60;
        // return (int)((Math.cos(angleY)*game.board.getHeight()*0.25));
    }
    
    private int getXRevert(float X, float Y, float Z, float camX, float camY, float camZ, double sinRotX, double sinRotY, double cosRotY, double cosRotX, float AMP) {
        int point = (int) Math.floor(((((cosRotX) * X) - (sinRotX * Y)) - camX) * AMP);
        return point;
    }
    
    private int getYRevert(float X, float Y, float Z, float camX, float camY, float camZ, double sinRotX, double sinRotY, double cosRotY, double cosRotX, float AMP) {
        int point = (int) Math.floor(((cosRotY) * ((((cosRotX) * Y) - camZ) + ((sinRotX) * X)) + ((Z * (sinRotY)) + camY)) * AMP);
        return point;
    }
    
    public void drawTri(BufferedImage bimig) {
        if (onScreen) {
            Shape poly = new Polygon(drawPointsX, drawPointsY, 4);
            int shadeColor = Math.abs((int) ((1f / (Math.atan2(game.board.getWidth() / 2 - (drawPointsX[0] + drawPointsX[1] + drawPointsX[2]) / 3, game.board.getHeight() / 2 - (drawPointsY[0] + drawPointsY[1] + drawPointsY[2]) / 3) * 2)) * 360));
            double rotation = (Math.abs((Math.atan2(game.board.getWidth() / 2 - (drawPointsX[0] + drawPointsX[1] + drawPointsX[2]) / 3, game.board.getHeight() / 2 - (drawPointsY[0] + drawPointsY[1] + drawPointsY[2]) / 3))) / 3) * 360;
//            System.out.println(rotation);
            Graphics2D textureWorker = (Graphics2D) display.textures.getGraphics();
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            textureWorker.setRenderingHints(rh);
            AffineTransform defaultTransform = textureWorker.getTransform();
            //textureWorker.translate(drawPointsX[0],drawPointsY[0]);
            textureWorker.translate(poly.getBounds().x, poly.getBounds().y);
            textureWorker.scale(poly.getBounds().width / 10, poly.getBounds().height / 10);
            //textureWorker.rotate(Math.atan2((drawPointsX[0]+drawPointsX[1])/2,(drawPointsY[0]+drawPointsY[1])/2));
            textureWorker.rotate(Math.toRadians(rotation));
            AffineTransform drawTransform = textureWorker.getTransform();
            textureWorker.setTransform(defaultTransform);
            textureWorker.drawImage(texture, drawTransform, null);
            //textureWorker.clipRect(0,0,100,100);
            
            shadeColor /= 360;
            shadeColor *= 255;
            
            Graphics g2 = bimig.getGraphics();
            
            Graphics2D g2d = (Graphics2D) g2;
            /*RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);

            rh.put(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            g2d.setRenderingHints(rh);*/
            
            g2d.setRenderingHints(rh);
            
            Color c = new Color(255, 0, 255);
            try {
                c = new Color(bufferedTexture.getRGB(0, 0));
            } catch (NullPointerException e) {
            
            }
            
            if (shadeColor >= 255) {
                shadeColor = 255;
            }
            //System.out.println(shadeColor);
            Color rotShade = null;
            try {
                rotShade = new Color(shadeColor, shadeColor, shadeColor, 255 / 10);
            } catch (IllegalArgumentException err) {
//                System.out.println(shadeColor);
            }
            
            int R = c.getRed();
            int G = c.getGreen();
            int B = c.getBlue();
            //c=new Color(R,G,B,(255-bufferedTexture.getTransparency()));
            c = new Color(R, G, B, shade.getAlpha());
            //g2d.setColor(c);
            //g2d.fill(poly);
            TexturePaint paint = new TexturePaint(display.textures, new Rectangle(0, 0, bimig.getWidth(), bimig.getHeight()));
            g2d.setPaint(paint);
            //g2d.setColor(col);
            g2d.fill(poly);
            //g2d.drawImage(display.textures.getScaledInstance(bimig.getWidth(),bimig.getHeight(),BufferedImage.SCALE_SMOOTH),0,0,null);
            g2d.setColor(shade);
            g2d.fill(poly);
            g2d.setColor(rotShade);
            g2d.fill(poly);
            
            //g.drawImage(bimig.getScaledInstance(bimig.getWidth(),bimig.getHeight(),BufferedImage.SCALE_FAST),0,0,null);
        }
    }
    
    public float getAngle(float x, float y, float z) {
        //float Angle = (float)((1*Math.atan2((averageX/x),averageZ-z))*-1);
        //Angle += (float)((1*Math.atan2(((averageZ-z)+averageX-x)/2,averageY+y))*100000);
        float Angle = (float) (Math.atan(averageX - x) + Math.atan(averageZ - z) + Math.atan(averageY - y) * 10000);
        //Angle += (float)(Math.atan(averageX-z)/Math.atan(averageZ-x)+Math.atan(averageY-y)*10000);
        //Angle /= 2;
        //Angle -= (float)(Math.atan(averageX-x)-Math.atan(averageZ-z)-Math.atan(averageY-y)*1000);
        Angle = 1 / (Angle);
        return Angle;
    }
    
    public float getAngleInvert(float x, float y, float z) {
        float Angle = (float) ((Math.atan(averageX - x) + Math.atan(averageZ - z) + Math.atan(averageY - y)) * 10000);
        Angle /= 1;
        Angle = (int) Angle;
        Angle = 1 / (Angle);
        return Angle;
    }
}
