package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BonusAllDefsTest {

    @Test
    @DisplayName("All-defs: attendance parameter definition to null check")
    void nullAttendance_coversParameterDefinition() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(null, 0)
        );
    }

    @Test
    @DisplayName("All-defs: threshold parameter definition to negative check")
    void negativeThreshold_coversThresholdDefinition() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(new int[]{1}, -1)
        );
    }

    @Test
    @DisplayName("All-defs: present, streak, best, a and score definitions")
    void mixedAttendance_coversLoopDefinitions() {
        assertEquals(5, DataFlowTargets.bonus(
                new int[]{1, 1, 0, 1}, 3
        ));
    }

    @Test
    @DisplayName("All-defs: score definition at line 49 reaches line 54")
    void longStreak_coversScoreAdditionDefinition() {
        assertEquals(3, DataFlowTargets.bonus(
                new int[]{1, 1, 1, 1, 1}, 6
        ));
    }

    @Test
    @DisplayName("All-defs: score definition at line 49 reaches return")
    void onePresentDay_coversScoreReturnDefinition() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{1}, 2
        ));
    }
}
