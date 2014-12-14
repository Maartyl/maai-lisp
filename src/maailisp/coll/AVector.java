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
package maailisp.coll;

import java.util.Iterator;

/**
 *
 * @author maartyl
 * @param <T>
 */
public abstract class AVector<T> implements Vector<T>, Iterable<T> {

  @Override
  public boolean isCounted() {
    return true;
  }

  @Override
  public int count() {
    return length();
  }


  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      int index;

      @Override
      public boolean hasNext() {
        return (index < length());
      }

      @Override
      public T next() {
        return get(index++);
      }
    };
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    Iterator<T> it = this.iterator();
    sb.append(it.next()); //Vector0 overrides
    for (; it.hasNext();) {
      T e = it.next();
      sb.append(" ");
      sb.append(e);
    }
    sb.append("]");
    return sb.toString();
  }

  //protected methods shared between all classical vectors
  protected IndexOutOfBoundsException outOfBounds(int index) {
    return new IndexOutOfBoundsException("Expected: 0 to " + length() + "; got: " + index);
  }

}
