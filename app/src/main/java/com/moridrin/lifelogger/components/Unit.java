package com.moridrin.lifelogger.components;

/**
 * Created by jeroenhermkens on 17/02/16.
 */
public enum Unit {
    Gram,
    Kilo,
    Milliliter,
    Piece;

    @Override
    public String toString() {
        return this.name();
    }
}
