package org.example.message;

import org.example.game_logic.BoardType;

/**
 * Message class used to send the board type between the server and the client
 */
public class BoardTypeMessage extends Message {
    private final BoardType boardType;

    public BoardTypeMessage(BoardType boardType) {
        super(MessageType.BOARD_TYPE);
        this.boardType = boardType;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    @Override
    public String toString() {
        return "boardType='" + boardType;
    }
}
