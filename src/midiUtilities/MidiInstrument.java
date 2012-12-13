package midiUtilities;

/**
 *
 * @author alkerber
 */
public class MidiInstrument {
    int instrumentChannel;
    int instrumentProgram;
    String instrumentID;

    public MidiInstrument(){
	
    }

    public MidiInstrument(String id,int channel,int program){
        this.instrumentChannel = channel;
        this.instrumentID = id;
        this.instrumentProgram = program;
    }

    public String returnInstrumentID(){
        return this.instrumentID;
    }

    public int returnInstrumentProgram(){
        return this.instrumentProgram;
    }

    public int returnInstrumentChannel(){
        return this.instrumentChannel;
    }

}
