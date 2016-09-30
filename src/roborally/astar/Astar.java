package roborally.astar;

import java.math.BigInteger;
import java.util.*;

import roborally.astar.Tile.TileValueIndex;
import roborally.board.*;
import roborally.model.Cost;
import roborally.model.Direction;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Energy;

/**
 * A class implementing the A-star algorithm.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public final class Astar {
	
	/**
	 * Variable storing the possible paths to go from a tile.
	 * 
	 * @note	The order in which the elements appear correspond with DIRECTIONS_ENUM
	 */
	public final static BigInteger[][] DIRECTIONS = { {BigInteger.ZERO,BigInteger.valueOf(-1)}, {BigInteger.ONE,BigInteger.ZERO}, {BigInteger.ZERO,BigInteger.ONE}, {BigInteger.valueOf(-1),BigInteger.ZERO} };
	/**
	 * Variable storing the possible directions to go from a tile.
	 * 
	 * @note	The order in which the elements appear correspond with DIRECTIONS
	 */
	public final static Direction[] DIRECTIONS_ENUM = { Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT };
	/**
	 * The H multiplier to use for the a-star heuristic.
	 */
	public final static BigInteger H_ESTIMATE = BigInteger.valueOf(0);
	/**
	 * The multiplier to add turns to the g score.
	 */
	public final static BigInteger TURN_PUNISH = BigInteger.valueOf(1);
	/**
	 * The multiplier to add translations to the g score.
	 */
	public final static BigInteger TRANSLATE_PUNISH = BigInteger.valueOf(5);
	/**
	 * The amounts of tiles that will be maximally searched per Manhattan
	 * distance difference between the end and start tile.
	 */
	public final static BigInteger SEARCH_LIMIT_PER_MHDISTANCEUNIT = BigInteger.valueOf(200);
	
	/**
	 * Returns a tile list that contains efficient paths to the end position if energy is not taken into consideration.
	 * If energy is taken into consideration it returns a tile list of every possible ending tile that is reachable with the
	 * given robot and board.
	 * 
	 * @pre		The given start model may not refer the null reference.
	 * 			| startmodel != null
	 * @pre		The given  end position may not refer the null reference.
	 * 			| endpos != null
	 * @param	startmodel
	 * 			The robot to seek the path with.
	 * @param	endpos
	 * 			The end position to seek the path to.
	 * @param	takeEnergyIntoConsideration
	 * 			Boolean defining the output of the method.
	 * @return	Every tile is one Manhattan distance away from it's parent position, except for the start position tile.
	 * 			| for each i in [0..result.size()-1] : 
	 * 			| 	if (!result.get(i).getPosition().equals(startmodel.getPosition()))
	 * 			| 		then result.get(i).getPosition().getManhattanDistanceSeparation(result.get(i).getParentPosition()) == 1
	 * @return	Every tile has a position that is possible for the given robot
	 * 			| for each tile in result :
	 * 			| 	startmodel.getBoard().canHaveBoardModelAtNoBindingCheck(tile.getPosition(), startmodel)
	 * @return 	Every tile's energy is equal to its parent tile's energy decreased by the cost of one translation
	 * 			and the cost of one rotation multiplied by the amount of rotations required to turn to this tile's
	 * 			direction from the parent tile's direction.
	 * 			| for each tile in result :
	 * 			| 	tile.getEnergy().equals(parentTile.getEnergy()
	 * 			|		.subtract(startmodel.getEnergyCostOf(Cost.MOVE))
	 * 			|		.subtract(startmodel.getEnergyCostOf(Cost.TURN).multiply(
	 * 			|			Direction.amountOfEfficientTurnsToDirection(parentTile.getDirection(), newTile.getDirection()))))
	 * @return	The difference between a tile's position and its parent's position is defined by the tile's direction order.
	 * 			If the tile's direction dimension is 1 then the x coordinate has been decreased by the tile's direction order.
	 * 			If the tile's direction dimension is 2 then the y coordinate has been decreased by the tile's direction order.
	 * 			| for each Tile tile in result :
	 * 			| 	if (getDirectionDimension(tile.getDirection()) == 1)
	 * 			|		then tile.getParentPosition().getCoordinate(Dimension.HORIZONTAL) - tile.getPosition().getCoordinate(Dimension.HORIZONTAL) = tile.getDirection().getDirectionOrder()
	 * 			| 			&& tile.getParentPosition().getCoordinate(Dimension.VERTICAL) - tile.getPosition().getCoordinate(Dimension.VERTICAL) = 0
	 * 			| 	if (getDirectionDimension(tile.getDirection()) == 2)
	 * 			|		then tile.getParentPosition().getCoordinate(Dimension.HORIZONTAL) - tile.getPosition().getCoordinate(Dimension.HORIZONTAL) = 0
	 * 			| 			&& tile.getParentPosition().getCoordinate(Dimension.VERTICAL) - tile.getPosition().getCoordinate(Dimension.VERTICAL) = tile.getDirection().getDirectionOrder()
	 * @return	If energy is not taken into consideration, the last tile must be the ending position.
	 * 			| if (!takeEnergyIntoConsideration)
	 * 			| 	then result.get(result.size()-1).getPosition().equals(endpos)
	 * @return 	If energy is taken into consideration, every Tile has a positive energy
	 * 			| if (takeEnergyIntoConsideration)
	 * 			|	then for each tile in result : 
	 * 			|		tile.getEnergy().compareTo(Energy.WS_0) > 0
	 * @throws	IllegalArgumentException
	 * 			If the energy is not taken into consideration
	 * 			and the end position is too complex to reach.
	 * @throws  IllegalArgumentException
	 * 			If the ending path can not be reached.
	 */
	public static List<Tile> getAstarTileList(Robot startmodel, Position endpos, boolean takeEnergyIntoConsideration)
			throws IllegalArgumentException{
		PriorityQueue<Tile> openList = new PriorityQueue<Tile>();
		List<Tile> closedList = new ArrayList<Tile>();
		
		Tile parentTile = new Tile();
		
		boolean found = false;
		BigInteger maxX = BigInteger.valueOf(startmodel.getBoard().getSizeAt(Dimension.HORIZONTAL));
		BigInteger maxY = BigInteger.valueOf(startmodel.getBoard().getSizeAt(Dimension.VERTICAL));
		
		BigInteger counter = BigInteger.ZERO;
		BigInteger mhDistance = startmodel.getPosition().getManhattanDistanceSeparation(endpos);
		
		parentTile.setNodeValueGHF(BigInteger.ZERO, BigInteger.ZERO);
		parentTile.setNodeValueXY(BigInteger.valueOf(startmodel.getCoordinate(Dimension.HORIZONTAL)), BigInteger.valueOf(startmodel.getCoordinate(Dimension.VERTICAL)));
		parentTile.setNodeValuePXPY(parentTile.getNodeValueAtIndex(TileValueIndex.X), parentTile.getNodeValueAtIndex(TileValueIndex.Y));
		parentTile.setEnergy(startmodel.getEnergy());
		parentTile.setDirection(startmodel.getDirection());
		
		openList.add(parentTile);
		
		while (openList.size() > 0){
			parentTile = openList.poll();
			//System.out.println("parent:"+parentTile.toString());
			counter = counter.add(BigInteger.ONE);
			//System.out.println(counter + " :: " + SEARCH_LIMIT_PER_MHDISTANCEUNIT.multiply(mhDistance) + " // " + counter.compareTo(SEARCH_LIMIT_PER_MHDISTANCEUNIT.multiply(mhDistance)));
			if (!takeEnergyIntoConsideration && counter.compareTo(SEARCH_LIMIT_PER_MHDISTANCEUNIT.multiply(mhDistance)) > 0){
				throw new IllegalArgumentException("Search limit has been reached, position is unreachable or too complex to reach.");
			}
			
			if (parentTile.getNodeValueAtIndex(TileValueIndex.X).equals(BigInteger.valueOf(endpos.getCoordinate(Dimension.HORIZONTAL)))
				&& parentTile.getNodeValueAtIndex(TileValueIndex.Y).equals(BigInteger.valueOf(endpos.getCoordinate(Dimension.VERTICAL)))){
				closedList.add(parentTile);
				found = true;
				break;
			}
			
			for (int i = 0; i < 4; i++){				
				Tile newTile = new Tile();
				newTile.setNodeValueXY(parentTile.getNodeValueAtIndex(TileValueIndex.X).add(DIRECTIONS[i][0]), parentTile.getNodeValueAtIndex(TileValueIndex.Y).add(DIRECTIONS[i][1]));
				newTile.setEnergy(parentTile.getEnergy().subtract(startmodel.getEnergyCostOf(Cost.MOVE)));
				newTile.setDirection(DIRECTIONS_ENUM[i]);
				
				BigInteger newTileX = newTile.getNodeValueAtIndex(TileValueIndex.X);
				BigInteger newTileY = newTile.getNodeValueAtIndex(TileValueIndex.Y);
				
				if ( newTileX.compareTo(BigInteger.ZERO) < 0
						|| newTileY.compareTo(BigInteger.ZERO) < 0
						|| newTileX.compareTo(maxX) > 0
						|| newTileY.compareTo(maxY) > 0 ){
					continue;
				}
				
				if (parentTile.getParentPosition().equals(newTile.getPosition())){
					continue;
				}
				
				BigInteger Gscore = parentTile.getNodeValueAtIndex(TileValueIndex.G).add(TRANSLATE_PUNISH);

				if (!startmodel.getBoard().canHaveBoardModelAtNoBindingCheck(newTile.getPosition(), startmodel)){
					continue;
				}
				
				int turnsFromParent = Direction.amountOfEfficientTurnsToDirection(parentTile.getDirection(), newTile.getDirection());
				
				newTile.setNodeValueT(parentTile.getNodeValueAtIndex(TileValueIndex.T).add(BigInteger.valueOf(turnsFromParent)));
				Gscore = Gscore.add(TURN_PUNISH.multiply(newTile.getNodeValueAtIndex(TileValueIndex.T)));
				newTile.setEnergy(newTile.getEnergy().subtract(startmodel.getEnergyCostOf(Cost.TURN).multiply(turnsFromParent)));

				if (takeEnergyIntoConsideration && newTile.getEnergy().getEnergyAmount() < 0){
					continue;
				}
                
                Tile tileInOpenList = null;
                
                for (Tile tile : openList){
                	if (tile.getNodeValueAtIndex(TileValueIndex.X).equals(newTileX) && tile.getNodeValueAtIndex(TileValueIndex.Y).equals(newTileY)){
                		tileInOpenList = tile;
                		break;
                	}
                }
                
                if (tileInOpenList != null && tileInOpenList.getNodeValueAtIndex(TileValueIndex.G).compareTo(Gscore) <= 0)
                    continue;
                
                Tile tileInClosedList = null;
                
                for (Tile tile : closedList){
                	if (tile.getNodeValueAtIndex(TileValueIndex.X).equals(newTileX) && tile.getNodeValueAtIndex(TileValueIndex.Y).equals(newTileY)){
                		tileInClosedList = tile;
                		break;
                	}
                }
                
                if (tileInClosedList != null && tileInClosedList.getNodeValueAtIndex(TileValueIndex.G).compareTo(Gscore) <= 0)
                    continue;
                
                newTile.setNodeValuePXPY(parentTile.getNodeValueAtIndex(TileValueIndex.X), parentTile.getNodeValueAtIndex(TileValueIndex.Y));
                newTile.setNodeValueGHF(Gscore, endpos.getManhattanDistanceSeparation(new Position(newTileX.longValue(), newTileY.longValue())).multiply(H_ESTIMATE));

				//System.out.println(newTile.toString());
                openList.add(newTile);
			}
			
			closedList.add(parentTile);
		}

		if (found || takeEnergyIntoConsideration){
			return closedList;
		}
		throw new IllegalArgumentException("Robot is unable to reach given position due to obstacles.");
	}
	
	/**
	 * Returns the optimal end situation for the given two tile lists.
	 * 
	 * @param 	closedTiles1
	 * 			The first tile list to calculate the optimal end positions with.
	 * @param 	closedTiles2
	 * 			The second tile list to calculate the optimal end positions with.
	 * @return	Returns the combination of tiles, where one tile is from the first given tile list and
	 * 			the second tile from the second given tile list, that minimizes the Manhattan distance
	 * 			between the two tiles as first priority and as second priority maximizes the total amount
	 * 			of energy left.
	 * 			| closedTiles1.contains(result[0]) &&
	 * 			| closedTiles2.contains(result[1]) &&
	 * 			| for each tile1 in closedTiles1 :
	 * 			|	for each tile2 in closedTiles2 :
	 * 			|		result[0].getPosition().getManhattanDistanceSeperation(result[1].getPosition())
	 * 			|			.compareTo(tile1.getPosition().getManhattanDistanceSeperation(tile2.getPosition())) <= 0
	 * 			|		result[0].getEnergy().add(result[1].getEnergy()) &&
	 * 			|			.compareTo(tile1.getEnergy().add(tile2.getEnergy())) >= 0
	 * 			| [SEQUENTIAL]
	 * @throws	NullPointerException
	 * 			At least one of the given lists refers the null reference.
	 * 			| (closedTiles1 == null || closedTiles2 == null)
	 */
	public static Tile[] findOptimalRoute(List<Tile> closedTiles1, List<Tile> closedTiles2)
			throws NullPointerException{
		if(closedTiles1 == null || closedTiles2 == null)
			throw new NullPointerException("At least one of the given lists refers the null reference.");
		Tile[] tiles = new Tile[2];
		Energy energy = null;
		BigInteger mhdistance = BigInteger.valueOf(-1);
		
		for (Tile otile1 : closedTiles1){
			for (Tile otile2 : closedTiles2){
				Position pos1 = otile1.getPosition();
				Position pos2 = otile2.getPosition();
				if (otile1.getPosition().equals(otile2.getPosition())){
					continue;
				}
				if (tiles[0] == null & tiles[1] == null){
					tiles[0] = otile1;
					tiles[1] = otile2;
				}
				BigInteger mhdistancesep = pos1.getManhattanDistanceSeparation(pos2);
				if (mhdistance.equals(BigInteger.valueOf(-1)) || mhdistancesep.compareTo(mhdistance) < 0){
					tiles[0] = otile1;
					tiles[1] = otile2;
					mhdistance = mhdistancesep;
					energy = otile1.getEnergy().add(otile2.getEnergy());
					continue;
				}
				if (mhdistancesep.equals(mhdistance)){
					Energy oenergy = otile1.getEnergy().add(otile2.getEnergy());
					if (oenergy.compareTo(energy) > 0){
						tiles[0] = otile1;
						tiles[1] = otile2;
						energy = oenergy;
					}
				}
			}
		}
		return tiles;
	}
	
	/**
	 * Returns the optimal end situation for the given two robots.
	 * 
	 * @param	first
	 * 			The first robot
	 * @param	second
	 * 			The second robot
	 * @return	Calculates the two astar tile lists for both robots,
	 * 			taking energy requirements into consideration.
	 * 			Then returns the optimal end positions for these
	 * 			two robots given their tile lists.
	 * 			| result == findOptimalRoute(
	 * 			|				Astar.getAstarTileList(first, second.getPosition(), true),
	 * 			|			    Astar.getAstarTileList(second, first.getPosition(), true))
	 * @throws	NullPointerException
	 * 			At least one of the given robots refers the null reference.
	 * 			| (first == null || second == null)
	 */
	public static Tile[] findOptimalRoute(Robot first, Robot second)
			throws NullPointerException{
		if(first == null || second == null)
			throw new NullPointerException("At least one of the given robots refers the null reference.");
		List<Tile> tileList1 = Astar.getAstarTileList(first, second.getPosition(), true);
		List<Tile> tileList2 = Astar.getAstarTileList(second, first.getPosition(), true);
		return findOptimalRoute(tileList1, tileList2);
	}
	
	/**
	 * Returns a position list generated from the given tile list.
	 * 
	 * @param	tileList
	 * 			The tile list to generate a position list from.
	 * @return	Every consecutive position in the generated position list
	 * 			is one Manhattan distance away from each other.
	 * 			| for each i in [1..result.size()-1]
	 * 			| 	result.get(i).getManhattanDistanceSeparation(result.get(i-1))
	 * @return	Every position that's in the result position list is contained by
	 * 			the given tile list's positions.
	 * 			| for each Position pos in result :
	 * 			| 	for exactly one Tile tile in tileList :
	 * 			|		tile.getPosition().equals(pos)
	 * @return	Every position that is in the result position list is contained by
	 * 			the given tile list's parent positions.
	 * 			| for each pos in result :
	 * 			| 	for some tile in tileList :
	 * 			|		tile.getParentPosition().equals(pos)
	 * @throws	NullPointerException
	 * 			The given tile list refers the null reference.
	 * 			| tileList == null
	 */
	public static List<Position> generatePositionListFromTileList(List<Tile> tileList)
			throws NullPointerException{
		if(tileList == null)
			throw new NullPointerException("The given tile list refers the null reference.");
		List<Position> returnList = new ArrayList<Position>();
        Tile lastTile = tileList.get(tileList.size() - 1);
        for(int i=tileList.size() - 1; i>=0; i--) {
        	if (tileList.get(i).getPosition().equals(lastTile.getParentPosition())
        			|| i == tileList.size() - 1){
            	returnList.add(tileList.get(i).getPosition());
            	lastTile = tileList.get(i);
            }
            else
            	tileList.remove(i);
        }
        Collections.reverse(returnList);
        return returnList;
	}
	
	/**
	 * Generates a position list from the given tile list,
	 * where the given last tile must be the end position tile.
	 * 
	 * @param	tileList
	 * 			The tile list to create the position list from.
	 * @param	lastTile
	 * 			The ending position tile.
	 * @return	If the given last tile is not yet the last tile in the given tile list,
	 * 			it will be added to the list. Then the position list will result from this new list.
	 * 			Else returns the generated position list from the given tile list.
	 * 			| if (tileList.get(tileList.size()-1) != lastTile)
	 *			|	then result == generatePositionListFromTileList(Collections.addAll(tileList, lastTile))
	 * 			| else
	 * 			|	then result == generatePositionListFromTileList(tileList)
	 * @throws	NullPointerException
	 * 			At least one of the given arguments refers the null reference.
	 * 			| (tileList == null || lastTile == null)
	 */
	public static List<Position> generatePositionListFromTileList(List<Tile> tileList, Tile lastTile)
			throws NullPointerException{
		if(tileList == null || lastTile == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		if (tileList.get(tileList.size()-1) != lastTile)
			tileList.add(lastTile);
        return generatePositionListFromTileList(tileList);
	}
	
	/**
	 * Returns the lowest amount of energy required for the given robot
	 * in order to reach the given end position.
	 * 
	 * @param	startmodel
	 * 			The robot to start the calculation with.
	 * @param	endpos
	 * 			The end position the robot must reach.
	 * @return	The returned energy is equal to the given robot's energy subtracted
	 * 			by the energy of the end position tile returned by the astar tile list.
	 * 			| let
	 * 			|	closedTiles = getAstarTileList(startmodel, endpos, false)
	 * 			| in :
	 * 			| result.equals(startmodel.getEnergy().subtract(closedTiles.get(closedTiles.size()- 1).getEnergy()))
	 * @throws	IllegalArgumentException
	 * 			If the energy is not taken into consideration
	 * 			and the end position is too complex to reach.
	 * @throws  IllegalArgumentException
	 * 			If the ending path can not be reached.
	 * @throws	NullPointerException
	 * 			At least one of the given arguments refers the null reference.
	 * 			| (startmodel == null || endpos == null)
	 */
	public static Energy getEnergyRequiredForAstarRoute(Robot startmodel, Position endpos)
			throws IllegalArgumentException, NullPointerException{
		if(startmodel == null || endpos == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		List<Tile> closedTiles = getAstarTileList(startmodel, endpos, false);
		return startmodel.getEnergy().subtract(closedTiles.get(closedTiles.size() - 1).getEnergy());
	}
	
	/**
	 * Returns two position lists that describe the optimal path for the two given robots
	 * to minimize their Manhattan distance with the least energy requirements.
	 * 
	 * @pre		The given robots are located on the same board.
	 * 			| first.getBoard() == second.getBoard()
	 * @param	first
	 * 			The first robot to work with.
	 * @param	second
	 * 			The second robot to work with.
	 * @return	The result contains two lists, respectively for the first and second robot.
	 * 			These two lists contain the optimal route to follow for both robots to reach the
	 * 			optimal ending situation in Manhattan distance and energy requirements.
	 * 			| let 
	 * 			|	Tile[] endTiles = findOptimalRoute(Astar.getAstarTileList(first, second.getPosition(), true),
	 * 			|									   Astar.getAstarTileList(second, first.getPosition(), true))
	 * 			| in :
	 * 			| result == Collections.addAll(new ArrayList<List<Position>>(),
	 * 			|				Astar.generatePositionListFromTileList(tileList1, endTiles[0]), 
	 * 			|				Astar.generatePositionListFromTileList(tileList2, endTiles[1]))
	 * @throws	NullPointerException
	 * 			At least one of the given robots refers the null reference.
	 * 			| (first == null || second == null)
	 */
	public static List<List<Position>> generateOptimalPositionLists(Robot first, Robot second)
			throws NullPointerException{
		if(first == null || second == null)
			throw new NullPointerException("At least one of the given robots refers the null reference.");
		List<List<Position>> returnList = new ArrayList<List<Position>>();
		List<Tile> tileList1 = Astar.getAstarTileList(first, second.getPosition(), true);
		List<Tile> tileList2 = Astar.getAstarTileList(second, first.getPosition(), true);
		Tile[] endTiles = findOptimalRoute(tileList1, tileList2);
		returnList.add(Astar.generatePositionListFromTileList(tileList1, endTiles[0]));
		returnList.add(Astar.generatePositionListFromTileList(tileList2, endTiles[1]));
		return returnList;
	}
}
