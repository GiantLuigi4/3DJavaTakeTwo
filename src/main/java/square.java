import java.awt.*;
import java.awt.image.BufferedImage;

public class square {
    public triangle tri1;
    public triangle tri2;
    public float[] PointsX;
    public float[] PointsY;
    public float[] PointsZ;
    public float distanceX;
    public float distanceY;
    public float distanceZ;
    public float distanceTotal;
    
    public square(float[] pointsX, float[] pointsY, float[] pointsZ, Image texture1, Color shade1, Image texture2, Color shade2) {
        float[] x = new float[3];
        float[] y = new float[3];
        float[] z = new float[3];
        
        PointsX = pointsX;
        PointsY = pointsY;
        PointsZ = pointsZ;
        
        x[0] = pointsX[0];
        x[1] = pointsX[1];
        x[2] = pointsX[3];
        y[0] = pointsY[0];
        y[1] = pointsY[1];
        y[2] = pointsY[3];
        z[0] = pointsZ[0];
        z[1] = pointsZ[1];
        z[2] = pointsZ[3];
        
        tri1 = new triangle(x, y, z, texture1, shade1);
        
        x = new float[3];
        y = new float[3];
        z = new float[3];
        
        x[0] = pointsX[1];
        x[1] = pointsX[2];
        x[2] = pointsX[3];
        y[0] = pointsY[1];
        y[1] = pointsY[2];
        y[2] = pointsY[3];
        z[0] = pointsZ[1];
        z[1] = pointsZ[2];
        z[2] = pointsZ[3];
        
        tri2 = new triangle(x, y, z, texture2, shade2);
    }
    
    public void getDistanceFromCam(float camX, float camY, float camZ) {
        float totalDist;
        float distX;
        float distY;
        float distZ;
        distX = camX - (PointsX[0] + PointsX[1] + PointsX[2] + PointsX[3]) / 4;
        distY = camY - (PointsY[0] + PointsY[1] + PointsY[2] + PointsY[3]) / 4;
        distZ = camZ - (PointsZ[0] + PointsZ[1] + PointsZ[2] + PointsZ[3]) / 4;
        
        totalDist = (distX + distY + distZ) / 3;
        
        distanceX = distX;
        distanceY = distY;
        distanceZ = distZ;
        distanceTotal = totalDist;
    }
    
    public void drawSquare(BufferedImage bimig, float rotX, float rotY, float camX, float camY, float camZ) {
        tri1.getDrawPos(rotX, rotY, camX, camY, camZ);
        tri2.getDrawPos(rotX, rotY, camX, camY, camZ);
        tri1.drawTri(bimig);
        tri2.drawTri(bimig);
    }
    
    public void setShading(Color c1, Color c2) {
        tri1.shade = c1;
        tri2.shade = c2;
    }
    
    public float getAngle1(float x, float y, float z) {
        return tri1.getAngle(x, y, z);
    }
    
    public float getAngle2(float x, float y, float z) {
        return tri2.getAngle(x, y, z);
    }
    
    public float getAngle1Invert(float x, float y, float z) {
        return tri1.getAngleInvert(z, y, x);
    }
    
    public float getAngle2Invert(float x, float y, float z) {
        return tri2.getAngleInvert(z, y, x);
    }
}
