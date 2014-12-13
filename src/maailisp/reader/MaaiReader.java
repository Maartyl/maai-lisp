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
import java.util.ArrayList;
import maailisp.coll.Seq;

/*
 the idae is this:
 upon meeting "start character" (depending on context), I will trigger new Reader, specific for given state, and that will
 read that sub part; otherwise rest is string, theoretically could be just buffered and parsed by regex::

 could be just functions instead of readers, but...

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
 ...........................^ string reader doesn't start ParenReaders etc.
 */

/*
 actual states:

 with end:
 - numerical : long
 - numerical : double
 - string
 - char  : \a \newline
 - list
 - vector
 - map
 - comment

 one time alter next token:
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
  //each subReader returns ...

  /**
   * reads s-expressions from given Reader
   * <p>
   * //@throws IOException
   * @param r
   * @return each object is a top-level s-expression read from stream r
   */
  public Seq<Object> read(BufferedReader r) {
    PosReader prdr = new PosReader(r);

    ArrayList<Object> buffer = new ArrayList<>();
    genericReader(buffer, prdr);

    return Seq.of(buffer.toArray()); 
  }

  private void genericReader(ArrayList<Object> buf, PosReader pr) {

  }

  /**
   * <p>
   * reads until given terminator
   */
  private void untilReader(ArrayList<Object> buf, PosReader pr, int terminator) {

  }

  private boolean isWhitespace(int ch) {
    switch (ch) {
    case ' ':
    case '\n':
    case '\t':
    case ',':      //comma is considered whitespace
    case '\u00A0': //nbsp
      return true;
    default: return false;

    }
  }
}
