# 概览

transient 用来表示一个域不是该对象序行化的一部分，当一个对象被序行化的时候，transient修饰的变量不会被序列化。ArrayList中的

```java
/**
 * The array buffer into which the elements of the ArrayList are stored.
 * The capacity of the ArrayList is the length of this array buffer. Any
 * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
 * will be expanded to DEFAULT_CAPACITY when the first element is added.
 */
transient Object[] elementData; // non-private to simplify nested class access
```

elementData 是用transient 修饰的，表示elementData 不会被序列化，elementData 是ArrayList的底层实现，不能序列化的话就会导致数据不能传输，猜测使用transient 修饰的原因是elementData 存在没有用到的空间，此时序列化的话会影响性能。但是，JDK提供了另外两个方位来实现writeObject() readObject*()

```java
private void writeObject(java.io.ObjectOutputStream s)
    throws java.io.IOException{
    // Write out element count, and any hidden stuff
    int expectedModCount = modCount;
    s.defaultWriteObject();
    

    // 只读取到真正的长度
    // Write out size as capacity for behavioural compatibility with clone()
    s.writeInt(size);

    // Write out all elements in the proper order.
    for (int i=0; i<size; i++) {
        s.writeObject(elementData[i]);
    }

    if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
    }
}
```