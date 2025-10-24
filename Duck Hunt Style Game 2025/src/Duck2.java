import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Duck2 {
	private Image img;               // Stores the picture of the duck
    
    private Image normal; 
    private Image stunned; 
    
     
    private AffineTransform tx;      // Used to move (translate) and resize (scale) the image

    // Variables to control the size (scale) of the duck image
    private double scaleX;           
    private double scaleY;           

    // Variables to control the location (x and y position) of the duck
    private double x;                
    private double y;        
    
    //variables for speed
    private int vx;
    private int vy;

    public boolean debugging = true; 
    
    
    // Constructor: runs when you make a new Duck object
    public Duck2() {
        normal = getImage("/imgs/flyingGreenBook_spookyGame.gif"); // Load the image file
        stunned = getImage("/imgs/spookyGame_stunnedGreenBook.gif");
        
        img = normal;
        
        tx = AffineTransform.getTranslateInstance(0, 0); // Start with image at (0,0)
        
        // Default values
        scaleX = 4;
        scaleY = 4;
        x = 350;
        y = 125;

        
       vx = 5;   
       vy = 3;
        
        
        init(x, y); // Set up the starting location and size
    }
    
    //2nd constructor to initialize location and scale!
    public Duck2(int x, int y, int scaleX, int scaleY) {
    	this();
    	this.x 		= x;
    	this.y 		= y;
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	init(x,y);
    }
    
    //2nd constructor to initialize location and scale!
    public Duck2(int x, int y, int scaleX, int scaleY, int vx, int vy) {
    	this();
    	this.x 		= x;
    	this.y 		= y;
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	this.vx 	= vx; 
    	this.vy 	= vy;
    	init(x,y);
    }
    
    public void setVelocityVariables(int vx, int vy) {
    	this.vx = vx;
    	this.vy = vy;
    }
    
    
    // Changes the picture to a new image file
    public void changePicture(String imageFileName) {
        img = getImage("/imgs/"+ imageFileName);
        init(x, y); // keep same location when changing image
    }
    
    //update any variables for the object such as x, y, vx, vy
    public void update() {
    	
    	x += vx; 
    	
    	if(x >= 860 || x <= -20) {
    		vx *= -1; 
    	}
    	
    	y += vy; 
    	
    	if(y <= -20) {
    		vy *= -1; 
    	}
    	
    	//object falling
    	if(vx == 0 && vy > 10) {
    		if(y >= 450) {
    			vy = - (int)(Math.random()*6+3);
    			vx = (int)(Math.random()*6+3);
    			 if(Math.random()< 0.5){
    				vx *= -1; 
    			 }
    			 img = normal;
    		}
    	}
    //	regular behavior
    	if (y >= 475 && vx != 0) vy *= -1; 
    	
    }
    
    
    
    // Draws the duck on the screen
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;   // Graphics2D lets us draw images
        tx.scale(0.5, 0.5);
        if (vx < 0) {
        		tx.scale(-1, 1);
        		tx.translate(-img.getWidth(null), 0);
        }
        
        
        
        
        
        g2.drawImage(img, tx, null);      // Actually draw the duck image
        update();
        init(x,y);
        
        if(debugging){
        	g.setColor(Color.green);
        g.drawRect((int)x, (int)y, 100, 100);
        }
    }
    
    // Setup method: places the duck at (a, b) and scales it
    private void init(double a, double b) {
        tx.setToTranslation(a, b);        // Move the image to position (a, b)
        tx.scale(scaleX, scaleY);         // Resize the image using the scale variables
    }

    // Loads an image from the given file path
    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Duck.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }

    // NEW: Method to set scale
    public void setScale(double sx, double sy) {
        scaleX = sx;
        scaleY = sy;
        init(x, y);  // Keep current location
    }

    // NEW: Method to set location
    public void setLocation(double newX, double newY) {
        x = newX;
        y = newY;
        init(x, y);  // Keep current scale
    }
    
    
  //Collisions 
    public boolean checkCollision(int mX, int mY) {
    		
    		//represent mouse as rectangle
    		Rectangle mouse = new Rectangle(mX, mY, 50, 50);
    	
    		Rectangle thisObject = new Rectangle((int)x+50, (int)y+50, 50, 50); 
    
    		
    		if(mouse.intersects(thisObject)) {
    			
    			vx = 0;
    			vy = 12; 
    			img = stunned; 
    			
    			return true;
    			
    		}else {
    			return false; 
    		}
    	
    }
    
    
    
    
    
    
    
}
