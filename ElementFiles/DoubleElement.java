public class DoubleElement implements DataElement {
    private double value;

    public DoubleElement(double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Double) {
            this.value = (Double) value;
        } else {
            throw new IllegalArgumentException("Invalid value type for DoubleElement.");
        }
    }

    @Override
    public int compareTo(DataElement other) {
        if (!(other instanceof DoubleElement)) {
            throw new IllegalArgumentException("Invalid comparison between DoubleElement and " + other.getClass().getSimpleName());
        }
        return Double.compare(this.value, ((DoubleElement) other).value);
    }
}
