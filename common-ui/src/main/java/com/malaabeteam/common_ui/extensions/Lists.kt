package com.malaabeteam.common_ui.extensions

import android.view.View

fun <T> MutableList<T>.replace(newList: List<T>) {
  clear()
  addAll(newList)
}

fun <T> MutableList<T>.replaceItemIf(newItem: T, predicate: (T) -> Boolean): MutableList<T> {
  val index = indexOfFirst(predicate)
  if (index != -1) {
    removeAt(index)
    add(index, newItem)
  }
  return this.toMutableList()
}

fun <T> MutableList<T>.removeItemIf(predicate: (T) -> Boolean): MutableList<T> {
  val index = indexOfFirst(predicate)
  if (index != -1) {
    removeAt(index)
  }
  return this
}

fun List<View>.onClick(action: (View) -> Unit) {
  forEach { view -> view.onClick { action(view) } }
}
