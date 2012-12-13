package midiUtilities;

/**
 *
 * @author alkerber
 */
public class MidiDynamics {

    public MidiDynamics(){

    }

    int returnMidiDynamics(int mxmlDynamics){
        return (mxmlDynamics/100)*90;
    }

}
