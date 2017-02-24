package nbody;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.Collection;

/**
 * Created by Gebrial on 2/15/2017.
 */
public class Body {
    private static int UNIQUE_ID = 0;
    private int uid = ++UNIQUE_ID;

    private Vector3 position, prevPosition, nextPosition, velocity;
    private float mass;

    public Body(Vector3 position, Vector3 velocity, float mass){
        Gdx.app.log("Body", "initial position of " + uid + ": " + position.toString());
        this.position = position.cpy();
        this.velocity = velocity.cpy();
        this.mass = mass;
    }

    /**
     * Need to call this method after all bodies are set up to calculate first movement step
     * @param delta
     * @param bodies
     */
    public void init(float delta, Collection<Body> bodies){
        nextPosition = new Vector3();
        for(Body b: bodies){
            if(b == this) continue;
            float scale = (float)(-b.getMass()/Math.pow(dst2(b), 1.5));
            Vector3 contribution = this.getPosition().cpy().sub(b.getPosition()).scl(scale);
            nextPosition.add(contribution);
        }
        nextPosition.scl(delta*delta/2);
        nextPosition.add(getPosition().cpy().add(getVelocity().scl(delta)));
        update();
    }

    public Vector3 getPosition(){
        return position;
    }

    public Vector3 getPrevPosition(){
        return prevPosition;
    }

    public Vector3 getVelocity(){
        return velocity;
    }

    public double calculateInitialKE(){
        double KE = 0.5d*mass*velocity.len2();
        return KE;
    }

    public double calculateInitialPE(Collection<Body> bodies){
        double PE = 0;
        for(Body b : bodies){
            PE += calculateInitialPE(b);
        }
        return PE;
    }

    public double calculateInitialPE(Body body){
        if(body == this) return 0d;
        double PE = -mass*body.getMass()/Math.pow(dst2(body), 1f/2);
        return PE;
    }

    public double calculateInitialEnergy(Collection<Body> bodies){
        return calculateInitialKE() + calculateInitialPE(bodies);
    }

    public float getMass(){
        return mass;
    }

    /**
     * Call this before 'update' to calculate next position.
     * @param delta
     * @param bodies
     */
    public void updateNextPosition(float delta, Collection<Body> bodies){
        nextPosition.setZero();
        for(Body b: bodies){
            if(b == this) continue;
            float scale =  (float) (-b.getMass()/Math.pow(dst2(b), 1.5));
            Vector3 contribution = this.getPosition().cpy().sub(b.getPosition()).scl(scale);
            nextPosition.add(contribution);
        }
        nextPosition.scl(delta*delta);
        nextPosition.add(getPosition().cpy().scl(2).sub(getPrevPosition()));
    }

    /**
     * Call this to move body forward one step in time.
     */
    public void update(){
        Gdx.app.log("Body", "next position " + uid + ": " + nextPosition.toString());
        prevPosition = position.cpy();
        position = nextPosition.cpy();
        nextPosition.setZero();
    }

    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.point(position.x, position.y, position.z);
    }

    public int hashCode(){
        return uid;
    }

    private float dst2(Body b){
        return getPosition().dst2(b.getPosition());
    }
}
