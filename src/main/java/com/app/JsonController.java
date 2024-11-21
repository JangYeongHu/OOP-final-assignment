package com.app;

import com.player.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.nio.file.Files;
import java.nio.file.Paths;




public class JsonController {

    private static JsonController singletonJsonController;
    private JSONArray statisticsData;
    private static final String FILE_PATH = "src/main/resources/savefile.json";

    private JSONArray logData;
    private JSONArray rowDatas;

    //싱글톤으로 관리해주는 메소드
    public static JsonController getInstance() {
        if(singletonJsonController == null)
            singletonJsonController = new JsonController();
        return singletonJsonController;
    }

    public JsonController() {
        readJson();
        for (int i = 0; i < rowDatas.length(); i++) {
            loadJsonDataWithIndex(i);
        }

    }




    public void readJson() {
        try {
            String jsonText = new String(Files.readAllBytes(Paths.get(FILE_PATH)), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(jsonText);
            rowDatas = jsonArray;
            System.out.println(rowDatas.getJSONObject(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //로드 클릭 시 해당 인덱스의 데이터를 가져오는 메소드
    public void loadJsonDataWithIndex(int index) {

        JSONObject jsonData = rowDatas.getJSONObject(index);
        loadPlayerDataInJson(jsonData, index);
        //어쩌면 통계와 로그 데이터도 싸그리 플레이어 클래스에서 관리해야 할 수 있습니다.
        loadStatisticsDataInJson(jsonData);
        loadLogDataInJson(jsonData);

        loadPlayerDataInJson(rowDatas.getJSONObject(index), index);
        loadStatisticsDataInJson(rowDatas.getJSONObject(index));
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


    //플레이어 데이터 설정하기
    private void setPlayerData(JSONObject playerData,int index) {
        Player player = Player.getInstance(index);
        player.setMoney(playerData.getInt("money"));
        player.setNowSword(MainController.findSwordById(playerData.getInt("sword_id")));
        player.setUpdatedDate(playerData.getString("latest_date"));

        System.out.println("setPlayerData" + player.getUpdateDate());
        //item 관련
        
    }



    //플레이어 데이터를 userDatas에 저장 - log랑 statics도 나중에 추가
    private void savaPlayerData(int index) {
        Player player = Player.getInstance();

        // Player 데이터를 JSON 객체로 변환
        JSONObject playerData = new JSONObject();
        playerData.put("money", player.getMoney());
        playerData.put("sword_id", player.getNowSword().getId());
        //아이템 추가해야함

        // 시간 포함
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        playerData.put("latest_date", formattedDateTime);


        rowDatas.getJSONObject(index).put("player_data", playerData);

//        System.out.println(rowDatas.toString());



    }

    //파일 처리 분리
    public void writeJson(int index) {
        savaPlayerData(index);
        try {
            for (int i = 0; i < rowDatas.length(); i++) {
                // JSON 데이터를 문자열로 변환
                String jsonString = rowDatas.toString(4);
                // 파일에 저장
                Files.write(Paths.get(FILE_PATH), jsonString.getBytes());
                System.out.println("saved successfully");

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        rowDatas.clear();
        readJson();
        loadJsonDataWithIndex(index);
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
