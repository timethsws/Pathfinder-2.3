
//import static Cell.endCell;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author timethsubasinghe w1654560 2016384
 */

public class PathFind {

    private static final int WINDOW_SIZE = 500;
    static int N;
    private static Cell startCell,endCell;
    private static int[][] map;

    private static void run() {
        AStar aStar = new AStar(map);
        ArrayList<Cell> path = aStar.findPath(startCell, endCell, Heuristic.NONE);
        Cell pathCell;
        pathCell = path.get(0);
        double prea = pathCell.x + 0.5;
        double preb = N - pathCell.y - 0.5;
        for (int i = 1; i < path.size(); i++) {
            pathCell = path.get(i);
            int a = pathCell.x;
            int b = pathCell.y;
            StdDraw.setPenRadius(0.008);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(prea, preb, a + 0.5, N - b - 0.5);
            StdDraw.setPenRadius(0.002);
            pathCell = pathCell.parent;
            prea = a + 0.5;
            preb = N - b - 0.5;
            if (pathCell != null) {
                System.out.println(" (" + pathCell.x + " " + pathCell.y + ")" + " (" + endCell.x + " " + endCell.y + ") : " + pathCell.h_cost + " - " + pathCell.totTravelCost);
            }
        }
    }


    public static void main(String[] args) {
        map = Map.getMap(2);
        N = map.length;
        StdDraw.setCanvasSize(WINDOW_SIZE, WINDOW_SIZE);
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N);
        drawMap();
        Thread t = new Thread(new mouseInteraction(N));
        t.setDaemon(true);
        t.start();
    }

    public static void drawMap() {
        StdDraw.picture(N / 2, N / 2, "map.png", N, N);
    }

    public static void mouseClicked(double x, double y) {
        int j = (int) x;
        int i = (int) y;
        if (map[i][j] == 5) {
            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Can't start or end here.");
        } else {
            if (startCell == null) {
                startCell = new Cell(j, i, 0);
                StdDraw.setPenColor(StdDraw.GREEN);
                StdDraw.filledCircle(j + 0.5, N - i - 0.5, .25);
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.circle(j + 0.5, N - i - 0.5, .25);
                System.out.println("Cell : (" + startCell.x + ":" + startCell.y + ")" + startCell.totTravelCost);

            } else if (endCell == null) {
                endCell = new Cell(j, i, map[i][j]);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledCircle(j + 0.5, N - i - 0.5, .25);
                StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                StdDraw.circle(j + 0.5, N - i - 0.5, .25);
                System.out.println("Cell : (" + endCell.x + ":" + endCell.y + ")" + endCell.totTravelCost);
                run();
            }
        }
    }
}

class mouseInteraction implements Runnable {
    boolean pressedOnce = false;
    int N;
    public mouseInteraction(int N){
        this.N = N;
    }
    @Override
    public void run() {
        while (true) {
            if (StdDraw.mousePressed()) {
                if (!pressedOnce) {
                    PathFind.mouseClicked((StdDraw.mouseX()), (N - StdDraw.mouseY()));
                    pressedOnce = true;
                }
            } else {
                pressedOnce = false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(mouseInteraction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
