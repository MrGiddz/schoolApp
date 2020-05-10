package main.resources.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProgressWorker {

    private static DoubleProperty progressCounter = new SimpleDoubleProperty(0);

    private static StringProperty progressString = new SimpleStringProperty("");

    private static double getProgressCounter() {
        if (progressCounter != null)
            return progressCounter.get();
        return 0;
    }

    public static StringProperty progressStringProperty() {
        return progressString;
    }

    public static void setProgressString() {
        ProgressWorker.progressString.set(String.valueOf((int)(getProgressCounter() * 100)).concat("%"));
    }

    public static DoubleProperty progressCounterProperty() {
        if (progressCounter == null)
            progressCounter = new SimpleDoubleProperty(0);
        return progressCounter;
    }

    public static void setProgressCounter(double progressCounter) {
        ProgressWorker.progressCounter.set(progressCounter);
    }
}
