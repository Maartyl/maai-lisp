/*
 * Copyright (c) 2014 maartyl.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    maartyl - initial API and implementation and/or initial documentation
 */
package maailisp.reader;

/**
 *
 * @author maartyl
 */
public class Tokenizer {

  static enum TokenType {
    SYMBOL, //KEYWORD, :keyword is just a symbol; examined upon creation
    NUM, DOUBLE,
    STRING,
    LPAR, RPAR, LBRCK, RBRCK, LCURLY, RCURLY,
    MACRO_HASH/*#*/, MACRO_DEREF/*@*/, //reader macros
    MACRO_QUOTE/*'*/, MACRO_NSQUOTE/*`*/, MACRO_UNQUOTE/*~*/,
    TRUE, FALSE, NIL,
  }

  /*inner static: independent; could be possibly moved out*/
  static final class Token {
    private final int column;
    private final int row;
    private final TokenType type;
    private final String text; //it's just a pointer; can be null if obvious (like: parens...)

    public Token(int column, int row, TokenType type, String text) {
      this.column = column;
      this.row = row;
      this.type = type;
      this.text = text;
    }

    public int getColumn() {
      return column;
    }

    public int getRow() {
      return row;
    }

    public TokenType getType() {
      return type;
    }

    public String getText() {
      return text;
    }

  }

}
