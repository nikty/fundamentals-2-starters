import tester.*;

// interp. Represents tiles.
// One of:
// - BaseTile
// - MergeTile
interface GamePiece {
    // Return the value of this tile
    int getValue();
}

// interp. Simple piece
class BaseTile implements GamePiece {
    int value;

    BaseTile(int value) {
        this.value = value; // starting with 2
    }

    int getValue() {
        return this.value;
    }
}

// interp. Piece merge from two tiles
class MergeTile implements GamePiece {
    GamePiece piece1;
    GamePiece piece2;

    MergeTile(GamePiece piece1, GamePiece piece2) {
        this.piece1 = piece1;
        this.piece2 = piece2;
    }

    int getValue() {
        return this.piece1.getValue() this.piece2.getValue();
    }
}

class Examples {
    GamePiece base1 = new BaseTile(2);
    GamePiece merge1 = new Merge1(base1, base1);

    boolean testGetValue(Tester t) {
        return t.checkExpect(base1.getValue(), 2)
            && t.checkExpect(merge1.getValue(), 4);
    }
}
