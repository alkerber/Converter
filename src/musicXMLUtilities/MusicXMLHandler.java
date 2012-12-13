package musicXMLUtilities;

/**
 *
 * @author alkerber
 */
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
//import player.VisualMIDlet;
import generalUtilities.ByteConverter;
import generalUtilities.MathTools;
import java.util.Vector;
import midiObjects.MidiNoteParameters;
import midiObjects.MidiProgramParameters;
import midiObjects.MidiTempoParameters;

import midiUtilities.MidiChannelEvent;

import midiUtilities.MidiChord;
import midiUtilities.MidiFile;
import midiUtilities.MidiInstrument;
import midiUtilities.MidiNote;
import midiUtilities.MidiTrack;

public class MusicXMLHandler extends DefaultHandler {

	/*Vectors*/
	Vector<MidiTrack> midiTracks = new Vector<MidiTrack>();
	Vector<MidiInstrument> midiInstruments = new Vector<MidiInstrument>(5);
	Vector deltaTimeTicks = new Vector();

	/*internal*/
	MidiFile midiFile;
	MidiChord midiChord = new MidiChord();
	MidiNote midiNote = new MidiNote();
	MidiTrack currentTrack;
	int currentMidiChannel = 0;
	int currentMidiVelocity = 90; /*MXML Dynamics*/

	/*20/01/11*/
	byte timeOffset =0;
	/*20/01/11*/

	MidiNoteParameters midiNoteParameters = new MidiNoteParameters();
	MidiProgramParameters midiProgramParameters = new MidiProgramParameters();
	MidiTempoParameters midiTempoParameters = new MidiTempoParameters();

	/*booleans*/
	private static boolean isOct = false;
	private static boolean isDur = false;
	private static boolean isStep = false;
	private static boolean isAlter = false;
	private static boolean isDivisions = false;
	private static boolean isChannel = false;
	private static boolean isProgram = false;
	private static boolean isMidiInstrument = false;
	private static boolean isRest = false;
	private static boolean isChord = false;
	private static boolean wasChord = false;
	public static boolean isDynamic; /*necessary?*/

	public static boolean isChromatic; /*necessary?*/

	/*Strings and ints*/
	public String fileName = "";
	public String step = "";
	public String instrumentID = "";
	public String currentPartID = "";
	public int oct = 0;
	public int alter = 0;
	public int dur = 0;
	public int channel = 0;
	public int program = 0;
	public int chromatic = 0;
	public int dynamics = 90;
	public int tempo = 120; //TODO: set a correct default

	///***** 2chk ****
	private static boolean getChars = false;/*necessary?*/


	private static boolean isArticulation = false;
	private static boolean isBeats = false;
	private static boolean isScorePart = false;

	public boolean isRunning = true;
	public int row = 0;
	public int whichTagIsBeingParsed;
	public static String[] tagsToParse;
	//private int articulation;

	public static int ctr = 0; //necessary?

	public MusicXMLHandler(){
		//construtor
	}

	//original constructor JAVAME - revise
	public MusicXMLHandler(String fName) {
		this.fileName = fName;
		this.ParseThis(fileName); 
	}

