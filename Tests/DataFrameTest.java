package Tests;
import org.junit.jupiter.api.Test;

import DataElement;
import DataFrame;
import DataFrameException;
import DoubleElement;
import IntElement;
import StringElement;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataFrameTest {

    @Test
    // Test case for subsetting a DataFrame based on a condition
    public void testSubsetDataFrame() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        try {
            // Subset the DataFrame where "tripduration" is greater than 500
            DataFrame subset = df.subsetDataFrame("tripduration > 500");
            // Check if the size of the subset DataFrame is as expected
            assertEquals(expectedSize, subset.getDataRows().size());
        } catch (DataFrameException e) {
            // If an exception is thrown, the test fails
            fail("Exception should not be thrown.");
        }
    }

    @Test
    // Test case for exporting a DataFrame to a CSV file
    public void testExportToCSV() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        try {
            // Export the DataFrame to a CSV file
            df.exportToCSV("test_export.csv");
            // Check if the exported file exists
            File file = new File("test_export.csv");
            assertTrue(file.exists() && !file.isDirectory());
        } catch (DataFrameException e) {
            // If an exception is thrown, the test fails
            fail("Exception should not be thrown.");
        }
    }

    @Test
    // Test case for calculating the average of a column
    public void testAverageColumn() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        try {
            // Calculate the average of the "tripduration" column
            double avg = df.averageColumn("tripduration");
            // Check if the calculated average is as expected
            assertEquals(expectedAverage, avg);
        } catch (DataFrameException e) {
            // If an exception is thrown, the test fails
            fail("Exception should not be thrown.");
        }
    }

    @Test
    // Test case for finding the minimum value of a column
    public void testMinColumn() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        try {
            // Find the minimum value of the "tripduration" column
            double min = df.minColumn("tripduration");
            // Check if the found minimum is as expected
            assertEquals(expectedMin, min);
        } catch (DataFrameException e) {
            // If an exception is thrown, the test fails
            fail("Exception should not be thrown.");
        }
    }

    @Test
    // Test case for finding the maximum value of a column
    public void testMaxColumn() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        try {
            // Find the maximum value of the "tripduration" column
            double max = df.maxColumn("tripduration");
            // Check if the found maximum is as expected
            assertEquals(expectedMax, max);
        } catch (DataFrameException e) {
            // If an exception is thrown, the test fails
            fail("Exception should not be thrown.");
        }
    }

    @Test
    // Test case for creating a frequency table of a column
    public void testFrequencyTable() {
        DataFrame df = new DataFrame();
        // Import a CSV file into the DataFrame
        df.importCsv("Divvy_Trips_July2023.csv");
        try {
            // Create a frequency table of the "tripduration" column
            Map<Double, Integer> freqTable = df.frequencyTable("tripduration");
            // Check if the created frequency table is as expected
            assertEquals(expectedFreqTable, freqTable);
        } catch (DataFrameException e) {
            // If an exception is thrown, the test fails
            fail("Exception should not be thrown.");
        }
    }

    @Test
    // Test case for getting and setting the data types of a DataFrame
    public void testGetAndSetDataTypes() {
        DataFrame df = new DataFrame();
        // Set the data types of the DataFrame
        List<String> dataTypes = Arrays.asList("int", "String", "String", "int", "int", "int", "String", "int", "String", "String", "String", "int");
        df.setDataTypes(dataTypes);
        // Check if the set data types are as expected
        assertEquals(dataTypes, df.getDataTypes());
    }

    @Test
    // Test case for getting and adding data rows to a DataFrame
    public void testGetAndAddDataRows() {
        DataFrame df = new DataFrame();
        // Create a data row
        List<DataElement> row = Arrays.asList(new IntElement(1), new StringElement("test"), new DoubleElement(1.0));
        // Add the data row to the DataFrame
        df.addDataRow(row);
        // Check if the size of the DataFrame is as expected
        assertEquals(1, df.getDataRows().size());
        // Check if the added data row is as expected
        assertEquals(row, df.getDataRows().get(0));
    }

    @Test
    // Test case for handling non-existent columns when calculating the average
    public void testAverageNonExistentColumn() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        // Check if an exception is thrown when trying to calculate the average of a non-existent column
        assertThrows(DataFrameException.class, () -> df.averageColumn("non_existent_column"));
    }

    @Test
    // Test case for handling non-numeric columns when calculating the average
    public void testAverageNonNumericColumn() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        // Check if an exception is thrown when trying to calculate the average of a non-numeric column
        assertThrows(DataFrameException.class, () -> df.averageColumn("starttime"));
    }

    @Test
    // Test case for handling null values in data
    public void testNullValueInData() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        // Add a row with a null value
        List<DataElement> row = new ArrayList<>(df.getDataRows().get(0));
        row.set(0, null);
        df.addDataRow(row);
        // Test methods that should handle null values
        assertDoesNotThrow(() -> df.averageColumn("tripduration"));
        assertDoesNotThrow(() -> df.minColumn("tripduration"));
        assertDoesNotThrow(() -> df.maxColumn("tripduration"));
        assertDoesNotThrow(() -> df.frequencyTable("tripduration"));
    }

    @Test
    // Test case for handling null values when setting a value
    public void testNullValueInSetValue() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        // Check if an exception is thrown when trying to set a null value
        assertThrows(IllegalArgumentException.class, () -> df.getDataRows().get(0).get(0).setValue(null));
    }

    @Test
    // Test case for handling null values when comparing values
    public void testNullValueInCompareTo() {
        DataFrame df = new DataFrame();
        // Load data from a CSV file into the DataFrame
        df.loadDataFromCSV("Divvy_Trips_July2023.csv");
        // Check if an exception is thrown when trying to compare a value with a null value
        assertThrows(IllegalArgumentException.class, () -> df.getDataRows().get(0).get(0).compareTo(null));
    }
}