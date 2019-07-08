package com.example.lorin.thermontanks.Tank;


//import com.example.lorin.thermontanks.Camera;
import com.example.lorin.thermontanks.Vector2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lorinhersh on 10/23/17.
 */

public class BulletContainer implements Serializable {
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> removeBullets;
    //private transient Camera camera;
    //private transient Bitmap bulletImage;
    //boolean moving;

    private boolean addBullet;
    private Bullet tempBullet;

    /*public BulletContainer(Camera camera) {
        this.bullets = new ArrayList<>();
        this.removeBullets = new ArrayList<>();
        this.camera = camera;
        this.addBullet = false;

        bulletImage = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bulletImage);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        tempCanvas.drawRect(0f, 0f, 5f, 5f, paint);
    }*/

    public BulletContainer() {
        //no constructor
    }

    /*
    public void reloadObject(Camera camera) {
        this.addBullet = false;
        this.camera = camera;

        bulletImage = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bulletImage);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        tempCanvas.drawRect(0f, 0f, 5f, 5f, paint);
    }*/

    public void newBullet(Vector2 position, Vector2 velocity, int speed) {
        tempBullet = new Bullet(position, velocity, speed);
        addBullet = true;
    }

    public void move() {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (!bullet.move()) {
                removeBullets.add(bullet);
            }
        }

        for (Bullet bullet : removeBullets) {
            bullets.remove(bullet);
        }

        if (removeBullets.size() > 0) {
            removeBullets = new ArrayList<>();
        }

        callBack();
    }

    private void callBack() {
        if (addBullet) {
            bullets.add(tempBullet);
            addBullet = false;
        }

    }

    /*
    public void draw(Canvas canvas) {
        try {
            Iterator<Bullet> it = bullets.iterator();
            while (it.hasNext()) {
                Bullet bullet = it.next();
                bullet.draw(canvas, camera, bulletImage);
            }
        } catch (Exception e) { //Ignore concurrent modification exception
            //e.printStackTrace();
        }

    }
    */
}

class Bullet {
    Vector2 position;
    Vector2 velocity;
    int speed;

    int runTime;
    private final int FINALRUNTIME = 60;

    public Bullet(Vector2 position, Vector2 velocity, int speed) {
        this.position = position;
        this.velocity = velocity;
        this.speed = speed;
        runTime = 0;
    }

    public boolean move() {
        this.position = this.position.add(this.velocity.multiply(this.speed));
        runTime++;
        return runTime < FINALRUNTIME;
    }

    /*
    public void draw(Canvas canvas, Camera camera, Bitmap bulletImage) {
        canvas.drawBitmap(bulletImage, position.x - camera.getPosition().x, position.y - camera.getPosition().y, null);
    }
    */

}
