import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  //exercise 4: add sand
  public static final int SAND = 2;
  //exercise 6: add water
  public static final int WATER = 3;
  
  //extra extra credit: add fire and reset button
  public static final int FIRE = 4;
  public static final int RESET = 5;
  
  //constants for color
  public static final Color GRAY = new Color (128, 128, 128);
  public static final Color YELLOW = new Color (224, 224, 27);
  public static final Color BLUE = new Color (31, 31, 237);
  public static final Color RED = new Color (232, 14, 14);
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[6];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[FIRE] = "Fire";
    names[RESET] = "Reset";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    
    //exercise 1: constructor, and make sure screen is empty
    grid = new int[numRows][numCols];
    reset();
  }
  
  // code for reset button, which clears the whole screen
  private void reset()
  {
	  for (int i = 0; i < grid.length; i++) 
	    {
	    	for (int j = 0; j < grid[0].length; j++)
	    	{
	    		grid[i][j] = EMPTY;
	    	}
	    }
  }
  
  //exercise 2: called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
	  if (tool == RESET)
	  {
		  reset();
	  }
	  grid[row][col] = tool;
  }

  //exercise 3: copies each element of grid into the display
  public void updateDisplay()
  {
	  for (int i = 0; i < grid.length; i++)
	  {
		  for (int j = 0; j <  grid[0].length; j++)
		  {
			  if (grid[i][j] == METAL)
			  {
				  display.setColor(i, j, GRAY);
			  }
			  else if (grid[i][j] == SAND)
			  {
				  display.setColor(i, j, YELLOW);
			  }
			  else if (grid[i][j] == WATER)
			  {
				  display.setColor(i, j, BLUE);
			  }
			  else if (grid[i][j] == FIRE)
			  {
				  display.setColor(i, j, RED);
			  }
			  else 
			  {
				  display.setColor(i, j, Color.black);
			  }
		  }
	  }
  }

  // extra on exercise 6 & 7: minimize duplicate code
  // this function checks if grid[r2][c2] is equal to type, it switches it
  private void move(int r1, int c1, int r2, int c2, int type)
  {
	  if (grid[r2][c2] == type)
	  {
		  grid[r2][c2] = grid[r1][c1];
		  grid[r1][c1] = type;
	  }
  }
  
  //called repeatedly.
  //exercise 5: causes one random particle to maybe do something.
  public void step()
  {
	  Random random = new Random();
	  int row = random.nextInt(grid.length);
	  int col = random.nextInt(grid[0].length);
	  
	  // movement for sand
	  if (grid[row][col] == SAND)
	  {
		  if (row + 1 < grid.length)
		  {
			  move(row, col, row + 1, col, EMPTY);
			  
			  // exercise 7: sand falls below water
			  move(row, col, row + 1, col, WATER);
		  }
	  }
	  // movement for water
	  else if (grid[row][col] == WATER)
	  {
		  int direction = random.nextInt(3);
		  if (direction == 0)
		  {
			  if (col > 0)
			  {
				  move(row, col, row, col - 1, EMPTY);
			  }
		  }
		  else if (direction == 1)
		  {
			  if (col + 1 < grid[0].length)
			  {
				  move(row, col, row, col + 1, EMPTY);
			  }
		  }
		  else 
		  {
			  if (row + 1 < grid.length)
			  {
				  move(row, col, row + 1, col, EMPTY);
			  }
		  }
	  }
	  // movement for fire
	  else if (grid[row][col] == FIRE)
	  {
		  int direction = random.nextInt(3);
		  if (direction == 0)
		  {
			  if (col > 0)
			  {
				  move(row, col, row, col - 1, EMPTY);
			  }
		  }
		  else if (direction == 1)
		  {
			  if (col + 1 < grid[0].length)
			  {
				  move(row, col, row, col + 1, EMPTY);
			  }
		  }
		  else 
		  {
			  if (row > 0)
			  {
				  move(row, col, row - 1, col, EMPTY);
			  }
		  }
	  }
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}