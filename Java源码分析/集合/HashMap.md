# 概览



## 重要变量

1. 底层实现

   ```java
       transient Node<K,V>[] table;
   ```

2. 负载因子

   ```java
       /**
        * The load factor for the hash table.
        *
        * @serial
        */
       final float loadFactor;
   ```

3. 阈值

   ```java
       /**
        * The next size value at which to resize (capacity * load factor).
        *
        * @serial
        */
       // (The javadoc description is true upon serialization.
       // Additionally, if the table array has not been allocated, this
       // field holds the initial array capacity, or zero signifying
       // DEFAULT_INITIAL_CAPACITY.)
       int threshold;
   ```

4. 默认容量

   ```java
       /**
        * The default initial capacity - MUST be a power of two.
        */
       static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
   ```

5. 默认负载因子

   ```java
       /**
        * The load factor used when none specified in constructor.
        */
       static final float DEFAULT_LOAD_FACTOR = 0.75f;
   ```

   

## 常用方法

1. 默认构造器

   ```java
   /**
    * Constructs an empty <tt>HashMap</tt> with the default initial capacity
    * (16) and the default load factor (0.75).
    */
   public HashMap() {
       // 设置了默认的负载因子 0.75f
       this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
   }
   ```

2. 指定容量的构造器，初始容量只有16，当数据量大的时候会频繁扩容，消耗性能，最好提前指定容量

   ```java
   /**
    * Constructs an empty <tt>HashMap</tt> with the specified initial
    * capacity and the default load factor (0.75).
    *
    * @param  initialCapacity the initial capacity.
    * @throws IllegalArgumentException if the initial capacity is negative.
    */
   public HashMap(int initialCapacity) {
       this(initialCapacity, DEFAULT_LOAD_FACTOR);
   }
   ```
   
3. resize()

   ```java
   /**
    * Initializes or doubles table size.  If null, allocates in
    * accord with initial capacity target held in field threshold.
    * Otherwise, because we are using power-of-two expansion, the
    * elements from each bin must either stay at same index, or move
    * with a power of two offset in the new table.
    * 初始化或者两倍扩容
    *
    * @return the table
    */
   final Node<K,V>[] resize() {
       Node<K,V>[] oldTab = table;
       int oldCap = (oldTab == null) ? 0 : oldTab.length;
       int oldThr = threshold;
       int newCap, newThr = 0;
       if (oldCap > 0) {
           // 老的容量大于0
           if (oldCap >= MAXIMUM_CAPACITY) {
               // 限制容量最大值
               threshold = Integer.MAX_VALUE;
               return oldTab;
           }
           else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
               // 两倍扩容
               newThr = oldThr << 1; // double threshold
       }
       else if (oldThr > 0) // initial capacity was placed in threshold
           newCap = oldThr;
       else {            
           // 阈值，容量均为null，则初始化
           newCap = DEFAULT_INITIAL_CAPACITY; // 16
           newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);// 0.75 * 16
       }
       if (newThr == 0) {
           float ft = (float)newCap * loadFactor;
           newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                     (int)ft : Integer.MAX_VALUE);
       }
       threshold = newThr;
       @SuppressWarnings({"rawtypes","unchecked"})
       Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
       table = newTab;
       // 数组不为null，则对数组进行扩容处理，否则，直接返回数组
       if (oldTab != null) {
           for (int j = 0; j < oldCap; ++j) {
               Node<K,V> e;
               if ((e = oldTab[j]) != null) {
                   oldTab[j] = null;
                   if (e.next == null)
                       newTab[e.hash & (newCap - 1)] = e;
                   else if (e instanceof TreeNode)
                       ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                   else { // preserve order
                       Node<K,V> loHead = null, loTail = null;
                       Node<K,V> hiHead = null, hiTail = null;
                       Node<K,V> next;
                       do {
                           next = e.next;
                           if ((e.hash & oldCap) == 0) {
                               if (loTail == null)
                                   loHead = e;
                               else
                                   loTail.next = e;
                               loTail = e;
                           }
                           else {
                               if (hiTail == null)
                                   hiHead = e;
                               else
                                   hiTail.next = e;
                               hiTail = e;
                           }
                       } while ((e = next) != null);
                       if (loTail != null) {
                           loTail.next = null;
                           newTab[j] = loHead;
                       }
                       if (hiTail != null) {
                           hiTail.next = null;
                           newTab[j + oldCap] = hiHead;
                       }
                   }
               }
           }
       }
       return newTab;
   }
   ```

