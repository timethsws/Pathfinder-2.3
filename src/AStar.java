
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 * This class has all the methods to calculate the lowest path from a weighted two dimentional terrarin
 * @author timethsubasinghe
 */


public class AStar {

    private static boolean [][] visited;    // visited cells
    private int[][] map;                    // Array with the costs
    private Cell end;
    private Heuristic h;
    
    //Priority queue used to get the next lowest cost cell
    static PriorityQueue open= new PriorityQueue(new Comparator() {
            @Override
            public int compare(Object object1, Object object2) {
                Cell cell1 = (Cell) object1;
                Cell cell2 = (Cell) object2;
                
                double cost1 = cell1.totTravelCost + cell1.h_cost;
                double cost2 = cell2.totTravelCost + cell2.h_cost;
                
                if (cost1<cost2) {        // Cell 1 is less than Cell 2
                    return -1;
                } else if (cost1>cost2) { // Cell 2 is less than Cell 1
                    return 1;
                } else {                                        // Both cells are equal so the order doesn't matter
                    return 0;
                }
            }
        });

    public AStar(int[][] map){
        // Initialize instance variables
        int size = map.length;
        visited = new boolean[size][size];
        this.map = map;
    }

    public ArrayList<Cell> findPath( Cell start, Cell end, Heuristic h){
        this.end = end;
        this.h = h;
        ArrayList<Cell> path = new ArrayList<>();
        open.add(start);
        Cell currentCell = (Cell) open.remove();
        while (!(currentCell.x == end.x && currentCell.y == end.y)) {
            visited[currentCell.x][currentCell.y] = true;
            int x = currentCell.x;
            int y = currentCell.y;
            addCell(x - 1,y - 1,     currentCell , TravelDirection.Diagonal);
            addCell(x - 1,y,            currentCell , TravelDirection.straight);
            addCell(x - 1,y + 1,     currentCell , TravelDirection.Diagonal);
            addCell(x,y - 1,            currentCell , TravelDirection.straight);
            addCell(x,y + 1,            currentCell , TravelDirection.straight);
            addCell(x + 1,y - 1,     currentCell , TravelDirection.Diagonal);
            addCell(x + 1,y,            currentCell , TravelDirection.straight);
            addCell(x + 1,y + 1,     currentCell , TravelDirection.Diagonal);
            boolean proceed = false;
            while (!proceed) {
                try {
                    currentCell = (Cell) open.remove();
                } catch (NoSuchElementException e) {
                    // TODO : Handel the exception
                }
                if (!visited[currentCell.x][currentCell.y]) {
                    proceed = true;
                }
            }
        }
        Cell pathCell = currentCell;
        while (pathCell != null) {
            path.add(pathCell);
            pathCell = pathCell.parent;
        }
        return path;
    }
    private void addCell(int x, int y, Cell parent,TravelDirection d) {

        try {
            if (map[y][x] != 5) {
                Cell c = new Cell(x, y, map[y][x]);
                c.totalCost(parent,h,end,d);
                open.add(c);
            }
        } catch (IndexOutOfBoundsException e) {
            //TODO : Handel the exception
        }
    }
    
}
