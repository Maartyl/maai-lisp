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

import java.util.Objects;

/**
 *
 * @author maartyl
 */
public class Vector3<T> extends AVector<T> {

  private final T elem0;
  private final T elem1;
  private final T elem2;

  public Vector3(T elem0, T elem1, T elem2) {
    this.elem0 = elem0;
    this.elem1 = elem1;
    this.elem2 = elem2;
  }

  @Override
  public T get(int index) {
    if (index == 0) return elem0;
    if (index == 1) return elem1;
    if (index == 2) return elem2;
    throw outOfBounds(index);
  }

  @Override
  public Vector<T> set(int index, T elem) {
    if (index == 0) return new Vector3<>(elem, elem1, elem2);
    if (index == 1) return new Vector3<>(elem0, elem, elem2);
    if (index == 2) return new Vector3<>(elem0, elem1, elem);
    throw outOfBounds(index);
  }

  @Override
  public Vector<T> add(T elem) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int length() {
    return 3;
  }

  @Override
  public String toString() {
    return "[" + elem0 + " " + elem1 + " " + elem2 + "]";
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + Objects.hashCode(this.elem0);
    hash = 71 * hash + Objects.hashCode(this.elem1);
    hash = 71 * hash + Objects.hashCode(this.elem2);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final Vector3<?> other = (Vector3<?>) obj;
    if (!Objects.equals(this.elem0, other.elem0)) return false;
    if (!Objects.equals(this.elem1, other.elem1)) return false;
    return Objects.equals(this.elem2, other.elem2);
  }


}
