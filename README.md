## Everything
### 1.简介
+ 仿照Everything桌面工具，基于Java语言开发的命令行文件搜索工具
### 2.背景
+ 有时候在windows命令行下需要查询一些文件，由于for命令并不如Linux下的find命令好用，所以DIY开发一款命令  行工具，用来实现Windows命令行中搜索文件。
### 3.意义
+ 解决Windows命令行下文件搜索问题
+ 基于Java开发的工具可以在Windows和Linux平台上无差异使用锻炼编码能力
### 4.功能


备注：不建议采用MySQL存储
### 5.技术
+ Java（文件操作）
+ Database（嵌入式H2数据库或者MySQL数据库）
+ JDBC
+ Lombok库（IDEA安装Lombok插件） Java多线程
+ 文件系统监控（Apache Commons IO）

### 6.实现

#### 6.1索引
+ 指定目录建立索引
+ 指定目录排查建立索引（一些不经常搜索的目录，比如：windows系统的目录）
#### 6.2存储
+ 嵌入式数据库H2（优势是可以随着程序一起发布）
+ MySQL数据库（数据集中管理，分布式发布，安全性高）
#### 6.3检索
+ 根据条件检索内容
+ 检索内容后置过滤（由于只监控新增，因此检索后内容如果不存在，做清理操作）
#### 6.4监控
+ 指定目录的文件新增（考虑整个文件系统做监控，效率太低，只监控自定义的目录）
#### 6.5交互
+ 程序入口解析和配置
+ 交互式执行：帮助(help),索引(index),检索(search)
### 7.用法
#### 7.1使用

+ java -jar	everything-1.0.0-cmd.jar args

args：
+ --rebuildIndex=true/false : true表示重建索引，false表示不重建索引
+ --maxReturnFile=30 : 最大返回的文件数量
+ --indexPaths=file path : 索引的文件路径
+ --excludePaths=file path : 排除的文件路径
+ --depthAsc=true/false : true安装文件层级深度升序，false安装文件层级深度降序
#### 7.2命令

>>help
命令列表： 退出：quit 帮助：help 索引：index
搜索：search <name> [<file-Type> img | doc | bin | archive | other]

#### 7.3搜索
```
欢迎使用，Everything
>>search java
D:\ repository\javax D:\ repository\java3d
D:\ download\app\java2-httpd-1.0.0.jar E:\worskpace\github\java2-httpd E:\worskpace\github\java2-httpd-blog E:\worskpace\github\java2-httpd-blog.zip D:\ repository\net\java E:\worskpace\github\java2-httpd.zip E:\worskpace\gitee\java-httpd E:\worskpace\github\java-httpd.zip more...
```
### 8.测试
#### 机器信息
+ 处理器：Intel(R) Core(TM) i5-8250U CPU @1.60GHZ 1.80GHZ
+ 内存：8.00G 磁盘：SSD
#### 软件信息
+ H2嵌入式文件存储，版本1.4.196
+ 测试数据
+ 文件系统文件数: 693726
+ 索引效率



#### 结论：
+ 有索引会影响数据的写入检索效率
+ 有索引检索效率有较大幅度的提升
### 9.扩展
检索文件：项目中检索文件是采用文件名的后模糊匹配

+ 在输入时增加自动完成
+ 在输入拼音是也可以进行搜索
+ 最近检索：项目扩展点，可以增加一个如history的命令用来查看最近检索信息
内置JRE发布即可使用
