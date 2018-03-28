# iOS 面试基础题目

## 1. `@property` 后面可以有哪些修饰符？

属性分成以下四类：

* 原子性：`atomic`(default)、`nonatomic`。原子性只能保证多线程安全访问，但是不足以保证属性的线程安全。
* 读写权限：`readwrite`(default)、`readonly`。
* 内存管理语义：`assign`、`strong`、`weak`、`unsafe_unretained`、`copy`。
    * `assign`：对应值类型，简单赋值操作。
    * `strong`：强引用持有。
    * `weak`：弱引用持有。
    * `unsafe_unretained`：对象类型的值引用，不持有，但指向对象释放时自身也不会释放。
    * `copy`：`setter` 方法调用的时候，将目标对象的通常用在持有 `mutable` 的对象上。
* 属性读写：`getter` 和 `setter` 方法设置：弱不使用自动生成的 `getter` 和 `setter` 方法，则使用下列语法绑定 `getter` 和 `setter`。

```Objective-C
@property (nonatomic, getter=isOn) BOOL on; 
``` 

* *Nullability* 特性（WWDC 2015 新加入）：`nonnull`、`null_resettable`、`nullable`。兼容 Swift 中的 Optional 特性，`null_resettable` 的意思是初值可为 `nil`，在第一次调用的时候将初始化成非 `nil` 的对象。效果等同于 Swift 中 `!` 表达。

##  2. `@property (copy) NSMutableArray *array;` 这种写法会出现什么问题？

1. `NSMutableArray` 的 `copy` 方法是复制一个 `NSArray` 对象，当调用其增删改的方法时，会 crash。
2. 默认使用 `atomic`，会有性能损耗，但是却无法保证 `NSArray` 线程安全。

## 3. 如何让自己的类用 copy 修饰符？如何重写带 copy 关键字的 setter？

有两种关于 Copy 的协议，`NSCopying` 和 `NSMutableCopying`。这与使用的对象有关。该协议只有一个方法：

```Objective-C
- (id)copyWithZone:(NSZone *)zone;
```

当自己的类使用了 copy 修饰符，不仅要 override `copy` 方法，也要实现 `compyWithZone` 方法。

```Objective-C
// .h
#import <Foundation/Foundation.h>

@interface DGObject : NSObject<NSCopying>

@property (nonatomic, copy, nonnull) NSString *name;
@property (nonatomic, assign) NSUInteger age;

- (instancetype _Nullable )initWithName: (NSString *_Nullable)name age:(NSUInteger)age;
+ (instancetype _Nullable )dgoWithName:(NSString *_Nullable)name age:(NSUInteger)age;

@end

/**************************************/

// .m
#import "DGObject.h"

@implementation DGObject

+ (instancetype) dgoWithName:(NSString *)name age:(NSUInteger)age {
    return [DGObject.alloc initWithName:name age:age];
}

- (instancetype) initWithName:(NSString *)name age:(NSUInteger)age {
    self = [super init];
    if (self) {
        self.name = name;
        self.age = age;
    }
    return self;
}

- (nonnull id)copyWithZone:(nullable NSZone *)zone {
    DGObject *copy = [[self.class allocWithZone:zone] initWithName:self.name age:self.age];
    return copy;
}

@end
```

如果你需要深拷贝操作，可能需要增加一个深拷贝的方法。


## 4. `@property` 的本质是什么？`ivar`、`getter`、`setter` 是如何生成并添加到这个类中的

`@property` 是 `ivar`、`getter`、`setter` 的集中体现，其实就是 `ivar` 实例以及 `getter` 和 `setter` 方法。他的作用在于封装对象中的数据。ObjC 对象通常把所需要的数据保存为各种实例变量，实例变量一般通过 `getter` 和 `setter` 方法读取变量。这个概念与 Java 中的属性相同，在正规的 ObjC 的编码风格中，`setter` 和 `getter` 方法有着明确严格的命名规范，所以 Objective-C 才能自动创建 `getter` 和 `setter` 方法。

当代码的 `@interface` 作用域中存在以下的内容:

```Objective-C
@interface DGObject: NSObject
@property (nonatomic, copy) NSString *firstName;
@end
```

则在编译后与下列情况等效：

```Objective-C
@interface DGObject: NSObject
/// getter
- (NSString *)firstName;
/// setter
- (void)setFirstName: (NSStirng *)firstName;
@end
```

**`ivar`、`getter`和 `setter` 添加过程**

