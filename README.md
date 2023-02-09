# Traditional-Chinese-Medicine
基于SpringBoot+Vue的中医在线诊断与药方推荐平台 使用ElasticSearch、Redis、RabbitMQ等分布式框架，使用文本相似度算法进行药方推荐

后端采用模块化开发，将邮箱服务，实体类以及业务服务分离，便于后期搭建微服务

系统分为患者端和医生端。患者端进行通过对病情描述以及上传舌苔图片进行病历提交、查找医生信息、查看诊断状态、发邮箱提醒医生以及对诊断结果进行评论打分等；医生端可以查看病历并进行诊断，在诊断的过程中，根据诊断结果生成药方的推荐列表，供医生参考、对病人的提问进行回复以及对诊断结果进行实时修改、系统提供中医搜搜功能（已申请软著），医生可以通过中医搜搜搜索引擎对药方和药材进行搜索（数据由山东农业大学大数据实验室提供），使用ElasticSearch提供查询速度提高了效率。系统采用RestFul的方式发送请求。

### 患者端

#### 登陆注册

注册时使用RabbitMQ发送邮箱验证

![](https://pic.imgdb.cn/item/63e504174757feff33c0a02d.png)

![](https://pic.imgdb.cn/item/63e504174757feff33c0a054.png)

![](https://pic.imgdb.cn/item/63e505064757feff33c24957.png)

###### 提示激活邮箱

![](https://pic.imgdb.cn/item/63e505064757feff33c249a8.png)

###### 邮箱激活信息展示

![](https://pic.imgdb.cn/item/63e505694757feff33c31e1d.png)

![](https://pic.imgdb.cn/item/63e505694757feff33c31e26.png)

![](https://pic.imgdb.cn/item/63e505694757feff33c31e80.png)

#### 首页展示

![](https://pic.imgdb.cn/item/63e504174757feff33c0a089.png)

#### 通过对病情描述以及上传舌苔图片进行病历提交

![](https://pic.imgdb.cn/item/63e5045a4757feff33c118d4.png)

#### 查找医生信息

根据地区，城市对医生进行模糊查询

![](https://pic.imgdb.cn/item/63e5045a4757feff33c11901.png)

#### 查看诊断信息

![](https://pic.imgdb.cn/item/63e5045a4757feff33c1190f.png)

##### 发邮箱提醒医生

同样使用RabbitMQ进行邮箱发送

![](https://pic.imgdb.cn/item/63e5045a4757feff33c1193d.png)

##### 对诊断结果进行评论以及打分

![](https://pic.imgdb.cn/item/63e504aa4757feff33c1a5d0.png)

![](https://pic.imgdb.cn/item/63e504aa4757feff33c1a5da.png)

### 医生端

#### 登陆注册

![](https://pic.imgdb.cn/item/63e503a24757feff33bfd291.png)

![](https://pic.imgdb.cn/item/63e503a24757feff33bfd29e.png)

#### 首页展示

![](https://pic.imgdb.cn/item/63e503a24757feff33bfd305.png)

#### 病历诊断

##### 病历以及回复提醒

![](https://pic.imgdb.cn/item/63e505064757feff33c24947.png)

##### 病历信息查看

![](https://pic.imgdb.cn/item/63e503a24757feff33bfd330.png)

##### 根据诊断结果生成推荐列表

![](https://pic.imgdb.cn/item/63e503a24757feff33bfd367.png)

![](https://pic.imgdb.cn/item/63e504174757feff33c0a009.png)

###### 对诊断结果进行修改

![](https://pic.imgdb.cn/item/63e504ab4757feff33c1a60b.png)

![](https://pic.imgdb.cn/item/63e504ab4757feff33c1a656.png)

##### 对病人的提问进行回复

![](https://pic.imgdb.cn/item/63e504174757feff33c0a00e.png)

#### 中医搜搜

使用ElasticSearch搜索引擎

![](https://pic.imgdb.cn/item/63e505694757feff33c31e9d.png)

![](https://pic.imgdb.cn/item/63e504ab4757feff33c1a689.png)

![](https://pic.imgdb.cn/item/63e505064757feff33c248b1.png)

##### 药材药方详情

![](https://pic.imgdb.cn/item/63e505064757feff33c248fd.png)

需要先将数据库中的信息通过索引批量插入到ES中

![](https://pic.imgdb.cn/item/63e506024757feff33c41ad1.png)

此处分页的地方遇到了一些bug,ES的分页和maven中添加的分页依赖使用不太一样

![](https://pic.imgdb.cn/item/63e506ca4757feff33c56233.png)

##### 个人信息修改

![](https://pic.imgdb.cn/item/63e505064757feff33c24947.png)
