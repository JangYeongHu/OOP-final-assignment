package com.app;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BgmController {

    private static BgmController bgmController;
    private Clip clip;
    private FloatControl volumeControl;
    private boolean isBgmOn = true;  // 초기값: BGM 켜짐
    private static final String BGM_FILE_PATH = "src/main/resources/bgm.wav"; // BGM 파일 경로
    private int roughVolume = 100;

    public static BgmController getInstance() {
        if(bgmController == null)
            bgmController = new BgmController();
        return bgmController;
    }



    public void play() {
        try {
            // BGM 파일 로딩
            File soundFile = new File(BGM_FILE_PATH);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // 볼륨 조정 컨트롤
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            clip.start();  // BGM 재생 시작
            isBgmOn = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();  // BGM 정지
            isBgmOn = false;
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            // 볼륨 값은 -80.0f에서 +6.0f 사이로 설정
            volumeControl.setValue(volume);  // 볼륨 조절
        }
    }

    public void setRoughVolume(int roughVolume) {
        this.roughVolume = roughVolume;
    }

    public int getRoughVolume() {
        return roughVolume;
    }

    public boolean isBgmOn() {
        return isBgmOn;
    }
}
