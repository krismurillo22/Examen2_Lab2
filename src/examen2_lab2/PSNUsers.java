/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2_lab2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class PSNUsers {

    private RandomAccessFile archivo, trofeos;
    private HashTable users;

    public PSNUsers() throws IOException {
        this.archivo = new RandomAccessFile("users.psn", "rw");
        this.trofeos = new RandomAccessFile("psn.psn", "rw");
        this.users = new HashTable();
        reloadHashTable();
    }

    private void reloadHashTable() throws IOException {
        archivo.seek(0);
        while (archivo.getFilePointer() < archivo.length()) {
            long pos = archivo.getFilePointer();
            String username = archivo.readUTF();
            archivo.readInt();
            archivo.readInt();
            boolean estaActivo = archivo.readBoolean();
            if (estaActivo) {
                users.add(username, pos);
            }
        }
    }

    private boolean buscar(String username) throws IOException {
        archivo.seek(0);
        while (archivo.getFilePointer() < archivo.length()) {
            String user = archivo.readUTF();
            archivo.readInt();
            archivo.readInt();
            archivo.readBoolean();
            if (user.equals(username)) {
                return true;
            }
        }
        return false;
    }

    /*
    Formato addUser
    String username
    int puntos
    int contador
    boolean estado
     */
    public void addUser(String username) throws IOException {
        if (users.search(username) != null || buscar(username)) {
            JOptionPane.showMessageDialog(null, "El usuario ya existe.");
            return;
        }
        archivo.seek(archivo.length());
        long position = archivo.getFilePointer();
        archivo.writeUTF(username);
        archivo.writeInt(0);
        archivo.writeInt(0);
        archivo.writeBoolean(true);
        users.add(username, position);
        JOptionPane.showMessageDialog(null, "Usuario agregado: " + username);
    }

    public void deactivateUser(String username) throws IOException {
        Long pos = users.search(username);
        if (pos == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            return;
        }

        archivo.seek(pos);
        archivo.readUTF();
        archivo.readInt();
        archivo.readInt();
        archivo.writeBoolean(false);
        users.remove(username);
        JOptionPane.showMessageDialog(null, "El usuario " + username + " se desactivado correctamente.");

    }

    /*
    Formato trofeos psn.psn
    String username;
    String tipoTrofeo;
    String JuegoTrofeo;
    String nombreTrofeo;
    Long fecha;
     */
    public void addTrophieTo(String username, String trophyGame, String trophyName, Trophy type, String descripcion) throws IOException {
        Long pos = users.search(username);
        if (pos == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            return;
        }
        archivo.seek(pos);
        archivo.readUTF();
        int actualPoints = archivo.readInt();
        int actualTrophies = archivo.readInt();
        archivo.seek(pos);
        archivo.readUTF();
        archivo.writeInt(actualPoints + type.points);
        archivo.writeInt(actualTrophies + 1);

        trofeos.seek(trofeos.length());
        trofeos.writeUTF(username);
        trofeos.writeUTF(type.name());
        trofeos.writeUTF(trophyGame);
        trofeos.writeUTF(trophyName);
        trofeos.writeLong(Calendar.getInstance().getTimeInMillis());
        trofeos.writeUTF(descripcion);
        JOptionPane.showMessageDialog(null, "Trofeo agregado correctamente.");
    }

    public String playerInfo(String username) throws IOException {
        String infoUser = "";
        Long pos = users.search(username);
        if (pos == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            return null;
        }

        archivo.seek(pos);
        String user = archivo.readUTF();
        int points = archivo.readInt();
        int trophies = archivo.readInt();
        boolean esActivo = archivo.readBoolean();
        if (esActivo) {
            infoUser = "\nUsuario: " + user + "\nPuntos: " + points + "\nTrofeos: " + trophies;
            infoUser += "\nTrofeos ganados: \n\nFECHA  -  TIPO DE TROFEO  -  JUEGO GANADO  -  NOMBRE DEL PREMIO  -  DESCRIPCION\n\n";
            trofeos.seek(0);
            while (trofeos.getFilePointer() < trofeos.length()) {
                String userTrophy = trofeos.readUTF();
                String type = trofeos.readUTF();
                String game = trofeos.readUTF();
                String name = trofeos.readUTF();
                Long date = trofeos.readLong();
                String descripcion = trofeos.readUTF();
                if (userTrophy.equals(username)) {
                    Date fecha = new Date(date);
                    SimpleDateFormat formatoBonito = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
                    String fechaFormateada = formatoBonito.format(fecha);
                    infoUser += "->" + fechaFormateada + " - " + type + " - " + game + " - " + name + " - " + descripcion + "\n";
                }
            }
        }

        return infoUser;

    }
}
