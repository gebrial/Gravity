package nbody;

import com.badlogic.gdx.math.Vector3;

import java.util.Collection;

/**
 * Created by Gebrial on 2/15/2017.
 */
public class Body {
    private Vector3 position, prevPosition, nextPosition, velocity;
    private float mass;

    public Body(Vector3 position, Vector3 velocity, float mass){
        this.position = position;
        this.velocity = velocity;
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
            nextPosition.add(this.getPosition().cpy().sub(b.getPosition()).scl((float)(-b.mass/Math.pow(dst2(b), 1.5))));
        }
        nextPosition.scl(delta*delta/2);
        nextPosition.add(getPosition().cpy().add(getVelocity().scl(delta)));
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

    /**
     * Call this before 'update' to calculate next position.
     * @param delta
     * @param bodies
     */
    public void updateNextPosition(float delta, Collection<Body> bodies){
        nextPosition.setZero();
        for(Body b: bodies){
            nextPosition.add(this.getPosition().cpy().sub(b.getPosition()).scl((float)(-b.mass/Math.pow(dst2(b), 1.5))));
        }
        nextPosition.scl(delta*delta);
        nextPosition.add(getPosition().cpy().scl(2).sub(getPrevPosition()));
    }

    /**
     * Call this to move body forward one step in time.
     */
    public void update(){
        prevPosition = position;
        position = nextPosition;
        nextPosition.setZero();
    }

    private float dst2(Body b){
        return getPosition().dst2(b.getPosition());
    }
}
