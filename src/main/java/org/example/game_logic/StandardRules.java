package org.example.game_logic;

import java.util.List;
import java.util.Set;

public class StandardRules implements Rules<StandardBoard> {

    private static final Set<Integer> ALLOWED_PLAYER_COUNTS = Set.of(2, 3, 4, 6);
    private static final int PLAYER_PAWN_COUNT = 10;

    @Override
    public boolean validatePlayerCount(int playerCount) {
        return ALLOWED_PLAYER_COUNTS.contains(playerCount);
    }

    @Override
    public void assignBasesToAgents(StandardBoard board, List<Agent> agents){
        if (agents.size() % 2 != 0)
            for (int i = 0; i < agents.size(); i++) {
                agents.get(i).assignBases(i*2, (i*2 + 3) % 6);
            }
        else {
            int d = 6/agents.size();
            for (int i = 0; i < agents.size(); i++) {
                agents.get(i).assignBases(i*d, (i + 3) % 6);
            }
        }
    }

    @Override
    public StandardBoard setupBoard(StandardBoard board, List<Agent> agents) {
        for (Agent agent : agents) {
            for (Node node : board.getBases().get(agent.getStartBaseIndex())) {
                board.addPawn(node, agent);
            }
        }
        return board;
    }

    @Override
    public boolean validateMove(StandardBoard board, Move move) {
        Pawn startPawn = board.getPawn(move.getStart());
        Node startNode = move.getStart();
        Node endNode = move.getEnd();
        Agent owner = startPawn.getOwner();

        // Check if the owner already has a current pawn
        if (owner.getCurrentPawn() != null) {
            if (owner.getCurrentPawn() != startPawn) {
                System.out.println("Can't move multiple pawns in one move!");
                return false;
            }
        }

        // A pawn can't leave its base after it enters it
        if (startPawn.isBaseLocked()) {
            if (endNode.getBaseId() != owner.getFinishBaseIndex()) {
                return false;
            }
        }

        // If the player committed a normal step already, they can't make another one
        if (owner.isStepLocked()) {
            return false;
        } else if (startNode.getNeighbours().contains(endNode)) {
            if (endNode.getIsOccupied()) {
                System.out.println("Can't move to an occupied node");
                return false;
            }
            System.out.println("Valid move to an empty neighbour");
            owner.hopLock();
            owner.stepLock();
            return true;
        }

        if (owner.isHopLocked()) {
            System.out.println("Player can't make a hop after taking a step!");
            return false;
        } else {
            // A pawn can hop over a neighbouring pawn (horizontal hopping)
            if (startNode.getYCoordinate() == endNode.getYCoordinate()) {
                int midX = (startNode.getXCoordinate() + endNode.getXCoordinate()) / 2;
                if (Math.abs(startNode.getXCoordinate() - endNode.getXCoordinate()) == 4 &&
                        board.getNode(new Coordinate(midX, startNode.getYCoordinate())).getIsOccupied()) {
                    System.out.println("Valid horizontal hop");
                    owner.setCurrentPawn(startPawn);
                    owner.stepLock();
                    return true;
                }
            }

            // A pawn can hop over a neighbouring pawn (diagonal hopping)
            if (Math.abs(startNode.getYCoordinate() - endNode.getYCoordinate()) == 2) {
                int midX = (startNode.getXCoordinate() + endNode.getXCoordinate()) / 2;
                int midY = (startNode.getYCoordinate() + endNode.getYCoordinate()) / 2;
                Node midNode = board.getNode(new Coordinate(midX, midY));
                if(midNode == null)
                    return false;
                if (midNode.getIsOccupied()) {
                    System.out.println("Valid diagonal hop");
                    owner.setCurrentPawn(startPawn);
                    owner.stepLock();
                    return true;
                }
            }
        }

        // For everything else, discard as an invalid move
        System.out.println("Invalid move");
        return false;
    }



}