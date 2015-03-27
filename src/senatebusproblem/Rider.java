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
public class Rider extends Thread {
    private static Logger log=Logger.getLogger(Rider.class.getName());    
        
    @Override
    public void run(){
        try {
            mutex.acquire();      // aquire mutex to update waiting     
            log.info("[ Rider ID : " + Thread.currentThread().getId() + " ] rider is ready to board on bus"); 
        } catch (InterruptedException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        
        waitings += 1;        
        log.info("[Waiting : " + waitings+" ]");
        mutex.release();         // release mutex after updating waiting
 
        try {
            bus.acquire();      // wait for bus to board
        } catch (InterruptedException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        boardBus();            // get into bus
        boarded.release();  // succesfully enter to the bus
        log.info("[ Rider ID : " + Thread.currentThread().getId() + " ] rider boarded on bus"); 
    }
    
    public void boardBus() {       
        try {    //board bus
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            log.log(Level.SEVERE, null, ex);
        }
 
    }
}
