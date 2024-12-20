import java.io.*;
import java.util.*;

public class DataFrame {
    private List<String> columnHeaders;
    private List<String> dataTypes;
    private List<List<DataElement>> dataRows;

    public DataFrame() {
        this.columnHeaders = new ArrayList<>();
        this.dataTypes = new ArrayList<>();
        this.dataRows = new ArrayList<>();
    }

    // Validate column name and type
    private int validateColumn(String columnName, String expectedType) throws DataFrameException {
        int columnIndex = columnHeaders.indexOf(columnName);
        if (columnIndex == -1) {
            throw new DataFrameException("Column '" + columnName + "' does not exist.");
        }
        if (!dataTypes.get(columnIndex).equals(expectedType)) {
            throw new DataFrameException("Column '" + columnName + "' is not of type '" + expectedType + "'.");
        }
        return columnIndex;
    }

    // Calculate the average value of a column with the given name.
    public double averageColumn(String columnName) throws DataFrameException {
        int columnIndex = validateColumn(columnName, "double");
        double sum = 0;
        int count = 0;

        for (List<DataElement> row : dataRows) {
            DataElement element = row.get(columnIndex);
            if (element instanceof DoubleElement) {
                sum += ((DoubleElement) element).getValue();
                count++;
            }
        }

        if (count == 0) {
            throw new DataFrameException("No valid data to calculate average in column '" + columnName + "'.");
        }

        return sum / count;
    }

    // Find the minimum value in a column with the given name.
    public double minColumn(String columnName) throws DataFrameException {
        int columnIndex = validateColumn(columnName, "double");
        double min = Double.POSITIVE_INFINITY;

        for (List<DataElement> row : dataRows) {
            DataElement element = row.get(columnIndex);
            if (element instanceof DoubleElement) {
                min = Math.min(min, ((DoubleElement) element).getValue());
            }
        }

        if (min == Double.POSITIVE_INFINITY) {
            throw new DataFrameException("No valid data to find minimum in column '" + columnName + "'.");
        }

        return min;
    }

    // Find the maximum value in a column with the given name.
    public double maxColumn(String columnName) throws DataFrameException {
        int columnIndex = validateColumn(columnName, "double");
        double max = Double.NEGATIVE_INFINITY;

        for (List<DataElement> row : dataRows) {
            DataElement element = row.get(columnIndex);
            if (element instanceof DoubleElement) {
                max = Math.max(max, ((DoubleElement) element).getValue());
            }
        }

        if (max == Double.NEGATIVE_INFINITY) {
            throw new DataFrameException("No valid data to find maximum in column '" + columnName + "'.");
        }

        return max;
    }

    // Create a frequency table for a column with the given name.
    public Map<String, Integer> frequencyTable(String columnName, int intervalCount) throws DataFrameException {
        int columnIndex = validateColumn(columnName, "double");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        // Find min and max
        for (List<DataElement> row : dataRows) {
            DataElement element = row.get(columnIndex);
            if (element instanceof DoubleElement) {
                double value = ((DoubleElement) element).getValue();
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
        }

        if (Double.isInfinite(min) || Double.isInfinite(max)) {
            throw new DataFrameException("No valid data to create frequency table in column '" + columnName + "'.");
        }

        // Calculate interval size
        double intervalSize = (max - min) / intervalCount;
        Map<String, Integer> frequencyTable = new LinkedHashMap<>();

        // Initialize intervals
        for (int i = 0; i < intervalCount; i++) {
            double start = min + i * intervalSize;
            double end = (i == intervalCount - 1) ? max : start + intervalSize;
            frequencyTable.put(String.format("[%.2f - %.2f)", start, end), 0);
        }

        // Populate frequency table
        for (List<DataElement> row : dataRows) {
            DataElement element = row.get(columnIndex);
            if (element instanceof DoubleElement) {
                double value = ((DoubleElement) element).getValue();
                for (String range : frequencyTable.keySet()) {
                    String[] parts = range.replace("[", "").replace(")", "").split(" - ");
                    double rangeStart = Double.parseDouble(parts[0]);
                    double rangeEnd = Double.parseDouble(parts[1]);

                    if (value >= rangeStart && value < rangeEnd) {
                        frequencyTable.put(range, frequencyTable.get(range) + 1);
                        break;
                    }
                }
            }
        }

        return frequencyTable;
    }

    // Create a subset DataFrame based on the given condition.
    public DataFrame subsetDataFrame(String condition) throws DataFrameException {
        String[] conditions = condition.split(" AND | OR ");
        boolean isAnd = condition.contains(" AND ");

        DataFrame subset = new DataFrame();
        subset.columnHeaders = new ArrayList<>(this.columnHeaders);
        subset.dataTypes = new ArrayList<>(this.dataTypes);

        for (List<DataElement> row : this.dataRows) {
            boolean matches = isAnd;

            for (String cond : conditions) {
                String[] parts = cond.split(" ");
                if (parts.length < 3) {
                    throw new DataFrameException("Invalid condition format. Example: 'columnName > value'.");
                }

                String columnName = parts[0];
                String operator = parts[1];
                String valueStr = parts[2];
                int columnIndex = validateColumn(columnName, dataTypes.get(columnHeaders.indexOf(columnName)));

                DataElement element = row.get(columnIndex);
                boolean match = false;

                switch (operator) {
                    case "<":
                        match = element.compareTo(new DoubleElement(Double.parseDouble(valueStr))) < 0;
                        break;
                    case "==":
                        match = element.getValue().toString().equals(valueStr);
                        break;
                    case ">":
                        match = element.compareTo(new DoubleElement(Double.parseDouble(valueStr))) > 0;
                        break;
                    case "!=":
                        match = !element.getValue().toString().equals(valueStr);
                        break;
                    default:
                        throw new DataFrameException("Invalid operator '" + operator + "'.");
                }

                if (isAnd) {
                    matches = matches && match;
                } else {
                    matches = matches || match;
                }
            }

            if (matches) {
                subset.dataRows.add(new ArrayList<>(row));
            }
        }

        return subset;
    }

    // Export the DataFrame to a CSV file with the given file name.
    public void exportToCSV(String fileName) throws DataFrameException {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            writer.println(String.join(",", columnHeaders));
            writer.println(String.join(",", dataTypes));

            for (List<DataElement> row : dataRows) {
                List<String> rowValues = new ArrayList<>();
                for (DataElement element : row) {
                    rowValues.add(element.getValue().toString());
                }
                writer.println(String.join(",", rowValues));
            }
        } catch (FileNotFoundException e) {
            throw new DataFrameException("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Accessors and Mutators
    public List<String> getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(List<String> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public List<String> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<String> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public List<List<DataElement>> getDataRows() {
        return dataRows;
    }

    public void addDataRow(List<DataElement> dataRow) {
        this.dataRows.add(dataRow);
    }
}
