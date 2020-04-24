# 概览

1. 底层由数组实现
2. 线程不安全
3. 默认构造器会创建一个空数组{}。
4. 新增的时候，如果数组为空数组，则数组长度会扩从到10。不为空的时候数组长度则会扩充大概一半，在旧的长度上右移一位之后相加。比如：原来是10【1010】，右移一位就是5【101】，总长度就是15。同时会把原来的数组复制到新数组。因此，当新增的操作多的时候，会反复扩容，此时最好在初始化的时候就定义个长度。
5. 实现了RandomAccess，标明ArrayList是支持随机访问的。通过下标的方式。
6. 实现了快速失败。
7. 实现了List接口，就意味着ArrayList元素有序的，可以重复的，并且可以有null的元素。

# ArrayList 和 Vector 的区别

1. ArrayList 是线程不安全的，Vector是线程安全的，Vector方法上都有synchronized，因此ArrayList效率高。
2. ArrayList 采用动态对象数组实现，默认构造方法创建了一个空数组，Vecotr 采用动态数组对象实现，默认构造方法创建了一个大小为10的对象数组
3. 在数据新增的时候，Vector扩充的算法：当增量为0时，扩充为原来大小的2倍，当增量大于0时，扩充为原来大小+增量， ArrayList第一次添加元素，扩展容量为10，之后的扩充算法：原来数组大小+原来数组的一半。

```java
 /**
     *
     *  ArrayList
     * 
     * @author yangyh
     * @since 2019/11/20
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        // 根据二进制的 8421 以下代码相当于在oldCapacity的基础上增加一半
        // 例如：oldCapacity = 3;  newCapacity = 3 + 1 = 4；
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```

```java
    /**
     *
     * Vector
     *
     * @author yangyh
     * @since 2019/11/20
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                capacityIncrement : oldCapacity);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```



## 阐述ArrayList、Vector、LinkedList的存储性能和特性

1. ArrayList 和Vector他们底层的实现都是一样的，都是使用数组方式存储数据，此数组元素数大于实际存储的数据以便增加和插入元素，它们都允许直接按序号索引元素，但是插入元素要涉及数组元素移动等内存操作，所以索引数据快而插入数据慢。
2. Vector中的方法由于添加了synchronized修饰，因此Vector是线程安全的容器，但性能上较ArrayList差，因此已经是Java中的遗留容器
3.  LinkedList使用双向链表实现存储（将内存中零散的内存单元通过附加的引用关联起来，形成一个可以按序号索引的线性结构，这种链式存储方式与数组的连续存储方式相比，内存的利用率更高），按序号索引数据需要进行前向或后向遍历，但是插入数据时只需要记录本项的前后项即可，所以插入速度较快。            



## CopyOnWriteArrayList、Vector、SynchronizedList

1. 三者都是线程安全的
2. Vector在所有的方法上加了synchronized关键字
3. SynchronizedList在大部分方法内部的代码块上加了synchronized，除了读方法，因此读方法要自己加上锁
4. CopyOnWriteArrayList的写方法通过Arrays.copyOf ()实现，写比较耗性能，相当于实现了读写分离
5. SynchronizedList在java.util.Collections下，可以实现很多集合的线程安全

结论：写操作比较少的时候推荐CopyOnWriteArrayList。Vector、SynchronizedList性能相差不大