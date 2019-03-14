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
