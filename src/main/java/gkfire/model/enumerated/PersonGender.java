/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gkfire.model.enumerated;

/**
 *
 * @author CIUNAS
 */
public enum PersonGender {
    M("Hombre"),F("Mujer");
    
    private final String description;

    private PersonGender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
