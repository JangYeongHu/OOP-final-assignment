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
import java.util.ArrayList;



public class JsonController {

    private static JsonController singletonJsonController;
    private static final String FILE_PATH = "src/main/resources/savefile.json";
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.json"; // config.json 경로
    private JSONArray rowDatas;
    private JSONObject configData;

    //싱글톤으로 관리해주는 메소드
    public static JsonController getInstance() {
        if(singletonJsonController == null)
            singletonJsonController = new JsonController();
        return singletonJsonController;
    }

    public JsonController() {
        readJson();
        readConfigJson(); // config.json 읽기
    }

    public void init() {
        for (int i = 0; i < rowDatas.length(); i++) {
            loadJsonDataWithIndex(i);
        }
        Player.setSelectedIdx(0);
    }

    //Json 읽기
    public void readJson() {
        try {
            String jsonText = new String(Files.readAllBytes(Paths.get(FILE_PATH)), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(jsonText);
            rowDatas = jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // config.json 읽기
    public void readConfigJson() {
        try {
            String jsonText = new String(Files.readAllBytes(Paths.get(CONFIG_FILE_PATH)), StandardCharsets.UTF_8);
            configData = new JSONObject(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // config.json에서 설정 읽기
    public boolean isBgmOn() {
        return configData.getJSONObject("setting").getJSONObject("sound").getBoolean("bgm-on");
    }

    public int getBgmVolume() {
        return configData.getJSONObject("setting").getJSONObject("sound").getInt("volume");
    }

    public JSONArray getSwords() {
        return configData.getJSONArray("swords");
    }



    //로드 클릭 시 해당 인덱스의 데이터를 가져오는 메소드
    public void loadJsonDataWithIndex(int index) {
        JSONObject jsonData = rowDatas.getJSONObject(index);
        setPlayerData(jsonData,index);
    }


    //플레이어 데이터 설정하기
    private void setPlayerData(JSONObject jsonData,int index) {
        //player 데어터 관련
        JSONObject playerData = jsonData.getJSONObject("player_data");
        Player player = Player.getInstance(index);
        player.setMoney(playerData.getInt("money"));
        player.setNowSword(MainController.findSwordById(playerData.getInt("sword_id")));
        player.setUpdatedDate(playerData.getString("latest_date"));
        player.setBestSword(playerData.getInt("best_sword"));

        //statistics 관련
        ArrayList<int[]> statics = new ArrayList<>(); // [SuccessCount,FailureCount]
        JSONArray statisticsData = jsonData.getJSONArray("statistics");
        for (int i = 0; i < statisticsData.length() ; i++) {
            JSONObject jo = statisticsData.getJSONObject(i);
            int sword_id = jo.getInt("sword_id");
            int data[] = {jo.getInt("success"), jo.getInt("fail")};
            statics.add(sword_id-1, data);
        }
        player.setStatics(statics);

//        System.out.println("setPlayerData" + player.getUpdateDate());
        //item 관련

        //log 관련
    }



    //플레이어 데이터 셋팅 - log 추가하기
    private void savaPlayerData(int index) {
        Player player = Player.getInstance();

        // Player 데이터를 JSON 객체로 변환
        JSONObject playerData = new JSONObject();
        playerData.put("money", player.getMoney());
        playerData.put("sword_id", player.getNowSword().getId());
        playerData.put("best_sword",player.getBestSword());
        //아이템 추가해야함

        // 시간 포함
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        playerData.put("latest_date", formattedDateTime);
        rowDatas.getJSONObject(index).put("player_data", playerData);

        // 통계 정보 가져오기 
        JSONArray statistics = new JSONArray();
        ArrayList<int[]> playerStatics = player.getStatics();
        for (int i = 0; i < playerStatics.size(); i++) {
           JSONObject jo = new JSONObject();
           jo.put("sword_id", i+1);
           jo.put("success", playerStatics.get(i)[0]);
           jo.put("fail", playerStatics.get(i)[1]);
           statistics.put(jo);
        }
        rowDatas.getJSONObject(index).put("statistics", statistics);
    }

    //파일 처리 분리 - 쓰기
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


//    public static void main(String[] args) {
//        // 테스트를 위한 메인
//        // UTF-8 출력 스트림 강제 설정 - 콘솔에 계속 한글이 깨져서 설정함
//        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
//
//
//    }
}