	public void startDocument() {
		midiFile = new MidiFile(fileName);
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		if (localName.equals("score-timewise")) {
			/*only suports score-timewise*/
			System.out.println("\n Formato nao suportado \n"); /*error message!*/
		}
		if (localName.equals("score-part")) {
			this.midiFile.addNumTracks();
			isScorePart = true;
		}
		if (localName.equals("midi-instrument")) {
			isMidiInstrument = true;
			instrumentID = atts.getValue("id");
		}
		if (localName.equals("midi-channel")) {
			isChannel = true;
		}
		if (localName.equals("midi-program")) {
			isProgram = true;
		}
		if (localName.equals("part")) {
			currentPartID = atts.getValue("id");
			//System.out.println("Part ID: " + currentPartID); //debug
			currentTrack = new MidiTrack(currentPartID);

			boolean found = false;
			int i = 0,j;
			MidiChannelEvent mce = new MidiChannelEvent();
			MidiInstrument mi = new MidiInstrument();
			while (!this.midiInstruments.isEmpty() && found == false) {
				mi = (MidiInstrument) this.midiInstruments.elementAt(i);
				System.out.println("mi: " + mi.returnInstrumentID() + currentPartID);
				if (mi.returnInstrumentID().startsWith(currentPartID)) {
					this.currentMidiChannel = mi.returnInstrumentChannel();
					this.program = mi.returnInstrumentProgram();
					//System.out.println("\né igual\n");
					//System.out.println("channel: " + this.currentMidiChannel);
					//System.out.println("\nprogram: " + this.program);

					/*here*/
					this.midiProgramParameters = new MidiProgramParameters(currentMidiChannel, program);
					this.currentTrack.AddMidiObjectOnTrack(midiProgramParameters);
					/*end*/

					found = true;
				}
				i++;
				if (i >= this.midiInstruments.size()) {
					found = true;
				}
			}
			//byte[] b = mce.programChange(channel, program);
			//for (j = 0; j < b.length; j++) {
				//System.out.println("byte " + b[j]); //debug
			//}

		}
		if (localName.equals("divisions")) {
			isDivisions = true;
		}
		if (localName.equals("step")) {
			isStep = true;
		}
		if (localName.equals("alter")) {
			isAlter = true;
		}
		if (localName.equals("octave")) {
			isOct = true;
		}
		if (localName.equals("duration")) {
			isDur = true;
		}
		if (localName.equals("chromatic")) {
			isChromatic = true;
		}
		if (localName.equals("sound")) {
			//System.out.println("\nSOUND!!!!!!!!!!\n");
			String readTempo = "", readDynamics = "";
			/*tempo -> no default (quarter notes/minute)
			 *dynamics - default midi-> 90 (% of default)
			 *caso critico-> nao ha atributos em sound.
			 *isSound = true; -> criar a variavel 'isSound'*/

			try {
				readTempo = atts.getValue("tempo");
				this.tempo = Integer.parseInt(readTempo);
				//MidiMetaMessage mmm = new MidiMetaMessage();
				this.midiTempoParameters = new MidiTempoParameters(tempo);
				this.currentTrack.AddMidiObjectOnTrack(midiTempoParameters);
				//this.currentTrack.AddMidiEventOnTrack(mmm.setTempo(tempo));
				//System.out.println("TEMPO: " + this.tempo);
			} catch (Exception e) {
				/*not important -> when the tag 'sound' don't have the
                atribute 'tempo'*/
			}
			try {
				//transformar MXML dynamics em MidiDynamics
				readDynamics = atts.getValue("dynamics");
				this.dynamics = Integer.parseInt(readDynamics);
				this.currentMidiVelocity = this.dynamics;
				System.out.println("DYNAMICS: " + this.dynamics);
			} catch (Exception e) {
				/*not important -> when the tag 'sound' don't have the
                atribute 'tempo'*/
			}

		}
		if (localName.equals("chord")) {
			isChord = true;
			if (!wasChord) {
				midiChord = new MidiChord();
			}
		}
		if (localName.equals("instrument")) {
			String id;
			id = atts.getValue("id");
			/*send message change program if is diferent*/
		}
		if (localName.equals("rest")){
			isRest = true;
		}
	}

	public void endElement(String namespaceURI, String localName,
			String qName) {
		if (localName.equals("part-list")) {
			//incluir dados da track?
			//At the end of part-list we are able to know how many tracks there will be.
			midiFile.setFileFormat();
		}
		if (localName.equals("part")) {
			/*end of track*/
			this.midiTracks.addElement(this.currentTrack);
		}
		if (localName.equals("midi-instrument")) {
			//id, channel, program
			MidiInstrument mi = new MidiInstrument(instrumentID, channel, program);
			midiInstruments.addElement(mi);
			//System.out.println("ID: " + instrumentID + " - canal: " + channel + " - programa: " + program);
			isMidiInstrument = false;
		}

		if (localName.equals("score-part")) {
			isScorePart = false;
		}
		if (localName.equals("divisions")) {
			isDivisions = false;
		}
		if (localName.equals("chromatic")) {
			isChromatic = false;
		}
		if (localName.equals("step")) {
			isStep = false;
		}
		if (localName.equals("alter")) {
			isAlter = false;
		}
		if (localName.equals("octave")) {
			isOct = false;
		}
		if (localName.equals("duration")) {
			isDur = false;
		}
		if (localName.equals("midi-channel")) {
			isChannel = false;
		}
		if (localName.equals("midi-program")) {
			isProgram = false;
		}
		if (localName.equals("note")) {
			if (isRest) {
				/*supondo que não há pausas em um acorde!*/
				//System.out.println("MIDI:  -NO-  tocar por " + dur);
				//System.out.println("pausa");
				//adicionar o delta-time corretamente na proxima nota
				timeOffset = (byte)dur;
				//System.out.println("offset: " + timeOffset);
				isRest = false;
			} else {
				if (isChord && (!wasChord)) {
					midiChord.addNoteOnChord(midiNote);
					midiNote = new MidiNote(step.charAt(0), oct, alter + chromatic, this.dur);
					midiChord.addNoteOnChord(midiNote);
					alter = 0;
					wasChord = true;
					isChord = false;
					// System.out.println("");
					//midiChord.printChord();
				} else if (isChord && wasChord) {
					midiNote = new MidiNote(step.charAt(0), oct, alter + chromatic, this.dur);
					midiChord.addNoteOnChord(midiNote);
					alter = 0;
					wasChord = true;
					isChord = false;
					midiChord.printChord();
				} else {
					midiNote = new MidiNote(step.charAt(0), oct, alter + chromatic, this.dur);
					byte notenum = midiNote.returnNoteNumber();


					alter = 0;
					wasChord = false;


					byte[] midiEvent;

					ByteConverter bc = new ByteConverter();
					MidiChannelEvent mce;
					byte[] nullDeltaTime = new byte[1];
					nullDeltaTime[0] = 0x00;
					mce = new MidiChannelEvent();
					nullDeltaTime[0] = (byte) (nullDeltaTime[0] + timeOffset);
					//System.out.println("\nnull delta time: " + nullDeltaTime[0]);
					midiEvent = mce.noteOn(nullDeltaTime, this.currentMidiChannel, notenum, (byte) this.currentMidiVelocity);
					//System.out.println("\nconseguiu criar o byte array\n " + midiEvent[0] + " " + midiEvent[1] + " ");
					//this.currentTrack.AddMidiEventOnTrack(midiEvent);
					midiNoteParameters = new MidiNoteParameters(true,(int)nullDeltaTime[0], this.currentMidiChannel, notenum, (byte) this.currentMidiVelocity);
					this.currentTrack.AddMidiObjectOnTrack(midiNoteParameters);
					//System.out.println("\nconseguiu adicionar na track\n");

					mce = new MidiChannelEvent();
					//midiEvent = mce.noteOn(bc.intoVariableLength(this.dur), this.currentMidiChannel, notenum, (byte) 0x00);
					//midiEvent = mce.noteOff(bc.intoVariableLength(this.dur), this.currentMidiChannel, notenum, (byte) 0x00);
					//this.currentTrack.AddMidiEventOnTrack(midiEvent);
					midiNoteParameters = new MidiNoteParameters(false, this.dur, this.currentMidiChannel, notenum, (byte) this.currentMidiVelocity);
					this.currentTrack.AddMidiObjectOnTrack(midiNoteParameters);

					timeOffset = 0;
				}

			}
		}
		if (localName.equals("midi-channel")) {
			isChannel = false;
		}
		if (localName.equals("midi-program")) {
			isProgram = false;
		}
		if (localName.equals("midi-instrument")) {
			MidiInstrument mi = new MidiInstrument("id", this.channel, this.program);
			this.midiInstruments.addElement(mi);
		}



		///***** 2chk ****
		if (getChars) {
			getChars = false;
		}
		/*        if (localName.equals("duration") && isDur == 2) {
        isDur = 1;
        }*/
		if (localName.equals("beats")) {
			isBeats = false;
		}
		if (localName.equals("duration")) {
			isDur = false;
		}
	}


