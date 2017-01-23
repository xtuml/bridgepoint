package org.xtuml.bp.core.common;

import java.util.Arrays;

/*
 * Keeps a count of activities. Can be compared for equality.
 */

public class ActivityCount {


    private int[][] activityMatrix;

    private static final String[] TYPES = { "S_SYNC", "S_BRG", "O_TFR", "O_DBATTR", "SM_ACT", "SPR_PO", "SPR_PS", "SPR_RO", "SPR_RS" };

    public ActivityCount() {

        // get the number of types and number of dialects
        int num_types = TYPES.length;
        int num_dialects = ActionFile.getAvailableDialectCodes().length;

        // create activity matrix
        activityMatrix = new int[num_types][num_dialects];
    }

    // increment the count of an activity type
    public void increment( String type, int dialect ) {
        if ( type == null || !Arrays.asList(TYPES).contains(type) || dialect < 0 || dialect >= activityMatrix[0].length ) return;

        activityMatrix[Arrays.asList(TYPES).indexOf(type)][dialect]++;
    }

    // compare two counts
    public boolean equals( ActivityCount other, int dialect ) {
        if ( other == null || dialect < 0 || dialect >= activityMatrix[0].length ) return false;

        // get other activity matrix
        int[][] otherActivityMatrix = other.getActivityMatrix();
        if ( otherActivityMatrix == null ) return false;

        // check sizes
        if ( activityMatrix.length != otherActivityMatrix.length ) return false;

        for ( int i = 0; i < activityMatrix.length; i++ ) {
            // check sizes
            if ( activityMatrix[i].length != otherActivityMatrix[i].length ) return false;

            // check equal count for the dialect
            if ( activityMatrix[i][dialect] != otherActivityMatrix[i][dialect] ) return false;
        }

        // if nothing fails, they are equal
        return true;
    }

    public int[][] getActivityMatrix() {
        return activityMatrix;
    }

}
