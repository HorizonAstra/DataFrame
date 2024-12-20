public class CharElement implements DataElement {
    private char value; // initialize char value
  
    public CharElement(char value) {
      this.value = value; 
    }
  
    @Override
    public Character getValue() {
      return value; //getter for value
    }
  
    @Override
    public void setValue(Object value) { //setter for value 
      if (value instanceof Character) {
        this.value = (Character) value; 
      } else {
        throw new IllegalArgumentException("Invalid value type for CharElement.");
      }
    }
  
    @Override
    public int compareTo(DataElement other) {
      if (!(other instanceof CharElement)) { // can only compare to another charElement, otherwise must throw an illegal argument exception (not same type)
        throw new IllegalArgumentException(
            "Invalid comparison between CharElement and " + other.getClass().getSimpleName());
      }
      return Character.compare(this.value, ((CharElement) other).value);
    }
  }