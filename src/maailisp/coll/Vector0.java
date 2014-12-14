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

/**
 *
 * @author maartyl
 */
public class Vector0<T> extends AVector<T> {

  @Override
  public T get(int index) {
    throw new IndexOutOfBoundsException("accessing field of empty Vector");
  }

  @Override
  public Vector<T> set(int index, T elem) {
    throw new IndexOutOfBoundsException("accessing field of empty Vector");
  }

  @Override
  public Vector<T> add(T elem) {
    return new Vector1<>(elem);
  }

  @Override
  public int length() {
    return 0;
  }

  @Override
  public String toString() {
    return "[]";
  }


}