在定义之后，编译器会自动编写这些属性需要的方法，这就是 *autosynthesis* 过程。这个过程是在编译期完成的，编辑器中无法发现 Sythesized Method 的实现。除了 `getter` 和 `setter`，编译器还会自动向类中添加适当的实例变量，并且在属性名前添加下划线，作为实例变量的名字。在 ObjC 1.0 时，是需要自行使用 `@synthesize` 来指定成员名：

```Objective-C
@implementation DGObject

@sythesize firstName = _firstName;

@end
```

在 Runtime 源码中的逆向思考，在每次添加属性的时候，系统都会在 `ivar_list` 中添加成员变量描述，在 `method_list` 中增加 `getter` 和 `setter` 方法描述，通过 hardcode 来存储变量的 offset。这个 offset 的变量名一般是 `OBJC_IVAR_$Class name$Property Name`。

## 5. `@protocol` 和 Category 中如何使用 `@property`

1. 在 `protocol` 中使用 `@property` 只会生成 `setter` 和 `getter` 的方法声明，让 Class 遵循这个 Protocol 并实现其属性即可。
2. Category 中无法添加 `@property`，需要使用 Runtime 的两个方法来添加 `@property` 并实现其 `getter` 和 `setter` 方法。`objc_setAssociatedObject` 和 `objc_getAssociatedObject`。
3. 对于 Swift 来说，`protocol` 中可以声明成员，并且在 `protocol` 的 `extension` 中可以指定其 `getter` 和 `setter` 方法。
4. 在 Swift 中 Category 即为 `extension`，同样的需要使用 Runtime 来进行设置。导入 `import ObjectiveC` 保持和 ObjC 相同的实现即可。

## 6. Runtime 中实现 weak 属性的原理

