/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2_lab2;

/**
 *
 * @author User
 */
public class HashTable {

    private Entry head;

    public HashTable() {
        this.head = null;
    }

    public void add(String username, long pos) {
        Entry newObj = new Entry(username, pos);
        if (head == null) {
            head = newObj;
        } else {
            Entry temp = head;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(newObj);
        }
    }

    public boolean remove(String username) {
        if (head == null) {
            return false;
        }
        
        if (head.getUsername().equals(username)) {
            head = head.getSiguiente();
            return true;
        }
        Entry temp = head;
        while (temp.getSiguiente() != null) {
            if (temp.getSiguiente().getUsername().equals(username)) {
                temp.setSiguiente(temp.getSiguiente().getSiguiente());
                return true;
            }
            temp = temp.getSiguiente();
        }
        return false;
    }

    public Long search(String username) {
        Entry temp = head;
        while (temp != null) {
            if (temp.getUsername().equals(username)) {
                return temp.getPosicion();
            }
            temp = temp.getSiguiente();
        }
        return null;
    }
}
