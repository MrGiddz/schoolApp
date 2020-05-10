package main.resources.utilities.actions;

public class IOActions<T> {

        private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public IOActions(T object) {
        this.object = object;
    }
}
