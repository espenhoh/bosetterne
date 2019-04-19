package com.holtebu.brettspill.api.components.boardcomponents;

/**
 * Created by Espen on 14.04.2017.
 */
public abstract class Board {

    BoardPiece[][] pieces;

    Board(BoardPiece[][] pieces) {
        this.pieces = pieces;
    }


}
