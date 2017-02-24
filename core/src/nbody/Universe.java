package nbody;

import com.badlogic.gdx.Gdx;
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

    public void populate(int numBodies){
        populateUniformSphere(numBodies);
    }

    public void populateUniformSphere(int numBodies){
        for(int i = 0; i < numBodies; i++){
            Vector3 position = new Vector3();
            Vector3 velocity = new Vector3();
            addBody(new Body(randomlySetToInsideUnitSphereUniformly(position),
//                    randomlySetToInsideUnitSphereUniformly(velocity),
                    velocity,
                    1 - MathUtils.random()));
        }
    }

    public void initBodies(float delta){
        Gdx.app.log("Universe", "initializing bodies");
        for(Body b : bodies)
            b.init(delta, bodies);
    }

    public double calculateTotalKE(){
        double totalKE = 0;

        for(Body b: bodies){
            totalKE += b.calculateInitialKE();
        }

        return totalKE;
    }

    public double calculateTotalPE(){
        double totalPE = 0;

        for(Body b: bodies){
            totalPE += b.calculateInitialPE(bodies);
        }

        return totalPE;
    }

    public double calculateTotalEnergy(){
        return calculateTotalKE() + calculateTotalPE();
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
        Gdx.app.log("Universe", "updating bodies");
        for(Body b : bodies){
            b.updateNextPosition(delta, bodies);
        }
        for(Body b : bodies){
            b.update();
        }
    }

    private Vector3 randomlySetToInsideUnitSphereUniformly(Vector3 vector){
        float scaling = 100;
        do {
            float randx = (float) Math.random()*scaling;
            float randy = (float) Math.random()*scaling;
            float randz = (float) Math.random()*scaling;
            vector.set(randx, randy, randz);
        } while (vector.len2() > scaling);

        return vector;
    }
}
