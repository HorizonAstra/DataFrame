# DataFrame System

## Overview
This project is a **DataFrame System** implemented in Java, designed to manage and analyze tabular data. The system includes functionality to:
1. Import and export CSV files.
2. Perform column-based operations (e.g., average, minimum, maximum).
3. Create frequency tables and subsets of data.
4. Run an interactive menu-based system for user interaction.


## Components

### 1. DataElement Interface
The `DataElement` interface defines the core operations for all types of data elements used in the system:
- **Methods**:
  - `Object getValue()`: Retrieves the value of the element.
  - `void setValue(Object value)`: Sets the value of the element.
  - `int compareTo(DataElement other)`: Compares the current element to another element.

### 2. DataElement Implementations
Each implementation represents a specific data type.

- **IntElement.java**: Handles integer data.
  - Validates that the value is an integer.
- **DoubleElement.java**: Handles double-precision floating-point numbers.
  - Validates that the value is a `Double`.
- **CharElement.java**: Handles single characters.
  - Validates that the value is a `Character`.
- **StringElement.java**: Handles string data.
  - Validates that the value is a `String`.

### 3. DataFrame
The `DataFrame` class provides a robust structure to store and manage tabular data:
- **Core Features**:
  - Column operations: `averageColumn`, `minColumn`, `maxColumn`.
  - Frequency tables for numerical data.
  - Subsets of data based on conditions.
  - CSV import/export for interoperability.
- **Key Methods**:
  - `void importCSV(String filePath)`: Imports data from a CSV file.
  - `void exportToCSV(String fileName)`: Exports data to a CSV file.
  - `double averageColumn(String columnName)`: Calculates the average of a column.
  - `double minColumn(String columnName)`: Finds the minimum value in a column.
  - `double maxColumn(String columnName)`: Finds the maximum value in a column.
  - `DataFrame subsetDataFrame(String condition)`: Creates a subset of the data based on a condition.

### 4. DataFrameMenu
This class provides an interactive, menu-driven interface for users to interact with the `DataFrame`:
- **Key Features**:
  - Import CSV files and create new DataFrames.
  - Perform operations like averaging, finding min/max, and creating frequency tables.
  - Subset the active DataFrame based on conditions.
  - Export the active DataFrame to a CSV file.

### 5. Tests
Unit tests are included for comprehensive validation:
- **DataElementTest.java**:
  - Validates the behavior of `IntElement`, `DoubleElement`, `CharElement`, and `StringElement`.
  - Includes tests for `getValue`, `setValue`, and `compareTo`.
- **DataFrameTest.java**:
  - Tests column operations (`averageColumn`, `minColumn`, `maxColumn`).
  - Validates frequency table creation and subset functionality.
  - Ensures CSV export works as expected.
- **DataFrameMenuTest.java**:
  - Tests menu operations like CSV import/export, column operations, and subset creation.
  - Simulates user interaction with the menu.

### 6. Main
The entry point for running the application. It creates a `DataFrameMenu` instance and starts the interactive menu.


## Future Improvements

1. Add support for new data types (e.g., DateElement).
2. Enhance subset filtering with advanced condition parsing (e.g., parentheses for logical grouping).
3. Support more advanced statistical operations (e.g., standard deviation, variance).
4. Implement a GUI for a more user-friendly experience.
 
