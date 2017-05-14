/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit260.lostinSpace.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author devinerickson
 */
public class RegularSceneType implements Serializable {
    
    //class instance variables
    private String description;
    private int surfaceDensity;

       public RegularSceneType(){
           
       }     

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSurfaceDensity() {
        return surfaceDensity;
    }

    public void setSurfaceDensity(int surfaceDensity) {
        this.surfaceDensity = surfaceDensity;
    }

    @Override
    public String toString() {
        return "RegularSceneType{" + "description=" + description + ", surfaceDensity=" + surfaceDensity + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + this.surfaceDensity;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RegularSceneType other = (RegularSceneType) obj;
        if (this.surfaceDensity != other.surfaceDensity) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

 
  
            
            
    
}
