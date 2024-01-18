import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /**
         * Loading and Mapping valid data with "EmployeeShiftData class" object from "Assignment_Timecard.xlsx"
         * using "EmployeeShiftDataLoader class".
         * All the solution algorithm as mentioned in the assignment is present in the "AssignmentSolution class"
         * Executing the solution algorithm by calling the appropriate method with the loaded data.
         */

        /**
         * Specifying the name of Excel file (Assignment_Timecard.xlsx)
         * Note: "Assignment_Timecard.xlsx" is expected to be present in the "resources" directory
         */
        List<EmployeeShiftData> employees = EmployeeShiftDataLoader.loadEmployeeData("Assignment_Timecard.xlsx");

        /**
         * Solution data for STATEMENT-A(UNIQUE-DATA): Employees who worked for 7 consecutive days.
         */
        List<EmployeeShiftData> regularEmployees = AssignmentSolution.getRegularEMP(employees);

        /**
         * Solution data for STATEMENT-B(UNIQUE-DATA): Employees who have less than 10 hours of time between shifts but greater than 1 hour
         */
        List<EmployeeShiftData> longRestingEmployees = AssignmentSolution.getLongRestingEMP(employees);

        /**
         * Solution data for STATEMENT-C(UNIQUE-DATA): Employees Who worked for more than 14 hours in a single shift
         */
        List<EmployeeShiftData> hardWorkingEmployees = AssignmentSolution.getHardWorkingEMP(employees);

        System.out.println("Output of STATEMENT-A(Regular employees): ");
        printData(regularEmployees);

        System.out.println("Output of STATEMENT-B(Long Resting Employees): ");
        printData(longRestingEmployees);

        System.out.println("Output of STATEMENT-C(Hard Working Employees):");
        printData(hardWorkingEmployees);
    }

    /**
     * Prints the final data
     *
     * @param employeeShiftData
     */
    private static void printData(List<EmployeeShiftData> employeeShiftData){
        System.out.println("Employee Name \t\t Position ID");
        System.out.println("------------------------------------");
        employeeShiftData.stream()
                .sorted(Comparator.comparing(EmployeeShiftData::getPositionId))
                .forEach(
                        x -> System.out.println(x.getEmployeeName() + "\t\t" + x.getPositionId())
                );
        System.out.println("------------------------------------");
        System.out.println("\n");
    }
}
