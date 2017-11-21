package com.lsk.DataAccess;

import com.lsk.BasicCore.DbUnit;
import com.lsk.Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lsk10238 on 2017/11/20.
 */
public class UserDataAccess {

    public static boolean ValidateUserByUsernameAndPwd(String userName,String password){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        String sql=new StringBuilder()
                .append("select count(*) as count ")
                .append("from users ")
                .append("where username=? ")
                .append("and password=? ")
                .toString();


        try {
            connection= DbUnit.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,password);

            resultSet=preparedStatement.executeQuery();

            if (resultSet.first() && resultSet.getInt("count") > 0){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUnit.closeConnection(connection,preparedStatement,resultSet);
        }
        return false;
    }


    public static boolean insertUser(User user){
        Connection connection=null;
        PreparedStatement preparedStatement=null;

        String sql=new StringBuilder()
                .append("insert into users ")
                .append("    (username,password,sex,hobbies,educational,remark) ")
                .append("values ")
                .append("    (?,?,?,?,?,?) ")
                .toString();
        boolean result=false;
        try{
            connection= DbUnit.getConnection();
            connection.setAutoCommit(false);
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getSex());
            preparedStatement.setString(4,user.getHobbies());
            preparedStatement.setString(5,user.getEducational());
            preparedStatement.setString(6,user.getRemark());
            int resultCount= preparedStatement.executeUpdate();
            if (resultCount >= 1){
                connection.commit();
                result=true;
            }else{
                connection.rollback();
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            DbUnit.closeConnection(connection,preparedStatement,null);
        }
        return result;
    }


    public static boolean selectIsExist(String username){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection= DbUnit.getConnection();
            String sql=new StringBuilder()
                    .append("select count(*) as count ")
                    .append("from users ")
                    .append("where username=? ")
                    .toString();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.first() && resultSet.getInt("count") > 0){
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUnit.closeConnection(connection,preparedStatement,resultSet);
        }
        return false;
    }
}
