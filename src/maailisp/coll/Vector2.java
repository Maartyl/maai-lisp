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
 * Tuple
 *
 * @author maartyl
 */
public class Vector2<T> extends AVector<T> {

  private final T elem0;
  private final T elem1;

  public Vector2(T elem0, T elem1) {
    this.elem0 = elem0;
    this.elem1 = elem1;
  }

  @Override
  public T get(int index) {
    if (index == 0) return elem0;
    if (index == 1) return elem1;
    throw outOfBounds(index);
  }

  @Override
  public Vector<T> set(int index, T elem) {
    if (index == 0) return new Vector2<>(elem, elem1);
    if (index == 1) return new Vector2<>(elem0, elem);
    throw outOfBounds(index);
  }

  @Override
  public Vector<T> add(T elem) {
    return new Vector3<>(elem0, elem1, elem);
  }

  @Override
  public int length() {
    return 2;
  }

  @Override
  public String toString() {
    return "[" + elem0 + " " + elem1 + "]";
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.elem0);
    hash = 37 * hash + Objects.hashCode(this.elem1);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final Vector2<?> other = (Vector2<?>) obj;
    if (!Objects.equals(this.elem0, other.elem0)) return false;
    return Objects.equals(this.elem1, other.elem1);
  }


}
