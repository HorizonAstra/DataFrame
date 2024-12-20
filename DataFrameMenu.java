import java.io.*;
import java.util.*;

public class DataFrameMenu {
    private List<DataFrame> dataFrames;
    private DataFrame activeDataFrame;

    public DataFrameMenu() {
        this.dataFrames = new ArrayList<>();
        this.activeDataFrame = null;
    }

    // Import data from a CSV file and create a new DataFrame.
    public void importCSV(String filePath) throws DataFrameException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line == null) {
                throw new DataFrameException("Empty CSV file.");
            }
            List<String> columnHeaders = Arrays.asList(line.split(","));
            line = reader.readLine();
            if (line == null) {
                throw new DataFrameException("Missing data types in CSV file.");
            }
            List<String> dataTypes = Arrays.asList(line.split(","));
            if (columnHeaders.size() != dataTypes.size()) {
                throw new DataFrameException("Mismatch between column headers and data types.");
            }

            DataFrame dataFrame = new DataFrame();
            dataFrame.setColumnHeaders(columnHeaders);
            dataFrame.setDataTypes(dataTypes);

            while ((line = reader.readLine()) != null) {
                List<DataElement> row = new ArrayList<>();
                String[] values = line.split(",");
                if (values.length != columnHeaders.size()) {
                    throw new DataFrameException("Mismatch between data and column headers.");
                }

                for (int i = 0; i < values.length; i++) {
                    DataElement element;
                    switch (dataTypes.get(i)) {
                        case "int":
                            element = new IntElement(Integer.parseInt(values[i]));
                            break;
                        case "double":
                            element = new DoubleElement(Double.parseDouble(values[i]));
                            break;
                        case "char":
                            element = new CharElement(values[i].charAt(0));
                            break;
                        case "String":
                            element = new StringElement(values[i]);
                            break;
                        default:
                            throw new DataFrameException("Invalid data type: " + dataTypes.get(i));
                    }
                    row.add(element);
                }
                dataFrame.addDataRow(row);
            }

            dataFrames.add(dataFrame);
            activeDataFrame = dataFrame;
            System.out.println("CSV imported successfully. Active DataFrame set to the new one.");
        } catch (IOException e) {
            throw new DataFrameException("Error reading CSV file: " + e.getMessage());
        }
    }

    // Change the active DataFrame to the one at the specified index.
    public void changeActiveDataFrame(int index) throws DataFrameException {
        if (index < 0 || index >= dataFrames.size()) {
            throw new DataFrameException("Invalid DataFrame index.");
        }
        this.activeDataFrame = dataFrames.get(index);
        System.out.println("Active DataFrame changed to index: " + index);
    }

    // Calculate the average value of a column in the active DataFrame.
    public void averageColumn(String columnName) {
        try {
            if (activeDataFrame == null) {
                throw new DataFrameException("No active DataFrame.");
            }
            double average = activeDataFrame.averageColumn(columnName);
            System.out.println("Average of column '" + columnName + "': " + average);
        } catch (DataFrameException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Find the minimum value in a column in the active DataFrame.
    public void minColumn(String columnName) {
        try {
            if (activeDataFrame == null) {
                throw new DataFrameException("No active DataFrame.");
            }
            double min = activeDataFrame.minColumn(columnName);
            System.out.println("Minimum of column '" + columnName + "': " + min);
        } catch (DataFrameException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Find the maximum value in a column in the active DataFrame.
    public void maxColumn(String columnName) {
        try {
            if (activeDataFrame == null) {
                throw new DataFrameException("No active DataFrame.");
            }
            double max = activeDataFrame.maxColumn(columnName);
            System.out.println("Maximum of column '" + columnName + "': " + max);
        } catch (DataFrameException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Create a frequency table for a column in the active DataFrame.
    public void frequencyTable(String columnName, int intervalCount) {
        try {
            if (activeDataFrame == null) {
                throw new DataFrameException("No active DataFrame.");
            }
            Map<String, Integer> table = activeDataFrame.frequencyTable(columnName, intervalCount);
            System.out.println("Frequency table for column '" + columnName + "':");
            for (Map.Entry<String, Integer> entry : table.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (DataFrameException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Create a subset DataFrame based on the given condition and set it as active.
    public void subsetDataFrame(String condition) {
        try {
            if (activeDataFrame == null) {
                throw new DataFrameException("No active DataFrame.");
            }
            DataFrame subset = activeDataFrame.subsetDataFrame(condition);
            dataFrames.add(subset);
            activeDataFrame = subset;
            System.out.println("Subset created and set as active DataFrame.");
        } catch (DataFrameException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Export the active DataFrame to a CSV file.
    public void exportToCSV() {
        try {
            if (activeDataFrame == null) {
                throw new DataFrameMenuException("No active DataFrame.");
            }
            int index = dataFrames.indexOf(activeDataFrame);
            String fileName = "DataFrame" + index + ".csv";
            activeDataFrame.exportToCSV(fileName);
            System.out.println("Active DataFrame exported to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Quit the DataFrameMenu.
    public void quit() {
        System.out.println("Thank you for using the DataFrame system. Goodbye!");
    }

    // Display menu options and process user input
    public void startMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Import CSV");
            System.out.println("2. Change active DataFrame");
            System.out.println("3. Average a column");
            System.out.println("4. Find minimum of a column");
            System.out.println("5. Find maximum of a column");
            System.out.println("6. Create frequency table");
            System.out.println("7. Subset DataFrame");
            System.out.println("8. Export DataFrame to CSV");
            System.out.println("9. Quit");

            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1":
                        System.out.println("Enter file path:");
                        String filePath = scanner.nextLine();
                        importCSV(filePath);
                        break;
                    case "2":
                        System.out.println("Enter DataFrame index:");
                        int index = Integer.parseInt(scanner.nextLine());
                        changeActiveDataFrame(index);
                        break;
                    case "3":
                        System.out.println("Enter column name:");
                        String columnName = scanner.nextLine();
                        averageColumn(columnName);
                        break;
                    case "4":
                        System.out.println("Enter column name:");
                        columnName = scanner.nextLine();
                        minColumn(columnName);
                        break;
                    case "5":
                        System.out.println("Enter column name:");
                        columnName = scanner.nextLine();
                        maxColumn(columnName);
                        break;
                    case "6":
                        System.out.println("Enter column name:");
                        columnName = scanner.nextLine();
                        System.out.println("Enter number of intervals:");
                        int intervals = Integer.parseInt(scanner.nextLine());
                        frequencyTable(columnName, intervals);
                        break;
                    case "7":
                        System.out.println("Enter condition (e.g., 'columnName > value'):");
                        String condition = scanner.nextLine();
                        subsetDataFrame(condition);
                        break;
                    case "8":
                        exportToCSV();
                        break;
                    case "9":
                        quit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
