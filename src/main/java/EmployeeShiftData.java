import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents employee shift data and provides a structured container for mapping valid data.
 * Instances of this class encapsulate information such as position ID, employee name,
 * start time, and end time for valid shifts.
 *
 * This class leverages Lombok annotations to automatically generate common boilerplate code:
 * - @Builder: Provides a convenient builder pattern for constructing instances.
 * - @Getter: Generates getters for all fields.
 * - @Setter: Generates setters for all fields.
 * - @NoArgsConstructor: Generates a no-argument constructor.
 * - @AllArgsConstructor: Generates an all-argument constructor.
 *
 * The overridden equals method ensures equality comparison based on the 'positionId' field.
 * Two instances are considered equal if their 'positionId' values are equal.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeShiftData {
    private String positionId;
    private String employeeName;
    private LocalDateTime time;
    private LocalDateTime timeOut;

    /**
     * Overrides the default equals method to enable custom equality comparison.
     * Two EmployeeShiftData instances are considered equal if their 'positionId' values are equal.
     *
     * @param o The object to compare for equality.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeShiftData employee = (EmployeeShiftData) o;
        return Objects.equals(positionId, employee.positionId);
    }
}
