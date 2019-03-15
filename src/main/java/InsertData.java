import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

public class InsertData {
    public static void main(String[] args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum","192.168.0.243");  //hbase 服务地址
        config.set("hbase.zookeeper.property.clientPort","12181"); //端口号


        TableName tableName = TableName.valueOf("test-table");

        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();
        if(admin != null){
            try {
                System.out.println("start insert data ......");
                HTable table = (HTable) connection.getTable(tableName);
                Put put = new Put("112233bbbcccc".getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值
                put.addColumn("column1".getBytes(), null, "aaa".getBytes());// 本行数据的第一列
                put.addColumn("column2".getBytes(), null, "bbb".getBytes());// 本行数据的第三列
                put.addColumn("column3".getBytes(), null, "ccc".getBytes());// 本行数据的第三列

                try {
                    table.put(put);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("end insert data ......");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
