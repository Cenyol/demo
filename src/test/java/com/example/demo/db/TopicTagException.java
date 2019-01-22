package com.example.demo.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/12/25 16:38
 */
public class TopicTagException {

    public static void main(String[] args) {
//        System.out.println("cenyol".length());
//        System.out.println("现代极简实木照片墙 13件装".length());
//        String testStr = "123\t{\"pageTitle\":\"严选LOOK\",\"nickName\":\"m****7\",\"handcraft\":\"2\",\"banner\":[{\"picUrl\":\"https://yanxuan.nosdn.127.net/0a0c70e8676f67aaab66e18b53dba768.jpg\",\"videoUrl\":\"\",\"width\":1000,\"rank\":0,\"isVideo\":false,\"comment\":[{\"itemId\":1117000,\"picUrl\":\"https://yanxuan.nosdn.127.net/bcb926ffa02e7ebf3edd5ba6e3746366.png\",\"itemName\":\"网易智造易魔方蓝牙音箱\",\"positionRight\":true,\"top\":522,\"left\":450,\"price\":238,\"itemStatus\":2,\"position\":2,\"itemDesc\":\"纯粹原声 便携体型 高颜值\",\"tagName\":\"网易智造易魔方蓝牙音箱\",\"cart\":false},{\"itemId\":1225007,\"picUrl\":\"https://yanxuan.nosdn.127.net/2fdbb175c9f2e3f0056403b74311aff7.png\",\"itemName\":\"fresh fruit系列香薰蜡烛\",\"positionRight\":true,\"top\":616,\"left\":252,\"price\":59,\"itemStatus\":2,\"position\":2,\"itemDesc\":\"清新果香，舒缓身心\",\"tagName\":\"fresh fruit系列香薰蜡烛\",\"cart\":false}],\"url\":\"https://yanxuan.nosdn.127.net/0a0c70e8676f67aaab66e18b53dba768.jpg\",\"height\":1000}],\"description\":\"售后超好，换货超快！生活被严选承包！\",\"avatar\":\"https://yanxuan.nosdn.127.net/9938b44eddb2ec3d9526b5e8eb29a17c.jpg\",\"pageKeywords\":\"严选LOOK\",\"userId\":56611143,\"backupItemList\":[{\"itemId\":1117000,\"picUrl\":\"https://yanxuan.nosdn.127.net/bcb926ffa02e7ebf3edd5ba6e3746366.png\",\"itemName\":\"网易智造易魔方蓝牙音箱\",\"top\":522,\"left\":450,\"price\":238,\"itemStatus\":2,\"position\":2,\"itemDesc\":\"纯粹原声 便携体型 高颜值\",\"tagName\":\"网易智造易魔方蓝牙音箱\",\"cart\":false},{\"itemId\":1225007,\"picUrl\":\"https://yanxuan.nosdn.127.net/2fdbb175c9f2e3f0056403b74311aff7.png\",\"itemName\":\"fresh fruit系列香薰蜡烛\",\"top\":616,\"left\":252,\"price\":59,\"itemStatus\":2,\"position\":2,\"itemDesc\":\"清新果香，舒缓身心\",\"tagName\":\"fresh fruit系列香薰蜡烛\",\"cart\":false}],\"pageDesc\":\"售后超好，换货超快！生活被严选承包！\",\"bannerList\":[{\"picUrl\":\"https://yanxuan.nosdn.127.net/0a0c70e8676f67aaab66e18b53dba768.jpg\",\"videoUrl\":\"\",\"width\":1000,\"rank\":0,\"isVideo\":false,\"comment\":[{\"itemId\":1117000,\"picUrl\":\"https://yanxuan.nosdn.127.net/bcb926ffa02e7ebf3edd5ba6e3746366.png\",\"itemName\":\"网易智造易魔方蓝牙音箱\",\"positionRight\":true,\"top\":522,\"left\":450,\"price\":238,\"itemStatus\":2,\"position\":2,\"itemDesc\":\"纯粹原声 便携体型 高颜值\",\"tagName\":\"网易智造易魔方蓝牙音箱\",\"cart\":false},{\"itemId\":1225007,\"picUrl\":\"https://yanxuan.nosdn.127.net/2fdbb175c9f2e3f0056403b74311aff7.png\",\"itemName\":\"fresh fruit系列香薰蜡烛\",\"positionRight\":true,\"top\":616,\"left\":252,\"price\":59,\"itemStatus\":2,\"position\":2,\"itemDesc\":\"清新果香，舒缓身心\",\"tagName\":\"fresh fruit系列香薰蜡烛\",\"cart\":false}],\"url\":\"https://yanxuan.nosdn.127.net/0a0c70e8676f67aaab66e18b53dba768.jpg\",\"height\":1000}],\"createTime\":\"2018-04-28\",\"choosedCommentId\":39695638,\"commentId\":39695638,\"itemIdList\":\"1117000,1225007\"}";
//        String[] testStrArr = testStr.split("\t");
//        isException(testStrArr, new ArrayList<>());

        try{
            List<String> topicIds = new ArrayList<>();
            List<String> topicCondition = new ArrayList<>();
            String filePath = "/Users/cenyol/Downloads/topic_extend0.txt";
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath)));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] tmpArray = line.split("\t");
                if (isException(tmpArray, topicCondition)) {
                    topicIds.add(tmpArray[0] + ",");
                }
            }
            br.close();
            System.out.println(topicIds.size());
            System.out.println(topicCondition.size());
            topicIds.forEach(System.out::print);
            System.out.println();
            topicCondition.forEach(System.out::print);
        }catch(Exception e){ System.out.println(e);}
    }

    private static boolean isException(String[] jsonString, List<String> topicCondition) {
        boolean result = false;
        JSONObject moduleObj = JSON.parseObject(jsonString[1]);
        JSONArray bannerObj = moduleObj.getJSONArray("banner");
        if (!CollectionUtils.isEmpty(bannerObj)) {
            JSONObject bannerFirst = (JSONObject) bannerObj.get(0);
            if (bannerFirst != null) {
                JSONArray comments = bannerFirst.getJSONArray("comment");
                for (int i = 0; i < comments.size(); i++) {
                    JSONObject comment = comments.getJSONObject(i);
                    if (comment != null) {
                        int left = comment.getIntValue("left");
                        int length = String_length(comment.getString("tagName")) / 2;
                        if (comment.getBoolean("positionRight")) {
                            if (length >= 13 && left > 400 ||
                                    length == 12 && left > 420 ||
                                    length == 11 && left > 444 ||
                                    length == 10 && left > 468 ||
                                    length == 9 && left > 492 ||
                                    length == 8 && left > 516
                                    ) {
                                result = true;
                            }
                        }else {
                            if (length >= 13 && left < 350 ||
                                    length == 12 && left < 330 ||
                                    length == 11 && left < 306 ||
                                    length == 10 && left < 282 ||
                                    length == 9 && left < 258 ||
                                    length == 8 && left < 234
                                    ) {
                                result = true;
                            }
                        }
                        if (result) {
                            topicCondition.add(jsonString[0] + ":" +
                                            comment.getBoolean("positionRight") + "," +
                                    left + "," + length + "\n\r");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static int String_length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }
}
