/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.util;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 *
 * @author Jhoan Brayam
 */
public class FoxProConverter {
    private static final long BASE_FOXPRO_MILLIS = 210866803200000L;
    private static final long DAY_TO_MILLIS_FACTOR = 24L * 60L * 60L * 1000L;

    public static Date toDate( byte[] foxProDate ) {
        boolean isNull = true;
        for(byte b : foxProDate){
            if(b != 0) isNull = false;
        }
        if(isNull) return null;
        if ( foxProDate.length != 8 ) {
            throw new IllegalArgumentException("FoxPro date must be 8 bytes long");
        }

        // FoxPro date is stored with bytes reversed.

        byte[] reversedBytes = new byte[8];
        for ( int i = 0;  i < 8; i++ ) {
            reversedBytes[i] = foxProDate[8 - i - 1];
        }

        // Grab the two integer fields from the byte array

        ByteBuffer buf = ByteBuffer.wrap(reversedBytes);

        long timeFieldMillis = buf.getInt();
        long dateFieldDays = buf.getInt();

        // Convert to Java date by converting days to milliseconds and adjusting
        // to Java epoch.

        return new Date(dateFieldDays * DAY_TO_MILLIS_FACTOR - BASE_FOXPRO_MILLIS + timeFieldMillis);

    }
}
