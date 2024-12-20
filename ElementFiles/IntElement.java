public class IntElement implements DataElement {
    private int value;

    public IntElement(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Integer) {
            this.value = (Integer) value;
        } else {
            throw new IllegalArgumentException("Invalid value type for IntElement.");
        }
    }

    @Override
    public int compareTo(DataElement other) {
        if (!(other instanceof IntElement)) {
            throw new IllegalArgumentException("Invalid comparison between IntElement and " + other.getClass().getSimpleName());
        }
        return Integer.compare(this.value, ((IntElement) other).value);
    }
}
