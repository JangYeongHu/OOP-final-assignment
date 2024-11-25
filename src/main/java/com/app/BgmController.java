package com.app;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BgmController {
    private Clip clip;
    private FloatControl volumeControl;

    public void play(String filePath) {
        try {
            // BGM 파일 로딩
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // 볼륨 조정 컨트롤
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            clip.start();  // BGM 재생 시작
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();  // BGM 정지
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            // 볼륨 값은 -80.0f에서 +6.0f 사이로 설정
            volumeControl.setValue(volume);  // 볼륨 조절
        }
    }
}
