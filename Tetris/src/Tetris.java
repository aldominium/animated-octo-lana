
/*
Aldo Olivares Dominguez
A01096624
*/
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import javax.swing.*;

class Point3D {
    
    public int x, y, z;
    
    public Point3D( int X, int Y, int Z ) {
        
        x = X;  y = Y;  z = Z;
        
    }
}

class Edge {
    public int a, b;
    public Edge( int A, int B ) {
        a = A;  b = B;
    }
}    
    
class Cara{
    
    public int v1, v2, v3, v4;
    
    public Color color;
    
    float z;
    
    public Cara(int v1, int v2, int v3, int v4, float z, Color color){
        
        this.v1 = v1;
        
        this.v2 = v2;
        
        this.v3 = v3;
        
        this.v4 = v4;
        
        this.z = z;
        
        this.color = color; 
        
        
    }
    
}

public class Tetris extends Applet
    implements MouseListener, MouseMotionListener{

    int width, height;
    int mx, my;
    int n = 1;
    int alpha = 255;

    Image backbuffer;
    Graphics backg;

    int azimuth = 35, elevation = 30;
   
    Point3D[] puntos;
    Edge[] lados;
    Cara[] caras;
    Point[] points;

    void pintarTetris( Graphics g ) {
        
        puntos = new Point3D[ 32 ];
        puntos[0] = new Point3D( -1, -1, -1 );
        puntos[1] = new Point3D( -1, -1,  1 );
        puntos[2] = new Point3D( -1,  1, -1 );
        puntos[3] = new Point3D( -1,  1,  1 );
        puntos[4] = new Point3D(  1, -1, -1 );
        puntos[5] = new Point3D(  1, -1,  1 );
        puntos[6] = new Point3D(  1,  1, -1 );
        puntos[7] = new Point3D(  1,  1,  1 );
       
        lados = new Edge[ 48 ];
        lados[ 0] = new Edge( 0, 1 );
        lados[ 1] = new Edge( 0, 2 );
        lados[ 2] = new Edge( 0, 4 );
        lados[ 3] = new Edge( 1, 3 );
        lados[ 4] = new Edge( 1, 5 );
        lados[ 5] = new Edge( 2, 3 );
        lados[ 6] = new Edge( 2, 6 );
        lados[ 7] = new Edge( 3, 7 );
        lados[ 8] = new Edge( 4, 5 );
        lados[ 9] = new Edge( 4, 6 );
        lados[10] = new Edge( 5, 7 );
        lados[11] = new Edge( 6, 7 );
        
        caras = new Cara[24];
        caras[0] = new Cara(0, 4, 6, 2, 0, new Color(6,145,23));
        caras[1] = new Cara(1, 5, 7, 3, 0, Color.RED);
        caras[2] = new Cara(0, 1, 3, 2, 0, Color.YELLOW);
        caras[3] = new Cara(4, 5, 7, 6, 0, Color.BLUE);
        caras[4] = new Cara(0, 4, 5, 1, 0, Color.CYAN);
        caras[5] = new Cara(2, 6, 7, 3, 0, Color.DARK_GRAY);
       
                    //Dibujas puntos hacia atras
                    for(int i = 8; i<16; i++){
                        puntos[i]= new Point3D(puntos[i-8].x - 2, puntos[i-8].y, puntos[i-8].z);
                    }
                    //Puntos de arriba
                    for(int i = 16; i<24; i++){
    
                        puntos[i]= new Point3D(puntos[i-16].x, puntos[i-8].y + 2, puntos[i-16].z);
                    }
                    //Puntos de la derecha
                    for(int i = 24; i<32; i++){    
                        puntos[i]= new Point3D(puntos[i-8].x + 2, puntos[i-8].y, puntos[i-8].z);
                    }
      
                    for(int i = 12; i<24; i++){
                        lados[i] = new Edge(lados[i-12].a + 8,lados[i-12].b + 8);
                    }
                    for(int i = 24; i<36; i++){ 
                        lados[i] = new Edge(lados[i-12].a + 8,lados[i-12].b + 8);
                    }
                    for(int i = 36; i<48; i++)  {
                        lados[i] = new Edge(lados[i-12].a + 8,lados[i-12].b + 8);
                    }
                    
                    for(int i = 6; i<24; i++){
                        caras[i] = new Cara(caras[i-6].v1 + 8, caras[i-6].v2 + 8, caras[i-6].v3 + 8, caras[i-6].v4 + 8,
                                            0, caras[i-6].color);
                    }

        // compute coefficients for the projection
        double theta = Math.PI * azimuth / 180.0;
        double phi = Math.PI * elevation / 180.0;
        float cosT = (float)Math.cos( theta ), sinT = (float)Math.sin( theta );
        float cosP = (float)Math.cos( phi ), sinP = (float)Math.sin( phi );
        float cosTcosP = cosT*cosP, cosTsinP = cosT*sinP,
              sinTcosP = sinT*cosP, sinTsinP = sinT*sinP;

        // project puntos onto the 2D viewport
        points = new Point[ puntos.length ];
        
        float [] f = new float [32];
        
        int j;
        int scaleFactor = width/4;
        float near = 50;  // distance from eye to near plane
        float nearToObj = 90;  // distance from near plane to center of object
        for ( j = 0; j < puntos.length; ++j ) {
            int x0 = puntos[j].x;
            int y0 = puntos[j].y;
            int z0 = puntos[j].z;

            // compute an orthographic projection
            float x1 = cosT*x0 + sinT*z0;
            float y1 = -sinTsinP*x0 + cosP*y0 + cosTsinP*z0;

            // now adjust things to get a perspective projection
            float z1 = cosTcosP*z0 - sinTcosP*x0 - sinP*y0;
            x1 = x1*near/(z1+near+nearToObj);
            y1 = y1*near/(z1+near+nearToObj);

            f[j] = z1;
            
            // the 0.5 is to round off when converting to int
            points[j] = new Point(
                (int)(width/2 + scaleFactor*x1 + 0.5),
                (int)(height/2 - scaleFactor*y1 + 0.5)
            );
        }

        for(int i = 0; i<24; i++)
            caras[i].z = f[caras[i].v1] + f[caras[i].v2] + f[caras[i].v3] + f[caras[i].v4];
        
        for(int i = 0; i<24;i++)
            for(j = i+1; j<24; j++){
                if(caras[i].z < caras[j].z){
                    Cara x = caras[i];
                    caras[i] = caras[j];
                    caras[j] = x;
                }
            }
  
        // draw the wireframe
        g.setColor( Color.black );
        g.fillRect( 0, 0, width, height );
        g.setColor( Color.white );
      
        for(int i = 0; i<24;i++)
            paintFace(caras[i], g);
        
        g.setColor( Color.white );
        
        for(int i = 0; i < 1; i++){
      
            for ( j = 0; j < lados.length; ++j ) {
                g.drawLine(
                    points[ lados[j].a ].x, points[ lados[j].a ].y,
                    points[ lados[j].b ].x, points[ lados[j].b ].y
                );
            }
      
        }
    }
    
    void paintFace(Cara C, Graphics g){
       
       g.setColor(new Color(C.color.getRed(), C.color.getGreen(), C.color.getBlue()));
       
       
       int [] x = new int[4];
       
       int [] y = new int[4];
       
       
       x[0] = points[C.v1].x;
       y[0] = points[C.v1].y;
       x[1] = points[C.v2].x;
       y[1] = points[C.v2].y;
       x[2] = points[C.v3].x;
       y[2] = points[C.v3].y;
       x[3] = points[C.v4].x;
       y[3] = points[C.v4].y;
       
       g.fillPolygon(x, y, 4);
       
              
   }

    @Override
    public void mouseEntered( MouseEvent e ) { }
    @Override
    public void mouseExited( MouseEvent e ) { }
    @Override
    public void mouseClicked( MouseEvent e ) { }
    @Override
    public void mousePressed( MouseEvent e ) {
        mx = e.getX();
        my = e.getY();
        e.consume();
    }
    @Override
    public void mouseReleased( MouseEvent e ) { }
    @Override
    public void mouseMoved( MouseEvent e ) { }
    @Override
    public void mouseDragged( MouseEvent e ) {
        // get the latest mouse position
        int new_mx = e.getX();
        int new_my = e.getY();

        // adjust angles according to the distance travelled by the mouse
        // since the last event
        azimuth -= new_mx - mx;
        elevation += new_my - my;

        // update the backbuffer
        pintarTetris( backg );

        // update our data
        mx = new_mx;
        my = new_my;

        repaint();
        e.consume();
    }

    @Override
    public void update( Graphics g ) {
       g.drawImage( backbuffer, 0, 0, this );
        showStatus("Elev: "+elevation+" deg, Azim: "+azimuth+" deg");
    }

    @Override
    public void paint( Graphics g ) {
        
       
        width = 400;
        
        height = 500;
            
        backbuffer = createImage( width, height );
        
        backg = backbuffer.getGraphics();
        
        pintarTetris( backg );

        addMouseListener( this );
        
        addMouseMotionListener( this );
       
        this.setSize(400,500); 
  
        update( g );
    }
   
   
}