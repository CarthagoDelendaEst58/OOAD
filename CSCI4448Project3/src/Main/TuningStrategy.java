package Main;

import java.util.*;

public abstract class TuningStrategy {
    private Random rand;

    TuningStrategy() {
        rand = new Random();
    }

    public Random getRand() { return rand; }

    abstract boolean tune(boolean state);
}
