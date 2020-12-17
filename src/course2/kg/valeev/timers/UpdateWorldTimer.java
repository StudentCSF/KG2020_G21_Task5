/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.valeev.timers;

import course2.kg.valeev.model.Billiard;

/**
 *
 * @author Alexey
 */
public class UpdateWorldTimer extends AbstractWorldTimer {

    private long last;
    public UpdateWorldTimer(Billiard world, int period) {
        super(world, period);
    }
    
    @Override
    void worldAction(Billiard w) {
        long time = System.currentTimeMillis();
        actualWorld.update((time - last) * 0.001);
        last = time;
    }

    @Override
    public void start() {
        last = System.currentTimeMillis();
        super.start();
    }
    
    
}
