

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
    public int a, b, c, d, re, gr, bl;
    float z;
    public Cara(int A, int B, int C, int D, float Z,
                int RE, int GR, int BL){
        a = A; b = B; c = C; d = D; z = Z;
        re = RE; gr = GR; bl = BL; 
    }
    
}   

public class Tetris2 extends Applet
    implements MouseListener, MouseMotionListener{

    int width, height;
    int mx, my;
    int n = 1;
    int alpha = 255;

    Image backbuffer;
    Graphics backg;

    int azimuth = 35, elevation = 30;
   
    Point3D[] vertices;
    Edge[] edges;
    Cara[] caras;
    Point[] points;

    void drawPiece( Graphics g ) {
        
        vertices = new Point3D[ 32 ];
        vertices[0] = new Point3D( -1, -1, -1 );
        vertices[1] = new Point3D( -1, -1,  1 );
        vertices[2] = new Point3D( -1,  1, -1 );
        vertices[3] = new Point3D( -1,  1,  1 );
        vertices[4] = new Point3D(  1, -1, -1 );
        vertices[5] = new Point3D(  1, -1,  1 );
        vertices[6] = new Point3D(  1,  1, -1 );
        vertices[7] = new Point3D(  1,  1,  1 );
       
        edges = new Edge[ 48 ];
        edges[ 0] = new Edge( 0, 1 );
        edges[ 1] = new Edge( 0, 2 );
        edges[ 2] = new Edge( 0, 4 );
        edges[ 3] = new Edge( 1, 3 );
        edges[ 4] = new Edge( 1, 5 );
        edges[ 5] = new Edge( 2, 3 );
        edges[ 6] = new Edge( 2, 6 );
        edges[ 7] = new Edge( 3, 7 );
        edges[ 8] = new Edge( 4, 5 );
        edges[ 9] = new Edge( 4, 6 );
        edges[10] = new Edge( 5, 7 );
        edges[11] = new Edge( 6, 7 );
        
        caras = new Cara[24];
        caras[0] = new Cara(0, 4, 6, 2, 0,   6, 162,  23);
        caras[1] = new Cara(1, 5, 7, 3, 0,   9,  19, 180);
        caras[2] = new Cara(0, 1, 3, 2, 0, 237, 113,  24);
        caras[3] = new Cara(4, 5, 7, 6, 0, 209,  22,  15);
        caras[4] = new Cara(0, 4, 5, 1, 0, 241, 223,  30);
        caras[5] = new Cara(2, 6, 7, 3, 0, 248, 248, 248);
       
            
                    for(int i = 8; i<16; i++)
                        vertices[i]= new Point3D(vertices[i-8].x - 2, vertices[i-8].y, vertices[i-8].z);
                    for(int i = 16; i<24; i++)
                        vertices[i]= new Point3D(vertices[i-16].x, vertices[i-8].y + 2, vertices[i-16].z);
                    for(int i = 24; i<32; i++)    
                        vertices[i]= new Point3D(vertices[i-8].x + 2, vertices[i-8].y, vertices[i-8].z);
      
                    for(int i = 12; i<24; i++)
                        edges[i] = new Edge(edges[i-12].a + 8,edges[i-12].b + 8);   
                    for(int i = 24; i<36; i++) 
                        edges[i] = new Edge(edges[i-12].a + 8,edges[i-12].b + 8);
                    for(int i = 36; i<48; i++)  
                        edges[i] = new Edge(edges[i-12].a + 8,edges[i-12].b + 8);
                    
                    for(int i = 6; i<24; i++)
                        caras[i] = new Cara(caras[i-6].a + 8, caras[i-6].b + 8, caras[i-6].c + 8, caras[i-6].d + 8,
                                            0, caras[i-6].re, caras[i-6].gr, caras[i-6].bl);

        // compute coefficients for the projection
        double theta = Math.PI * azimuth / 180.0;
        double phi = Math.PI * elevation / 180.0;
        float cosT = (float)Math.cos( theta ), sinT = (float)Math.sin( theta );
        float cosP = (float)Math.cos( phi ), sinP = (float)Math.sin( phi );
        float cosTcosP = cosT*cosP, cosTsinP = cosT*sinP,
              sinTcosP = sinT*cosP, sinTsinP = sinT*sinP;

        // project vertices onto the 2D viewport
        points = new Point[ vertices.length ];
        
        float [] f = new float [32];
        
        int j;
        int scaleFactor = width/4;
        float near = 50;  // distance from eye to near plane
        float nearToObj = 90;  // distance from near plane to center of object
        for ( j = 0; j < vertices.length; ++j ) {
            int x0 = vertices[j].x;
            int y0 = vertices[j].y;
            int z0 = vertices[j].z;

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
            caras[i].z = f[caras[i].a] + f[caras[i].b] + f[caras[i].c] + f[caras[i].d];
        
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
      
            for ( j = 0; j < edges.length; ++j ) {
                g.drawLine(
                    points[ edges[j].a ].x, points[ edges[j].a ].y,
                    points[ edges[j].b ].x, points[ edges[j].b ].y
                );
            }
      
        }
    }
    
    void paintFace(Cara C, Graphics g){
       
       g.setColor(new Color(C.re, C.gr, C.bl, alpha));
       
       int [] x = new int[4];
       int [] y = new int[4];
       
       x[0] = points[C.a].x;
       y[0] = points[C.a].y;
       x[1] = points[C.b].x;
       y[1] = points[C.b].y;
       x[2] = points[C.c].x;
       y[2] = points[C.c].y;
       x[3] = points[C.d].x;
       y[3] = points[C.d].y;
       
       g.fillPolygon(x, y, 4);
              
   }

    public void mouseEntered( MouseEvent e ) { }
    public void mouseExited( MouseEvent e ) { }
    public void mouseClicked( MouseEvent e ) { }
    public void mousePressed( MouseEvent e ) {
        mx = e.getX();
        my = e.getY();
        e.consume();
    }
    public void mouseReleased( MouseEvent e ) { }
    public void mouseMoved( MouseEvent e ) { }
    public void mouseDragged( MouseEvent e ) {
        // get the latest mouse position
        int new_mx = e.getX();
        int new_my = e.getY();

        // adjust angles according to the distance travelled by the mouse
        // since the last event
        azimuth -= new_mx - mx;
        elevation += new_my - my;

        // update the backbuffer
        drawPiece( backg );

        // update our data
        mx = new_mx;
        my = new_my;

        repaint();
        e.consume();
    }

    public void update( Graphics g ) {
       g.drawImage( backbuffer, 0, 0, this );
        showStatus("Elev: "+elevation+" deg, Azim: "+azimuth+" deg");
    }

    public void paint( Graphics g ) {
       
        width = 400;
        height = 500;
            
        backbuffer = createImage( width, height );
        backg = backbuffer.getGraphics();
        drawPiece( backg );

        addMouseListener( this );
        addMouseMotionListener( this );
       
        this.setSize(400,500); 
  
        update( g );
    }
   
   
}