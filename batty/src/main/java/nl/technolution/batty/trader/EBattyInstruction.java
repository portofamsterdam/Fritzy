/*
 (C) COPYRIGHT TECHNOLUTION BV, GOUDA NL
| =======          I                   ==          I    =
|    I             I                    I          I
|    I   ===   === I ===  I ===   ===   I  I    I ====  I   ===  I ===
|    I  /   \ I    I/   I I/   I I   I  I  I    I  I    I  I   I I/   I
|    I  ===== I    I    I I    I I   I  I  I    I  I    I  I   I I    I
|    I  \     I    I    I I    I I   I  I  I   /I  \    I  I   I I    I
|    I   ===   === I    I I    I  ===  ===  === I   ==  I   ===  I    I
|                 +---------------------------------------------------+
+----+            |  +++++++++++++++++++++++++++++++++++++++++++++++++|
     |            |             ++++++++++++++++++++++++++++++++++++++|
     +------------+                          +++++++++++++++++++++++++|
                                                        ++++++++++++++|
                                                                 +++++|
 */
package nl.technolution.batty.trader;

import java.util.Arrays;

/**
 * 
 */
public enum EBattyInstruction {

    CHARGE(1),

    DISCHARGE(2);

    private final int runningModeId;

    EBattyInstruction(int runningModeId) {
        this.runningModeId = runningModeId;
    }

    /**
     * Find instruction type based on runningmode Id
     * 
     * @param runningModeId to find
     * @return EBattyInstruction
     */
    public static EBattyInstruction fromRunningModeId(int runningModeId) {
        return Arrays.asList(values())
                .stream()
                .filter(e -> e.runningModeId == runningModeId)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getRunningModeId() {
        return runningModeId;
    }
}
