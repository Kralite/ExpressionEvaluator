# ExpressionEvaluator
ExpressionEvaluator是一个Java库，用来对表达式进行求值，并且可以很容易地扩展新的运算方法、值类型等。

ExpressionEvaluator已封装了最常用的应用场景：对**字符串**类型的表达式进行求值。

例如：
要求 `a/5 + b * (2 - c + 52.4/3)` 这个表达式的值，其中`a`、`b`、`c`是参数，可以向它们赋值。代码如下：

```
// 定义表达式
String expression = "a/5 + b * (2 - c + 52.4/3)";

// 传入变量值
Map<String, String> params = new TreeMap<>();
params.put("a", "6");
params.put("b", "3");
params.put("c", "49");

try{
	Operand result = CommonEvaluator.value(expression, params);
	System.out.println("Sample1 result: " + result.value());
}
catch (Exception e) {
	e.printStackTrace();
}
```

求值的结果是：-87.4

除了数值运算外，逻辑运算、字符串拼接、正则表达式、变量名正则匹配等，能满足大多数自定义运算需求。

事实上除了字符串类型的表达式求值，**任何自定义类型**的都可以使用公共的求值内核，只需要为自定义类型扩展一个**解析器**（Parser）即可。

----

## 功能及实例教程（字符串类型表达式）

### 一 字符串类型表达式使用方法

调用`CommonEvaluator`的静态方法value即可：

```
/**
  * 对字符串类型的表达式求值
  * @param expression 表达式
  * @param params 参数，用以为表达式中的变量赋值
  * @return 计算结果（调用Operand.value()方法可以得到实际值）
  * @throws Exception 求值异常
*/
public static Operand value(String expression, Map<String, String> params)
```

### 二 变量的表示、加减乘除、逻辑运算和括号

- 变量的表示就直接写它的名字即可：

```
    x > 3
```    

含义：若x这个变量的数值大于3，就返回true：

```
    {x:4}
```

- 数字类型可以进行加减乘除，以及大于 `>` 、小于 `<` 、等于 `==` 运算：

```
    a * 2 + b
        
    {x:1, b:2} // 结果为：4
        
        
    a + 2 == b
        
    {a:1, b:3} //结果为：true
```

- 字符串可以相加、判断相等。字符串也可以和数字相加拼接。
字符串常量需要用双引号包起来（如 `"string"`），否则会被认为是变量：

```
    "free" + x + "_" + (2.1 + 1) == "freeaccount_3.1"
        
    {x: "account"} //告警
```

- 支持与 `&&` 、或 `||` 、非 `!` 逻辑运算。

```
    x == "freeshare" && ( e>0 || (!(c>0)))
        
    {x:freeshare, e:2, c: 1 ...} //告警
    {x:freelol, e:2, c:1 ...}    //不告警
```
> 1. 表达式中运算符和运算数之间对空格没有要求，没有空格或任意多个空格都不影响。
> 2. params中可以有表达式中没有出现的变量，但是不能缺少表达式中出现了的变量。

------

### 三 正则表达式、复杂变量名、变量名正则匹配

- 支持判断某字符串是否匹配某正则表达式，语法是 `str ~ regex("regular expression")`,其中str可以是字符串常量也可以是变量名，regex()括号内的正则表达式必须是字符串常量。

```
    x ~ regex("free\w+")
        
    {x: freeshare ...}  //告警
    {x: freelol ...}    //告警
    {x: appstore ...}   //不告警


    (x + "@" + e + ".com") ~ regex("\w+@\w+\.com") && c>0
        
    {x: cdl, e:free4inno, c:1}  //true
```

- **复杂变量名**：带有+、-、*、/这些和表达式中运算符号相同的变量名会造成歧义，使运算结果出错。这样的变量名可以用 `v("fieldName")` 来包围，就不会出现歧义，其运算效果和直接写变量名完全一样。

