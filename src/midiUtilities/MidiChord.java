package midiUtilities;

import java.util.Vector;
import midiUtilities.MidiChannelEvent;

/**
 *
 * @author alkerber
 */

//TODO: implement
public class MidiChord {

    public Vector<MidiNote> notesOnChord;

    public MidiChord(){
	//constructor
        this.notesOnChord = new Vector<MidiNote>(5);
    }

    public void addNoteOnChord(MidiNote mn){
	this.notesOnChord.addElement(mn);
    }

    public void returnChord(){
	//fazer
    }

    public void printChord(){
	int i;
        MidiNote tempMN;
        System.out.println("tamanho do vetor: " + notesOnChord.size());
	for(i = 0; i < notesOnChord.size(); i++){
            tempMN = (MidiNote)notesOnChord.elementAt(i);
	    System.out.println("\nnota " + i + " ACORDE : " + tempMN.step + "\n");
	}
    }

}

