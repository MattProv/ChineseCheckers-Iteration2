package org.example.game_logic;

import java.util.List;
import java.util.Map;
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
        // A pawn can't leave it's base after it enters it
        if (board.getPawn(move.getStart()).isBaseLocked())
            if (move.getEnd().getBaseId() != board.getPawn(move.getStart()).getOwner().getFinishBaseIndex())
                return false;

        // A pawn can move to an empty neighbouring node
        if (move.getStart().getNeighbours().contains(move.getEnd()))
            if (move.getEnd().getIsOccupied())
                return false;
            else
                return true;
        // A pawn can hop over any neighbouring pawn
        else
            if (move.getStart().getYCoordinate() == move.getEnd().getYCoordinate())
                if (abs(move.getStart().getXCoordinate() - move.getEnd().getXCoordinate()) == 4)
                    if (board.getNode(new Coordinate((move.getStart().getXCoordinate() + move.getEnd().getXCoordinate())/2,
                            move.getStart().getYCoordinate())).getIsOccupied())
                        return true;
            else if (abs(move.getStart().getYCoordinate() - move.getEnd().getYCoordinate()) == 2)
                if (board.getNode(new Coordinate((move.getStart().getXCoordinate() + move.getEnd().getXCoordinate())/2,
                                (move.getStart().getYCoordinate() + move.getEnd().getYCoordinate())/2)).getIsOccupied())
                    return true;
        // For everything else, discard as an invalid move
        return false;
    }

}
