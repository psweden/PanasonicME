package mac;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 Classen tar reda på skärmstorleken, på telefonen.

 */
public class setSize extends Canvas {

    private int width, height;

    public setSize() {




    }
    public void paint(Graphics g) {
        this.width = getWidth();
        this.height = getHeight();


        System.out.println("Height Of Display Screen: " + height);
        System.out.println("Width Of Display Screen: " + width);


    }

    public int setWidht(int w){

        this.width = w;

        return width;
    }
    public int setHight(int h){

        this.height = h;

        return height;
    }



}
