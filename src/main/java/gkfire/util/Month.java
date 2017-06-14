/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.util;

/**
 *
 * @author Jhoan Brayam
 */
public enum Month {

    Enero("01"),
    Febrero("02"),
    Marzo("03"),
    Abril("04"),
    Mayo("05"),
    Junio("06"),
    Julio("07"),
    Agosto("08"),
    Septiembre("09"),
    Octubre("10"),
    Noviembre("11"),
    Diciembre("12");

    private final String number;

    private Month(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public static Month byOrdinal(Number ordinal) {
        Month[] months = Month.values();
        if (ordinal == null) {
            return null;
        }
        for (Month month : months) {
            if (month.ordinal() == ordinal.intValue()) {
                return month;
            }
        }
        return null;
    }

    public Integer maxDays(Integer year) {
        switch (ordinal()) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return year % 4 == 0 ? 29 : 28;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                return null;
        }
    }

}
