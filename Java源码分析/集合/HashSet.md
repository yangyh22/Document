# 概览

## 重要变量

1. 底层实现，主要利用了HashMap的Key，所以叫hashSet

   ```java
   private transient HashMap<E,Object> map;
   ```

2. 空对象

   ```java
   // Dummy value to associate with an Object in the backing Map
   private static final Object PRESENT = new Object();
   ```



## 常用方法

1. add

   ```java
   public boolean add(E e) {
       // HashMap key不能重复
       return map.put(e, PRESENT)==null;
   }
   ```

2. remove

   ```java
   public boolean remove(Object o) {
       // 调用了HashMap的remove方法
       return map.remove(o)==PRESENT;
   }
   ```

3. contains

   ```java
   public boolean contains(Object o) {
       // 调用了HashMap的containsKey方法
       return map.containsKey(o);
   }
   ```

4. 构造方法

   ```java
   public HashSet() {
       map = new HashMap<>();
   }
   ```

5. 构造方法

   ```java
   public HashSet(int initialCapacity) {
       // 涉及到HashMap容量问题，数据量很大时，最好初始化容量
       map = new HashMap<>(initialCapacity);
   }
   ```