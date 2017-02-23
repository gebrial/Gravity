package nbody;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Gebrial on 2/15/2017.
 */
public class Universe {
    private Collection<Body> bodies;

    public Universe(){
        this(new HashSet<Body>());
    }

    public Universe(Collection<Body> bodies){
        this.bodies = bodies;
    }

    public void populate(){
        populateUniformSphere();
    }

    public void populateUniformSphere(){
        Vector3 position = new Vector3();
        Vector3 velocity = new Vector3();
        addBody(new Body(randomlySetToInsideUnitSphereUniformly(position),
                randomlySetToInsideUnitSphereUniformly(velocity),
                1 - MathUtils.random()));
        for(Body b : bodies){
            b.init(1f/60, bodies);
        }
    }

    public void addBody(Body b){
        bodies.add(b);
    }

    public void addBodies(Collection<Body> bodies){
        this.bodies.addAll(bodies);
    }

    public void removeBody(Body b){
        bodies.remove(b);
    }

    public void removeBodies(Collection<Body> bodies){
        this.bodies.removeAll(bodies);
    }

    public Collection<Body> getBodies(){
        return bodies;
    }

    public void update(float delta){
        for(Body b : bodies){
            b.updateNextPosition(delta, bodies);
        }
        for(Body b : bodies){
            b.update();
        }
    }

    private Vector3 randomlySetToInsideUnitSphereUniformly(Vector3 vector){
        do {
            float randx = (float) Math.random();
            float randy = (float) Math.random();
            float randz = (float) Math.random();
            vector.set(randx, randy, randz);
        } while (vector.len2() > 1);

        return vector;
    }
}
