public interface DataElement { // calls objects and methods 
    Object getValue(); 
  
    void setValue(Object value);
  
    int compareTo(DataElement other);
  }