4. hash()，length 总是2的n次方时，hash & (length-1)运算等价于对 length 取模，也就是 hash%length，但是&比%具有更高的效率

   ```java
   static final int hash(Object key) {
       int h;
       return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
   }
   ```

5. putVal()

   ```java
   final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                  boolean evict) {
       Node<K,V>[] tab; Node<K,V> p; int n, i;
       if ((tab = table) == null || (n = tab.length) == 0)
           // 数组为null的时候初始化数组
           n = (tab = resize()).length;
       if ((p = tab[i = (n - 1) & hash]) == null)
           // key的hash值在数组中位置没有值，新增一个节点放到这个位置中
           tab[i] = newNode(hash, key, value, null);
       else {
           // key的hash值在数组中位置有值，进行一系列的处理
           Node<K,V> e; K k;
           if (p.hash == hash &&
               ((k = p.key) == key || (key != null && key.equals(k))))
               // 返回的节点的hash值和key与传入的hash,key值一致，则返回当前位置的节点
               e = p;
           else if (p instanceof TreeNode)
               // 返回的节点是树形节点，则在树形节点中新增值
               e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
           else {
               // 返回的节点是链表结构，同时key不在第一位
               for (int binCount = 0; ; ++binCount) {
                   // 遍历整个链表
                   if ((e = p.next) == null) {
                       // 找到链表的最末尾的一个节点
                       p.next = newNode(hash, key, value, null);
                       if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                           // 到达链表转树形结构的阈值时，把链表转换为树形结构
                           treeifyBin(tab, hash);
                       break;
                   }
                   if (e.hash == hash &&
                       ((k = e.key) == key || (key != null && key.equals(k))))
                       // 返回的节点的hash值和key与传入的hash,key值一致，则返回当前位置的节点
                       break;
                   p = e;
               }
           }
           if (e != null) { // existing mapping for key
               // 传入的key在整个hashMap中存在，对值进行覆盖
               V oldValue = e.value;
               if (!onlyIfAbsent || oldValue == null)
                   e.value = value;
               afterNodeAccess(e);
               return oldValue;
           }
       }
       ++modCount;
       if (++size > threshold)
           resize();
       afterNodeInsertion(evict);
       return null;
   }
   ```

6. get方法，核心

   ```java
   final Node<K,V> getNode(int hash, Object key) {
       Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
       if ((tab = table) != null && (n = tab.length) > 0 &&
           (first = tab[(n - 1) & hash]) != null) {
           // 数组不为空，同时能够在数组中找到节点
           if (first.hash == hash && // always check first node
               ((k = first.key) == key || (key != null && key.equals(k))))
               return first;
           if ((e = first.next) != null) {
               if (first instanceof TreeNode)
                   // 树形结构获得数据
                   return ((TreeNode<K,V>)first).getTreeNode(hash, key);
               do {
                   // 链表结构获得数据
                   if (e.hash == hash &&
                       ((k = e.key) == key || (key != null && key.equals(k))))
                       return e;
               } while ((e = e.next) != null);
           }
       }
       return null;
   }
   ```







## 原理分析

1. 基本组成

   JDK 1.8 以后 HashMap的实现方式改成了 数组+单向链表+红黑树，主要目的是为了提高查找效率。

2. JDK 1.8 以后 HashMap 数据结构图如下， 当链表节点较少时仍然是以链表存在，当链表节点较多时（大于8）会转为红黑树。 

    ![](../../pic/20180121151050372.png)

   

