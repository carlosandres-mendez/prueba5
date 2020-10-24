/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;
/**
 * Creating a Java thread.
 *
 */

class Tarea implements Runnable
{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}

public class ThreadExample
{
    public static void main(String[] args) {
        int numTareas = 5;
        
        for (int i = 0; i < numTareas; i++){
            Thread t = new Thread(new Tarea());
            t.start();
        }

    }
}