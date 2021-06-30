package dontTouchTheWhiteTile;

import java.io.*;
import javax.sound.midi.*;

public class MidiSound {
    private static Sequencer midiPlayer;
    private final String filename;

    public MidiSound(String input){
        filename = input;
    }
    public static void startMidi(String midFilename) {
        try {
            File midiFile = new File(midFilename);
            Sequence song = MidiSystem.getSequence(midiFile);
            midiPlayer = MidiSystem.getSequencer();
            midiPlayer.open();
            midiPlayer.setSequence(song);
            midiPlayer.setLoopCount(0); // repeat 0 times (play once)
            midiPlayer.setTempoInBPM(89F);
            midiPlayer.start();
            System.out.println(midiPlayer.getTempoInMPQ());
        } catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
    }

    // testing main method
    public void run() {
        startMidi(filename);     // start the midi player
//        float x = 1;
//        while(true) {
//            try {
//                Thread.sleep(5000);   // delay
//            } catch (InterruptedException e) {
//            }
//            System.out.println("faster");
////        midiPlayer.setTempoInBPM(60);
//            x += 0.2;
//            midiPlayer.setTempoFactor(x);   // >1 to speed up the tempo
//        }
//        try {
//            Thread.sleep(10000);   // delay
//        } catch (InterruptedException e) { }
//        midiPlayer.setTempoFactor(10.0F);

        // Do this on every move step, you could change to another song
//        if (!midiPlayer.isRunning()) {  // previous song finished
//            // reset midi player and start a new song
//            midiPlayer.stop();
//            midiPlayer.close();
//            startMidi("song2.mid");
//        }
    }

    public void ChangeSpeed(float x){
        midiPlayer.setTempoFactor(x);
    }
    public boolean CheckRunning(){
        return 	midiPlayer.isRunning();
    }


}
