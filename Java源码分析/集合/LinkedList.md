# 用来表示一个域不是该对象序行化的一部分，当一个对象被序行化的时候，transient修饰的变量不会被序列化概览

## 重要变量

1. 底层实现，表名这是一个双向链表

   ```java
   /**
    * Pointer to first node.
    * Invariant: (first == null && last == null) ||
    *            (first.prev == null && first.item != null)
    */
   transient Node<E> first;
   
   /**
   * Pointer to last node.
   * Invariant: (first == null && last == null) ||
   *            (last.next == null && last.item != null)
   */
   transient Node<E> last;
   ```

2. Node，节点会指向下一个和上一个

   ```java
   private static class Node<E> {
       E item;
       Node<E> next;
       Node<E> prev;
   
       Node(Node<E> prev, E element, Node<E> next) {
           this.item = element;
           this.next = next;
           this.prev = prev;
       }
   }
   ```



## 常用方法

1. add

   ```java
   public boolean add(E e) {
       linkLast(e);
       return true;
   }
   
   /**
    * Links e as last element.
    */
    void linkLast(E e) {
       final Node<E> l = last;
       final Node<E> newNode = new Node<>(l, e, null);
       last = newNode;
       if (l == null)
           first = newNode;
       else
           l.next = newNode;
       size++;
       modCount++;
   }
   ```

2. remove

   ```java
   public boolean remove(Object o) {
       if (o == null) {
           for (Node<E> x = first; x != null; x = x.next) {
               if (x.item == null) {
                   unlink(x);
                   return true;
               }
           }
       } else {
           for (Node<E> x = first; x != null; x = x.next) {
               if (o.equals(x.item)) {
                   unlink(x);
                   return true;
               }
           }
       }
       return false;
   }
       E unlink(Node<E> x) {
           // assert x != null;
           final E element = x.item;
           final Node<E> next = x.next;
           final Node<E> prev = x.prev;
   
           if (prev == null) {
               first = next;
           } else {
               prev.next = next;
               x.prev = null;
           }
   
           if (next == null) {
               last = prev;
           } else {
               next.prev = prev;
               x.next = null;
           }
   
           x.item = null;
           size--;
           modCount++;
           return element;
       }
   
   ```

