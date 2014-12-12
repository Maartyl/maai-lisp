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
import java.util.function.Consumer;
import java.util.function.Function;
import maailisp.core.*;

/**
 *
 * @author maartyl
 * @param <T> collection of what
 */
public abstract class Seq<T> extends Obj {

  public abstract T first();

  public abstract Seq<T> rest();

//  public <R> Seq<R> map(final Function<? super T, R> mapper) {
//    return Seq.map(this, mapper);
//  }
//
//  public <R> Seq<R> mapRecur(final Function<? super T, R> mapper) {
//    return Seq.mapRecur(this, mapper);
//  }

  protected boolean isNil() {
    return false;
  }

  //------------------------------------------
  public static <T> Seq<T> cons(final T first, final Seq<T> rest) {
    return new Seq<T>() {

      @Override
      public final T first() {
        return first;
      }

      @Override
      public final Seq<T> rest() {
        return rest;
      }
    };
  }

  /**
   * Simple lazy-cons. Not thread safe.
   */
  public static <T> Seq<T> lazy(final T first, final LazyRest<T> restfn) {
    return new LazyCons<>(first, restfn);
  }

  /**
   * Simple lazy-seq. Not thread safe.
   * Doesn't evaluate first until needed. Just delayed.
   * <p>
   */
  public static <T> Seq<T> lazy(final LazyRest<T> seq) {
    return new LazySeq<>(seq);
  }

  public static <T> Seq<T> ofIterator(Iterator<T> it) {
    if (!it.hasNext()) return null;
    return Seq.lazy(it.next(), () -> ofIterator(it));
  }

  public static <T> Seq<T> ofArray(final T[] arr) {
    return ofArray(arr, 0);
  }

  @SafeVarargs
  public static <T> Seq<T> of(final T... arr) {
    return ofArray(arr);
  }

  public static <T> Seq<T> of(final T o1) {
    return Seq.cons(o1, null);
  }

  public static <T> Seq<T> of(final T o1, final T o2) {
    return Seq.cons(o1, Seq.cons(o2, null));
  }

  public static <T> Seq<T> of(final T o1, final T o2, final T o3) {
    return Seq.cons(o1, Seq.cons(o2, Seq.cons(o3, null)));
  }

  /**
   * Lazy concatenation of 2 Seqs
   */
  public static <T> Seq<T> concat(final Seq<T> s1, final Seq<T> s2) {
    if (Seq.isNil(s1)) return s2;
    return Seq.lazy(s1.first(), () -> concat(s1.rest(), s2));
  }

  /**
   * Recursive concatenation of 2 Seqs.
   * Faster but only useful if first seq is very short.
   */
  public static <T> Seq<T> concatRecur(final Seq<T> s1, final Seq<T> s2) {
    if (Seq.isNil(s1)) return s2;
    return Seq.cons(s1.first(), concatRecur(s1.rest(), s2));
  }

  public static <T> boolean isNil(final Seq<T> s) {
    return s == null || s.isNil();
  }

  /**
   * also checks if seq is nil
   */
  public static <T> T first(final Seq<T> s) {
    return isNil(s) ? null : s.first();
  }

  /**
   * also checks if seq is nil
   */
  public static <T> Seq<T> rest(final Seq<T> s) {
    return isNil(s) ? null : s.rest();
  }

  public static <T, R> Seq<R> map(final Seq<T> s, final Function<? super T, R> mapper) {
    if (isNil(s)) return null;
    return Seq.lazy(mapper.apply(s.first()), () -> map(s.rest(), mapper));
  }

  public static <T, R> Seq<R> mapRecur(final Seq<T> s, final Function<? super T, R> mapper) {
    if (isNil(s)) return null;
    return Seq.cons(mapper.apply(s.first()), mapRecur(s.rest(), mapper));
  }

  /**
   * foldl.
   */
  public static <T, Acc> Acc reduce(Seq<T> s, Acc acc, final java.util.function.BiFunction<Acc, ? super T, Acc> reducer) {
    for (; !isNil(s); s = s.rest())
      acc = reducer.apply(acc, s.first());
    return acc;
  }

  public static <T> Seq<T> take(final Seq<T> s, int count) {
    if (count == 0 || isNil(s)) return null;
    return Seq.lazy(s.first(), () -> take(s.rest(), count - 1));
  }

  public static <T> Seq<T> drop(Seq<T> s, int count) {
    for (; count > 0 && !isNil(s); --count, s = s.rest()) {
      //just move count far (or less, if seq ends sooner)
    }
    return s;
  }

  public static <T> Seq<T> reverse(final Seq<T> s) {
    return Seq.reduce(s, null, (acc, o) -> cons(o, acc));
  }

  public static <T> void doseq(Seq<T> s, final Consumer<T> consumer) {
    for (; !Seq.isNil(s); s = s.rest())
      consumer.accept(s.first());
  }

  /**
   * Allows to perform some action inside function call. like cleaning up (arg=null)
   * Useful for freeing vars so GC can collect them etc.
   * <p>
   */
  public static <T> T ret1(T a, Object b) {
    return a;
  }

  protected static <T> Seq<T> ofArray(final T[] arr, final int index) {
    return new Seq<T>() {
      @Override
      public final T first() {
        return arr[index];
      }

      @Override
      public final Seq<T> rest() {
        return index + 1 < arr.length ? ofArray(arr, index + 1) : null;
      }
    };
  }

  public static interface LazyRest<T> {

    /**
     * Function to produce a lazy sequence.
     * Will only be ever evaluated once. (in the context of LazySeq)
     * <p>
     */
    public Seq<T> eval();
  }

  static class LazySeq<T> extends Seq<T> {

    LazyRest<T> seqFn;
    Seq<T> cashedSeq = null;

    public LazySeq(LazyRest<T> seqFn) {
      this.seqFn = seqFn;
    }

    @Override
    public final T first() {
      assureEvaluated();
      return Seq.first(cashedSeq);
    }

    @Override
    public final Seq<T> rest() {
      assureEvaluated();
      return Seq.rest(cashedSeq);
    }

    private void assureEvaluated() {
      if (seqFn != null) {
        cashedSeq = seqFn.eval();
        seqFn = null; //GC friendly + no need for extra boolean
      }
    }

    @Override
    protected boolean isNil() {
      assureEvaluated();
      return cashedSeq == null;
    }
  }

  static class LazyCons<T> extends Seq<T> {

    T first;
    LazyRest<T> restFn; //doesn't work: restfn is remembered : make its own class
    Seq<T> cashedRest = null;

    public LazyCons(T first, LazyRest<T> restFn) {
      this.first = first;
      this.restFn = restFn;
    }

    @Override
    public final T first() {
      return first;
    }

    @Override
    public final Seq<T> rest() {
      if (restFn != null) {
        cashedRest = restFn.eval();
        restFn = null; //GC free + no need for extra boolean
      }
      return cashedRest;
    }
  }

}
