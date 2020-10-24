/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;
// https://www.geeksforgeeks.org/multithreading-in-java/
// Java code for thread creation by implementing 

class MulthreadingDemo implements Runnable {

    @Override
    public void run() {
        try {
            // Displaying the thread that is running 
            System.out.println("Thread "
                    + Thread.currentThread().getId()
                    + " is running");

        } catch (Exception e) {
            // Throwing an exception 
            System.out.println("Exception is caught");
        }
    }
}

// Main Class 
class EjemploRunnable {

    public static void main(String[] args) {
        int n = 8; // Number of threads 
        for (int i = 0; i < n; i++) {
            Thread object = new Thread(new MulthreadingDemo());
            object.start();
        }
    }
}