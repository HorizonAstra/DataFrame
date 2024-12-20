import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DataElementTest {

    @Test
    public void testIntElement() {
        IntElement intElement = new IntElement(5);
        assertEquals(5, intElement.getValue());

        intElement.setValue(10);
        assertEquals(10, intElement.getValue());

        assertThrows(IllegalArgumentException.class, () -> intElement.setValue("string"));
    }

    @Test
    public void testDoubleElement() {
        DoubleElement doubleElement = new DoubleElement(5.5);
        assertEquals(5.5, doubleElement.getValue());

        doubleElement.setValue(10.0);
        assertEquals(10.0, doubleElement.getValue());

        assertThrows(IllegalArgumentException.class, () -> doubleElement.setValue("string"));
    }

    @Test
    public void testCharElement() {
        CharElement charElement = new CharElement('a');
        assertEquals('a', charElement.getValue());

        charElement.setValue('z');
        assertEquals('z', charElement.getValue());

        assertThrows(IllegalArgumentException.class, () -> charElement.setValue(5));
    }

    @Test
    public void testStringElement() {
        StringElement stringElement = new StringElement("hello");
        assertEquals("hello", stringElement.getValue());

        stringElement.setValue("world");
        assertEquals("world", stringElement.getValue());

        assertThrows(IllegalArgumentException.class, () -> stringElement.setValue(5));
    }

    @Test
    public void testCompareTo() {
        IntElement intElement1 = new IntElement(5);
        IntElement intElement2 = new IntElement(10);
        assertTrue(intElement1.compareTo(intElement2) < 0);

        DoubleElement doubleElement1 = new DoubleElement(5.5);
        DoubleElement doubleElement2 = new DoubleElement(3.3);
        assertTrue(doubleElement1.compareTo(doubleElement2) > 0);

        CharElement charElement1 = new CharElement('a');
        CharElement charElement2 = new CharElement('b');
        assertTrue(charElement1.compareTo(charElement2) < 0);

        StringElement stringElement1 = new StringElement("apple");
        StringElement stringElement2 = new StringElement("banana");
        assertTrue(stringElement1.compareTo(stringElement2) < 0);
    }
}
