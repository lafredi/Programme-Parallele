// -*- coding: utf-8 -*-

import java.util.ArrayList;

public class SeptNains {
    public static void main(String[] args) {
        int nbNains = 7;
        BlancheNeige bn = new BlancheNeige();
        String nom [] = {"Simplet", "Dormeur",  "Atchoum", "Joyeux", "Grincheux", "Prof", "Timide"};
        Nain nain [] = new Nain [nbNains];
        for(int i = 0; i < nbNains; i++) nain[i] = new Nain(nom[i], bn);
        for(int i = 0; i < nbNains; i++) nain[i].start();
        for(int i = 0; i < nbNains; i++) {
            try { nain[i].join(); } catch (InterruptedException e) {e.printStackTrace();}	
        }
        System.out.println("C'est fini");        
    }
}    

class BlancheNeige {
    private volatile boolean libre = true;
    // Initialement, Blanche-Neige est libre.
    public synchronized void requerir() {
        System.out.println(Thread.currentThread().getName()
                           + " veut un accès exclusif à la ressource");
    }

    public synchronized void acceder() {
        while( ! libre ) // Le nain s'endort sur l'objet bn
            try { wait(); } catch (InterruptedException e) {e.printStackTrace();}
        libre = false;
        System.out.println("\t" + Thread.currentThread().getName()
                           + " accède à la ressource.");
    }

    public synchronized void relacher() {
        System.out.println("\t\t\t" + Thread.currentThread().getName()
                           + " relâche la ressource.");
        libre = true;
        notifyAll();
    }
}

class Nain extends Thread {
    public BlancheNeige bn;
    public Nain(String nom, BlancheNeige bn) {
        this.setName(nom);
        this.bn = bn;
    }
    public void run() {
        while(true) {
            bn.requerir();
            bn.acceder();
            System.out.println("\t\t" + getName() + " a un accès exclusif à Blanche-Neige.");
            try {sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            bn.relacher();
        }
        // System.out.println(getName() + " a terminé!");
    }	
}

/*
  $ make
  $ java SeptNains
  Simplet veut un accès exclusif à la ressource
  Grincheux veut un accès exclusif à la ressource
      Grincheux accède à la ressource.
  Prof veut un accès exclusif à la ressource
          Grincheux a un accès exclusif à Blanche-Neige.
  Joyeux veut un accès exclusif à la ressource
  Atchoum veut un accès exclusif à la ressource
  Dormeur veut un accès exclusif à la ressource
  Timide veut un accès exclusif à la ressource
               Grincheux relâche la ressource.
  Grincheux veut un accès exclusif à la ressource
       Grincheux accède à la ressource.
           Grincheux a un accès exclusif à Blanche-Neige.
		       Grincheux relâche la ressource.
  Grincheux veut un accès exclusif à la ressource
      Grincheux accède à la ressource.
          Grincheux a un accès exclusif à Blanche-Neige.
              Grincheux relâche la ressource.
  Grincheux veut un accès exclusif à la ressource
       Grincheux accède à la ressource.
           Grincheux a un accès exclusif à Blanche-Neige.
		       Grincheux relâche la ressource.
  Grincheux veut un accès exclusif à la ressource
      Grincheux accède à la ressource.
          Grincheux a un accès exclusif à Blanche-Neige.
              Grincheux relâche la ressource.
  Grincheux veut un accès exclusif à la ressource
       Grincheux accède à la ressource.
           Grincheux a un accès exclusif à Blanche-Neige.
		       Grincheux relâche la ressource.
  Grincheux veut un accès exclusif à la ressource
      Grincheux accède à la ressource.
          Grincheux a un accès exclusif à Blanche-Neige.
              Grincheux relâche la ressource.
*/
