/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lostinspace;

import byui.cit260.lostinSpace.model.Player;

/**
 *
 * @author devinerickson
 */
public class LostInSpace {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Player playerOne = new Player ();
        
        playerOne.setName("Michael");
        playerOne.setHighScore(20000);
        
        String playerInfo = playerOne.toString();
        System.out.println(playerInfo);
        
    }
    
}