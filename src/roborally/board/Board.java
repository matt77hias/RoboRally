package roborally.board;

import roborally.Terminatable;
import roborally.model.*;
import roborally.model.inventory.item.InventoryModel;
import be.kuleuven.cs.som.annotate.*;

import	java.util.*;

import roborally.conditiontester.ConditionTester;

/**
 * A class of two dimensional boards involving a horizontal size, a vertical size
 * and collection of board models.
 * 
 * @invar	The size corresponding to the 'X', horizontal dimension
 * 			must be a valid size for any board.
 * 			| isValidSizeAt(1, getSizeAt(1))
 * @invar	The size corresponding to the 'Y', vertical dimension
 * 			must be a valid size for any board.
 * 			| isValidSizeAt(2, getSizeAt(2))
 * @invar	The length of the size array of every board must be equal
 * 			to the dimension for boards.
 * 			| getSize().length == getNbDimensions()
 * @invar	Every board must have proper board models.
 * 			| hasProperBoardModels()
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Board implements Terminatable, Iterable<BoardModel>{
	
	/**
	 * Variable containing the standard horizontal size for boards.
	 */
	public static final long STANDARD_BOARD_HORIZONTAL_SIZE = 10L;
	
	/**
	 * Variable containing the standard vertical size for boards.
	 */
	public static final long STANDARD_BOARD_VERTICAL_SIZE = 10L;
	
	/**
	 * Initializes a new board with standard size values.
	 * 
	 * @post	The new size of the board is set to sizeX by sizeY.
	 * 			| new.getSize() == {STANDARD_BOARD_HORIZONTAL_SIZE, STANDARD_BOARD_VERTICAL_SIZE}
	 * @post	The new board isn't terminated.
	 * 			| new.isTerminated() == false
	 */
	public Board(){
		this(STANDARD_BOARD_HORIZONTAL_SIZE, STANDARD_BOARD_VERTICAL_SIZE);
	}
	
	/**
	 * Creates a new board with the given size for the first (horizontal)
	 * and second (vertical) dimension.
	 * 
	 * @param 	sizeX
	 * 			The size of the board in the first 'X', horizontal dimension.
	 * @param 	sizeY
	 * 			The size of the board in the second 'Y', vertical dimension.
	 * @post	The new size of the board is set to sizeX by sizeY.
	 * 			| new.getSize() == {sizeX, sizeY}
	 * @post	The new board isn't terminated.
	 * 			| new.isTerminated() == false
	 * @throws 	IllegalArgumentException
	 * 			At least one of the given size values is invalid for a board.
	 * 			| !isValidSizeAt(Dimension.HORIZONTAL, sizeX) || !isValidSizeAt(Dimension.VERTICAL,sizeY))
	 */
	public Board(long sizeX, long sizeY) throws IllegalArgumentException {
		if(!isValidSizeAt(Dimension.HORIZONTAL, sizeX) || !isValidSizeAt(Dimension.VERTICAL,sizeY))
			throw new IllegalArgumentException("At least one of the given sizes is invalid.");
		size = new long[]{sizeX, sizeY};
		this.isTerminated = false;
	}
	
	/**
	 * Terminates this board and all board models situated on this board.
	 * 
	 * @effect	All board models on this board are terminated.
	 * 			| for every position in boardModels.keySet() :
	 * 			| 	 for each model in boardModels.get(position) :
	 * 			|		model.terminate()
	 * @post	This board is terminated.
	 * 			| new.isTerminated() == true
	 */
	@Override
	public void terminate() {
		if(!isTerminated){
			for(Object position : boardModels.keySet().toArray()){
				List<BoardModel> temp = boardModels.get(position);
				while(temp.size() != 0){
					temp.get(0).terminate();
				}
			}
			this.isTerminated = true;
		}
	}

	/**
	 * Returns whether this board is terminated.
	 */
	@Basic @Raw @Override
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
	 * Variable registering whether this board is terminated.
	 */
	private boolean isTerminated;

	/**
	 * Returns the amount of dimensions of every board.
	 * 
	 * @note	For a two dimensional boards the amount of dimensions is two.
	 */
	@Immutable
	public static int getNbDimensions(){
		return BOUNDARIES.length;
	}
	
	/**
	 * Returns the maximum boundary value according to the given dimension for every board.
	 * 
	 * @param 	dimension
	 * 			The dimension of which the boundary has to be returned.
	 * @return	Returns the maximum boundary according to the given dimension for every board.
	 * 			| result == BOUNDARIES[dimension.getDimensionnr()-1]
	 * @throws	IllegalDimensionException
	 * 			The given dimension is invalid.
	 * 			| !Dimension.isValidDimension(dimension)
	 */
	@Immutable
	public static long getBoundaryAt(Dimension dimension)
			throws IllegalDimensionException{
		if(!Dimension.isValidDimension(dimension))
			throw new IllegalDimensionException("The given dimension is invalid.");
		return BOUNDARIES[dimension.getDimensionnr()-1];
	}
	
	/**
	 * Returns the maximum boundary values for every board.
	 */
	@Basic @Immutable
	public static long[] getBoundaries(){
		return BOUNDARIES;
	}

	/**
	 * Array storing the maximum boundaries for every board.
	 */
	private static final long[] BOUNDARIES = new long[]{Long.MAX_VALUE, Long.MAX_VALUE};
	
	/**
	 * Returns whether the given size is valid at the given dimension.
	 * 
	 * @param 	dimension
	 * 			The dimension of which the size has to be checked.
	 * @return	The size must be equal or greater than zero
	 * 			and less to the boundary value corresponding to
	 * 			the given dimension. The given dimension must be a
	 * 			valid dimension.
	 * 			| result == (Dimension.isValidDimension(dimension))
	 * 			|			&& (size >= 0) && (size <= getBoundaryAt(dimension))
	 */
	public static boolean isValidSizeAt(Dimension dimension, long size){
		return (Dimension.isValidDimension(dimension)) && (size >= 0) && (size <= getBoundaryAt(dimension));
	}

	/**
	 * Returns the size according to the given dimension of this board.
	 * 
	 * @param 	dimension
	 * 			The dimension according to which the size is returned.
	 * 			| result == getSize()[dimension.getDimensionnr()-1]
	 * @throws	IllegalDimensionException
	 * 			The given dimension is invalid.
	 * 			| !Dimension.isValidDimension(dimension)
	 */
	@Raw @Immutable
	public long getSizeAt(Dimension dimension)
			throws IllegalDimensionException{
		if(!Dimension.isValidDimension(dimension))
			throw new IllegalDimensionException("The given dimension is invalid.");
		return size[dimension.getDimensionnr()-1];
	}
	
	/**
	 * Returns the size of this board.
	 * 
	 * @note	size[0]	corresponds with the 'X', horizontal size of this board.
	 * 			size[1] corresponds with the 'Y', vertical size of this board.
	 */
	@Basic @Raw @Immutable
	public long[] getSize(){
		return size.clone();
	}
	
	/**
	 * Array storing the size of this board.
	 * 
	 * @note	Position (0,0) is always included in the board.
	 * 			This means that a 20x20 board always have a
	 * 			physical size of 21 by 21.
	 */
	private final long[] size;

	/**
	 * Checks if this board contains the given coordinates.
	 * 
	 * @param 	coordinates
	 * 			The coordinates that have to be checked.
	 * @return	True if and only if the given coordinates
	 * 			are valid for this board.
	 * 			| result == (coordinates.length == getNbDimensions()) &&
	 * 			| (for i from 1 by 1 till getNbDimensions()) : 
	 * 			|	canHaveAsCoordinate(coordinates[i-1],Dimension.dimensionFromInt(i))
	 */
	@Raw
	public boolean canHaveAsCoordinates(long[] coordinates){
		if(coordinates.length != getNbDimensions())
			return false;
		for(int i=1; i<=getNbDimensions(); i++){
			if(!canHaveAsCoordinate(coordinates[i-1],Dimension.dimensionFromInt(i)))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks if this board contains the given coordinate for the given dimension.
	 * 
	 * @param 	coordinate
	 * 			The coordinate that has to be checked.
	 * @param 	dimension
	 * 			The dimension that corresponds to the given coordinate.
	 * @return	true if and only if the given coordinate
	 * 			corresponding to the given dimension is valid for this board.
	 * 			| result == Dimension.isValidDimension(dimension) &&
	 * 			| 	(0<=coordinate && coordinate<=getSizeAt(dimension))
	 */
	@Raw
	public boolean canHaveAsCoordinate(long coordinate, Dimension dimension){
		return Dimension.isValidDimension(dimension) && (0<=coordinate && coordinate<=getSizeAt(dimension));
	}
	
	/**
	 * Checks if the given position is already used as key value
	 * for the board model collection of this board.
	 * 
	 * @param 	position
	 * 			The position that has to be checked.
	 * @return	True if and only if the given position is
	 * 			already used as key value.
	 * 			| result == getAllBoardModels().containsKey(position)
	 */
	@Raw
	public boolean containsPositionKey(Position position){
		return boardModels.containsKey(position);
	}
	
	/**
	 * Checks if the given board model is situated on a position on this board.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to be checked.
	 * @return	True if and only if the board model is situated on a position on this board.
	 * 			| for each key in boardModels.keySet():
	 * 			| 	if(getBoardModelsAt(key).contains(boardModel)) then
	 * 			| 	result == true
	 * 			| end for
	 * 			| result == false
	 */
	@Raw
	public boolean containsBoardModel_allCheck(BoardModel boardModel){
		for(Position key : boardModels.keySet()){
			if(getBoardModelsAt(key).contains(boardModel))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if the given board model is situated on its position on this board.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to be checked.
	 * @return	True if and only if the board model is situated on its position on this board,
	 * 			if this position doesn't refer the null reference.
	 * 			| result == boardModel.getPosition() != null && containsPositionKey(boardModel.getPosition())
	 * 			|			&& getBoardModelsAt(boardModel.getPosition()).contains(boardModel)
	 */
	@Raw
	public boolean containsBoardModel_positionCheck(BoardModel boardModel){
		return boardModel.getPosition() != null && containsPositionKey(boardModel.getPosition()) 
				&& getBoardModelsAt(boardModel.getPosition()).contains(boardModel);
	}
	
	/**
	 * Checks if the given board model is situated on the given position on this board.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to be checked.
	 * @param	position
	 * 			The position that has to be checked.
	 * @return	True if and only if the board model is situated on the given position on this board.
	 * 			| result == position != null && containsPositionKey(position)
	 * 			|			&& getBoardModelsAt(position).contains(boardModel)
	 */
	@Raw
	public boolean containsBoardModel(BoardModel boardModel, Position position){
		return position != null && containsPositionKey(position) && getBoardModelsAt(position).contains(boardModel);
	}
	
	/**
	 * Checks if the given board model could be added to this board onto
	 * the given position.
	 * 
	 * @param 	position
	 * 			The position that has to be checked.
	 * @param 	boardModel
	 * 			The board model that has to be added to this board.
	 * @return	The board of the given board model may refer
	 * 			the null reference only and the given board model
	 * 			could be positioned on the given board at the given position.
	 * 			| result == canHaveBoardModelAtNoBindingCheck(position, boardModel) && (boardModel.getBoard() == null)
	 */
	@Raw
	public boolean canHaveBoardModelAt(Position position, @Raw BoardModel boardModel){
		return canHaveBoardModelAtNoBindingCheck(position, boardModel) && (boardModel.getBoard() == null);
	 }
	
	/**
	 * Checks if the given board model could be added to this board onto
	 * the given position. No board binding check included.
	 * 
	 * @param 	position
	 * 			The position that has to be checked.
	 * @param 	boardModel
	 * 			The board model that has to be added to this board.
	 * @return	This board may not be terminated.
	 * 			| if(this.isTerminated())
	 * 			| 	then result == false
	 * @return	The given board model may not be terminated.
	 * 			The given board model may not refer the null reference.
	 * 			The given position may not refer the null reference.
	 * 			| if(boardModel == null || boardModel.isTerminated() || position == null)
	 * 			| 	then result == false
	 * @return	If the given board model is an inventory model and already picked up,
	 * 			it can not be added to this board.
	 * 			| if(InventoryModel.class.isInstance(boardModel))
	 * 			| 	then if(((InventoryModel) boardModel).isPickedUp())
	 * 			|		then result == false
	 * @return	The coordinates of the given position must be valid for this board.
	 * 			| if(!canHaveAsCoordinates(position.getCoordinates())
	 * 			| 	then result == false
	 * @return	If there are already positioned models situated on the given position
	 * 			there is checked for each situated board model that the given board model
	 * 			could be situated together with the positioned model. 
	 * 			If this is not possible for at least one positioned model, the result is false.
	 * 			| if(containsPositionKey(position)) then
	 * 			| for each BoardModel model in getAllBoardModels().get(position) do :
	 * 			| 	if(!model.canSharePositionWith(boardModel))
	 * 			| 		then result == false
	 * @return	In all other cases:
	 * 			| else then result == true
	 */
	@Raw
	public boolean canHaveBoardModelAtNoBindingCheck(Position position, @Raw BoardModel boardModel){
		if(this.isTerminated())
			return false;		
		if(boardModel == null || boardModel.isTerminated() || position == null)
			return false;
		if(InventoryModel.class.isInstance(boardModel)){
			if(((InventoryModel) boardModel).isPickedUp())
				return false;
		}
		if(!canHaveAsCoordinates(position.getCoordinates()))
			return false;
		if(containsPositionKey(position)){
			for(BoardModel model : boardModels.get(position)){				
				if (!model.canSharePositionWith(boardModel))
					return false;
			}
			
		}
		return true;
	}

	/**
	 * Checks if the board model collection of this board is valid.
	 * 
	 * @return	True if and only if the board model collection of this board is valid.
	 * 			This means that there could not exist board models situated on this board
	 * 			that are terminated or the model, its board or its position is referring
	 * 			the null reference or its board is not referring this board or its position
	 * 			is not referring the key under which that board model is stocked in this board's
	 * 			board model collection or the position has invalid coordinates for this board.
	 * 			All board models located on the same position, must have the ability to share a position.
	 * 			If this board is terminated then there may not exist board models situated on this board.
	 * 			Beside that it's not allowed that there exists a key referring the null reference
	 * 			or exist keys that refer a value that refers the null reference or refers a collection
	 * 			with no entries.
	 * 			| result ==
	 * 			| if(isTerminated())
	 * 			|	then boardModels.keySet().size() == 0
	 * 			| else
	 * 			| 	for each Position key in boardModels.keySet() :
	 * 			| 		getBoardModelsAt(key) != null &&
	 * 			| 		getBoardModelsAt(key).size() != 0 &&
	 * 			|		for each BoardModel model1 in getBoardModelsAt(key) :
	 * 			|			model1 != null &&
	 * 			|			!model1.isTerminated() &&
	 * 			|			model1.getPosition() != null &&
	 * 			|			canHaveAsCoordinates(model1.getPosition().getCoordinates()) &&
	 * 			|			model1.getBoard() == this &&
	 * 			|			model1.getPosition().equals(key) &&
	 * 			|			(for each BoardModel model2 in getBoardModelsAt(key) && model1!=model2 :
	 * 			|				model1.canSharePositionWith(model2))
	 */
	@Raw
	public boolean hasProperBoardModels(){
		for(Position key : boardModels.keySet()){
			if(this.isTerminated)
				return false;
			List<BoardModel> temp = getBoardModelsAt(key);
			if(temp == null || temp.size() == 0)
				return false;
			
			for(int i=0; i<temp.size(); i++){
				BoardModel model = temp.get(i);
				if(model == null || model.isTerminated())
					return false;
				if(model.getPosition() == null || !canHaveAsCoordinates(model.getPosition().getCoordinates()))
					return false;
				if(model.getBoard() != this || !model.getPosition().equals(key))
					return false;
				for(int j=i+1; j<temp.size(); j++){
					BoardModel other = temp.get(j);
					if(!model.canSharePositionWith(other)){
						return false;
					}
				}
			}	
		}
		return true;
	}
		
	/**
	 * Adds the given board model to this board.
	 * 
	 * @param 	position
	 * 			The position of which the coordinates corresponds
	 * 			to the location of the board model on this board.
	 * @param 	boardModel
	 * 			The board model that has to be added to this board.
	 * @post	Adds the given board model to the collection of board models
	 * 			of this board.
	 * 			| new.getAllBoardModels().get(position).contains(boardModel)
	 * @effect	Updates the position and board of the given board model.
	 * 			| boardModel.setBoard(new.this) &&
	 * 			| boardModel.setPosition(position)
	 * 			| [SEQUENTIAL]
	 * @throws	IllegalArgumentException
	 * 			The given board model could not be positioned
	 * 			onto the given position on this board.
	 * 			| !canHaveBoardModelAt(position, boardModel)
	 */
	public void addBoardModelAt(Position position, @Raw BoardModel boardModel)
		throws IllegalArgumentException{
		if(!canHaveBoardModelAt(position, boardModel))
			throw new IllegalArgumentException("The given board model could not be added to the given position.");
		if(!containsPositionKey(position))
			boardModels.put(position, new ArrayList<BoardModel>());
		boardModels.get(position).add(boardModel);
		boardModel.setBoard(this);
		boardModel.setPosition(position);
	}
	
	/**
	 * Returns all the board models located at the given position
	 * on this board.
	 * 
	 * @param 	position
	 * 			The position of which all the corresponding board models
	 * 			have to be returned.
	 * @return	If and only if there is already an existing entry, matching
	 * 			the given position, this entry is returned.
	 * 			(Returns an unmodifiable list)
	 * 			| if(boardModels.containsKey(position)) then
	 * 			| 	result == getAllBoardModels().get(position)
	 * @return	If no existing entry matches the given position,
	 * 			the null reference is returned.
	 * 			| if(!containsPositionKey(position)) then
	 * 			| 	result == null
	 */
	@Raw
	public List<BoardModel> getBoardModelsAt(Position position){
		if(!containsPositionKey(position))
			return null;
		return Collections.unmodifiableList(boardModels.get(position));
	}
	
	/**
	 * Returns all the board models of the given class located at the given position
	 * on this board.
	 * 
	 * @param 	position
	 * 			The position of which all the corresponding board models
	 * 			have to be returned.
	 * @param	clazz
	 * 			The specific class (in the board model hierarchy) for the
	 * 			returned board models.
	 * @return	The result contains every board model that is of the given
	 * 			subclass at the given position of this board
	 * 			| for each T extends BoardModel model in getBoardModelsAt(position) :
	 * 			| 	result.contains(model)
	 * @return	The result only contains board models of the given subclass
	 * 			| for each T extends BoardModel model in result :
	 * 			| 	clazz.isInstance(model)
	 * @return	If no existing entry matches the given position,
	 * 			the null reference is returned.
	 * 			| if(!containsPositionKey(position))
	 * 			| 	then result == null
	 */
	@Raw
	public <T extends BoardModel> List<T> getBoardModelsClassAt(Position position, Class<T> clazz){
		if(!containsPositionKey(position))
			return null;
		List<T> temp = new ArrayList<T>();
		for(BoardModel model : boardModels.get(position)){
			if(clazz.isInstance(model))
				temp.add(clazz.cast(model));	
		}
		return Collections.unmodifiableList(temp);
	}
	
	/**
	 * Returns an unmodifiable view of all the board models located on this board.
	 */
	@Basic @Raw
	public Map<Position, ArrayList<BoardModel>> getAllBoardModels(){
		return Collections.unmodifiableMap(boardModels);
	}
	
	/**
	 * Returns all board models of given type that are located on this board.
	 * 
	 * @param 	BoardModelType
	 * 			The board model types to look for.
	 * @return	The result contains every board model that is of the given
	 * 			subclass that is located on this board
	 * 			| for each T extends BoardModel model in getAllBoardModels() :
	 * 			| 	result.contains(model)
	 * @return	The result only contains board models of the given subclass
	 * 			| for each T extends BoardModel model in result :
	 * 			| 	BoardModelType.isInstance(model)
	 */
	@Raw
	public <T extends BoardModel> Set<T> getAllBoardModelsClass(Class<T> BoardModelType){
		Set<T> temp = new HashSet<T>();
		for (Position pos : boardModels.keySet()){
			temp.addAll(((List<T>)getBoardModelsClassAt(pos, BoardModelType)));
		}
		return Collections.unmodifiableSet(temp);
	}
	
	/**
	 * Returns the amount of board models located on this board.
	 * 
	 * @return	Returns the amount of board models located on this board.
	 * 			| count = 0
	 * 			| for each Position key in getAllBoardModels().keySet() :
	 * 			| 	count = count + getNbBoardModelsAt(key)
	 * 			| end for
	 * 			| result == count
	 */
	@Raw
	public int getNbBoardModels(){
		int count = 0;
		for(Position key : boardModels.keySet())
			count = count + getNbBoardModelsAt(key);
		return count;
	}
	
	/**
	 * Returns the amount of board models located on this board
	 * on the given position.
	 * 
	 * @return	Returns the amount of board models located on this board
	 * 			on the given position.
	 * 			| if(containsPositionKey(position))
	 * 			| 	then result == getAllBoardModels().get(position).size()
	 * @throws	If the given position isn't occupied the result
	 * 			equals zero.
	 * 			| if(!containsPositionKey(position))
	 * 			| 	then result == 0
	 */
	@Raw
	public int getNbBoardModelsAt(Position position)
		throws IllegalArgumentException{
		if(!containsPositionKey(position))
			return 0;
		return boardModels.get(position).size();
	}
	
	/**
	 * Moves the given board model that is located on this board to the given position.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to be moved.
	 * @param 	position
	 * 			The position to move the board model to.
	 * @effect 	The board model will be moved to the new position by first
	 * 			removing it from its current position and adding it to the new position
	 *			| removeBoardModel(boardModel) &&
	 *			| addBoardModelAt(position, boardModel)
	 *			| [SEQUENTIAL]
	 * @throws	NullPointerException
	 * 			The board model or the position refers the null reference.
	 * 			| boardModel == null || position == null
	 * @throws	IllegalArgumentException
	 * 			The given board model is not located on its position on this board.
	 * 			| !containsBoardModel_positionCheck(boardModel)
	 * @throws	IllegalArgumentException
	 * 			The given position is an invalid position for the given board model.
	 * 			| !canHaveBoardModelAtNoBindingCheck(position, boardModel)
	 */
	public void moveBoardModelTo(BoardModel boardModel, Position position)
			throws NullPointerException, IllegalArgumentException{
		if (boardModel == null || position == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		if (!containsBoardModel_positionCheck(boardModel))
			throw new IllegalArgumentException("The given boardmodel is not located on its position on this board");
		if (!canHaveBoardModelAtNoBindingCheck(position, boardModel))
			throw new IllegalArgumentException("The given boardmodel can not move to the given position: " + boardModel + "pos: " + position);
		removeBoardModel(boardModel);
		addBoardModelAt(position, boardModel);
	}
	
	/**
	 * A map collection with list collections of all board models
	 * situated at the same position and with the corresponding position as key.
	 */
	private	Map<Position,ArrayList<BoardModel>> boardModels = new HashMap<Position,ArrayList<BoardModel>>();
	
	/**
	 * Removes the given board model from this board.
	 * 
	 * @param	boardModel
	 * 			The board model that has to be removed form this board.
	 * @effect	If and only if the given board model is situated on this board,
	 * 			the board model is removed from this board and the board model
	 * 			its board is terminated.
	 * 			| if (getBoardModelsAt(boardModel.getPosition()).contains(boardModel)
	 * 			| && boardModel.getBoard() == this)
	 * 			| 	then boardModel.setBoard(null)
	 * @effect	If the given board model is the only board model
	 * 			located on its position on this board. The whole entry
	 * 			corresponding to the position of the given robot is removed
	 * 			form the board model collection of this board.
	 * 			| if(getBoardModelsAt(boardModel.getPosition())
	 * 			| 	then getAllBoardModels().remove(boardModel.getPosition())
	 */
	public void removeBoardModel(BoardModel boardModel){
		if(boardModel.getBoard() == this){
			List<BoardModel> temp = boardModels.get(boardModel.getPosition());
			// May never return index -1
			temp.remove(temp.indexOf(boardModel));
			if(temp.size() == 0)
				boardModels.remove(boardModel.getPosition());
			boardModel.setBoard(null);
		}
	}
	
	/**
	 * Checks whether the given model is situated in the area
	 * around the given position with the given range as the range
	 * of the area.
	 * 
	 * @param 	position
	 * 			The center of the area.
	 * @param 	model
	 * 			The model that has to be checked.
	 * @param 	range
	 * 			The range of the area.
	 * @return	If one of the given arguments refers the null reference,
	 * 			the result is always false.
	 * 			| if(position == null || model == null)
	 * 			| 	then result == false
	 * @return	If the given model is not situated on this board,
	 * 			the result is false.
	 * 			| if(model.getBoard() != this)
	 * 			|	then result == false
	 * @return	If the given position's coordinates are not valid
	 * 			coordinates for this board, the result is false.
	 * 			| if(!canHaveAsCoordinates(position.getCoordinates()))
	 * 			|	then result == false
	 * @return	If the given board model is situated in the area with
	 * 			the given position as center and the given range as range,
	 * 			the result is true.
	 * 			| result ==	((position.getCoordinate(Dimension.HORIZONTAL) + Math.abs(range)) >= model.getPosition().getCoordinate(Dimension.HORIZONTAL))
	 * 			|			&& ((position.getCoordinate(Dimension.HORIZONTAL) - Math.abs(range)) <= model.getPosition().getCoordinate(Dimension.HORIZONTAL))
	 * 			|			&& ((position.getCoordinate(Dimension.VERTICAL) + Math.abs(range)) >= model.getPosition().getCoordinate(Dimension.VERTICAL))
	 * 			|			&& ((position.getCoordinate(Dimension.VERTICAL) - Math.abs(range)) <= model.getPosition().getCoordinate(Dimension.VERTICAL))
	 */
	@Raw
	public boolean hasInArea(Position position, BoardModel model, long range){
		if(position == null || model == null)
			return false;
		if(model.getBoard() != this)
			return false;
		if(!canHaveAsCoordinates(position.getCoordinates()))
			return false;
		return ((position.getCoordinate(Dimension.HORIZONTAL) + Math.abs(range)) >= model.getPosition().getCoordinate(Dimension.HORIZONTAL))
		&& ((position.getCoordinate(Dimension.HORIZONTAL) - Math.abs(range)) <= model.getPosition().getCoordinate(Dimension.HORIZONTAL))
		&& ((position.getCoordinate(Dimension.VERTICAL) + Math.abs(range)) >= model.getPosition().getCoordinate(Dimension.VERTICAL))
		&& ((position.getCoordinate(Dimension.VERTICAL) - Math.abs(range)) <= model.getPosition().getCoordinate(Dimension.VERTICAL));
	}
	
	/**
	 * Checks whether the given model is situated next to
	 * the given reference position.
	 * 
	 * @param 	position
	 * 			The reference position.
	 * @param 	model
	 * 			The model that has to be checked.
	 * @return	If one of the given arguments refers the null reference,
	 * 			the result is always false.
	 * 			| if(position == null || model == null)
	 * 			| 	then result == false
	 * @return	If the given model is not situated on this board,
	 * 			the result is false.
	 * 			| if(model.getBoard() != this)
	 * 			|	then result == false
	 * @return	If the given position's coordinates are not valid
	 * 			coordinates for this board, the result is false.
	 * 			| if(!canHaveAsCoordinates(position.getCoordinates()))
	 * 			|	then result == false
	 * @return	If the given board model is situated next (above, beneath, left, right)
	 * 			to the given position, the result is true.
	 * 			| result ==	(((position.getCoordinate(Dimension.HORIZONTAL)+1) == model.getPosition().getCoordinate(Dimension.HORIZONTAL))
	 * 			|			&& (position.getCoordinate(Dimension.VERTICAL) == model.getPosition().getCoordinate(Dimension.VERTICAL)))
	 * 			|			|| (((position.getCoordinate(Dimension.HORIZONTAL)-1) == model.getPosition().getCoordinate(Dimension.HORIZONTAL))
	 * 			|			&& (position.getCoordinate(Dimension.VERTICAL) == model.getPosition().getCoordinate(Dimension.VERTICAL)))
	 * 			|			|| (((position.getCoordinate(Dimension.HORIZONTAL) == model.getPosition().getCoordinate(Dimension.HORIZONTAL))
	 * 			|			&& (position.getCoordinate(Dimension.VERTICAL)+1) == model.getPosition().getCoordinate(Dimension.VERTICAL)))
	 * 			|			|| (((position.getCoordinate(Dimension.HORIZONTAL) == model.getPosition().getCoordinate(Dimension.HORIZONTAL))
	 * 			|			&& (position.getCoordinate(Dimension.VERTICAL)-1) == model.getPosition().getCoordinate(Dimension.VERTICAL)))
	 */
	@Raw
	public boolean isSituatedNextTo(Position position, BoardModel model){
		if(position == null || model == null)
			return false;
		if(model.getBoard() != this)
			return false;
		if(!canHaveAsCoordinates(position.getCoordinates()))
			return false;
		return	(((position.getCoordinate(Dimension.HORIZONTAL)+1L) == model.getPosition().getCoordinate(Dimension.HORIZONTAL))
				&& (position.getCoordinate(Dimension.VERTICAL) == model.getPosition().getCoordinate(Dimension.VERTICAL)))
				|| (((position.getCoordinate(Dimension.HORIZONTAL)-1L) == model.getPosition().getCoordinate(Dimension.HORIZONTAL))
				&& (position.getCoordinate(Dimension.VERTICAL) == model.getPosition().getCoordinate(Dimension.VERTICAL)))
				|| (((position.getCoordinate(Dimension.HORIZONTAL) == model.getPosition().getCoordinate(Dimension.HORIZONTAL))
				&& (position.getCoordinate(Dimension.VERTICAL)+1L) == model.getPosition().getCoordinate(Dimension.VERTICAL)))
				|| (((position.getCoordinate(Dimension.HORIZONTAL) == model.getPosition().getCoordinate(Dimension.HORIZONTAL))
				&& (position.getCoordinate(Dimension.VERTICAL)-1L) == model.getPosition().getCoordinate(Dimension.VERTICAL)));
	}
	
	/**
	 * Merges this board with the given board. This method tries to set every board model
	 * of the second board onto this board. All board models that could not be transmitted
	 * and the given board become terminated.
	 * 
	 * @param 	board
	 * 			The board that has to be merged.
	 * @effect	Merges this board with the given board. This method tries to set every board model
	 * 			of the second board onto this board. All board models that could not be transmitted
	 * 			and the given board become terminated.
	 * 			| for each BoardModel model in board.getAllBoardModelsClass(BoardModel.class) :
	 * 			|	if(canHaveBoardModelAtNoBindingCheck(model.getPosition(), model)
	 * 			|		then board.removeBoardModel(model) && addBoardModelAt(pos, model)
	 * 			| end for
	 * 			| board.terminate()
	 * 			| [SEQUENTIAL]
	 * @throws	IllegalArgumentException
	 * 			The given board is not valid for a merge operation.
	 * 			The given board may not refer the null reference.
	 * 			The given board may not be terminated.
	 * 			This board may not be terminated.
	 * 			The given board may not refer this board.
	 * 			| (board == null || board.isTerminated || this.isTerminated() || board == this)
	 */
	public void mergeBoard(Board board) throws IllegalArgumentException{
		if(board == null || board.isTerminated() || this.isTerminated() || board == this)
			throw new IllegalArgumentException("One of the operating boards is invalid for this operation.");
		for(Object position : board.boardModels.keySet().toArray()){
			Position pos = (Position) position;
			List<BoardModel> temp = board.boardModels.get(pos);
			int unMoveableSize = 0;
			while(temp.size() != unMoveableSize){
				BoardModel model = temp.get(unMoveableSize);
				if (canHaveBoardModelAtNoBindingCheck(pos, model)){
					board.removeBoardModel(model);
					addBoardModelAt(pos, model);
				}
				else{
					unMoveableSize++;
				}
			}
		}
		board.terminate();
	}
	
	/**
	 * Returns the board model(s) situated the most close to the given
	 * start position according to the given direction.
	 * There is a possibility that no board model, that satisfies all
	 * these conditions, could be found.  
	 * 
	 * @param	position
	 * 			The position of the attacker.
	 * @param	direction
	 * 			The direction in which the target has to be found.
     * @return	Returns the board model(s) situated on the closest position
     * 			to the given start position according to the given direction.
	 * 			There is a possibility that no board model, that satisfies all
	 * 			these conditions, could be found. 
	 * 			| for each BoardModel target1 and target2 in result :
	 * 			|	target1.getPosition().equals(target2.getPosition())
	 * 			|	&&
	 * 			|	if(direction == Direction.UP)
	 * 			|		then (target1.getPosition().getCoordinate(Dimension.HORIZONTAL) == position.getCoordinate(Dimension.HORIZONTAL)
	 * 			|		&& target1.getPosition().getCoordinate(Dimension.VERTICAL) < position.getCoordinate(Dimension.VERTICAL))
	 * 			|	&&
	 * 			|	if(direction == Direction.DOWN)
	 * 			|		then (target1.getPosition().getCoordinate(Dimension.HORIZONTAL) == position.getCoordinate(Dimension.HORIZONTAL)
	 * 			|		&& target1.getPosition().getCoordinate(Dimension.VERTICAL) > position.getCoordinate(Dimension.VERTICAL)) 
	 * 			|	&&
	 * 			|	if(direction == Direction.LEFT)
	 * 			|		then (target1.getPosition().getCoordinate(Dimension.VERTICAL) == position.getCoordinate(Dimension.VERTICAL)
	 * 			|		&& target1.getPosition().getCoordinate(Dimension.HORIZONTAL) < position.getCoordinate(Dimension.HORIZONTAL))
	 * 			| 	&&
	 * 			|	if(direction == Direction.RIGHT)
	 * 			|		then (target1.getPosition().getCoordinate(Dimension.VERTICAL) == position.getCoordinate(Dimension.VERTICAL)
	 * 			|		&& target1.getPosition().getCoordinate(Dimension.HORIZONTAL) > position.getCoordinate(Dimension.HORIZONTAL))
	 * 			| &&
	 * 			| there exists no model in getAllBoardModelsClass(BoardModel.class) :
	 * 			|	!result.contains(model)
	 * 			|	&&
	 * 			|	if(result.size() != 0)
	 * 			|		then (model.getPosition().getManhattanDistance(position)
	 * 			|				.compareTo(result.get(0).getPosition().getManhattanDistance(position)<=0)
	 * 			|	&&
	 * 			|	if(direction == Direction.UP)
	 * 			|		then (model.getPosition().getCoordinate(Dimension.HORIZONTAL) == position.getCoordinate(Dimension.HORIZONTAL)
	 * 			|		&& then model.getPosition().getCoordinate(Dimension.VERTICAL) < position.getCoordinate(Dimension.VERTICAL))
	 * 			|	&&
	 * 			|	if(direction == Direction.DOWN)
	 * 			|		then (model.getPosition().getCoordinate(Dimension.HORIZONTAL) == position.getCoordinate(Dimension.HORIZONTAL)
	 * 			|		&& model.getPosition().getCoordinate(Dimension.VERTICAL) > position.getCoordinate(Dimension.VERTICAL)) 
	 * 			|	&&
	 * 			|	if(direction == Direction.LEFT)
	 * 			|		then (model.getPosition().getCoordinate(Dimension.VERTICAL) == position.getCoordinate(Dimension.VERTICAL)
	 * 			|		&& model.getPosition().getCoordinate(Dimension.HORIZONTAL) < position.getCoordinate(Dimension.HORIZONTAL))
	 * 			| 	&&
	 * 			|	if(direction == Direction.RIGHT)
	 * 			|		then (model.getPosition().getCoordinate(Dimension.VERTICAL) == position.getCoordinate(Dimension.VERTICAL)
	 * 			|		&& model.getPosition().getCoordinate(Dimension.HORIZONTAL) > position.getCoordinate(Dimension.HORIZONTAL))
	 * @throws	IllegalArgumentException
	 * 			The given direction is invalid.
	 * 			| !Direction.isValidDirection(direction)
	 * @throws	IllegalArgumentException
	 * 			The given position is invalid.
	 * 			Meaning that it refers the null reference or that its coordinates
	 * 			are invalid for this board.
	 * 			| position == null || !canHaveAsCoordinates(position.getCoordinates())
	 */
	public List<BoardModel> getAllTargets(Position position, Direction direction)
			throws IllegalArgumentException{
		if(!Direction.isValidDirection(direction))
			throw new IllegalArgumentException("The given direction is invalid.");
		if(position == null || !canHaveAsCoordinates(position.getCoordinates()))
			throw new IllegalArgumentException("The given position is invalid.");
		
		long reachingBorder = getReachingBorder(direction);
		long reverser = (reachingBorder == 0)? -1:1;
		boolean found = false;
		
		List<BoardModel> result = new ArrayList<BoardModel>();
		long count = Direction.getDirectionOrder(direction);
		long[] start = position.getCoordinates();

		while(!found && (reverser*start[Direction.getDirectionDimension(direction)-1] <= reachingBorder)){
			start[Direction.getDirectionDimension(direction)-1] = start[Direction.getDirectionDimension(direction)-1] + count;
			Position key = new Position(start);
			if(boardModels.containsKey(key)){
				for(BoardModel element : getBoardModelsAt(key)){
					result.add(element);
					found = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * Returns the border value that would be reached while
	 * travelling in the given direction.
	 * 
	 * @param 	direction
	 * 			The direction of travelling.
	 * @return	If the direction is up or left then the result is zero.
	 * 			| if(direction == Direction.UP || direction == Direction.LEFT)
	 * 			| then result == 0
	 * @return	If the direction is right, returns the horizontal border.
	 * 			| if(direction == Direction.RIGHT)
	 * 			| then result == getSizeAt(Dimension.HORIZONTAL)
	 * @return	If the direction is down, returns the vertical border.
	 * 			| if(direction == Direction.DOWN)
	 * 			| then result == getSizeAt(Dimension.VERTICAL)
	 * @throws	IllegalArgumentException
	 * 			The given direction is invalid.
	 * 			| !Direction.isValidDirection(direction)
	 */
	@Raw @Model
	private long getReachingBorder(Direction direction)
			throws IllegalArgumentException{
		switch(direction){
			case UP:
				return 0;
			case LEFT:
				return 0;
			case RIGHT:
				return getSizeAt(Dimension.HORIZONTAL);
			case DOWN:	
				return getSizeAt(Dimension.VERTICAL);
		}
		throw new IllegalArgumentException("The given direction is invalid.");
	}
	
	/**
	 * Returns the first board model (random chosen if more than one) situated
	 * the closest to the given start position while travelling from the given
	 * direction.
	 * 
	 * @param	position
	 * 			The position of the attacker.
	 * @param	direction
	 * 			The direction in which the target has to be found.
	 * @return	Returns the first board model (random chosen if more than one) situated
	 * 			the closest to the given start position while travelling from the given
	 * 			direction. If no such board model could be found, the null reference is returned.
	 * 			| getAllTargets(position, direction).contains(result) == true
	 * 			| || result == null
	 * @throws	IllegalArgumentException
	 * 			The given direction is invalid.
	 * 			| !Direction.isValidDirection(direction)
	 * @throws	IllegalArgumentException
	 * 			The given position is invalid.
	 * 			| position == null || !canHaveAsCoordinates(position.getCoordinates())
	 */
	public BoardModel getRandomTarget(Position position, Direction direction)
			throws IllegalArgumentException{
		if(!Direction.isValidDirection(direction))
			throw new IllegalArgumentException("The given direction is invalid.");
		if(position == null || !canHaveAsCoordinates(position.getCoordinates()))
			throw new IllegalArgumentException("The given position is invalid.");
		
		ArrayList<BoardModel> possibleTargets = (ArrayList<BoardModel>) getAllTargets(position, direction);
		if(possibleTargets.size() > 0)
			return possibleTargets.get((new Random()).nextInt(possibleTargets.size()));
		return null;
	}
	
	/**
	 *  Returns an iterator in order to iterate all the board models situated on this board.
	 *  
	 *  @return		The resulting iterator contains all board models that are located on this board.
	 *  			| if (result.hasNext())
	 *  			|	let BoardModel newModel = result.next() in :
	 *  			| 	getBoardModelsAt(newModel.getPosition()).contains(newModel)
	 */
	@Basic @Raw @Override
	public Iterator<BoardModel> iterator(){
		return new Iterator<BoardModel>(){
			
			/**
			 * The position iterator for this board.
			 * 
			 * The positions of the board models are used as keys for the internal
			 * board model collection of this board.
			 */
			private final Iterator<Position> posIterator = boardModels.keySet().iterator();
			
			/**
			 * Returns the position iterator for this board iterator.
			 */
			@Basic @Model
			private Iterator<Position> getPositionIterator(){
				return posIterator;
			}
			
			/**
			 * The board model iterator for one (current iteration) position on the board.
			 */
			private Iterator<BoardModel> modelListIterator;
			
			/**
			 * Sets the board model iterator for this board iterator.
			 * 
			 * @post 	new.getModelListIterator() == iterator
			 */
			private void setModelListIterator(Iterator<BoardModel> iterator){
				modelListIterator = iterator;
			}
			
			/**
			 * Returns the model list iterator for this board iterator.
			 */
			@Basic @Model
			private Iterator<BoardModel> getModelListIterator(){
				return modelListIterator;
			}
			
			{
				if(getPositionIterator().hasNext()){
					setModelListIterator(getBoardModelsAt(getPositionIterator().next()).iterator());
				}
			}

			/**
			 * Checks if the iteration has more board models.
			 * 
			 * @return	False if the model list iterator is ineffective.
			 * 			| if(getModelListIterator() == null)
			 *			|	result == false
			 * @return	False if the model list iterator has no next element and the position iterator
			 * 			has no next element.
			 * 			| if(!getModelListIterator().hasNext() && !getPositionIterator().hasNext())
			 * 			| 	result == false
			 * @return 	Else returns whether the model list iterator has a next element.
			 * 			| result == getModelListIterator().hasNext()
			 */
			@Override
			public boolean hasNext() {
				if(getModelListIterator() == null)
					return false;
				if(!getModelListIterator().hasNext() && !getPositionIterator().hasNext())
					return false;
				return getModelListIterator().hasNext();
			}

			/**
			 * Returns the next board model in the iteration.
			 * 
			 * @return	Returns the next board model in the iteration.
			 * 			| if (hasNext())
			 * 			| 	result == getModelListIterator().next()
			 * @throws	NoSuchElementException
			 * 			The iteration has no more elements.
			 * 			| !hasNext()
			 * 
			 * @note	If after returning the next element of the model iterator the model	
			 * 			iterator has no elements anymore, but the position iterator does have
			 * 			remaining elements, then the model list iterator will be changed
			 * 			to the next one that still has elements (the order is defined by the position
			 * 			iterator), if no such model list iterator exists then it will be the last one.
			 */
			@Override
			public BoardModel next()
					throws NoSuchElementException{
				if(!hasNext())
					throw new NoSuchElementException();
				BoardModel result = getModelListIterator().next();
				if(!getModelListIterator().hasNext() && getPositionIterator().hasNext()){
					do{
						setModelListIterator(getBoardModelsAt(getPositionIterator().next()).iterator());
					}
					while (!getModelListIterator().hasNext() && getPositionIterator().hasNext());
				}
				return result;
			}

			/**
			 * This operation is not supported.
			 * 
			 * @throws	UnsupportedOperationException
			 * 			This operation is not supported.
			 * @note	The self-defined removeBoardModel method must be used
			 * 			to remove a board model from a board.
			 */
			@Override
			public void remove() throws UnsupportedOperationException{
				throw new UnsupportedOperationException();
			}
		};
	}
	
	/**
	 * Returns an iterator in order to iterate the board models situated on this board
	 * that satisfies the condition of the given condition tester.
	 * 
	 * @param 	tester
	 * 			The condition tester that's used as condition while iterating.
	 * @return	The resulting iterator contains all board models that are located on this board
	 * 			that satisfy the condition from the given condition tester.
	 *  		| if (result.hasNext())
	 *  		|	let BoardModel newModel = result.next() in :
	 *  		| 	getBoardModelsAt(newModel.getPosition()).contains(newModel)
	 *  		|	&& tester.testCondition(newModel) == true
	 */
	@Raw
	public Iterator<BoardModel> conditionIterator(final ConditionTester tester){
		return new Iterator<BoardModel>() {
			
			/**
			 * The general board model iterator that is used.
			 */
			private final Iterator<BoardModel> boardIterator = iterator();
			
			/**
			 * Returns the board iterator of this conditional board iterator.
			 */
			@Basic @Model
			private Iterator<BoardModel> getBoardIterator(){
				return boardIterator;
			}
			
			/**
			 * The next board model positioned on this board
			 * that satisfies the condition.
			 */
			private BoardModel next;
			
			/**
			 * Sets the stored next element of this conditional board iterator.
			 * 
			 * @param	nextElement
			 * 			The next element to set.
			 * @post 	The stored next element of this board iterator
			 * 			is the given next element.
			 * 			| new.getNextElement() == nextElement
			 */
			@Model
			private void setNextElement(BoardModel nextElement){
				next = nextElement;
			}
			
			/**
			 * Returns the stored next element of this iterator.
			 */
			@Basic @Model
			private BoardModel getNextElement(){
				return next;
			}
			
			{
				toNext();
			}
			
			/**
			 * Checks if the iteration has more board models,
			 * positioned on this board, that satisfy the condition.
			 * 
			 * @return	Returns true if the stored next element is effective.
			 * 			| getNextElement() != null
			 */
			@Override
			public boolean hasNext() {
				return getNextElement() != null;
			}

			/**
			 * Returns the next board model in the iteration,
			 * positioned on this board, that satisfies the condition.
			 * 
			 * @return	Returns the next board model in the iteration
			 * 			that satisfies the condition.
			 * 			| result == getNextElement()
			 * @throws	NoSuchElementException
			 * 			The iteration has no more elements.
			 * 			| !hasNext()
			 * 
			 * @note	This method will also alter the stored next element
			 * 			of the iterator. This is done with the toNext() method.
			 */
			@Override
			public BoardModel next() throws NoSuchElementException{
				 if(!hasNext())
					 throw new NoSuchElementException();
				 BoardModel returnValue = getNextElement();
				 toNext();
				 return returnValue;
			}

			/**
			 * This operation is not supported.
			 * 
			 * @throws	UnsupportedOperationException
			 * 			This operation is not supported.
			 * @note	The self-defined removeBoardModel method must be used
			 * 			to remove a board model from a board.
			 */
			@Override
			public void remove() throws UnsupportedOperationException{
				throw new UnsupportedOperationException();
			}
			
			/**
			 * Searches the next board model in the iteration, 
			 * positioned on this board, that satisfies the condition
			 * and sets the stored next model to that model if such model exists.
			 */
			private void toNext(){
				setNextElement(null);
				while (getBoardIterator().hasNext()) {
					BoardModel item = getBoardIterator().next();
					if(item != null && tester.testCondition(item)) {
						setNextElement(item);
						break;
					}
				}
			}
			
		};
	}
}
