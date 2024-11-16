package com.app;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData {

    private String[] lableKeys = {"statistics", "log"};
    private JSONArray rowUsers;
    private int playerIdx = 0; //0, 1, 2 존재


    public JSONArray getRowUsers() {
        return rowUsers;
    }

    public UserData() {
        rowUsers = readRowUserData();
    }

    public JSONArray readRowUserData() {
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
        return jsonArray;
    }

    public void setPlayerIdx(int playerIdx) {
        this.playerIdx = playerIdx;
    }


    public List<Map<String, Integer>> getInfo(int keyValue) { //key - 0은 statistics, key - 1은 log
        //2차원 배열로 받길 원하시면 수정하겠습니다

        List<Map<String, Integer>> mapList = new ArrayList<>();

        JSONObject jo = rowUsers.getJSONObject(playerIdx);
        JSONArray ja = jo.getJSONArray(lableKeys[keyValue]);

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonObject = ja.getJSONObject(i);
            Map<String, Integer> map = new HashMap<>();
            jsonObject.keys().forEachRemaining(key -> map.put(key, jsonObject.getInt(key)));
            mapList.add(map);
        }
        mapList.forEach(System.out::println);

        return mapList;
    }




//    public static void main(String[] args) {
//        // 테스트를 위한 메인
//        // UTF-8 출력 스트림 강제 설정 - 콘솔에 계속 한글이 깨져서 설정함
//        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
//
//
//    }
}
