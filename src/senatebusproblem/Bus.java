/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senatebusproblem;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import static senatebusproblem.SenateBusProblem.mutex;
import static senatebusproblem.SenateBusProblem.boarded;
import static senatebusproblem.SenateBusProblem.bus;
import static senatebusproblem.SenateBusProblem.waitings;
import static senatebusproblem.SenateBusProblem.BUS_CAPACITY;
/**
 *
 * @author kevin
 */
public class Bus extends Thread {
    private static Logger log=Logger.getLogger(Bus.class.getName());    
        
    @Override
    public void run(){
        try {
            mutex.acquire();     // acquire mutex for waiting
            log.info("[ Bus ID : " + Thread.currentThread().getId() + " ] bus is ready for board riders");
        } catch (InterruptedException ex) {
            log.log(Level.SEVERE, null, ex);
        } 
        int n = Math.min(waitings, BUS_CAPACITY); // maximum can board 
        for (int i = 0; i < n; i++) {
            bus.release();          // release the bus per rider to board
            try {
                boarded.acquire();  // wait for rider to board
            } catch (InterruptedException ex) {
                log.log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        } 
        waitings = Math.max(waitings - BUS_CAPACITY, 0);        
        mutex.release();   //release mutex for waiting
        depart();    //depart 
        log.info("[ Bus ID : " + Thread.currentThread().getId() + " ] bus departed with "+"[ Waitings: "+n+" ]");
    }   
    
    
    private void depart() {  
        try {   //depart
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }
    
}
