import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides solutions algorithms for analyzing and categorizing employee shift data
 * based on specific criteria outlined in the problem statements.
 *
 * The class includes methods to identify:
 * - Regular employees who worked for 7 consecutive days.
 * - Employees who have less than 10 hours of time between shifts but greater than 1 hour.
 * - Hard-working employees Who worked for more than 14 hours in a single shift.
 *
 * Additionally, utility methods are included to calculate time differences and
 * days differences between LocalDateTime instances.
 */
public class AssignmentSolution {

    /**
     * Identifies regular employees based on a consecutive day shift pattern solving STATEMENT-A.
     * The algorithm processes the provided employee shift data and stores the identified
     * regular employees in a set to ensure uniqueness.
     *
     * The method iterates through the employee shift data and compares each entry with the
     * previously processed data to determine if it belongs to the same employee. If it does,
     * it checks if the current day is consecutive to the previous day. If yes, it increments
     * a score for the employee. Once the score reaches 7, indicating seven consecutive days,
     * the employee is added to the set of regular employees.
     *
     * @param employees A list of EmployeeShiftData representing employee shift data.
     * @return A list of EmployeeShiftData for regular employees based on consecutive day shifts.
     */
    public static List<EmployeeShiftData> getRegularEMP(List<EmployeeShiftData> employees) {

        // Set to store regular employees without duplicates
        Set<EmployeeShiftData> regularEmployees = new HashSet<>();

        // Variable to track the previous employee for comparison
        EmployeeShiftData prvEmployee = null;

        // Score to track consecutive days for each employee
        int score = 1;

        // Iterate through the provided employee shift data
        for(EmployeeShiftData data : employees){
            // Check if the current data entry belongs to the same employee
            if(data.equals(prvEmployee)){
                // Check if the current day is consecutive to the previous day
                if(daysDifference(data.getTime(), prvEmployee.getTime()) == 0){
                    prvEmployee = data;
                    continue;
                } else if (daysDifference(data.getTime(), prvEmployee.getTime()) == 1) {
                    // Increment the score for consecutive days
                    score++;
                    // Check if the employee has worked for seven consecutive days
                    if(score == 7){
                        // Reset the score and add the employee to the set of regular employees
                        score = 0;
                        regularEmployees.add(data);
                    }
                } else {
                    // If the current day is not consecutive, reset the score
                    score = 1;
                }
            } else {
                // If the current data entry is for a different employee, reset the score
                score = 1;
            }

            // Update the previous employee for the next iteration
            prvEmployee = data;
        }

        // Convert the set of regular employees to a list and return
        return regularEmployees.stream().toList();
    }

    /**
     * Identifies employees with long resting periods between consecutive shifts, solving STATEMENT-B.
     * The algorithm uses a set to maintain uniqueness and iterates through the provided employee shift data.
     * It checks if the time gap between shifts of an employee is greater than 1 hour and less than 10 hours.
     *
     * The method creates a set of EmployeeShiftData representing employees with long resting periods
     * based on the specified criteria. Duplicate entries are avoided by using a set.
     *
     * @param employees A list of EmployeeShiftData representing employee shift data.
     * @return A list of EmployeeShiftData for employees with long resting periods between shifts.
     */
    public static List<EmployeeShiftData> getLongRestingEMP(List<EmployeeShiftData> employees) {
        // Set to store employees with long resting periods without duplicates
        Set<EmployeeShiftData> longRestingEmployee = new HashSet<>();

        // Variable to track the previous employee for comparison
        EmployeeShiftData prvEmployee = null;

        // Iterate through the provided employee shift data
        for(EmployeeShiftData data : employees){
            // Check if the current data entry belongs to the same employee and the time gap is within the specified range
            if(data.equals(prvEmployee) &&
                    timeDifference(prvEmployee.getTimeOut(), data.getTime()) > 1 &&
                    timeDifference(prvEmployee.getTimeOut(), data.getTime()) < 10){
                // Add the employee to the set of employees with long resting periods
                longRestingEmployee.add(data);
            }
            // Update the previous employee for the next iteration
            prvEmployee = data;
        }

        // Convert the set of employees with long resting periods to a list and return
        return longRestingEmployee.stream().toList();
    }

    /**
     * Identifies hard-working employees based on extended shift durations, solving STATEMENT-C.
     * The algorithm iterates through the provided employee shift data and checks if the duration
     * of the current shift exceeds 14 hours.
     *
     * The method uses Java Streams to filter the employee shift data, selecting only those entries
     * representing shifts of more than 14 hours. The resulting set of EmployeeShiftData is then
     * converted to a list.
     *
     * @param employees A list of EmployeeShiftData representing employee shift data.
     * @return A list of EmployeeShiftData for hard-working employees with shifts exceeding 14 hours.
     */
    public static List<EmployeeShiftData> getHardWorkingEMP(List<EmployeeShiftData> employees) {
        // Use Java Streams to filter employee shift data based on shift duration
        return employees.stream()
                .filter(
                        x -> timeDifference(x.getTime(), x.getTimeOut()) > 14
                )
                // Collect the filtered data into a set to ensure uniqueness
                .collect(Collectors.toSet())
                // Convert the set of hard-working employees to a list and return
                .stream()
                .toList();
    }


    private static long timeDifference(LocalDateTime t1, LocalDateTime t2){
        return Math.abs(Duration.between(t1, t2).toHours());
    }

    private static long daysDifference(LocalDateTime t1, LocalDateTime t2){
        return Math.abs(t1.getDayOfYear() - t2.getDayOfYear());
    }

}
