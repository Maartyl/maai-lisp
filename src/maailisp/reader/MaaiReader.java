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

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import maailisp.coll.Seq;

/*
 the idea is this:
 upon meeting "start character" (depending on context), I will trigger new Reader, specific for given state, and that will
 readEager that sub part; otherwise rest is string, theoretically could be just buffered and parsed by regex:: (not really)

 could be just functions instead of readers

 they all share some kind of "position state" instance thingy...

 that is:

 (foo (+ 1 2) :bar " only \"(string) here")
 ^ new ParensReader, that will return the contents: that is: looks for ")" and buffers until then
 .^ either just string, so ignore and just buffer
 ...or SymbolReader, that terminates when no longer symbol
 .....^ ParenReader
 ...........^ inner paren reader ends, passing found stuff to parent as 1 elem
 .............^start KeywordReader (or symbol)
 ..................^start StringReader
 .........................^ start EscapeReader / skip exit
 ...........................^ stringReader doesn't start ParensReader etc.
 */

/*
 actual states:

 with end:                   --introduces new buffer  /toka
 - numerical : long
 - numerical : double
 - string
 - char  : \a \newline
 - list
 - vector
 - map
 - comment

 one time alter next token:   --only changes state
 - hash (further dispatch) : #
 - deref : @
 - quote : '
 - qualifiedQuote : `
 - unquote : ~
 - minus : -
 - ERROR : anthing that just can't happen... (takes msg, position and throws exception)

 */

/**
 *
 * @author maartyl
 */
public class MaaiReader {
  //initialized with some reader settings... (if should expand this or that...)
  // : also namespace for qualified symbols
  // :: improve namespace concept : make some default "script" contexts etc...
  //
  private String curNamespace;


  /**
   * eagerly reads s-expressions from given Reader
   * <p>
   * //@throws IOException
   * @param r
   * @return each object is a top-level s-expression read from stream r
   */
  public Seq<Object> readEager(Reader r) {
    PosReader prdr = new PosReader(r);

    ArrayList<Object> buffer = new ArrayList<>();
    topLevelReader(buffer, prdr);

    return Seq.of(buffer.toArray());
  }

  /**
   * lazily reads s-expressions from given Reader
   * <p>
   * //@throws IOException
   * <p>
   * @param r
   * @return each object is a top-level s-expression read from stream r
   */
  public Seq<Object> readLazy(Reader r) {
    return readLazy(new PosReader(r));
  }

  /**
   * lazily reads s-expressions from given PosReader
   * <p>
   * //@throws IOException
   * <p>
   *
   * @param r
   * @return each object is a top-level s-expression read from stream r
   */
  public Seq<Object> readLazy(PosReader r) {
    Object ret = read1(r);
    if (ret == EOFMark) return null;
    return Seq.lazy(ret, () -> readLazy(r));
  }

  /**
   * reads 1 expression (top-level of start of given Reader stream).
   * (this method is public but should be used with caution)
   * <p>
   * @param r source; ;; PosReader is not a Java Reader
   * @return the readEager object || MaaiReader.EOFMark (it is possible to readEager null <- is valid)
   */
  public Object read1(PosReader r) {
    return recognizerReader(skipWhitespace(r), r);
  }

  private void topLevelReader(ArrayList<Object> buf, PosReader pr) {
    untilReader(buf, pr, -1); //just readEager to end
  }

  /**
   * not lazy. (and shouldn't... can't be: stateful reader)
   */
  private Seq<Object> listReader(PosReader pr) {
    int row = pr.getRow();
    int col = pr.getColumn();
    ArrayList<Object> buf = new ArrayList<>();
    untilReader(buf, pr, ')');
    return Seq.of(buf.toArray());// TODO: change: special seq: counted and info about position
  }

  /**
   * <p>
   * reads until given terminator, adding each found object into buffer
   */
  private void untilReader(ArrayList<Object> buf, PosReader pr, final int terminator) {
    int ch;

    //curChar:  ...45) ... I need the paren for both terminating the number (or symbol) and the whole list; etc.
    while (pr.getCurrentChar() != terminator && (ch = skipWhitespace(pr)) != terminator) {
      buf.add(recognizerReader(ch, pr));
    }
  }

  //parametrize with action to return if nothing was matched; overload for implicit exception
  private Object recognizerReader(int firstChar, PosReader pr) {
    switch (firstChar) {
    case '(': //list
      return (listReader(pr)); //read list and add into buffer
    case '#': //hash
      break;
    case '[': //vector
      break;
    case '{': //map (+ info if set (in hashReader))
      break;
    case '\'': //quote
      break;
    case '~': //unquote
      break;
    case '`': //qualified quote
      break;
    case '@': //deref / alter vectors/maps in some way?
      break;
    case '-': //minus: num: negate; symbol: prepend; whitespace: just MINUS symbol
      break;
    case -1: return EOFMark;

    default:
    //range checks (isAlphanumeric...)
    //isSymbolStart?
    //isNumber? ...

    }

    errReader("unmatched character: " + (char) firstChar + "(" + firstChar + ")", pr);
    return null;
  }

  private void errReader(String message, final PosReader pr) {
//TODO: throw some custom error, that includes position etc.
    // ... or just return "error" object? ... nope, I chose: First error is error policy; anything after: not necessarily.
    // -> throw at first error, ignore rest.
  }

  /**
   * returns first non-whitespace char
   */
  private int skipWhitespace(PosReader pr) {
    int ch = pr.readNextChar();
    while (isWhitespace(ch)) ch = pr.readNextChar();
    return ch;
  }

  private boolean isWhitespace(int ch) {
    return Character.isWhitespace(ch) || ch == ',';
//    switch (ch) {
//    case ' ':
//    case '\n':
//    case '\t':
//    case ',':      //comma is considered whitespace
//    case '\u00A0': //nbsp
//      return true;
//    default: return false;
//    }
  }
  public static final Object EOFMark = new Object() {

    @Override
    public String toString() {
      return "EOFMark";
    }

  };

  //
  //------ Tests (simple)
  //
  public static void main(String args) {

  }
}