自己在博客中做过分析：[链接](http://www.desgard.com/weak/)。

`__weak` 关键字引起 `obje_initWeak(&p, 指针)` 的调用，然后进行**更新散列过程**。通过指针先构造出 `weak_entry_t`，其中会持有一个 `DisguisedPtr`，这个指针使用了泛型，通过对于 SideTable 的构造，将其拓展成了一个 **弱引用散列表**。以下是 `weak_entry_t` 的数据结构：

```Objective-C
typedef objc_object ** weak_referrer_t;
struct weak_entry_t {
    DisguisedPtr<objc_object> referent;
    union {
        struct {
            weak_referrer_t *referrers;
            uintptr_t        out_of_line : 1;
            uintptr_t        num_refs : PTR_MINUS_1;
            uintptr_t        mask;
            uintptr_t        max_hash_displacement;
        };
        struct {
            // out_of_line=0 is LSB of one of these (don't care which)
            weak_referrer_t  inline_referrers[WEAK_INLINE_COUNT];
        };
 }
```

其中各个成员的定义如下：

* **out_of_line**：最低有效位，也是标志位。当标志位 0 时，增加引用表指针纬度。
* **num_refs**：引用数值。这里记录弱引用表中引用有效数字，因为弱引用表使用的是静态 hash 结构，所以需要使用变量来记录数目。
* **mask**：计数辅助量。
* **max_hash_displacement**：hash 元素上限阀值。

总结一下 `StripedMap<SideTable>[]` ： `StripedMap` 是一个模板类，在这个类中有一个 array 成员，用来存储 PaddedT 对象，并且其中对于 `[]` 符的重载定义中，会返回这个 `PaddedT` 的 value 成员，这个 value 就是我们传入的 T 泛型成员，也就是 SideTable 对象。在 array 的下标中，这里使用了 indexForPointer 方法通过位运算计算下标，实现了静态的 Hash Table。而在 `weak_table` 中，其成员 weak_entry 会将传入对象的地址加以封装起来，并且其中也有访问全局弱引用表的入口。

![](http://7xwh85.com1.z0.glb.clouddn.com/weaktable-2.png)

**重点记录**

1. `storeWeak` 方法的实现的几个步骤：重新构造 SideTables() -> `lockTwo` 加锁操作 -> `weak_unregister` 接触旧对象和引用的 hash 关联 -> 新的 weak 引用注册。
2. SideTable 数据结构：自旋锁 `slock`，引用计数 hash 表 `refcnts`，全局弱引用表 `weak_table`。


## 7. weak 属性需要在 `dealloc` 中置 `nil` 么？

不需要。在 ARC 模式下模式下，`strong` 和 `weak` 类型均由 ARC 自动处理。而在 MRC 模式下，`weak` 属性在自身作用域之后会直接释放，所以也不需要手动处理。

## 8. `@synthesize` 和 `@dynamic` 的作用。

1. `@property` 中有两个对应的关键字 `@synthesize` 和 `@dynamic`。如果没有手动显示写出，那么默认就是：

```Objective-C
@synthesize var = _var
```

2. `@synthesize` 的意思就是如果你没有手动实现 `getter` 和 `setter`，编译器会自动添加这来能个方法。
3. `@dynamic` 的语义是告诉编译器，我会显式申明 `getter` 和 `setter` 方法，不需要自动生成。对于 `readonly` 属性自己仅仅需要实现 `getter` 即可。如果显式写出 `@dynamic`，缺没有手动实现 `getter` 和 `setter`，编译可通过，在调用对应方法的时候会造成 crash。其实这一组关键字也反映了 ObjC 的**动态绑定**性

## 9. ARC 模式下，修饰属性对应的 default 值有哪些？

`atomic` 控制原子操作，`readwrite` 控制可读可写。当类型为常用数据类型的时候，`assign` 作为数据读取策略，当时自定义 Class 时，使用 `strong`。

 [Objective-C ARC: strong vs retain and weak vs assign](https://stackoverflow.com/questions/8927727/objective-c-arc-strong-vs-retain-and-weak-vs-assign/15541801#15541801)
 
## 10. 用 `@property` 来声明 `NSString` 或（`NSArray`、`NSDictionary`）使用 `copy` 关键字的原因？如果使用 `strong` 会怎么样？
 
 自己曾经写过一篇文章：[从经典问题来看 Copy 方法](http://www.desgard.com/copy/)。
 
 具体原因就是深复制、浅复制的原因。

## 11. `@synthesize` 合成实例变量的规则是什么？假如 `@property` 存在一个名为 `foo` 属性，我在声明一个 `@property` 名为 `_foo`，会导致什么结果？

有两种情况：

1. 如果我显式写明了 `@synthesize` 规则，且 `@synthesize foo = ***`，其中 `**` 不是 `_foo` 那么可以同时存在。
2. 如果未显式写明 `@synthesize` 规则，属性则自动与 `_ + 属性名` 关联成员变量，此时 xcode 会报出 warning ，且不会自动合成新变量。

![](http://p632x9050.bkt.clouddn.com/ch02-1.jpeg)

## 12. 什么情况下不会 autosynthesize ?

1. 重写了 `setter` 和 `getter` 时
2. 重写了只读属性的 `getter` 
3. 使用了 `@dynamic`
4. 在 `@protocol` 中定义了属性
5. 在 `category` 中定义了属性
6. overrite 了属性

这里尤其注意到，当子类重载了父类的属性时，我们需要用 `@synthesize` 来手动合成 `ivar`。



[When should I use @synthesize explicitly?](https://stackoverflow.com/questions/19784454/when-should-i-use-synthesize-explicitly/19821816#19821816)

## 13. ObjC 中向一个对象发送消息 `[obj foo]` 和 `objc_msgSend()` 方法有什么关系？

查阅自己写的博客：

所谓发送消息 `[obj foo]` 在编译后其实就是 `objc_msgSend()` 函数调用。

```Objective-C
id retureValue = [DGObject foo];

// 等效于
id retureValue = objc_msgSend(DGObject, @selector(foo));
```

`objc_msgSend` 方法原型：

```C
void objc_msgSend(id self, SEL cmd, ...)
```

传入 `self` 指针和 `SEL` 选择子即可。

## 14. `unrecognized selector` 异常出现在什么情况下？消息转发的三个阶段？

调用改对象的某个方法却缺少具体的实现的时候。

关于消息转发，自己有两篇博客可以参考：[objc_msgSend消息传递学习笔记 - 对象方法消息传递流程](http://www.desgard.com/objc_msgSend1/)，[objc_msgSend消息传递学习笔记 - 消息转发](http://www.desgard.com/objc_msgSend2/)。

**1. 动态方法解析过程**

当 `objc_msgSend` 无法在 *Fast Map* 中根据选择子查询到对应名称的方法时，进入 `resolveInstanceMethor` 过程。在 `resolveInstanceMethod` 过程中，进入入口方法 `_class_resolveMethod`，先根据 isa 的指向来判断是类方法还是成员方法，从而调用 `resolveInstanceMethod` 或者 `resolveClassMethod`。主体流程中，执行 `lookUpImpOrNil` 方法在方法列表中来搜索 IMP 指针。若仍未命中，继续进入 **备援接受阶段**。

**2. Forwarding 备援接收**

如果选择子未命中，则继续将其传递给其他接受者来处理。这一块代码调用的主要方法为 `forwardingTargetForSelector: (SEL)selector`。在 Core Foundation 中这一方法的实现被隐藏，暴露出来的 `__forwarding__` 的核心方法 `objc_msgSend_stret` 也被隐藏。

**3. Message Dispatch System 消息派发**

在 Forwarding 步骤中最后会创建一个 `NSInvocation` 对象，其中包括了对应的选择子、target 主要参数。倘若自下而上始终没有发现可处理改消息的方式，则调用 `doesNotRecognizeSelector` 从而抛出异常。

![](http://7xwh85.com1.z0.glb.clouddn.com/Desktop.png)

## 15. ObjC 对象的 Memory Layout？与 Swift 的区别？

可参考自己写的博客：[用 isa 承载对象的类信息](http://www.desgard.com/isa/)

[objc4-680.tar.gz]() 版本的 Runtime 中其对象和类的数据结构如下描述：

```c
struct objc_object {
private:
    isa_t isa;
}

struct objc_class : objc_object {
    // Class ISA;
    Class superclass; 	// 父类引用
    cache_t cache;		// 用来缓存指针和虚函数表
    class_data_bits_t bits; // class_rw_t 指针加上 rr/alloc 标志
}
```

`objc_class` 通过继承关系保留 `isa` 指针。联合体 `union isa_t` 的数据结构如下：

```c
union isa_t {
    isa_t() { }
    isa_t(uintptr_t value) : bits(value) { }
    Class cls;
    uintptr_t bits;
    struct {
        uintptr_t indexed           : 1;
        uintptr_t has_assoc         : 1;
        uintptr_t has_cxx_dtor      : 1;
        uintptr_t shiftcls          : 33; 
        uintptr_t magic             : 6;
        uintptr_t weakly_referenced : 1;
        uintptr_t deallocating      : 1;
        uintptr_t has_sidetable_rc  : 1;
        uintptr_t extra_rc          : 19;
    };
};
```


区域名 | 代表信息
---|---
indexed| 0 表示普通的 isa 指针，1 表示使用优化，存储引用计数
has_assoc | 表示该对象是否包含 associated object，如果没有，则析构时会更快
has_cxx_dtor |	表示该对象是否有 C++ 或 ARC 的析构函数，如果没有，则析构时更快
shiftcls|	类的指针
magic	|固定值，用于在调试时分辨对象是否未完成初始化
weakly_referenced|	表示该对象是否有过 weak 对象，如果没有，则析构时更快
deallocating|	表示该对象是否正在析构
has_sidetable_rc|	表示该对象的引用计数值是否过大无法存储在 isa 指针
extra_rc|	存储引用计数值减一后的结果

给出 `class` 和 `metaclass` 的关系图：

![](http://7xwh85.com1.z0.glb.clouddn.com/isa_metaclass.png)

## 16. ObjC 对象的 `isa` 指针指向什么？有什么作用？

* 普通成员对象的 isa 指针指向其类对象
* 类对象 isa 指针指向其 metaClass
* metaClass 的 isa 指针有两种情况：
    1. `NSObject` 类对象的 isa 指向自己形成自环；
    2. 非 `NSObject` 类对象的 isa 指向其 `superclass` 类对象。

`isa` 指针作用，用于消息转发自上而下的查询流程，用于 Memory Layout 中的访问初始化操作，将所有的 Class 由于 `NSObject` 的存在而组织成图形链式结构，更加灵活。

## 17. 关于 `self` 和 `super` 关键字的理解？

下面的代码输出结果是什么？

```Objective-C
@implementation Son : Father
- (id)init
{
   self = [super init];
   if (self) {
       NSLog(@"%@", NSStringFromClass([self class]));
       NSLog(@"%@", NSStringFromClass([super class]));
   }
   return self;
}
@end
```

最终会输出：

```shell
NSStringFromClass([self class]) = Son
NSStringFromClass([super class]) = Son
```

`self` 是指向当前类实例的一个指针，但是 `super` 却是一个 *Magic Keyword*，他是一个编译器的标识符，与 `self` 指向同一消息接受者。`super` 关键字会告诉编译器当执行 `[super class]` 代码的时候，从父类发方法中执行，但是消息的对象都是 `Son *obj` 这个实例，在转换方法的时候同样的都会变成：

```Objective-C
objc_msgSend(obj, @selector(class));
```

## 18. `_objc_msgForward` 函数是做什么的，直接调用它将会发生什么？

`_objc_msgForward` 消息转发过程会涉及到以下方法：

1. `resolveInstanceMethod:` 方法 (或 `resolveClassMethod:`)。

    允许用户在此时为该 Class 动态添加实现。如果有实现了，则调用并返回YES，那么重新开始 `objc_msgSend` 流程。这一次对象会响应这个选择器，一般是因为它已经调用过 `class_addMethod`。如果仍没实现，继续下面的动作。

2. `forwardingTargetForSelector:` 方法

    尝试找到一个能响应该消息的对象。如果获取到，则直接把消息转发给它，返回非 `nil` 对象。否则返回 `nil` ，继续下面的动作。注意，这里不要返回 `self` ，否则会形成死循环。

3. `methodSignatureForSelector:` 方法

    调用 `methodSignatureForSelector:` 方法，尝试获得一个方法签名。如果获取不到，则直接调用 `doesNotRecognizeSelector` 抛出异常。如果能获取，则返回非 `nil`：创建一个 `NSlnvocation` 并传给 `forwardInvocation:`。

4. `forwardInvocation:` 方法

    将第3步获取到的方法签名包装成 `Invocation` 传入，如何处理就在这里面了，并返回非 `nil`。

5. `doesNotRecognizeSelector:` 方法

    默认的实现是抛出异常。如果第3步没能获得一个方法签名，执行该步骤。

## 19. RunLoop 和线程的关系？

RunLoop 是依托于线程存在的，每个线程，包括主线程都有对应的 RunLoop。除了主线程以外，其他线程的 RunLoop 都需要手动调用 Run 方法来开启，当线程结束后，RunLoop 由线程自行释放。

## 20. RunLoop 的 Mode 作用。

Mode 用来控制事件在运行循环中的优先级，分成以下几种：

* `NSRunLoopCommonModes`：一组 RunLoop Mode 集合，它包括了 `NSDefaultRunLoopMode`、`NSTaskDeathCheckMode`、`UITrackingRunLoopMode`，可以使用 `CFRunLoopAddCommonMode` 向其中添加自定义 Mode。
    * `NSDefaultRunLoopMode`（`kCFRunLoopDefaultMode`）：默认 Mode，空闲状态；
    * `NSTaskDeathCheckMode`
    * `UITrackingRunLoopMode`
* `UITrackingRunLoopMode`（`ScrollView`）滑动交互状态；
* `UIInitializationRunLoopMode`：启动状态；
* `NSConnectionReplyMode`：监听 `NSConnection` 对象状态；
* `NSModalPanelRunLoopMode`：在 Model Panel 情况下区分事件（macOS 开发）；
* `GSEventReceiveRunLoopMode`：接受系统时间，私有类；

开放的 RunLoop 只有以下两种：

* NSDefaultRunLoopMode（kCFRunLoopDefaultMode）
* NSRunLoopCommonModes（kCFRunLoopCommonModes）

## 21. 当 Timer 以 `+ scheduledTimerWithTimeInterval` 的方式触发，当回调中定时改变 cell 中的视图，在滑动过程中发现 Timer 会暂停调用的原因是什么？如何解决？

RunLoop 只能运行在一种 Mode 下，如果要更换 Mode，当前的 Loop 也需要停下重启新的。根据 RunLoop 这个机制，ScrollView 在滑动过程中，`NSDefaultRunLoopMode` 的 Mode 或切换到 `UITrackingRunLoopMode` 来保证 ScrollView 的流畅滑动；此时会停止 `NSDefaultRunLoopMode` 的运行，从而 `NSTimer` 会停止触发。

解决方案：
* 第一种是更换 `NSTimer` 的 Mode

```Objective-C
[[NSRunLoop currentRunLoop] addTimer: self.timer forMode: NSRunLoopCommonModes];
```

* 或者开启一个新的线程，让 Timer 在新的线程中构造，这样 `NSTimer` 由子线程的 RunLoop 处理。

```Objective-C
[NSTread detachNewThreadSelector: @selector(timerFunction) toTarget: self withObject: nil];

- (void)timerFunction {
    self.timer = [NSTimer scheduledTimerWithTimerInterval: 1 target: self selector: @selector(updateTimer) userInfo: nil repeats: YES];
    [[NSRunLoop currentRunLoop] addTimer: self.timer forMode: NSDefaultRunLoopMode];
    // 非 main 线程 RunLoop 需要手动调用 run 方法
    [[NSRunLoop currentRunLoop] run];
}
```

## 22. RunLoop 和 RunLoopMode 的数据结构分别是怎样的？

推荐一下 ibireme 大佬的 [深入理解RunLoop](https://blog.ibireme.com/2015/05/18/runloop/#base)

数据结构给出 Core Foundation 中的定义：

```Objective-C
struct __CFRunLoopMode {
    CFStringRef _name;            // Mode Name, 例如 @"kCFRunLoopDefaultMode"
    CFMutableSetRef _sources0;    // Set
    CFMutableSetRef _sources1;    // Set
    CFMutableArrayRef _observers; // Array
    CFMutableArrayRef _timers;    // Array
    ...
};
 
struct __CFRunLoop {
    CFMutableSetRef _commonModes;     // Set
    CFMutableSetRef _commonModeItems; // Set<Source/Observer/Timer>
    CFRunLoopModeRef _currentMode;    // Current Runloop Mode
    CFMutableSetRef _modes;           // Set
    ...
};
```

记住一个 Mode 中，有“一个名字、两个源头、一个观察组和一个计时组”。一个 RunLoop 包含若干个 Mode，每个 Mode 又包含了若干个 Source/Timer/Observer。每次调用 RunLoop 主函数时，只能指定其中一个 Mode，这个 Mode 被称作 `CurrentMode`。如果需要切换 Mode，只能先退出当前的 Loop，再指定一个 Mode 重新进入，这样就可以分隔不同组的 Source/Timer/Observer，使其互不影响。

另外需要介绍这些关于 RunLoop 的引用：

* **CFRunLoopSourceRef**：是事件产生的地方。Source 分为 Source0 和 Sourcs1。
    * Source0：只包含了一个回调指针，不能主动触发事件。使用时需要先调用 `CFRunLoopSourceSignal` 将这个 Source 标记为待处理，然后手动调用 `CFRunLoopWakeUp` 来唤醒 RunLoop 处理该事件。
    * Source1：包含了一个 **mach_port** 和一个回调指针，被用于通过内核和其他线程相互发送消息。这种 Source 能主动唤醒 RunLoop 线程。
* **CFRunLoopTimerRef**：是基于事件的触发器，它和 `NSTimer` 是 `toll-free bridge` 的，可以混用。其中包括一个时间记录和一个回调指针。当加入到 RunLoop 时，RunLoop 会注册对应的时间点，当时间点到达的时候，RunLoop 会被唤醒来执行回调。
* **CFRunLoopObserverRef**：针对于 Observer 的一个引用，包含一个回调指针，状态发生变化时，Observer 能通过回调接受到变化。观测的状态通过枚举方式定义了一个状态机：

```Objective-C
typedef CF_OPTIONS(CFOptionFlags, CFRunLoopActivity) {
    kCFRunLoopEntry         = (1UL << 0), // 即将进入Loop
    kCFRunLoopBeforeTimers  = (1UL << 1), // 即将处理 Timer
    kCFRunLoopBeforeSources = (1UL << 2), // 即将处理 Source
    kCFRunLoopBeforeWaiting = (1UL << 5), // 即将进入休眠
    kCFRunLoopAfterWaiting  = (1UL << 6), // 刚从休眠中唤醒
    kCFRunLoopExit          = (1UL << 7), // 即将退出Loop
};
```

Source/Timer/Observer 统称为 ModeItem，一个 item 可以同时加入多个 mode ，但是 item 被重复加入同一个 Mode 是不会有效果的。这也符合数据结构中 `set` 的定义。如果 Mode 中的一个 Item 都没有，RunLoop 则会在该线程中直接退出，这也就是非主线程在构造后需要手动添加 Mode 并显示调用 Run 的原因。

## 23. 大概叙述一下 RunLoop 的整体流程？

继续推荐 ibireme 大佬的 [深入理解RunLoop](https://blog.ibireme.com/2015/05/18/runloop/#base)

![](https://blog.ibireme.com/wp-content/uploads/2015/05/RunLoop_1.png)

## 24. AutoreleasePool 是如何通过 RunLoop 工作的？

App 启动后，Core Foundation 会在主线程的 RunLoop 中注册两个 Observer，其回调都是 `_wrapRunLoopWithAutoreleasePoolHandler`。

第一个 Observer 监听的时间是 Entry，内部会调用 `_objc_autoreleasePoolPop()` 和 `_objc_autoreleasePoolPush()`，用来释放旧池创建新池。且优先级最高，保证重置操作在所有回调之前。

第二个 Observer 监听来能个事件，BeforeWaiting 时调用 `_objc_autoreleasePoolPop()` 和 `_objc_autoreleasePoolPush()` 用来重置释放池。且优先级最低，保证重置操作在所有回调之后。

主线程中也是如此，所以不用显式创建 `AutoreleasePool` 了。

## 25. `NSTimer`、`CADisplayLink` 是如何通过 RunLoop 工作的？

`NSTimer` 前文讲到就是 `CFRunLoopTimerRef`，他们是 TFB 桥接的。一个 `NSTimer` 注册到 RunLoop 后，会添加一个时间点记录。当事件到达且误差不超出 Tolerance （误差范围度），则执行 `CFRunLoopTimerRef` 中的回调方法。

`CADisplayLink` 是一个和屏幕刷新频率一致的定时器，其原理不尽相同。（暂时没有做过多的分析，但是知道内部操作了一个 Source。后续如果有机会代码分析可以补充上。）

## 26. 不手动指定 `autoreleasePool` 的前提下，一个 `autorelease` 对象在什么时候释放？例如在一个 `ViewController` 中的 `viewDidLoad` 中创建。

分两种情况：**手动干预释放时机**、**系统自动释放**。

1. 手动干预释放时机：指定 `autoreleasePool`，就是花括号作用域指定。
2. 系统自动释放。


由于 `autoreleasePool` 对象是加入 RunLoop 来进行重置的，从 App 启动到加载是完整的 RunLoop，然后会停下来等待用户交互，用户的每一次交互都会启动一次 RunLoop 来处理用户所有的点击事件和触摸事件。

当 `autoreleasePool` 销毁的同时，会将池内所有的对象发送 `release` 消息，此时会释放其中所有对象。如果在 `viewDidLoad` 中创建释放池，并添加对象，那么其将会在 `viewDidAppear` 方法执行前被销毁。

## 27. `autoreleasePool` 数据结构是怎样的？是如何管理和调度的？

推荐博客 [自动释放池的前世今生 - 深入解析 autoreleasepool](https://draveness.me/autoreleasepool)

`autoreleasePool` 是由多个 `AutoreleasePoolPage` 组成的一个链式线性表，这里我们来关注一下 `AutoreleasePoolPage` 的结构：

```C++
class AutoreleasePoolPage {
    magic_t const magic;        // 用于对当前 AutoreleasePoolPage 完整性校验
    id *next;                   // 指向下一节点
    pthread_t const thread;     // 保存了当前所在页的线程
    AutoreleasePoolPage * const parent; // 父亲节点
    AutoreleasePoolPage *child;         // 孩子节点
    uint32_t const depth;               
    uint32_t hiwat;
};
```

每一个 `AutoreleasePoolPage` 大小均为 `4096` 字节（`0x1000`）。

![](http://p632x9050.bkt.clouddn.com/ch03-1.png)

 每个 `AutoreleasePoolPage` 在内存栈中的图示：
 
 ![](http://p632x9050.bkt.clouddn.com/ch03-2.png)
 
 `POOL_SENTINEL` 是*哨兵对象*，是 `nil` 的宏。在每个 `AutoreleasePage` 被 `objc_autoreleasePoolPush` 的时候，会把一个 `POOL_SENTINEL` 添加到释放池的顶端。这个哨兵就好比是每个池的边界节点，当 `objc_autoreleasePoolPop` 调用时，就会像池中对象发送 `release` 消息，直到访问到第一个 `POOL_SENTINEL` 从而停止 `release` 操作。调度方面除了 `push`、`pop` 常规操作，还有 *满页情况(autoreleaseFullPage)* 和 *无页情况(autoreleaseNoPage)* 的处理，都很常规。
 
## 28. GCD 的队列(`dispatch_queue_t`) 分成哪两种？
 
 根据类型分成：
 
 1. 串行队列 - Serial Dispatch Queue：同一时间队列中只有一个任务在执行，每个任务只有在前一个任务执行完成后才能开始执行；
 2. 并行队列 - Concureent Dispatch Queue：这些任务会按照被添加的顺序开始执行。但是可以任意顺序。
 
根据作用特点划分：
1. 全局队列（属于并行队列）：不能与 barrier 栅栏搭配使用，barrier 只能与自定义的并行队列一起使用，否则 barrier 无法达到预期效果；如果和串行队列和 global 队列一起使用，barrier 表现与 `dispatch_sync` 一样。
2. 主队列（属于串行队列）：不能与 sync 同步方法一起使用，会造成死锁。

## 29. 如何用 GCD 同步若干个异步调用？

使用 Dispatch Group 来组合多个异步调用，然后通过 `dispatch_group_notify` 来获取所有异步调用的状态。例如用 URL 来加载多张图片，在下载完成后处理多张图片的代码示例：


```Objective-C
dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
dispatch_group_t group = dispatch_group_create();
dispatch_group_async(group, queue, ^{ 
    // 加载图片一
});
dispatch_group_async(group, queue, ^{
    // 加载图片二
});
dispatch_group_async(group, queue, ^{
    // 加载图片三
});
dispatch_group_notify(group, dispatch_get_main_queue(), ^{
    // 处理图片
});
```

## 30. `dispatch_barrier_async` 的作用是什么？

在并行队列中，为了保证某些任务的顺序，需要等待一些任务完成后才能继续进行，使用 `barrier` 来等待之前任务完成，避免数据竞争等问题。`dispatch_barrier_async` 方法会的姑娘爱追加到 Concurrent Dispatch Queue 并行队列中的操作全部执行完成之后，在执行 `dispatch_barrier_async` 中的处理，在 `dispatch_barrier_async` 完成之后，并行队列恢复动作继续执行。

## 31. 如何手动触发一个 value 的 KVO 回调？

KVO 依赖于观察 `NSObject` 的两个方法：`willChangeValueForKey:` 和 `didChangeValueForKey`。这两个方法会在 `setter` 的赋值前后自动启用，没有必要显式写出。

```Objective-C
- (void)viewDidLoad {
   [super viewDidLoad];
   _now = [NSDate date];
   [self addObserver:self forKeyPath:@"now" options:NSKeyValueObservingOptionNew context:nil];
   NSLog(@"1");
   [self willChangeValueForKey:@"now"]; // “手动触发self.now的KVO”，必写。
   NSLog(@"2");
   [self didChangeValueForKey:@"now"]; // “手动触发self.now的KVO”，必写。
   NSLog(@"4");
}
```

## 32. KVO 实现原理？

先给出 KVO 官方的[解释文档](https://developer.apple.com/library/content/documentation/Cocoa/Conceptual/KeyValueObserving/Articles/KVOImplementation.html)。

关键词：**isa-swizzling**。当我对一个对象进行观察时，这时候 Runtime 会动态创建一个类，这个类继承自该对象自身的类，并重写了所要观察属性的 `setter` 方法。它会在赋值前后增加通知方法 `willChangeValueForKey` 和 `didChangevlueForKey`，然后原先对象的 isa 指针指向新创建的类。

这里有一篇 *mikeash* 大神的[经典文章](https://www.mikeash.com/pyblog/friday-qa-2009-01-23.html)可以参考一下。

## 33.  `IBOutlet` 连出来的视图属性为什么可以被设置成 `weak`?

因为 `xib` 或者 `storyboard` 中的视图，可以当做对应的 View 已经有了一个强引用持有，所以只需要设置这个持有指针为 `weak` 即可。

另外，在 `storyboard` 中创建的 ViewController（xib 是不存在的），会同时生成一个 `_topLevelObjectsToKeepAliveFromStoryboard` 的私有数组来强引用所有的 Top Level 对象，所以再拖出 `IBOutlet` 的声明可以声明成 weak。

## 34. Clang Extension 对于 Block 的扩展，其数据结构是怎样的？

一个 Block 在通过 `clang -rewrite-objc` 重写后，会变成以下数据结构：

```c
struct __block_impl {
	void *isa;
	int Flags;
	int Reserved;
	void *FuncPtr;
};

struct __outside_block_impl_0 {
    struct __block_impl impl;
    struct __outside_block_desc_0* Desc;
    __outside_block_impl_0(void *fp, struct __outside_block_desc_0 *desc, int flags=0) {
        impl.isa = &_NSConcreteGlobalBlock;
        impl.Flags = flags;
        impl.FuncPtr = fp;
        Desc = desc;
    }
};

static struct __outside_block_desc_0 {
	size_t reserved;
	size_t Block_size;
} __outside_block_desc_0_DATA = { 
	0, 
	sizeof(struct __outside_block_impl_0)
};

static void __outside_block_func_0(struct __outside_block_impl_0 *__cself) {
    /* block 的执行体 */
}
```

* **isa**：会指向一种 Block 类对象。在非 GC 的模式下只有三种：`_NSConcreteStackBlock`、`_NSConcreteGlobalBlock`、`_NSConcreteMallocBlock`。
* **Flags**：Block 的负载信息（引用计数和类型信息），按二进制位来存储。
* **Reserved**：保留字段。
* **FuncPtr**：指向 Block 执行体的指针。

## 35. Objective-C 中 Block 有几种类型？出现情况时什么样的？

* **_NSConcreteStackBlock**：栈类型，所处地址单元位于高地址。一般情况下声明的 Block 均为该类型，内部引入外部变量但不做赋值操作。
* **_NSConcreteMallocBlock**：堆类型。若需截获 `__block` 变量，改类型 Block 将出现。通过将对象和 Block 复制到堆上，保证了 `__block` 变量的作用域可扩展到 Block 内。这时会使用 `__forwarding` 指针来做关联，示意图如下。
* **_NSConcreteGloalBlock**：全局类型，所处地址单元位于低地址。两种出现时机：1. 在全局变量位置声明；2. Block 中不引入外部变量。

![](http://p632x9050.bkt.clouddn.com/ch04-1.png)

