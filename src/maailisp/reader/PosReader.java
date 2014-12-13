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

import java.io.IOException;
import java.io.Reader;

/**
 * Allows to read chars and remembers position in steam, in terms of row, col.
 * Assumes buffered reader.
 * <p>
 * @author maartyl
 */
public class PosReader {
  private final Reader rdr;
  private int row = 0;
  private int column = 0;

  public PosReader(Reader rdr) {
    this.rdr = rdr;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public int readChar() {
    int ch;
    try {
      ch = rdr.read();
      while (ch == '\r') ch = rdr.read(); //ignore \r: it's useless anyway (sorry, users of old Macs...)

      if (ch < 0) return ch;

      if (ch == '\n') {
        row++;
        column = 0;
      } else column++;

      return ch;
    } catch (IOException e) {
      throw maailisp.util.H.sneakyThrow(e); //just rethrow
    }
  }

}
