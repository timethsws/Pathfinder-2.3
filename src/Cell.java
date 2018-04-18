/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author timethsubasinghe
 */
public class Cell {

    int x, y;                       // X & Y coordinates
    private int cost;               // Cost to travel into this cell
    double totTravelCost,h_cost;    // Total travel cost & Distance to the end cell
    Cell parent;                    // The cell visited this cell from

    public Cell(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public void totalCost(Cell parent,Heuristic h, Cell endCell,TravelDirection d) {
        if (parent != null) {
            this.parent = parent;
            if(d == TravelDirection.Diagonal){
                this.totTravelCost = this.cost * 1.414 + parent.totTravelCost;
            }else{
                this.totTravelCost = this.cost + parent.totTravelCost;
            }
        } else {
            this.totTravelCost = 0;
        }
        calcHCost(h,endCell);
    }
    
    private void calcHCost(Heuristic h,Cell endCell){
        switch (h){
            case NONE:
                h_cost = 0;
                break;
            case MANHATTEN:
                h_cost = Math.abs(endCell.x - this.x) + Math.abs(endCell.y - this.y);
                break;
            case CHEBYSHEV:
                h_cost = Math.max(Math.abs(endCell.x - this.x) , Math.abs(endCell.y - this.y));
                break;
            case EUCLIDEAN:
                h_cost = Math.sqrt(Math.pow((this.x - endCell.x), 2) + Math.pow((this.y - endCell.y), 2));
                break;
        }
    }
}
