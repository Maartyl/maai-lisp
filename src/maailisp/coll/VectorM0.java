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

import java.util.Arrays;

/**
 *
 * Bottom most row of bigger vectors, holds array.
 * Will copy entire array upon each change.
 * -> Generally, meant to only hold arrays under or of 32 elements.
 *
 * @author maartyl
 */
public class VectorM0<T> extends AVector<T> {

  private final T[] elems;

  public VectorM0(T[] elems) {
    assert elems.length <= 32;
    this.elems = elems;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T get(int index) {
    if (index < 0 || index > length())
      throw outOfBounds(index);
    
    return elems[index];
  }

  @Override
  public Vector<T> set(int index, T elem) {
    if (index < 0 || index > length())
      throw outOfBounds(index);

    T[] nels = Arrays.copyOf(elems, elems.length);
    nels[index] = elem;
    return new VectorM0<>(nels);
  }

  @Override
  public Vector<T> add(T elem) {
    if (length() < 32) {//not equal
      T[] nels = Arrays.copyOf(elems, elems.length + 1);
      nels[elems.length] = elem;
      return new VectorM0<>(nels);
    }
    throw new UnsupportedOperationException(); //TODO: add Ms, that will hold referenced layers of sub-vectors
  }

  @Override
  public int length() {
    return elems.length;
  }

}
