import java.io.*;
import java.sql.*;

public class BulkQuestionUpload {
    public void addBulkQuestion(int sid, String mid) {
        String jdbcURL = "jdbc:mysql://127.0.0.1:3306/mpr";
        String username = "root";
        String password = "shubham1332";

        String csvFilePath = "Qbank.csv";
        int batchSize = 10;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO QUESTION (QUESID, SUB_ID, MODULE, QUES, MCQ1, MCQ2, MCQ3, MCQ4, MARKS, CORRECT_ANS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");

                // Check if the data array has enough elements
                if (data.length < 7) {
                    System.err.println("Skipping line due to insufficient data: " + lineText);
                    continue; // Skip this line
                }

                int quesNumber = Integer.parseInt(data[0]);
                if(quesNumber > 0 && quesNumber < 10){
                    data[0] = "0" + data[0];
                }
                String Ques_ID = "DSA" + mid + "0" + data[0];
                int SUB_ID = sid;
                int MODULE = Integer.parseInt(mid);
                String QUES = data[1];
                String MCQ1 = data[2];
                String MCQ2 = data[3];
                String MCQ3 = data[4];
                String MCQ4 = data[5];
                int MARKS = 1;
                String CORRECT_ANS = data[6];

                statement.setString(1, Ques_ID);
                statement.setInt(2, MODULE);
                statement.setInt(2, SUB_ID);
                statement.setString(3, QUES);
                statement.setString(4, MCQ1);
                statement.setString(5, MCQ2);
                statement.setString(6, MCQ3);
                statement.setString(7, MCQ4);
                statement.setInt(8, MARKS);
                statement.setString(9, CORRECT_ANS);

                statement.executeUpdate();
            }

            lineReader.close();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}