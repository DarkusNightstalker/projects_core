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

    Enero("01","Ene"),
    Febrero("02","Feb"),
    Marzo("03","Mar"),
    Abril("04","Abr"),
    Mayo("05","May"),
    Junio("06","Jun"),
    Julio("07","Jul"),
    Agosto("08","Ago"),
    Septiembre("09","Sep"),
    Octubre("10","Oct"),
    Noviembre("11","Nov"),
    Diciembre("12","Dic");

    private final String number;
    private final String abbr;

    private Month(String number,String abbr) {
        this.number = number;
        this.abbr = abbr;
    }

    public String getNumber() {
        return number;
    }

    public String getAbbr() {
        return abbr;
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
