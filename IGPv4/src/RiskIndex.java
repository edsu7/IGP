import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RiskIndex extends Database {
    // Connects to database and opens Blaeser table retrieving the closest entry
    // to starting date.
    public Date getBlaeser(Date dateStart, Date dateEnd) {
        Connection c = null;
        Statement stmt = null;
        List<Object[]> arrayholder = new ArrayList<Object[]>();
        try {
            c = connectDatabase();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            String start = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);
            stmt = c.createStatement();
            ResultSet rs = stmt
                    .executeQuery("select * from master where date>='"
                            + dateStart + "' and date<='" + dateEnd
                            + "' order by timestamp;");
            while (rs.next()) {
                Object[] holder = new Object[4];

                holder[0] = rs.getDate("date");
                holder[1] = rs.getDouble("hourlyrn");// hr
                holder[2] = rs.getDouble("hmd");// hmd
                holder[3] = rs.getDouble("tmp");// tmp
                arrayholder.add(holder);
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
        Object[][] res = new Object[arrayholder.size()][];
        arrayholder.toArray(res);

        boolean wet = false;
        boolean blaeser = false;
        Date returnDate = null;
        for (Object[] element : res) {
            List<Double> wetTemp = new ArrayList<Double>();
            double tempf = (double) element[3];
            double tempc = (tempf - 32) * 5 / 9;
            double hmd = (double) element[2];
            double rn = (double) element[1];
            double sat = 0.6108 * Math.exp((17.27 * tempc) / (tempc + 237.3));
            double ea = ((Double) element[2]) / 100 * sat;
            double vpd = ea - sat;
            if (rn > 0 || vpd <= 4.5) {
                wet = true;
            }
            if (wet = true && !(blaeser = false)) {
                wetTemp.add((Double) element[3]);
                Double average = 0.0;
                for (Double value : wetTemp) {
                    average += value;
                }
                if (average / (wetTemp.size()) >= 60) {
                    blaeser = true;
                    returnDate = (Date) element[0];
                    break;
                }
            }
        }
        return returnDate;
    }
    public Object[][] calculateIndex(Date dateStart, Date dateEnd) {
        Connection c = null;
        Statement stmt = null;
        int index = 0;
        ArrayList<Object> holder = new ArrayList<Object>();
        // String checkDate = null;
        try {
            c = connectDatabase();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            String start = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);
            System.out.println("The start date is: " + start);
            System.out.println("The start date is: " + end);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT DATE,array_agg(CASE WHEN tmp>84.2 THEN '2' WHEN tmp>=69.8 THEN '1' ELSE '3' END) AS VALID FROM master WHERE DATE>='"
                            + dateStart + "' and date<='" + dateEnd
                            + "' GROUP BY DATE ORDER BY DATE;");
            while (rs.next()) {
                Object[] tempHolder = new Object[2];
                String date = rs.getString("DATE");
                System.out.println(date);
                boolean big = false;
                int count = 0;
                // int temp = rs.getInt("TEMP");
                String valid = rs.getString("VALID");
                Pattern pt = Pattern.compile("[\\W+]");
                Matcher match = pt.matcher(valid);
                while (match.find()) {
                    String s = match.group();
                    valid = valid.replaceAll("\\" + s, "");
                }
                System.out.println(valid);
                Pattern pt2 = Pattern.compile("2");
                Matcher match2 = pt2.matcher(valid);
                while (match2.find()) {
                    MatchResult matchResult = match2.toMatchResult();
                    String substring = valid.substring(matchResult.start(),
                            matchResult.end());
                    if (substring.equals("2")) {
                        big = true;
                    }
                }

                Pattern pattern1 = Pattern.compile("(1+)");
                Matcher match1 = pattern1.matcher(valid);
                while (match1.find()) {
                    MatchResult matchResult = match1.toMatchResult();
                    String substring = valid.substring(matchResult.start(),
                            matchResult.end());
                    // System.out.println(substring);
                    if (substring.length() > count) {
                        count = substring.length();
                    }
                }
                if (index > 0 && big) {
                    index -= 10;
                }
                if (count >= 6 && index <= 100) {
                    index += 20;
                } else if (index >= 60 && count < 6 && !(big)) {
                    index -= 10;
                } else {
                    index = 0;
                    big = false;
                }

                /*
                 * String[] splitArray =
                 * valid.substring(1,valid.length()-1).split(","); int count =
                 * 0; int max = 0;
                 * 
                 * for(String item:splitArray){ System.out.print(item); if
                 * (item.equals("1")){ count++; } else if (item.equals("2")){
                 * count = 0; max = 0; break; } else{ if (count>max){ max=count;
                 * } count=0; } } if (count>max){ max=count; } if(index>=60 &&
                 * index<=100 && max>=6){ index+=20; } else if(index<60 &&
                 * max>=6){ index+=20; } else if(index<60 && max<6){ index=0; }
                 * else { index-=10; }
                 */
                tempHolder[0] = date;
                tempHolder[1] = index;
                holder.add(tempHolder);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        Object[][] res = new Object[holder.size()][];
        holder.toArray(res);
        return res;
    }
    public String[] getColNames() {
        String[] holder = new String[2];
        holder[0] = "Dates";
        holder[1] = "Risk Index";
        return holder;
    }
    public Integer[] createRiskDataset(Date dateStart, Date dateEnd) {
        Connection c = null;
        Statement stmt = null;
        int index = 0;
        ArrayList<Integer> holder = new ArrayList<Integer>();
        // String checkDate = null;
        try {
            c = connectDatabase();
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            String start = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);
            System.out.println("The start date is: " + start);
            System.out.println("The start date is: " + end);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT DATE,array_agg(CASE WHEN tmp>84.2 THEN '2' WHEN tmp>=69.8 THEN '1' ELSE '3' END) AS VALID FROM master WHERE DATE>='"
                            + dateStart + "' and date<='" + dateEnd
                            + "' GROUP BY DATE ORDER BY DATE;");
            while (rs.next()) {
                String date = rs.getString("DATE");
                System.out.println(date);
                boolean big = false;
                int count = 0;

                String valid = rs.getString("VALID");
                Pattern pt = Pattern.compile("[\\W+]");
                Matcher match = pt.matcher(valid);
                while (match.find()) {
                    String s = match.group();
                    valid = valid.replaceAll("\\" + s, "");
                }
                System.out.println(valid);
                Pattern pt2 = Pattern.compile("2");
                Matcher match2 = pt2.matcher(valid);
                while (match2.find()) {
                    MatchResult matchResult = match2.toMatchResult();
                    String substring = valid.substring(matchResult.start(),
                            matchResult.end());
                    if (substring.equals("2")) {
                        big = true;
                    }
                }

                Pattern pattern1 = Pattern.compile("(1+)");
                Matcher match1 = pattern1.matcher(valid);
                while (match1.find()) {
                    MatchResult matchResult = match1.toMatchResult();
                    String substring = valid.substring(matchResult.start(),
                            matchResult.end());
                    // System.out.println(substring);
                    if (substring.length() > count) {
                        count = substring.length();
                    }
                }
                if (index > 0 && big) {
                    index -= 10;
                } else if (count >= 6 && index <= 100) {
                    index += 20;
                } else if (index >= 60 && count < 6 && !(big)) {
                    index -= 10;
                } else {
                    index = 0;
                    big = false;
                }
                holder.add(index);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        Integer[] res = new Integer[holder.size()];
        holder.toArray(res);
        return res;
    }
    // Calculates risk index
    // See http://ipm.ucanr.edu/PMG/r302100311.html

}
