import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class CreateTable {
    public static void main(String[] args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum","192.168.0.243");  //hbase 服务地址
        config.set("hbase.zookeeper.property.clientPort","12181"); //端口号


        TableName tableName = TableName.valueOf("test-table");

        Admin admin = ConnectionFactory.createConnection(config).getAdmin();
        if(admin != null){
            try {
                if (admin.tableExists(tableName)) {// 如果存在要创建的表，那么先删除，再创建 
                    admin.disableTable(tableName);
                    admin.deleteTable(tableName);
                    System.out.println(tableName + " is exist,detele....");
                }
                HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
                tableDescriptor.addFamily(new HColumnDescriptor("column1"));
                tableDescriptor.addFamily(new HColumnDescriptor("column2"));
                tableDescriptor.addFamily(new HColumnDescriptor("column3"));
                admin.createTable(tableDescriptor);

                if (admin.tableExists(tableName)) {// 如果存在要创建的表，那么先删除，再创建
                    System.out.println(tableName + " is created");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
