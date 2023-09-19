public class Key<E> {
    private double index;
    private E value;
    public Key(double index, E value){
        this.index = index;
        this.value = value;
    }
    public Key(E value){
        this.value = value;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
    public String toString() {
        return "The index: "+index+" holds the value: "+value;
    }
}
