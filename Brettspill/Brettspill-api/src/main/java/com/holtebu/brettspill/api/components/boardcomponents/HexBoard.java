package com.holtebu.brettspill.api.components.boardcomponents;

/**
 * Created by Espen on 14.04.2017.
 */
public abstract class HexBoard extends Board {
    HexBoard(HexBoardPiece[][] pieces) {
        super(pieces);
    }

    abstract void firstColumn();
}
