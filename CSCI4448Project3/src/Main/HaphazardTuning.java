package Main;

public class HaphazardTuning extends TuningStrategy {
    HaphazardTuning() {
        super();
    }

    public boolean tune(boolean state) {
        if (getRand().nextInt(100) < 50 ) {
            return !state;
        }
        return state;
    }
}
