// 
// Decompiled by Procyon v0.5.36
// 

package travelingSalesmanProblem;

import java.util.StringTokenizer;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.io.FileWriter;

public class TspInstance
{
    public int numbCities;
    double[][] coordinates;
    String name;
    int N;
    int[][] nearestCities;
    double[][] D;
    String[] instanceNames;
    
    public static void saveNearest(final int[][] nearest, final String fileName) {
        final StringBuilder strb = new StringBuilder();
        final int N = nearest.length;
        final int n = nearest[0].length;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < n - 1; ++j) {
                strb.append(String.valueOf(nearest[i][j]) + " ");
            }
            if (i < N - 1) {
                strb.append(String.valueOf(nearest[i][n - 1]) + "\n");
            }
            else {
                strb.append(nearest[i][n - 1]);
            }
        }
        save(fileName, strb.toString());
    }
    
    public static void save(final String fileName, final String data) {
        try {
            final FileWriter writer = new FileWriter(fileName);
            writer.write(data);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void calculateNearest(final int N) {
        this.nearestCities = new int[this.numbCities][N];
        this.D = new double[this.numbCities][N];
        for (int i = 0; i < this.numbCities; ++i) {
            Arrays.fill(this.D[i], 2.147483647E9);
            for (int j = 0; j < this.numbCities; ++j) {
                if (i != j) {
                    final double cost = this.getDistance(i, j);
                    final int max = this.getMax(this.D[i]);
                    if (cost < this.D[i][max]) {
                        this.D[i][max] = cost;
                        this.nearestCities[i][max] = j;
                    }
                }
            }
        }
    }
    
    public int getMax(final double[] array) {
        double max = -1.7976931348623157E308;
        int maxIndex = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    
    public boolean isNearest(final int cityIndex, final int candidate) {
        for (int i = 0; i < this.nearestCities.length; ++i) {
            if (this.nearestCities[cityIndex][i] == candidate) {
                return true;
            }
        }
        return false;
    }
    
    public double getDistanceToNearest(final int cityIndex, final int nthNearest) {
        return this.D[cityIndex][nthNearest];
    }
    
    public TspInstance(final int number) {
        this.N = 8;
        this.instanceNames = new String[] { "pr299", "pr439", "rat575", "u724", "rat783", "pcb1173", "d1291", "u2152", "usa13509", "d18512" };
        this.name = this.instanceNames[number];
        String fileName = "data\\tsp\\" + this.instanceNames[number] + ".tsp";
        try {
            final FileReader fr = new FileReader(fileName);
            final BufferedReader bfr = new BufferedReader(fr);
            this.loadData(bfr);
        }
        catch (Exception ex1) {
            try {
                fileName = "data/tsp/" + this.instanceNames[number] + ".tsp";
                final BufferedReader bfr = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
                this.loadData(bfr);
            }
            catch (Exception ex2) {
                ex1.printStackTrace();
                ex2.printStackTrace();
                System.err.print("problem when opening file " + fileName);
            }
        }
        this.loadNearestCities();
    }
    
    public void loadNearestCities() {
        String fileName = "data\\tsp\\" + this.name + "NearestCities.txt";
        try {
            final FileReader fr = new FileReader(fileName);
            final BufferedReader bfr = new BufferedReader(fr);
            this.readTable(bfr);
        }
        catch (Exception ex1) {
            try {
                fileName = "data/tsp/" + this.name + "NearestCities.txt";
                final BufferedReader bfr = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
                this.readTable(bfr);
            }
            catch (Exception ex2) {
                ex1.printStackTrace();
                ex2.printStackTrace();
                System.err.print("problem when opening file " + fileName);
            }
        }
    }
    
    private void readTable(final BufferedReader bfr) throws Exception {
        this.nearestCities = new int[this.numbCities][this.N];
        for (int i = 0; i < this.numbCities; ++i) {
            final String line = bfr.readLine();
            final StringTokenizer tok = new StringTokenizer(line);
            for (int j = 0; j < this.N; ++j) {
                this.nearestCities[i][j] = Integer.parseInt(tok.nextToken());
            }
        }
    }
    
    private void loadData(final BufferedReader bfr) throws Exception {
        for (int i = 0; i < 3; ++i) {
            bfr.readLine();
        }
        String line = bfr.readLine();
        StringTokenizer tok = new StringTokenizer(line);
        tok.nextToken();
        tok.nextToken();
        this.numbCities = Integer.parseInt(tok.nextToken());
        for (line = bfr.readLine(); !line.equals("NODE_COORD_SECTION"); line = bfr.readLine()) {}
        this.coordinates = new double[this.numbCities][2];
        for (int j = 0; j < this.numbCities; ++j) {
            tok = new StringTokenizer(bfr.readLine());
            tok.nextToken();
            this.coordinates[j][0] = Double.parseDouble(tok.nextToken());
            this.coordinates[j][1] = Double.parseDouble(tok.nextToken());
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder build = new StringBuilder();
        for (int i = 0; i < this.numbCities; ++i) {
            build.append(String.valueOf(this.coordinates[i][0]) + " " + this.coordinates[i][1] + "\n");
        }
        return build.toString();
    }
    
    int getNumbCities() {
        return this.numbCities;
    }
    
    double[][] getCoordinates() {
        return this.coordinates;
    }
    
    public double getDistance(final int city1, final int city2) {
        final double x1 = this.coordinates[city1][0];
        final double x2 = this.coordinates[city2][0];
        final double y1 = this.coordinates[city1][1];
        final double y2 = this.coordinates[city2][1];
        return StrictMath.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
