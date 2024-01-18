import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilizes the EmployeeShiftDataLoader class to load and map valid employee shift data
 * from the "Assignment_Timecard.xlsx" file.
 */
public class EmployeeShiftDataLoader {

    /**
     * Fetches employee shift data from the specified Excel file.
     *
     * @param excelFile The path or resource location of the Excel file.
     * @return A list of EmployeeShiftData objects representing the loaded data.
     */
    public static List<EmployeeShiftData> loadEmployeeData(String excelFile) {

        // Retrieve the Excel file as an InputStream using the class loader
        InputStream inputStream = EmployeeShiftDataLoader.class.getClassLoader().getResourceAsStream(excelFile);

        Workbook workbook = null;
        Sheet sheet = null;

        try {
            // Create a Workbook instance from the InputStream
            workbook = WorkbookFactory.create(inputStream);

            // Get the first sheet from the workbook
            sheet = workbook.getSheetAt(0);

            // Close the workbook to release resources
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Map the loaded data using the ORM helper method
        return ormHelper(sheet);
    }

    /**
     * Maps the loaded data from the specified Excel sheet to a list of EmployeeShiftData objects.
     *
     * @param sheet The Excel sheet containing employee shift data.
     * @return A list of EmployeeShiftData objects representing the mapped data.
     */
    private static List<EmployeeShiftData> ormHelper(Sheet sheet) {
        List<EmployeeShiftData> employees = new ArrayList<>();

        // Iterate through each row in the sheet
        for (Row row : sheet) {
            Cell positionIdCell = row.getCell(0);
            Cell timeCell = row.getCell(2);
            Cell timeOutCell = row.getCell(3);
            Cell employeeNameCell = row.getCell(7);

            // Check if the cell content is a valid date-time value
            if (timeCell != null && timeCell.getCellType() == CellType.NUMERIC && timeOutCell != null && timeOutCell.getCellType() == CellType.NUMERIC) {
                // Create an EmployeeShiftData object and add it to the list
                employees.add(createEmployee(positionIdCell, timeCell, timeOutCell, employeeNameCell));
            }
        }

        return employees;
    }

    /**
     * Creates an EmployeeShiftData object with the given fields extracted from Excel cells.
     * Converts numeric date-time values to LocalDateTime.
     *
     * @param positionIdCell     The Excel cell containing the position ID.
     * @param timeCell           The Excel cell containing the start time (numeric date-time value).
     * @param timeOutCell        The Excel cell containing the end time (numeric date-time value).
     * @param employeeNameCell   The Excel cell containing the employee name.
     * @return An EmployeeShiftData object representing the extracted data.
     */
    private static EmployeeShiftData createEmployee(Cell positionIdCell, Cell timeCell, Cell timeOutCell, Cell employeeNameCell) {
        // Extract numeric date-time values from Excel cells and convert to LocalDateTime
        double timeNum = timeCell.getNumericCellValue();
        LocalDateTime time = LocalDateTime.ofEpochSecond(
                (long) ((timeNum - 25569) * 86400), 0, ZoneOffset.UTC);

        double timeOutNum = timeOutCell.getNumericCellValue();
        LocalDateTime timeOut = LocalDateTime.ofEpochSecond(
                (long) ((timeOutNum - 25569) * 86400), 0, ZoneOffset.UTC);

        // Extract position ID and employee name from Excel cells
        String positionId = positionIdCell.getStringCellValue();
        String employeeName = employeeNameCell.getStringCellValue();

        // Create and return an EmployeeShiftData object using Lombok @Builder
        return EmployeeShiftData.builder()
                .employeeName(employeeName)
                .positionId(positionId)
                .time(time)
                .timeOut(timeOut)
                .build();
    }
}
