package servlet;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CsvToEs {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectEs.class);
    private  static String index;
    private  static  String type;

    /**
     * index:想要创建的索引名（相当与数据库里的数据库名）
     * type:想要创建的类型名（相当于数据库里的表）
     * @param fields
     * @param file
     */
    public void csvToES(String[] fields, File []file) {
        index = "my_index1";
        type="test";

        TransportClient client = ConnectEs.getClient();
        int idNum = 0;
        //int error = 0;


        // 一、读取文件
            // 二、开始一行一行的写
        for (File f:file) {

            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                Map<String, Object> valuesMap = new HashMap<String, Object>();
                // 跳过文件第一行
                System.out.println("略过文件第一行==========" + br.readLine());
                String line;
                while ((line = br.readLine()) != null) {
                    String[] filedValues = line.split(",");
                    for (int filedNum = 0; filedNum < filedValues.length; filedNum++) {
                        valuesMap.put(fields[filedNum], filedValues[filedNum]);
                    }
                    // 导入数据有很多种方式，比如：1.手动构建JSON风格的字符串；2.使用map；3.使用JackSon等工具包序列化Beans；
                    // 3.使用ES的XContentBuilder ；4.BulkRequestBuilder  ;
                    // 5.https://www.elastic.co/guide/en/elasticsearch/client/java-api/6.1/java-docs-index.html#java-docs-index
                    bulkRequest.add(client.prepareIndex(index, type, Integer.toString(idNum)).setSource(valuesMap));
                    valuesMap.clear();

                    if (idNum % 2000 == 0) {
                        System.out.println("++++++++++++++++++++++++++++++++++++");
                        // 三、批量导入
                        bulkRequest.get();
                        // 与上面的语法效果相同：bulkRequest.execute().actionGet();
                    }
                    idNum++;
                }
                // 四、导入每个文件最后不足2000条的数据（但是必须得有数据，否则报错: no requests added）
                // 查阅源码可知，可通过拿到父类requests属性，判断其size来解决[子类调用父类方法获得属性]
                if (bulkRequest.request().requests().size() > 0) {
                    bulkRequest.get();
                }
                // 五、操作下一个文件
            } catch (IOException e) {
                LOGGER.error("：ERROR:堆栈信息====={}", e.getMessage());
            }
        }

        client.close();
    }


    public static void main(String[] args) {

        /*导入ES示例*/
        CsvToEs csvToES = new CsvToEs();
        String[] fields = {"timestamp","value","label","KpiId"};
        /*File file = new File("/Volumes/F/实训/数据集");*/
        File file = new File("/Volumes/F/实训/数据集");
        File []readfile = file.listFiles();
        csvToES.csvToES(fields, readfile);


        /*ES导出示例*/
        EsToCsv esToCsv = new EsToCsv();
        File writefile  = new File("/Volumes/F/实训/数据集/out.csv");
        esToCsv.EsToCsv(index,type,writefile);
    }


}


