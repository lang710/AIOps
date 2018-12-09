package servlet;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class EsToCsv {

    /**
     * @param index  索引名（相当于数据库中的数据库）
     * @param type   类型（相当于数据库中的表）
     * @param file   要导入的文件（.csv格式）
     */
    public void EsToCsv(String index,String type,File file){
        TransportClient client = ConnectEs.getClient();

        String fileds=("timestamp,value,label,kpiid");

        int id = 0;
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery()).setSize(1000).setScroll(new TimeValue(600000))
                .setSearchType(SearchType.QUERY_THEN_FETCH).execute().actionGet();

        String scrollid = response.getScrollId();

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            //先将第一行的信息写入
            bw.write(fileds);

            while (true) {
                SearchResponse response2 = client.prepareSearchScroll(scrollid).setScroll(new TimeValue(1000000))
                        .execute().actionGet();
                SearchHits searchHit = response2.getHits();
                //再次查询不到数据时跳出循环
                if (searchHit.getHits().length == 0) {
                    break;
                }
                System.out.println("查询数量 ：" + searchHit.getHits().length);
                for (int i = 0; i < searchHit.getHits().length; i++) {
                    String json = searchHit.getHits()[i].getSourceAsString();
                    bw.write(json);
                    bw.write("\r\n");
                }
            }
            System.out.println("查询结束");
            bw.close();


        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(response.getHits().totalHits);
    }
}
