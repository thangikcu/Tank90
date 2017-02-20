package com.thanggun99.tank90.managers;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioPlayer implements LineListener {
	
	public static final int PLAYER_FIRE = 0;
	public static final int GAME_START = 1;
	public static final int EXPLOSION = 2;
	public static final int PLAYER_DIE = 3;
	public static final int PLAYER_MOVE = 4;
	public static final int GAME_OVER = 5;
	public static final int STUFF_DIE = 6;
	public static final int LEVEL_COMPLETED = 7;
	public static final int PICK_ITEM = 8;

	static final int MAX_SOUND_INDEX = PICK_ITEM;
	static final int ARRAY_SIZE = MAX_SOUND_INDEX + 1;
	
	AudioFormat format;
	Clip[] clips;
	HashMap<Clip, Integer> numClip;
	boolean[] isPlaying;
	boolean[] suspended;
	
	String[] audioFiles = {
			"/res/audios/shot.wav",
			"/res/audios/start_game.wav",
			"/res/audios/explosion.wav",
			"/res/audios/mytank_explosion.wav",
			"/res/audios/move.wav",
			"/res/audios/game_over.wav",
			"/res/audios/stuff_explosion.wav",
			"/res/audios/level_completed.wav",
			"/res/audios/pick_item.wav",
			null,
			null
	};
	
	public AudioPlayer() {
		clips = new Clip[ARRAY_SIZE];
		numClip = new HashMap<Clip, Integer>();
		isPlaying = new boolean[ARRAY_SIZE];
		suspended = new boolean[ARRAY_SIZE];
		format = new AudioFormat(44100, 16, 2, true, false);
		
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		try {
			for(int i = 0; i <= MAX_SOUND_INDEX; i++) {
				if(audioFiles[i] != null) {
					clips[i] = (Clip) AudioSystem.getLine(info);
					clips[i].open(AudioSystem.getAudioInputStream(AudioPlayer.class.getResource(audioFiles[i])));
					clips[i].addLineListener(this);
					numClip.put(clips[i], i);
				}
			}
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	public void playSound(int index) {
		if(index != PLAYER_DIE && isPlaying[GAME_START]) return;
		if(isPlaying[index]) {
			clips[index].stop();
			clips[index].flush();
		}
		clips[index].setFramePosition(0);
		clips[index].start();
	}
	
	public void loopSound(int index) {
		if(index != PLAYER_DIE && isPlaying[GAME_START]) return;
		if(!isPlaying[index]) {

			if(!(isPlaying[PLAYER_MOVE])) {
				clips[index].setLoopPoints(0, -1);
				clips[index].setFramePosition(0);
				clips[index].start();
			}
		}
	}
	
	public void stopSound(int index) {
		if(isPlaying[index]) {
			clips[index].stop();
			clips[index].flush();
		}
	}
	

	@Override
	public void update(LineEvent event) {
		if(event.getType() == LineEvent.Type.STOP) {
			isPlaying[numClip.get((Clip)event.getLine())] = false;
		} else if(event.getType() == LineEvent.Type.START) {
			isPlaying[numClip.get((Clip)event.getLine())] = true;
		}
	}
}
