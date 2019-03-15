import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class InsertFamilyData {
    public static void main(String[] args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum","192.168.0.243");  //hbase 服务地址
        config.set("hbase.zookeeper.property.clientPort","12181"); //端口号


        TableName tableName = TableName.valueOf("test-table");

        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();

//        HTableDescriptor descriptor = new HTableDescriptor(admin.getTableDescriptor(tableName));
//        descriptor.addFamily(new HColumnDescriptor(Bytes.toBytes("info")));
//        admin.disableTable(tableName);
//        admin.modifyTable(tableName, descriptor);
//        admin.enableTable(tableName);

        if(admin != null){
            try {
                System.out.println("start insert data ......");
                HTable table = (HTable) connection.getTable(tableName);

                Put put = new Put(Bytes.toBytes("TheRealMT"));
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("Mark Twain"));
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("email"), Bytes.toBytes("samuel@clemens.org"));
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("password"), Bytes.toBytes("abc123"));

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
