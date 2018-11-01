package sample.database;

import java.nio.charset.StandardCharsets;
import java.nio.charset.StandardCharsets.*;
import java.sql.*;
import java.io.*;
// import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Database {

    Connection c = null;
    Statement stmt = null;

    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:sample.dict2.db");
//            System.out.println(());
//            c = DriverManager.getConnection("jdbc:sqlite:dict2.db");
            c = DriverManager.getConnection("jdbc:sqlite:" + getClass().getResource("/database/dict2.db"));
            stmt = c.createStatement();
            System.out.println("Opened database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void createTable() {
        try{
            String sql = "CREATE TABLE DIC " + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " WORDTARGET           TEXT    NOT NULL, " + " WORDEXPLAIN            TEXT     NOT NULL)";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO DIC (ID, WORDTARGET, WORDEXPLAIN) " +
                    "VALUES (0, '//////////////////None', 'None');";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + "1: " + e.getMessage());
            System.exit(0);
        }
    }

    public ArrayList<String> getAll() {
        ArrayList<String> res = new ArrayList<>();
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_edict;");

            while (rs.next()) {
                int id = rs.getInt("idx");
                String wordTarget = rs.getString("word");
                String wordExplain = rs.getString("detail");
                boolean canAdd = true;
                for(int i = 0; i < wordTarget.length(); i++){
                    if(wordTarget.charAt(i) >= 127){
                        canAdd = false;
                    }
                }
                if(canAdd == false) continue;
                res.add(wordTarget + "\t" + wordExplain);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return res;
    }

    public void insert(String wordTarget, String wordExplain){
        try {
            c.setAutoCommit(false);
            String sql = "INSERT INTO tbl_edict (word, detail) " +
                    "VALUES ('" + wordTarget + "', '" + wordExplain + "');";
            stmt.executeUpdate(sql);
            c.commit();
            c.setAutoCommit(true);
            System.out.println("Insert successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void update(String wordTarget, String wordExplain){
        try{
            c.setAutoCommit(false);
            String sql = "UPDATE tbl_edict set detail = '" + wordExplain + "' where word='" + wordTarget +"';";
            stmt.executeUpdate(sql);
            c.commit();
            c.setAutoCommit(true);
            System.out.println("Update successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void remove(String wordTarget){
        try{
            c.setAutoCommit(false);
            String sql = "DELETE from tbl_edict where word = '" + wordTarget + "';";
            stmt.executeUpdate(sql);
            c.commit();
            System.out.println("Delete successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void removeAll(){
        try{
            c.setAutoCommit(false);
            String sql = "DELETE from DIC where ID > 0;";
            stmt.executeUpdate(sql);
            c.commit();
            System.out.println("Delete successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void importFromFile(){
        BufferedReader reader = null;
        try {
            File file = new File("new_dic.txt");
            reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordsArray = line.split("\t");
                insert(wordsArray[0].toLowerCase(), wordsArray[1]);
                System.out.println(wordsArray[0] + " " + wordsArray[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}