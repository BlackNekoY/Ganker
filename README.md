## Ganker
提供每日干货(妹纸)的App，算是工作之余熟悉一下主流的开源库的使用和material design的设计。
目前目前项目仍在开发中。

### 为什么叫Ganker
- **来自gank.io的gank**
- **我曾经是Dota玩家，gank即抓人**
- **Ganker不就是抓人的人了吗**
- **听起来很炫酷是吧(笑)**

### 所有数据以及Api均来自[干货集中营](http://gank.io/)。


由于本人设计能力有限，界面的设计主要参考以下两位的gank客户端

@darkeet(https://github.com/drakeet/Meizhi)

@zzhoujay(https://github.com/zzhoujay/Gank4Android)


###Libraries   
* [RxJava](https://github.com/ReactiveX/RxJava) 
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [EventBus](https://github.com/greenrobot/EventBus)
* [Retrofit](https://github.com/square/retrofit)
* [Gson](https://github.com/google/gson)
* [Glide](https://github.com/bumptech/glide)
* [PhotoView](https://github.com/chrisbanes/PhotoView)
* [GreenDao](https://github.com/greenrobot/greenDAO)
* [Multitype](https://github.com/drakeet/MultiType)

###Design Pattern
项目采用MVP设计，复杂的逻辑放入Presenter中，使用RxJava异步完成，然后通知View层刷新UI。

###一些很有用的资料
[给Android开发者的RxJava详解](http://gank.io/post/560e15be2dca930e00da1083)

[RxJava与Retrofit 结合的最佳实践](http://gank.io/post/56e80c2c677659311bed9841)

[Material design使用入门](http://www.open-open.com/lib/view/open1436152483833.html#_label0)


