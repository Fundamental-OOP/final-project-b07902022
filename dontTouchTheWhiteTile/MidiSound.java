package dontTouchTheWhiteTile;

import java.io.*;
import java.util.concurrent.TimeUnit;
import javax.sound.midi.*;

public class MidiSound {
    private static Sequencer midiPlayer;
    private final String filename;
    private Float speed;

    public MidiSound(String input, Float speed){
        this.filename = input;
        this.speed = speed;
    }
    public void startMidi(String midFilename) {
        try {
            File midiFile = new File(midFilename);
            Sequence song = MidiSystem.getSequence(midiFile);

            midiPlayer = MidiSystem.getSequencer();
            midiPlayer.open();
            midiPlayer.setSequence(song);
            midiPlayer.setLoopCount(0); // repeat 0 times (play once)
            midiPlayer.setTempoInBPM(this.speed);
            midiPlayer.start();
            System.out.println(midiPlayer.getTempoInMPQ());
        } catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
    }

    // testing main method
    public void run() throws InterruptedException {

        startMidi(filename);     // start the midi player
        //TimeUnit.MINUTES.sleep(1);
        //midiPlayer.stop();
    }

    public void ChangeSpeed(float x){
        midiPlayer.setTempoFactor(x);
    }
    public boolean CheckRunning(){
        return 	midiPlayer.isRunning();
    }
    public void stop(){midiPlayer.stop();}


}
