package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ulis on 2017-10-29.
 */
public class DbConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DbConnect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11210138", "sql11210138", "meu5NJjWVk");
            st = con.createStatement();
        }catch(Exception ex){
            System.out.println("error has occured with DB : " + ex);
        }
    }
    public LinkedList<String> getData(){
        LinkedList<String> list = new LinkedList<>();
        try{
            String query = "select * from players order by score desc limit 9";
            rs = st.executeQuery(query);
            while(rs.next()){
                list.add(rs.getString("name"));
                list.add(rs.getString("score"));
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return list;
    }
    public void insertData(String name, int score){
        try {
            String checkQuery = "select * from players where name = '" + name + "'" ;
            rs = st.executeQuery(checkQuery);
            if(!rs.next()){
                st.executeUpdate("insert into players(name, score) values ('" + name + "','" + score + "')");
            } else{
                if(score > rs.getInt("score")){
                    rs.close();
                    String updateQuery = "update players set score = '"+score+"' where name = '" + name+"'";
                    st.executeUpdate(updateQuery);
                }
            }

        }catch(Exception ex){
            System.out.println(ex);
        }
    }
}
