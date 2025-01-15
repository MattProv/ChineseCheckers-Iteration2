package org.example.game_logic;

import java.util.List;
import java.util.Set;

import static java.lang.Math.abs;

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
        if(board.getPawn(move.getStart()) == null)
            return false;
        // A pawn can't leave it's base after it enters it
        if (board.getPawn(move.getStart()).isBaseLocked()) {
            if (move.getEnd().getBaseId() != board.getPawn(move.getStart()).getOwner().getFinishBaseIndex()) {
                return false;
            }
        }
        // If player commited a normal step already, he can't make another one
        if (board.getPawn(move.getStart()).getOwner().isStepLocked()) {
        }
        // A pawn can step to an empty neighbouring node, it it isn't stepLocked
        else if (move.getStart().getNeighbours().contains(move.getEnd())) {
            if (move.getEnd().getIsOccupied()) {
                System.out.println("Can't move to an occupied node");
                return false;
            }
            System.out.println("Valid move to an empty neighbour");
            board.getPawn(move.getStart()).getOwner().hopLock();
            board.getPawn(move.getStart()).getOwner().stepLock();
            return true;
        }
        if (board.getPawn(move.getStart()).getOwner().isHopLocked()) {
            System.out.println("Player can't make a hop after taking a step!");
            return false;
        }
        else {
            // A pawn can hop over a neighbouring pawn (horizontal hopping)
            if (move.getStart().getYCoordinate() == move.getEnd().getYCoordinate()) {
                int midX = (move.getStart().getXCoordinate() + move.getEnd().getXCoordinate()) / 2;
                if (Math.abs(move.getStart().getXCoordinate() - move.getEnd().getXCoordinate()) == 4 &&
                        board.getNode(new Coordinate(midX, move.getStart().getYCoordinate())).getIsOccupied()) {
                    System.out.println("Valid horizontal hop");
                    board.getPawn(move.getStart()).getOwner().stepLock();
                    return true;
                }
            }
            // A pawn can hop over a neighbouring pawn (diagonal hopping)
            if (Math.abs(move.getStart().getYCoordinate() - move.getEnd().getYCoordinate()) == 2) {
                int midX = (move.getStart().getXCoordinate() + move.getEnd().getXCoordinate()) / 2;
                int midY = (move.getStart().getYCoordinate() + move.getEnd().getYCoordinate()) / 2;
                if (board.getNode(new Coordinate(midX, midY)).getIsOccupied()) {
                    System.out.println("Valid diagonal hop");
                    board.getPawn(move.getStart()).getOwner().stepLock();
                    return true;
                }
            }
        }
        // For everything else, discard as an invalid move
        System.out.println("Invalid move");
        return false;
    }


}