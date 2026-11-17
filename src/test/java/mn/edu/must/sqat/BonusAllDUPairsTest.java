package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BonusAllDUPairsTest {

    @Test
    @DisplayName("DU attendance:31->32(True) - null input")
    void nullAttendance_coversNullBranch() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(null, 0)
        );
    }

    @Test
    @DisplayName("DU threshold:31->32(True) - negative threshold")
    void negativeThreshold_coversInvalidThresholdBranch() {
        assertThrows(
                IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(new int[]{1}, -1)
        );
    }

    @Test
    @DisplayName("DU attendance:31->38(False), present:35->50, best:37->53, score:51->56")
    void emptyAttendance_coversZeroIterationPairs() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{}, 0));
    }

    @Test
    @DisplayName("DU streak:46->41, a:38->39(False/True), score:49->56")
    void zeroThenPresent_coversResetDefinition() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{0, 1}, 3
        ));
    }

    @Test
    @DisplayName("DU present:35->40, 40->40/50; streak:36->41, 41->41/42(True)/43; best:37->42, 43->42/53")
    void consecutivePresent_coversLoopDataflowPairs() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{1, 1}, 3
        ));
    }

    @Test
    @DisplayName("DU streak:41->42(False), streak:46->41")
    void resetBetweenPresentDays_coversFalseStreakBranch() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{1, 0, 1}, 4
        ));
    }

    @Test
    @DisplayName("DU score:49->54->56 - bonus without threshold score")
    void longStreakBelowThreshold_coversDirectBonus() {
        assertEquals(3, DataFlowTargets.bonus(
                new int[]{1, 1, 1, 1, 1}, 6
        ));
    }

    @Test
    @DisplayName("DU score:51->54->56 - threshold and streak bonuses")
    void longStreakAtThreshold_coversScoreDefinitions() {
        assertEquals(8, DataFlowTargets.bonus(
                new int[]{1, 1, 1, 1, 1}, 5
        ));
    }

    @Test
    @DisplayName("M2 kill: best must store longest streak, not total present days")
    void bestUsesLongestStreakNotTotalPresentDays() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{1, 1, 0, 1, 1, 1, 1}, 7
        ));
    }
}
