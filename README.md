# ordering-system
点餐系统，买家服务端

### 项目源码均已添加注解
项目源码包简介：
1. config：微信支付、授权相关的配置，其中，微信授权，使用的是 weixin-java-tools 微信支付，使用的是 best-pay-sdk

2. controller：分为订单、支付、授权、商品四个controller，包括：商品查询、微信授权登录、微信支付、创建订单等API接口

3. converter：项目中在service之间传递的对象统一定义了一个OrderDTO，所以需要参数转换。一个是 表单转换，一个是数据库原始对象转换。

4. dataobjects： 项目中使用到的pojo，分别对应数据库中的表

5. enums： 枚举包，这里统一定义了项目中需要使用到的枚举常量，包括订单状态、支付状态、商品状态和最后返回给前端的状态

6. exception： 异常，定义了项目中统一抛出的异常

7. form： 前端创建订单，传过来的json数据，使用OrderForm接收

8. handle： 统一异常处理器，处理sellException异常和未知异常

9. repository： spring-data-jpa 使用到的repository，相当于dao层

10. service： 这里对应四种controller，分别为四种service

11. utils： 这里放了项目中的工具类，分别是 生成id工具类、两个数据对比工具类、创建返回对象工具类，其中：serializer包下为发送给前端的日期格式处理器

12. VO： VO包下为返回给前端的json数据对应的多层对象