```
    v("localhost/srv/nfs4") + v("c++") + x //正确
    
    {
        localhost/srv/nfs4 : 2 ,
        c++ : 2,
        x : 1
    }// 结果是：5
    
    localhost/srv/nfs4 + c++ + x   //错误
```

- **变量名正则匹配**的含义（此种运算下的结果必须是布尔类型）：params中某一变量的名字匹配某正则表达式的的话，就把params中该变量的值代入表达式计算；如果有多个变量的名字都匹配该正则表达式，则会分别把params中这些变量的值代入表达式计算，然后将各表达式的结果作“或”运算得到最后的布尔值。变量名正则匹配的语法是 `regV("regex of fieldName")` 。

```
    regV("192.168.33.\d+:?/srv/nfs4/\.system\.disk\.in_use") + x > 5
    
    //结果为true：
    {
        192.168.33.6:/srv/nfs4/.system.disk.free : 3,   //变量名不匹配
        192.168.33.3:/srv/nfs4/.system.disk.in_use : 4, //变量名匹配
        192.168.33.3/srv/nfs4/.system.disk.in_use : 5,  //变量名匹配
        192.168.33.6/srv/nfs4/.system.disk.free : 2,    //变量名不匹配
        x : 1
        ...
    }

    上面这个例子的4个变量中，只有第二个和第三个匹配了正则表达式，所以会分别将5和6代入表达式计算，即分别计算 `4+1>5` 和 `5+1>5` 两个式子，将各自的结果false和true进行逻辑或运算，得到结果为true。
```

```
    regV("\w+Name") ~ regex("free\w+") 
    
    {
        appName: freeshare, //appName匹配"\w+Name"，freeshare匹配"free\w+"，true
        groupName: freelol, //groupName匹配"\w+Name"，freelol匹配"free\w+"，true
        userName: cdl,      //userName匹配"\w+Name"，cdl不匹配"free\w+"，false
        creator: free4inno  //creator不匹配"\w+Name"，因此free4inno不参与计算
    }
    
    结果会分别将appName、groupName和userName的值代入表达式运算，将各自的结果取“或”得到true。
```

> &emsp;**注意：**对变量名进行正则匹配实际上是表达式的歧义（同一表达式多个运算结果），为了让结果可控，只支持最多一个变量名的正则匹配，否则计算结果会出现未知错误。例如 `regV("\w+price") + regV("\w+ammount") > 5`，这个表达式是不允许的。但是如果正则表达式相同，可以在同一表达式中多次出现，如 `regV("\w+price") + regV("\w+price") > 5`，这样是没问题的。

---

### 四 向量

&emsp;&emsp;向量的语法是 `vector(e1, e2, ...)` 。

```
    //向量间运算必须维度相同，取两向量同一位置处的元素进行运算，结果是一个同维度的向量
    vector(1, 2, 3) * vector(1, 2, 3) //结果是： vector(1, 4, 9)    //正确
    vector(1, 2, 3) + vector(1, 2, 3, 4) //错误，不同维度向量不能运算
    
    //向量和普通数运算，是把向量各元素分别和该数运算，然后得到一个同纬度向量
    vector(1, 2, 3) + 1 // 结果是： vector(2, 3, 4)  
  
    vector(1, 2, 3) + 1 + vector(1, 1, 1) > 4   //结果是vector(false,false,true)。
    
    //同一vector中各元素类型不一定要相同，目前暂时只支持字符串、数字和布尔,暂不支持变量。如果使用变量的话请使用“变量名正则匹配”的方法
    vector(1,"hello") + 3 //结果是： vector(4,"hello3")
```


------

### 五 优先级表

|优先级|操作符|
|:--:|:--:|
|5|~（正则表达式匹配）|
|4| *、/|
|3| +、-|
|2| \>、<、==|
|1| !、&&、\|\||


---

## 主要类图

![uml](http://ojh6r395f.bkt.clouddn.com/2018-01-30-uml.jpg)