	public void characters(char[] ch, int start, int len) {
		if (isDivisions) {
			int divisions;
			divisions = Integer.parseInt(new String(ch, start, len).trim());
			//System.out.println("divisions: " + divisions);
			this.currentTrack.trackDivisions = divisions;
			if (this.deltaTimeTicks.isEmpty() || !this.deltaTimeTicks.contains(new Integer(divisions))) {
				this.deltaTimeTicks.addElement(new Integer(divisions));
			}
		}
		if (isStep == true) {
			this.step = new String(ch, start, len).trim();
		}
		if (isAlter == true) {
			this.alter = Integer.parseInt(new String(ch, start, len).trim());
		}
		if (isOct == true) {
			this.oct = Integer.parseInt(new String(ch, start, len).trim());
		}
		if (isChromatic == true) {
			this.chromatic = Integer.parseInt(new String(ch, start, len).trim());
		}
		if (isDur == true) {
			this.dur = Integer.parseInt(new String(ch, start, len).trim());
		}
		if (isChannel == true) {
			this.channel = Integer.parseInt(new String(ch, start, len).trim());
		}
		if (isProgram == true) {
			this.program = Integer.parseInt(new String(ch, start, len).trim());
		}
	}

	public void endDocument() {
		int i, j;
		int arraySize = this.deltaTimeTicks.size();
		int[] arrayDeltaTTs = new int[arraySize];
		int finalDeltaTTs;
		for (j = 0; j < arraySize; j++) {
			arrayDeltaTTs[j] = (((Integer) this.deltaTimeTicks.elementAt(j)).intValue());
		}
		MathTools mt = new MathTools();
		finalDeltaTTs = mt.lcm(arrayDeltaTTs);
		System.out.println("delta time ticks final: " + finalDeltaTTs);
		midiFile.setDeltaTimeTicks(finalDeltaTTs);

		//MIDI: escrever o arquivo.
		/*TODO: corrigir as midiTracks com os delta-times corretos.*/
		midiFile.createMidiFile();
		for (i = 0; i < this.midiTracks.size(); i++) {
			((MidiTrack) this.midiTracks.elementAt(i)).ConvertMidiObjectsToEvent(finalDeltaTTs);
			midiFile.writeTrackOnFile((MidiTrack) this.midiTracks.elementAt(i));
		}
		//escrever as tracks

	}
	
	public void ParseThis(String fName) {
		try {
			File file = new File(fName);
			this.fileName = fName;
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(file, this); //params: (file, default handler)
		} catch (Exception ex) {
		} catch (Throwable err) {
			err.printStackTrace();
		}
		ctr = 0;
	}
}
