public class StringElement implements DataElement {
    private String value;

    public StringElement(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof String) {
            this.value = (String) value;
        } else {
            throw new IllegalArgumentException("Invalid value type for StringElement.");
        }
    }

    @Override
    public int compareTo(DataElement other) {
        if (!(other instanceof StringElement)) {
            throw new IllegalArgumentException("Invalid comparison between StringElement and " + other.getClass().getSimpleName());
        }
        return this.value.compareTo(((StringElement) other).value);
    }
}