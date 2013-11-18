//Graficas Computaciones
//Edoardo Alejandro Palazuelos Osorio 01170275
//Victor Iván Lima Alamo 01099286

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

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

public class campusPuebla extends Applet
   implements MouseListener, MouseMotionListener {

   int width, height;
   int mx, my;  // the most recently recorded mouse coordinates

   Image backbuffer;
   Graphics backg;

   int azimuth = 35, elevation = 30;
   int puntos=98;
   int union=86;
   Point3D[] vertices;
   Edge[] edges;

   public void init() {
	  setSize(800,600);
      width = getSize().width;
      height = getSize().height;
      
      vertices = new Point3D[ 436]; //90 Aulas+8 DAE=98
      //Aulas 1
      vertices[ 0] = new Point3D(-70,10,20);
      vertices[ 1] = new Point3D(-20,10,60);
      vertices[ 2] = new Point3D(-20,10,40);
      vertices[ 3] = new Point3D(20,10,40);
      vertices[ 4] = new Point3D(50,10,60);
      vertices[ 5] = new Point3D(60,10,40);
      vertices[ 6] = new Point3D(20,10,20);
      vertices[ 7] = new Point3D(-30,10,20);
      vertices[ 8] = new Point3D(-60,10,0);
      //Aulas 2
      vertices[ 9] = new Point3D(-60,10,-40);
      vertices[10] = new Point3D(-20,10,-20);
      vertices[11] = new Point3D(30,10,-20);
      vertices[12] = new Point3D(60,10,0);
      vertices[13] = new Point3D(70,10,-20);
      vertices[14] = new Point3D(20,10,-60);
      vertices[15] = new Point3D(20,10,-40);
      vertices[16] = new Point3D(-20,10,-40);
      vertices[17] = new Point3D(-50,10,-60);
      
      //Piso Aulas
      int punto=18;
      int y=20;
      for(int i=0; i<4; i++)
      {  
    	  for(int j=0; j<18; j++)
    	  {
    		  vertices[punto]=new Point3D(vertices[j].x,y,vertices[j].z);
    		  punto++;
    	  }
    	  
    	 y=y+10;;
      }
      
      //DAE
      vertices[90] = new Point3D(-20,20,20); 
      vertices[91] = new Point3D(20,20,20);
      vertices[92] = new Point3D(20,20,-20);
      vertices[93] = new Point3D(-20,20,-20);
      vertices[94] = new Point3D(-20,30,20); 
      vertices[95] = new Point3D(20,30,20);
      vertices[96] = new Point3D(20,30,-20);
      vertices[97] = new Point3D(-20,30,-20);
      
      /*int cilind=98, centx=0, centy=0;
      double angulo=0;
      for (int i=0;i<12;i++){
      	centx=(int)Math.cos(Math.toRadians(angulo))*10;
      	centy=(int)Math.sin(Math.toRadians(angulo))*10;
      	vertices[cilind]=new Point3D(centx,30,centy);
      	angulo=angulo+30;
      	cilind++;
      }*/
      
      vertices[98] = new Point3D((int)(Math.cos(Math.toRadians(0))*10),30,(int)(Math.sin(Math.toRadians(0))*10));
      vertices[99] = new Point3D((int)(Math.cos(Math.toRadians(30))*10),30,(int)(Math.sin(Math.toRadians(30))*10));
      vertices[100] = new Point3D((int)(Math.cos(Math.toRadians(60))*10),30,(int)(Math.sin(Math.toRadians(60))*10));
      vertices[101] = new Point3D((int)(Math.cos(Math.toRadians(90))*10),30,(int)(Math.sin(Math.toRadians(90))*10));
      vertices[102] = new Point3D((int)(Math.cos(Math.toRadians(120))*10),30,(int)(Math.sin(Math.toRadians(120))*10));
      vertices[103] = new Point3D((int)(Math.cos(Math.toRadians(150))*10),30,(int)(Math.sin(Math.toRadians(150))*10));
      vertices[104] = new Point3D((int)(Math.cos(Math.toRadians(180))*10),30,(int)(Math.sin(Math.toRadians(180))*10));
      vertices[105] = new Point3D((int)(Math.cos(Math.toRadians(210))*10),30,(int)(Math.sin(Math.toRadians(210))*10));
      vertices[106] = new Point3D((int)(Math.cos(Math.toRadians(240))*10),30,(int)(Math.sin(Math.toRadians(240))*10));
      vertices[107] = new Point3D((int)(Math.cos(Math.toRadians(270))*10),30,(int)(Math.sin(Math.toRadians(270))*10));
      vertices[108] = new Point3D((int)(Math.cos(Math.toRadians(300))*10),30,(int)(Math.sin(Math.toRadians(300))*10));
      vertices[109] = new Point3D((int)(Math.cos(Math.toRadians(330))*10),30,(int)(Math.sin(Math.toRadians(330))*10));
      vertices[110] = new Point3D((int)(Math.cos(Math.toRadians(360))*10),30,(int)(Math.sin(Math.toRadians(360))*10));
      
      vertices[111] = new Point3D((int)(Math.cos(Math.toRadians(0))*10),20,(int)(Math.sin(Math.toRadians(0))*10));
      vertices[112] = new Point3D((int)(Math.cos(Math.toRadians(30))*10),20,(int)(Math.sin(Math.toRadians(30))*10));
      vertices[113] = new Point3D((int)(Math.cos(Math.toRadians(60))*10),20,(int)(Math.sin(Math.toRadians(60))*10));
      vertices[114] = new Point3D((int)(Math.cos(Math.toRadians(90))*10),20,(int)(Math.sin(Math.toRadians(90))*10));
      vertices[115] = new Point3D((int)(Math.cos(Math.toRadians(120))*10),20,(int)(Math.sin(Math.toRadians(120))*10));
      vertices[116] = new Point3D((int)(Math.cos(Math.toRadians(150))*10),20,(int)(Math.sin(Math.toRadians(150))*10));
      vertices[117] = new Point3D((int)(Math.cos(Math.toRadians(180))*10),20,(int)(Math.sin(Math.toRadians(180))*10));
      vertices[118] = new Point3D((int)(Math.cos(Math.toRadians(210))*10),20,(int)(Math.sin(Math.toRadians(210))*10));
      vertices[119] = new Point3D((int)(Math.cos(Math.toRadians(240))*10),20,(int)(Math.sin(Math.toRadians(240))*10));
      vertices[120] = new Point3D((int)(Math.cos(Math.toRadians(270))*10),20,(int)(Math.sin(Math.toRadians(270))*10));
      vertices[121] = new Point3D((int)(Math.cos(Math.toRadians(300))*10),20,(int)(Math.sin(Math.toRadians(300))*10));
      vertices[122] = new Point3D((int)(Math.cos(Math.toRadians(330))*10),20,(int)(Math.sin(Math.toRadians(330))*10));
      vertices[123] = new Point3D((int)(Math.cos(Math.toRadians(360))*10),20,(int)(Math.sin(Math.toRadians(360))*10));
      //cilindro 2
      double angulo=0;
      
      for(int i=124;i<137;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-65,60,(int)(Math.sin(Math.toRadians(angulo))*13)+10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=137;i<150;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-65,50,(int)(Math.sin(Math.toRadians(angulo))*13)+10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=150;i<163;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-65,40,(int)(Math.sin(Math.toRadians(angulo))*13)+10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=163;i<176;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-65,30,(int)(Math.sin(Math.toRadians(angulo))*13)+10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=176;i<189;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-65,20,(int)(Math.sin(Math.toRadians(angulo))*13)+10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=189;i<202;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-65,10,(int)(Math.sin(Math.toRadians(angulo))*13)+10);
    
      angulo=angulo+30;
      
      }
     //cilindro 3
      angulo=0;
      for(int i=202;i<215;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+60,10,(int)(Math.sin(Math.toRadians(angulo))*13)+52);
    
      angulo=angulo+30;
      
      }
        angulo=0;
      for(int i=215;i<228;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+60,20,(int)(Math.sin(Math.toRadians(angulo))*13)+52);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=228;i<241;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+60,30,(int)(Math.sin(Math.toRadians(angulo))*13)+52);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=241;i<254;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+60,40,(int)(Math.sin(Math.toRadians(angulo))*13)+52);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=254;i<267;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+60,50,(int)(Math.sin(Math.toRadians(angulo))*13)+52);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=267;i<280;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+60,60,(int)(Math.sin(Math.toRadians(angulo))*13)+52);
    
      angulo=angulo+30;
      
      }
      //cilindro 4
      angulo=0;
      
      for(int i=280;i<293;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+65,60,(int)(Math.sin(Math.toRadians(angulo))*13)-10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      
      for(int i=293;i<306;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+65,50,(int)(Math.sin(Math.toRadians(angulo))*13)-10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      
      for(int i=306;i<319;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+65,40,(int)(Math.sin(Math.toRadians(angulo))*13)-10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      
      for(int i=319;i<332;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+65,30,(int)(Math.sin(Math.toRadians(angulo))*13)-10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      
      for(int i=332;i<345;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+65,20,(int)(Math.sin(Math.toRadians(angulo))*13)-10);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      
      for(int i=345;i<358;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)+65,10,(int)(Math.sin(Math.toRadians(angulo))*13)-10);
    
      angulo=angulo+30;
      
      }
      //cilindro 5
     angulo=0;
      for(int i=358;i<371;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-60,60,(int)(Math.sin(Math.toRadians(angulo))*13)-52);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=371;i<384;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-60,50,(int)(Math.sin(Math.toRadians(angulo))*13)-52);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=384;i<397;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-60,40,(int)(Math.sin(Math.toRadians(angulo))*13)-52);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=397;i<410;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-60,30,(int)(Math.sin(Math.toRadians(angulo))*13)-52);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=410;i<423;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-60,20,(int)(Math.sin(Math.toRadians(angulo))*13)-52);
    
      angulo=angulo+30;
      
      }
      angulo=0;
      for(int i=423;i<436;i++){
      
      vertices[i] = new Point3D((int)(Math.cos(Math.toRadians(angulo))*13)-60,10,(int)(Math.sin(Math.toRadians(angulo))*13)-52);
    
      angulo=angulo+30;
      
      }
      
      
      edges = new Edge[ 464 ];//70 Aulas+4 DAE=74
      //Aulas 1
      edges[ 0] = new Edge( 0, 1 );
      edges[ 1] = new Edge( 1, 2 );
      edges[ 2] = new Edge( 2, 3 );
      edges[ 3] = new Edge( 3, 4 );
      edges[ 4] = new Edge( 5, 6 );
      edges[ 5] = new Edge( 6, 7 );
      edges[ 6] = new Edge( 7, 8 );
      //Aulas 2
      edges[ 7] = new Edge( 9, 10 );
      edges[ 8] = new Edge( 10, 11 );
      edges[ 9] = new Edge( 11, 12 );
      edges[10] = new Edge( 13, 14 );
      edges[11] = new Edge( 14, 15 );
      edges[12] = new Edge( 15, 16 );
      edges[13] = new Edge( 16, 17 );
      
      //Piso Aulas
      int linea=14;
      for(int j=0; j<56; j++)
      {   		  
    	  edges[linea]=new Edge(edges[j].a+18,edges[j].b+18);
    	  linea++;
      }
      
      //DAE
      edges[70] = new Edge( 90 , 93 );
      edges[71] = new Edge( 91 , 92 );
      edges[72] = new Edge( 94 , 97 );
      edges[73] = new Edge( 95 , 96 );
      
      //Uniones Aulas
      edges[74] = new Edge( 1 , 73 ); //+72
      edges[75] = new Edge( 2 , 74 );
      edges[76] = new Edge( 3 , 75 );
      edges[77] = new Edge( 6 , 78 );
      edges[78] = new Edge( 7 , 79 );
      edges[79] = new Edge( 10 , 82 );
      edges[80] = new Edge( 11 , 83 );
      edges[81] = new Edge( 14 , 86 );
      edges[82] = new Edge( 15 , 87 );
      edges[83] = new Edge( 16 , 88 );
      //Uniones DAE
      edges[84] = new Edge( 90 , 94 );
      edges[85] = new Edge( 92 , 96 );
      //Uniones cilindro
      edges[86] = new Edge( 98 , 99 );  
      edges[87] = new Edge( 99 , 100 );  
      edges[88] = new Edge( 100 , 101 ); 
      edges[89] = new Edge( 101, 102 );  
      edges[90] = new Edge( 102 , 103 ); 
      edges[91] = new Edge( 103 , 104 ); 
      edges[92] = new Edge( 104 , 105 ); 
      edges[93] = new Edge( 105 , 106 ); 
      edges[94] = new Edge( 106 , 107 ); 
      edges[95] = new Edge( 107 , 108 ); 
      edges[96] = new Edge( 108 , 109 ); 
      edges[97] = new Edge( 109 , 110 ); 
     
      edges[98] = new Edge( 111 , 112 );
      edges[99] = new Edge( 112 , 113 );
      edges[100] = new Edge( 113 , 114 );
      edges[101] = new Edge( 114, 115);
      edges[102] = new Edge( 115 , 116 );
      edges[103] = new Edge( 116 , 117 );
      edges[104] = new Edge( 117 , 118 );
      edges[105] = new Edge( 118 , 119 );
      edges[106] = new Edge( 119 , 120 );
      edges[107] = new Edge( 120 , 121 );
      edges[108] = new Edge( 121 , 122 );
      edges[109] = new Edge( 122 , 123 );
      
      edges[110]= new Edge(98,111);
      edges[110]= new Edge(99,112);
      edges[111]= new Edge(100,113);
      edges[112]= new Edge(101,114);
      edges[113]= new Edge(102,115);
      edges[114]= new Edge(103,116);
      edges[115]= new Edge(104,117);
      edges[116]= new Edge(105,118);
      edges[117]= new Edge(106,119);
      edges[118]= new Edge(107,120);
      edges[119]= new Edge(108,121);
      edges[120]= new Edge(109,122);
      edges[121]= new Edge(110,123);
      //uniones cilindro 2
      for(int i=122;i<134;i++){
      
      edges[i]= new Edge(i+2,i+3);
      
      }
      for(int i=134;i<147;i++){
      
      edges[i]= new Edge(i+3,i+2);
      
      }
      for(int i=147;i<160;i++){
      
      edges[i]= new Edge(i+3,i+2);
      
      }
      for(int i=160;i<173;i++){
      
      edges[i]= new Edge(i+3,i+2);
      
      }
      for(int i=173;i<186;i++){
      
      edges[i]= new Edge(i+2,i+3);
      
      }
      for(int i=186;i<199;i++){
      
      edges[i]= new Edge(i+3,i+2);
      
      }
    
      for(int i=199;i<212;i++){
      
      edges[i]= new Edge(i-75,i-10);
      
      }
      for(int i=212;i<224;i++){
      
      edges[i]= new Edge(i-10,i-9);
      
      }
      for(int i=224;i<236;i++){
      
      edges[i]= new Edge(i-9,i-8);
      
      }
      for(int i=236;i<248;i++){
      
      edges[i]= new Edge(i-8,i-7);
      
      }
      for(int i=248;i<260;i++){
      
      edges[i]= new Edge(i-7,i-6);
      
      }
      for(int i=260;i<272;i++){
      
      edges[i]= new Edge(i-6,i-5);
      
      }
      for(int i=272;i<284;i++){
      
      edges[i]= new Edge(i-5,i-4);
      
      }
      for(int i=284;i<296;i++){
      
      edges[i]= new Edge(i-81,i-16);
      
      }
      //4
      for(int i=296;i<308;i++){
      
      edges[i]= new Edge(i-16,i-15);
      
      }
      for(int i=308;i<320;i++){
      
      edges[i]= new Edge(i-15,i-14);
      
      }
      for(int i=320;i<332;i++){
      
      edges[i]= new Edge(i-14,i-13);
      
      }
      for(int i=332;i<344;i++){
      
      edges[i]= new Edge(i-13,i-12);
      
      }
      for(int i=344;i<356;i++){
      
      edges[i]= new Edge(i-12,i-11);
      
      }
      for(int i=356;i<368;i++){
      
      edges[i]= new Edge(i-11,i-10);
      
      }
      for(int i=368;i<380;i++){
      
      edges[i]= new Edge(i-87,i-22);
      
      }
      //cilindro 5
      for(int i=380;i<392;i++){
      
      edges[i]= new Edge(i-22,i-21);
      
      }
      for(int i=392;i<404;i++){
      
      edges[i]= new Edge(i-21,i-20);
      
      }
      for(int i=404;i<416;i++){
      
      edges[i]= new Edge(i-20,i-19);
      
      }
      for(int i=416;i<428;i++){
      
      edges[i]= new Edge(i-19,i-18);
      
      }
      for(int i=428;i<440;i++){
      
      edges[i]= new Edge(i-18,i-17);
      
      }
      for(int i=440;i<452;i++){
      
      edges[i]= new Edge(i-17,i-16);
      
      }
      for(int i=452;i<464;i++){
      
      edges[i]= new Edge(i-94,i-29);
      
      }

      backbuffer = createImage( width, height );
      backg = backbuffer.getGraphics();
      drawWireframe( backg );

      addMouseListener( this );
      addMouseMotionListener( this );
   }

   void drawWireframe( Graphics g ) {

      // compute coefficients for the projection
      double theta = Math.PI * azimuth / 180.0;
      double phi = Math.PI * elevation / 180.0;
      float cosT = (float)Math.cos( theta ), sinT = (float)Math.sin( theta );
      float cosP = (float)Math.cos( phi ), sinP = (float)Math.sin( phi );
      float cosTcosP = cosT*cosP, cosTsinP = cosT*sinP,
             sinTcosP = sinT*cosP, sinTsinP = sinT*sinP;

      // project vertices onto the 2D viewport
      Point[] points;
      points = new Point[ vertices.length ];
      int j;
      int scaleFactor = width/8;
      float near = 3;  // distance from eye to near plane
      float nearToObj = 120f;  // distance from near plane to center of object
      for ( j = 0; j < vertices.length; ++j ) {
         float x0 = vertices[j].x;
         float y0 = vertices[j].y;
         float z0 = vertices[j].z;

         // compute an orthographic projection
         float x1 = cosT*x0 + sinT*z0;
         float y1 = -sinTsinP*x0 + cosP*y0 + cosTsinP*z0;

         // now adjust things to get a perspective projection
         float z1 = cosTcosP*z0 - sinTcosP*x0 - sinP*y0;
         x1 = x1*near/(z1+near+nearToObj);
         y1 = y1*near/(z1+near+nearToObj);

         // the 0.5 is to round off when converting to int
         points[j] = new Point(
            (int)(width/2 + scaleFactor*x1 + 0.5),
            (int)(height/2 - scaleFactor*y1 + 0.5)
         );
      }

      // draw the wireframe
      g.setColor( Color.black );
      g.fillRect( 0, 0, width, height );
      g.setColor( Color.white );
      for ( j = 0; j < edges.length; ++j ) {  
         g.drawLine(
            points[ edges[j].a ].x, points[ edges[j].a ].y,
            points[ edges[j].b ].x, points[ edges[j].b ].y
         );
      }
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
      drawWireframe( backg );

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
      update( g );
   }
}

