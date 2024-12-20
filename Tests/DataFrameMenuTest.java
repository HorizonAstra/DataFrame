import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;

public class DataFrameMenuTest {

    private DataFrameMenu menu;

    @BeforeEach
    public void setUp() {
        menu = new DataFrameMenu();
    }

    @Test
    public void testImportCSV() throws Exception {
        // Create a temporary CSV file
        File tempFile = File.createTempFile("test", ".csv");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Column1,Column2,Column3");
            writer.println("int,double,String");
            writer.println("1,2.5,hello");
            writer.println("2,3.5,world");
        }

        // Test importing the CSV
        assertDoesNotThrow(() -> menu.importCSV(tempFile.getAbsolutePath()));
    }

    @Test
    public void testChangeActiveDataFrame() throws Exception {
        // Import a CSV file to create a DataFrame
        File tempFile = File.createTempFile("test", ".csv");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Column1,Column2,Column3");
            writer.println("int,double,String");
            writer.println("1,2.5,hello");
        }
        menu.importCSV(tempFile.getAbsolutePath());

        // Test changing the active DataFrame
        assertDoesNotThrow(() -> menu.changeActiveDataFrame(0));
    }

    @Test
    public void testAverageColumn() throws Exception {
        // Import a CSV file to create a DataFrame
        File tempFile = File.createTempFile("test", ".csv");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Column1,Column2,Column3");
            writer.println("int,double,String");
            writer.println("1,2.5,hello");
            writer.println("2,3.5,world");
        }
        menu.importCSV(tempFile.getAbsolutePath());

        // Test averaging a column
        assertDoesNotThrow(() -> menu.averageColumn("Column2"));
    }

    @Test
    public void testMinColumn() throws Exception {
        // Import a CSV file to create a DataFrame
        File tempFile = File.createTempFile("test", ".csv");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Column1,Column2,Column3");
            writer.println("int,double,String");
            writer.println("1,2.5,hello");
            writer.println("2,3.5,world");
        }
        menu.importCSV(tempFile.getAbsolutePath());

        // Test finding minimum in a column
        assertDoesNotThrow(() -> menu.minColumn("Column2"));
    }

    @Test
    public void testMaxColumn() throws Exception {
        // Import a CSV file to create a DataFrame
        File tempFile = File.createTempFile("test", ".csv");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Column1,Column2,Column3");
            writer.println("int,double,String");
            writer.println("1,2.5,hello");
            writer.println("2,3.5,world");
        }
        menu.importCSV(tempFile.getAbsolutePath());

        // Test finding maximum in a column
        assertDoesNotThrow(() -> menu.maxColumn("Column2"));
    }

    @Test
    public void testFrequencyTable() throws Exception {
        // Import a CSV file to create a DataFrame
        File tempFile = File.createTempFile("test", ".csv");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Column1,Column2,Column3");
            writer.println("int,double,String");
            writer.println("1,2.0,hello");
            writer.println("2,4.0,world");
        }
        menu.importCSV(tempFile.getAbsolutePath());

        // Test creating a frequency table
        assertDoesNotThrow(() -> menu.frequencyTable("Column2", 2));
    }

    @Test
    public void testSubsetDataFrame() throws Exception {
        // Import a CSV file to create a DataFrame
        File tempFile = File.createTempFile("test", ".csv");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Column1,Column2,Column3");
            writer.println("int,double,String");
            writer.println("1,2.5,hello");
            writer.println("2,3.5,world");
        }
        menu.importCSV(tempFile.getAbsolutePath());

        // Test creating a subset DataFrame
        assertDoesNotThrow(() -> menu.subsetDataFrame("Column2 > 2.0"));
    }

    @Test
    public void testExportToCSV() throws Exception {
        // Import a CSV file to create a DataFrame
        File tempFile = File.createTempFile("test", ".csv");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Column1,Column2,Column3");
            writer.println("int,double,String");
            writer.println("1,2.5,hello");
            writer.println("2,3.5,world");
        }
        menu.importCSV(tempFile.getAbsolutePath());

        // Test exporting the DataFrame to a CSV
        assertDoesNotThrow(() -> menu.exportToCSV());
    }
}
