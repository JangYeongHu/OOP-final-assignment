package com.app;

import com.player.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonController {

    private static JsonController singletonJsonController;
    private JSONArray statisticsData;

    private JSONArray logData;


    //싱글톤으로 관리해주는 메소드
    public static JsonController getInstance() {
        if(singletonJsonController == null)
            singletonJsonController = new JsonController();
        return singletonJsonController;
    }

    public JsonController() {
        loadJsonDataWithIndex(0);
        loadJsonDataWithIndex(1);
        loadJsonDataWithIndex(2);
    }


    //파일에서 플레이어 데이터를 읽어와 저장하는 메소드
    private void loadPlayerDataInJson(JSONObject jsonData,int index) {
        JSONObject playerData = jsonData.getJSONObject("player_data");
        setPlayerData(playerData,index);
    }

    //파일에서 통계 데이터를 읽어와 저장하는 메소드
    private void loadStatisticsDataInJson(JSONObject jsonData) {
        statisticsData = jsonData.getJSONArray("statistics");
    }

    //파일에서 로그 데이터를 읽어와 저장하는 메소드
    private void loadLogDataInJson(JSONObject jsonData) {
        logData = jsonData.getJSONArray("log");
    }

    //로드 클릭 시 해당 인덱스의 데이터를 가져오는 메소드
    public JSONObject loadJsonDataWithIndex(int index) {
        JSONArray jsonArray = null;
        try {
            // JSON 파일 읽기
            InputStream inputStream = JSONArray.class.getResourceAsStream("/savefile.json");
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: savefile.json");
            }
            // JSON 데이터를 문자열로 변환
            String jsonText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            jsonArray = new JSONArray(jsonText);

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonData = jsonArray.getJSONObject(index);
        loadPlayerDataInJson(jsonData, index);
        loadStatisticsDataInJson(jsonData);
        loadLogDataInJson(jsonData);

        return jsonData;
    }


    //플레이어 데이터 설정하기
    private void setPlayerData(JSONObject playerData,int index) {
        Player player = Player.getInstance(index);
        player.setMoney(playerData.getInt("money"));
        player.setNowSword(MainController.findSwordById(playerData.getInt("sword_id")));
        player.setUpdatedDate(playerData.getString("latest_date"));
    }

    /* 클래스를 통으로 재설계하는 과정에서 불가피하게 빠졌습니다. 어떻게 잘 수정해서 쓰고 싶은데 지금은 시간이 없어서 보류하겠습니다.
    public List<Map<String, Integer>> getInfo(int keyValue) { //key - 0은 statistics, key - 1은 log
        //2차원 배열로 받길 원하시면 수정하겠습니다

        List<Map<String, Integer>> mapList = new ArrayList<>();
        JSONArray ja = user.getJSONArray(lableKeys[keyValue]);

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonObject = ja.getJSONObject(i);
            Map<String, Integer> map = new HashMap<>();
            jsonObject.keys().forEachRemaining(key -> map.put(key, jsonObject.getInt(key)));
            mapList.add(map);
        }
        mapList.forEach(System.out::println);

        return mapList;
    }
    */


//    public static void main(String[] args) {
//        // 테스트를 위한 메인
//        // UTF-8 출력 스트림 강제 설정 - 콘솔에 계속 한글이 깨져서 설정함
//        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
//
//
//    }
}
