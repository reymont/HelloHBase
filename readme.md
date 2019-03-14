## 0. 基于 hbase 2.1.2 版本写的demo

## 1. 创建mvn项目

mvn archetype:generate -DgroupId=com.cmi -DartifactId=HelloHBase \
  -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false


## 2. 配置pom.xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>hcg</groupId>
    <artifactId>hcg</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <hbase.version>2.1.2</hbase.version>
    </properties>

    <dependencies>
        <!-- HBase -->
        <dependency>
            <groupId>org.apache.hbase</groupId>
            <artifactId>hbase-client</artifactId>
            <version>${hbase.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.hbase</groupId>
            <artifactId>hbase-common</artifactId>
            <version>${hbase.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.hbase</groupId>
            <artifactId>hbase-server</artifactId>
            <version>${hbase.version}</version>
        </dependency>

    </dependencies>

</project>
```

## 3. 本地配置hosts

C:\Windows\System32\drivers\etc\hosts

```conf
192.168.0.243 hbae
```

## 4. 在hbase新建测试表


```sh
hbase shell
### 1. 创建有一个列族的表mytable
create 'mytable', 'cf'
### 2. 写数据
# 在mytable表的first行中cf:message列对应的数据单元中插入字节数组hello HBase
put 'mytable', 'first', 'cf:message', 'hello HBase'
put 'mytable', 'second', 'cf:foo', 0x0
put 'mytable', 'third', 'cf:bar', 3.14159
```

## 5. java client 列举表

```java
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

public class HBaseTest {
    public static void main(String[] args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum","192.168.0.243");  //hbase 服务地址
        config.set("hbase.zookeeper.property.clientPort","12181"); //端口号

        HBaseAdmin.available(config);

        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();
        //Admin admin = ConnectionFactory.createConnection(configuration).getAdmin();
        if(admin != null){
            try {
                //获取到数据库所有表信息
                HTableDescriptor[] allTable = admin.listTables();
                for (HTableDescriptor hTableDescriptor : allTable) {
                    System.out.println(hTableDescriptor.getNameAsString());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

```


## 参考

1. [JAVA 远程连接HBase数据库所遇到的坑](https://blog.csdn.net/ycf921244819/article/details/81706119)
2. https://github.com/JasonBabylon/hbase-operations-with-java.git