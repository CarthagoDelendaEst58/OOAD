package Main;

public class ManualTuning extends TuningStrategy {
    ManualTuning() {
        super();
    }

    public boolean tune(boolean state) {
        int val = getRand().nextInt(100);
        if (!state) {
            if (val < 80) {
                return true;
            }
            return false;
        }
        else if (val < 20) {
            return false;
        }
        return true;
    }
}
