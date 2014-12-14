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
public abstract class AVector<T> implements Vector<T> {
  //protected methods shared between all classical vectors

  protected IndexOutOfBoundsException outOfBounds(int index) {
    return new IndexOutOfBoundsException("Expected: 0 to " + length() + "; got: " + index);
  }


}
