package databaseproject;

/**
 * @author kevinsanchez, abelyera, ethanshim
 */

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBC extends JFrameLogin {

    static String url = "jdbc:mysql://localhost:3306/jdbc";
    static String username = "root";
    static String password = "";
    public String userEmail;


    public static void main(String[] args) {}

    public double displayGPA() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(("select GPA from student WHERE email='" + userEmail + "'"));
            while (resultSet.next()) {
                return resultSet.getDouble(1);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public String myClasses() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select courseEnroll from student WHERE email='"+ userEmail + "'");
            while (resultSet.next()) {
                return resultSet.getString(1);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String myStudentInfo() {
        try {
            String information = "First name: ";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select firstName from student WHERE email='" + userEmail + "'");
            while (resultSet.next()) {
                information += resultSet.getString(1) + "\n";
            }

            information += "Last name: ";
            resultSet = statement.executeQuery("select lastName from student WHERE email='" + userEmail + "'");
            while (resultSet.next()) {
                information += resultSet.getString(1) + "\n";
            }
            information += "Student ID: ";
            resultSet = statement.executeQuery("select studentID from student WHERE email='" + userEmail + "'");
            while (resultSet.next()) {
                information += resultSet.getString(1) + "\n";
            }

            information += "Student Email: " + userEmail + "\n";

            return information;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean Enroll(String newCourse) {
        try {
            String tempVal = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select courseEnroll from student WHERE email='" + userEmail + "'");
            while (resultSet.next()) {
                tempVal = resultSet.getString(1) + " ";
            }
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
            statement.executeUpdate("UPDATE student SET courseEnroll = CONCAT('"+ tempVal + "', '"+ newCourse + "')  WHERE email='" + userEmail + "'");
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean Drop() {

        try {
            String tempVal = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select courseEnroll from student WHERE email='" + userEmail + "'");
            while (resultSet.next()) {
                tempVal = resultSet.getString(1) + " ";
            }
            String eraser = "";
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
            statement.executeUpdate("UPDATE student SET courseEnroll = '' WHERE email ='" + userEmail + "'");
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkStudentLogin(String email, String userPassword) {
        try {
            boolean userCorrect = false, passCorrect = false;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from student WHERE email='" + email + "'");
            if (resultSet.next()) {
                userCorrect = true;
            }

            ResultSet resultSet2 = statement.executeQuery("select * from student WHERE studentPassword='" + userPassword + "'");
            if (resultSet2.next()) {
                passCorrect = true;
            }

            if (passCorrect && userCorrect) {

                userEmail = email;

                return true;

            } else
                return false;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkProfessorLogin(String email, String userPassword) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM professor WHERE email='" + email + "' AND professorPassword='" + userPassword + "'");
            if (resultSet.next()) {
                userEmail = email;
                System.out.println("Login Successful. userEmail set to: " + userEmail);// Set userEmail only if login is successful
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public String myProfessorInfo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT firstName, lastName, email FROM professor WHERE email='" + userEmail + "'");
            if (resultSet.next()) {
                String info = "First Name: " + resultSet.getString("firstName") + "\n"
                        + "Last Name: " + resultSet.getString("lastName") + "\n"
                        + "Email: " + resultSet.getString("email");
                return info;
            } else {
                return "Information not found for email: " + userEmail;
            }
        } catch (Exception e) {
            System.out.println(e);
            return "Error: " + e.getMessage();
        }
    }
    public String professorCourse() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT courseTeach FROM professor WHERE email='" + userEmail + "'");
            if (resultSet.next()) {
                return resultSet.getString("courseTeach");
            } else {
                return "No courses found for email: " + userEmail;
            }
        } catch (Exception e) {
            System.out.println(e);
            return "Error: " + e.getMessage();
        }
    }

    public String getGPA() {
        StringBuilder studentsInfo = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet coursesResultSet = statement.executeQuery("SELECT courseTeach FROM professor WHERE email='" + userEmail + "'");

            List<String> courses = new ArrayList<>();
            while (coursesResultSet.next()) {
                courses.add(coursesResultSet.getString("courseTeach"));
            }
            coursesResultSet.close();

            for (String course : courses) {
                Statement studentStatement = connection.createStatement();
                ResultSet studentsResultSet = studentStatement.executeQuery("SELECT s.firstName, s.lastName, s.email, s.GPA FROM student s INNER JOIN course c ON s.courseEnroll = c.courseID WHERE c.courseID='" + course + "'");
                while (studentsResultSet.next()) {
                    String studentName = studentsResultSet.getString("firstName") + " " + studentsResultSet.getString("lastName");
                    String studentEmail = studentsResultSet.getString("email");
                    String currentGPA = studentsResultSet.getString("GPA");

                    String newGPA = JOptionPane.showInputDialog("Enter new GPA for " + studentName + " (" + studentEmail + ") currently: " + currentGPA);

                    if (newGPA != null && !newGPA.isEmpty()) {
                        PreparedStatement updateStmt = connection.prepareStatement("UPDATE student SET GPA = ? WHERE email = ?");

                        updateStmt.setString(1, newGPA);
                        updateStmt.setString(2, studentEmail);
                        updateStmt.executeUpdate();
                        updateStmt.close();

                        studentsInfo.append("Updated GPA for ").append(studentName).append(" to ").append(newGPA).append("\n");
                    } else {
                        studentsInfo.append("No change for ").append(studentName).append("\n");
                    }
                }
                studentsResultSet.close();
                studentStatement.close();
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
            return "Error: " + e.getMessage();
        }
        return studentsInfo.toString();
    }
}
