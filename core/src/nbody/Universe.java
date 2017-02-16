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
        addBody(new Body(new Vector3().setToRandomDirection().setLength((float) Math.cbrt((double) MathUtils.random())),
                new Vector3().setToRandomDirection().setLength((float) Math.cbrt((double) MathUtils.random())),
                1 - MathUtils.random()));
        for(Body b : bodies){
            b.init(1/60, bodies);
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
}
