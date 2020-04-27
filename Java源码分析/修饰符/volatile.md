# 概览

volatile 修饰共享变量之后，

1. 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
2. 禁止进行指令重排序。

```java
public volatile int inc = 0;

public void increase() {
    inc++;
}

public static void main(String[] args) {
    final Test test = new Test();
    for(int i=0;i<10;i++){
        new Thread(){
            public void run() {
                for(int j=0;j<1000;j++)
                    test.increase();
            };
        }.start();
    }

    while(Thread.activeCount()>1)  //保证前面的线程都执行完
        Thread.yield();
    System.out.println(test.inc);// 输出的结果小于等于10000，volatile保证了可见性，但是inc++不是原子性的。
}
```