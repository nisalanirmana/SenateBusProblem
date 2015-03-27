/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senatebusproblem;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author kevin
 */
public class SenateBusProblem {
    
    private static Logger log=Logger.getLogger(SenateBusProblem.class.getName());    
   
    public static Semaphore mutex; 
    public static Semaphore bus;
    public static Semaphore boarded;
    public static int waitings = 0;
    public static final int BUS_CAPACITY = 50;
      
    
    public static void main(String[] args) {
        mutex = new Semaphore(1);
        bus = new Semaphore(0);
        boarded = new Semaphore(0);
        log.info("Initialized semaphores with initial values");           
              
        while (true) {                   
            //assusmption on random number generation more riders approaches bus stop when compared to the buses  
            if (Math.random() > 0.2) {   //random number generation if its between .2 and 1 create a new rider
               new Rider().start();  //starts a new rider thread
            }
            try {   
               Thread.sleep(1000);
            } catch (InterruptedException ex) {
               log.log(Level.SEVERE, null, ex);
            }            
            if (Math.random() > 0.9) {   //random number generation if its between .9 and 1 create a new bus
               new Bus().start();    //starts a new bus thread
            }
            try {   
               Thread.sleep(1000);
            } catch (InterruptedException ex) {
               log.log(Level.SEVERE, null, ex);
            }            
        }
    }    
    
    
}
