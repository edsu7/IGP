import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

import javax.swing.JFileChooser;

public class CSVloader extends Database {

    // Opens and parses through file.
    // Based off the code BIF812 sequenceloader
    public static void loadCSVFromFile() {
        // the next two lines show the dialog
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        // pass the selected file to the loadSequenceFromFile method
        loadCSVFromFile(fc.getSelectedFile().getAbsolutePath());
    }

    /**
     * @throws loads a sequence from the indicated file @param filepath the path
     * to the file that contains the sequence to load @throws
     */
    public static void loadCSVFromFile(String filepath) {
        try {
            Stream<String> stream = Files.lines(Paths.get(filepath));
            stream.forEach((s) -> {
                Database database = new Database();
                Connection c = database.connectDatabase();
                Statement stmt = null;
                try {
                    stmt = c.createStatement();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(s);
                String[] j = s.replaceAll("[a-zA-Z]|:1|=|;$", "").split(";");
                String sql = "INSERT INTO master"
                        + "(dailyrn, hourlyrn, ws, hourgust, daygust, cwd, kpa, hmd,"
                        + "tmp, hmt, lt, lat, lon, \"time\", date)" + " values("
                        + j[0] + "," + j[1] + "," + j[2] + "," + j[3] + ","
                        + j[4] + "," + j[5] + "," + j[6] + "," + j[7] + ","
                        + j[8] + "," + j[9] + "," + j[10] + "," + "NULL" + ","
                        + "NULL" + ",\'" + j[13] + ":00\'::TIME, \'" + j[14]
                        + "\'::DATE);";
                System.out.println(sql);
                try {
                    stmt.execute(sql);
                    stmt.close();
                    c.close();

                } catch (Exception e) {
                    System.err.println(
                            e.getClass().getName() + ": " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Data inserted successfully");
    }
}