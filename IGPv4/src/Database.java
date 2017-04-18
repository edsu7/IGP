import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jfree.data.category.DefaultCategoryDataset;

public class Database {

    public Connection connectDatabase() {
        String url = "jdbc:postgresql://stampy.db.elephantsql.com:5432/ihvaaosc";
        String username = "ihvaaosc";
        String password = "kAb2W66u506Qo9qX1o6zRr-DNBmDegGn";
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, username,
                    password);
            System.out.println("Connection successful");
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }

    public void makeTable() {
        Statement stmt = null;
        Connection c = null;
        try {
            c = connectDatabase();
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            String sql = "pg_restore --host \"localhost\" --port \"5432\" --username \"postgres\" --no-password --dbname \"igptest\" --verbose \"/Users/Oliver/Desktop/IGP/Database/sunpillar[jan27].sql\"";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
    /*
     * public void insertData(String filepath){ Connection c = null; Statement
     * stmt = null; String holder;
     * 
     * try { c = connectDatabase(); c.setAutoCommit(false);
     * System.out.println("Opened database successfully");
     * 
     * stmt = c.createStatement(); System.out.println("Printing file");
     * CSVloader csvloader = new CSVloader();
     * 
     * //Saves files as a string holder = csvloader.loadCSVFromFile(filepath);
     * //String is divided by new line String lines[] = holder.split("\\r?\\n");
     * //Each string is divided by , and stored into arrays //arrays called on
     * to make SQL insert statement for(String i : lines ) { String values[] =
     * i.split(","); String sql =
     * "copy public.master (dailyrn, hourlyrn, ws, hourgust, daygust, cwd, kpa, hmd,"
     * +
     * "tmp, hmt, lt, lat, lon, \"time\", date) FROM '/Users/Oliver/dataregex.csv' DELIMITER ';' CSV QUOTE '\"' ESCAPE '''';"
     * ;
     * 
     * System.out.println(sql); stmt.executeUpdate(sql); }
     * 
     * stmt.close(); c.commit(); c.close(); } catch ( Exception e ) {
     * System.err.println( e.getClass().getName() + ": " + e.getMessage() );
     * System.exit(0); } System.out.println("Records created successfully"); }
     */
    public Object[][] printData(Date dateStart, Date dateEnd,
            Integer[] numberArguements) {
        Connection c = null;
        Statement stmt = null;
        ArrayList<Object[]> holder = new ArrayList<Object[]>();
        try {
            c = connectDatabase();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            String start = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);
            stmt = c.createStatement();
            // StringBuilder temp = new StringBuilder();
            // ResultSet rs = stmt.executeQuery("select
            // to_char(timestamp,'YYYY-MM-DD') from timestamp where
            // timestamp>='"+start+"' AND timestamp<='"+end+"' order by
            // timestamp");
            // rs = stmt.executeQuery("");
            // ResultSetMetaData rsmd = rs.getMetaData();
            // System.out.println(rsmd.getColumnName(1));
            // sSystem.out.println(rsmd.getColumnName(2));
            // ResultSet rs = stmt.executeQuery("select
            // to_char(timestamp,'YYYY-MM-DD'), array_agg(element_value)from
            // element,forecast_element,forecast,timestamp where
            // (forecast_element.element_id = element.element_id)
            // and(forecast_element.forecast_id = forecast.forecast_id) and
            // (forecast.timestamp_id = timestamp.timestamp_id) and
            // timestamp>='"+start+"' AND timestamp<='"+end+"' group by
            // forecast_element.forecast_id,timestamp;");
            ResultSet rs = stmt
                    .executeQuery("select * from master where date>='"
                            + dateStart + "' and date<='" + dateEnd
                            + "' order by timestamp;");
            // select * from master where date>='2016-07-02' and
            // date<='2016-08-02' order by timestamp
            // ResultSetMetaData rsmd = rs.getMetaData();
            // int columnsNumber = rsmd.getColumnCount();
            System.out.println(numberArguements.length);
            // System.out.println(columnsNumber);
            while (rs.next()) {
                Object[] tempHolder = new Object[numberArguements.length];
                for (int j = 0; j <= numberArguements.length - 1; j++) {
                    tempHolder[j] = rs.getString(numberArguements[j]);
                    // System.out.print(columnsNumber);
                    // holder.append(tempHolder1+" "+ tempHolder2);
                }
                holder.add(tempHolder);
            }
            System.out.println(start);
            System.out.println(end);
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        // System.out.println(holder.toString());
        System.out.println("Operation done successfully");
        // System.out.println(holder.toString());
        Object[][] res = new Object[holder.size()][];
        holder.toArray(res);
        return res;
    }
    public String[] getColNames() {
        Connection c = null;
        Statement stmt = null;
        ArrayList<Object> holder = new ArrayList<Object>();
        // StringBuilder holder = new StringBuilder();
        try {
            c = connectDatabase();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt
                    .executeQuery("select * from master order by timestamp;");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            // ArrayList<Object> holder = new ArrayList<Object>();
            for (int i = 1; i < columnsNumber; i++) {
                holder.add(rsmd.getColumnName(i));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        // return holder.toString();
        String[] names = holder.toArray(new String[holder.size()]);
        return names;
    }
    public Integer[] getNumbers(Date dateStart, Date dateEnd,
            Integer returnValue) {
        Connection c = null;
        Statement stmt = null;
        ArrayList<Integer> holder = new ArrayList<Integer>();
        try {
            c = connectDatabase();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            String start = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);
            stmt = c.createStatement();
            // StringBuilder temp = new StringBuilder();
            // ResultSet rs = stmt.executeQuery("select
            // to_char(timestamp,'YYYY-MM-DD') from timestamp where
            // timestamp>='"+start+"' AND timestamp<='"+end+"' order by
            // timestamp");
            // rs = stmt.executeQuery("");
            // ResultSetMetaData rsmd = rs.getMetaData();
            // System.out.println(rsmd.getColumnName(1));
            // sSystem.out.println(rsmd.getColumnName(2));
            // ResultSet rs = stmt.executeQuery("select
            // to_char(timestamp,'YYYY-MM-DD'), array_agg(element_value)from
            // element,forecast_element,forecast,timestamp where
            // (forecast_element.element_id = element.element_id)
            // and(forecast_element.forecast_id = forecast.forecast_id) and
            // (forecast.timestamp_id = timestamp.timestamp_id) and
            // timestamp>='"+start+"' AND timestamp<='"+end+"' group by
            // forecast_element.forecast_id,timestamp;");
            ResultSet rs = stmt
                    .executeQuery("select * from master where date>='"
                            + dateStart + "' and date<='" + dateEnd
                            + "' order by timestamp;");
            // select * from master where date>='2016-07-02' and
            // date<='2016-08-02' order by timestamp
            // ResultSetMetaData rsmd = rs.getMetaData();
            // int columnsNumber = rsmd.getColumnCount();
            // System.out.println(numberArguements.length);
            // System.out.println(columnsNumber);
            while (rs.next()) {
                holder.add(rs.getInt(returnValue));
            }
            System.out.println(start);
            System.out.println(end);
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        // System.out.println(holder.toString());
        System.out.println("Operation done successfully");
        // System.out.println(holder.toString());
        Integer[] res = new Integer[holder.size()];
        holder.toArray(res);
        return res;
    }
    public Date[] getDates(Date dateStart, Date dateEnd) {
        Connection c = null;
        Statement stmt = null;
        ArrayList<Date> holder = new ArrayList<Date>();
        try {
            c = connectDatabase();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            String start = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);
            stmt = c.createStatement();
            // StringBuilder temp = new StringBuilder();
            // ResultSet rs = stmt.executeQuery("select
            // to_char(timestamp,'YYYY-MM-DD') from timestamp where
            // timestamp>='"+start+"' AND timestamp<='"+end+"' order by
            // timestamp");
            // rs = stmt.executeQuery("");
            // ResultSetMetaData rsmd = rs.getMetaData();
            // System.out.println(rsmd.getColumnName(1));
            // sSystem.out.println(rsmd.getColumnName(2));
            // ResultSet rs = stmt.executeQuery("select
            // to_char(timestamp,'YYYY-MM-DD'), array_agg(element_value)from
            // element,forecast_element,forecast,timestamp where
            // (forecast_element.element_id = element.element_id)
            // and(forecast_element.forecast_id = forecast.forecast_id) and
            // (forecast.timestamp_id = timestamp.timestamp_id) and
            // timestamp>='"+start+"' AND timestamp<='"+end+"' group by
            // forecast_element.forecast_id,timestamp;");
            ResultSet rs = stmt
                    .executeQuery("select * from master where date>='"
                            + dateStart + "' and date<='" + dateEnd
                            + "' order by timestamp;");
            // select * from master where date>='2016-07-02' and
            // date<='2016-08-02' order by timestamp
            // ResultSetMetaData rsmd = rs.getMetaData();
            // int columnsNumber = rsmd.getColumnCount();
            // System.out.println(numberArguements.length);
            // System.out.println(columnsNumber);
            while (rs.next()) {
                holder.add(rs.getDate(28));
            }
            System.out.println(start);
            System.out.println(end);
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        // System.out.println(holder.toString());
        System.out.println("Operation done successfully");
        // System.out.println(holder.toString());
        Date[] res = new Date[holder.size()];
        holder.toArray(res);
        return res;
    }
    public DefaultCategoryDataset createDataset(Date startDate, Date endDate,
            String yaxis, Integer[] returnValue) {
        Integer[] numberValues = {1, 1, 1, 1, 1, 1, 1};
        Date[] dateValues = getDates(startDate, endDate);

        if (yaxis.equals("Risk Index")) {
            RiskIndex results = new RiskIndex();
            numberValues = results.createRiskDataset(
                    results.getBlaeser(startDate, endDate), endDate);
            dateValues = getDates(results.getBlaeser(startDate, endDate),
                    endDate);
        } else {
            numberValues = getNumbers(startDate, endDate, returnValue[1]);
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < numberValues.length - 1; i++) {
            /*
             * System.out.println(element[1]); System.out.println(element[0]);
             * System.out.println(yaxis)
             */
            // int number = Integer.parseInt(element[1].toString().substring(0,
            // 1));
            dataset.addValue(numberValues[i], yaxis, dateValues[i]);
        } /*
           * dataset.addValue( 15 , "schools" , "1970-06-05 1:00:00" );
           * dataset.addValue( 30 , "schools" , "1970-06-05 2:00:00" );
           * dataset.addValue( 60 , "schools" , "1970-06-05 3:00:00" );
           * dataset.addValue( 120 , "schools" , "1970-06-05 4:00:00" );
           * dataset.addValue( 240 , "schools" , "1970-06-05 5:00:00" );
           * dataset.addValue( 300 , "schools" , "1970-06-05 6:00:00" );
           */
        return dataset;
    }
}
