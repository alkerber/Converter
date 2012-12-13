package pck.converter;


//import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.OutputStreamWriter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;


public class MainActivity extends Activity {
	String pathName;
	MediaPlayer mediaPlayer;


	public void buttonPause(View view) {
		mediaPlayer.pause();
	}

	public void buttonStop(View view) {
		mediaPlayer.stop();
	}

	public void buttonAbrir(View view){
		Intent intent = new Intent(this, FileBrowser.class);
		startActivity(intent);
	}


	/**botão play*/
	//provavelmente só vou usar para tocar midi. já vai ser passado como parâmetro o arquivo convertido.
	public void buttonPlay(View view) {
		//receive the file name from the file browser
		mediaPlayer.start();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mediaPlayer = new MediaPlayer();

		Intent intent = getIntent();       
		Bundle params = intent.getExtras();  
		String fileToPlay = ""; 
		if(params!=null)
		{   
			fileToPlay = params.getString("mensagem");
			EditText editText = (EditText) findViewById(R.id.edit_message);
			editText.setText(fileToPlay.toCharArray(), 0, fileToPlay.length());
			System.out.println("caminho da tela anterior" + fileToPlay);
		}
		mediaPlayer.reset();
		//System.out.println(pathName + "/n");
		if(fileToPlay != ""){
			try {
				mediaPlayer.setDataSource(fileToPlay);
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	        

			try {
				mediaPlayer.prepare();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} 
		//write on memory card
		/*		try {
			String txtData = "Hello World!";
		    pathName = Environment.getExternalStorageDirectory().getAbsolutePath();
		    pathName = pathName + "/mysdfilehello.txt";
		    System.out.println(pathName);
		    File myFile = new File(pathName);

			//myFile = new File("/mnt/sdcard/mysdfile.txt");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);

			OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);
			myOutWriter.append(txtData);
			myOutWriter.close();
			fOut.close();
			System.out.println("escreveu no cartão de memória");
		} catch (Exception e) {
			System.out.println("NÃO escreveu no cartão de memória");
		}*/
		//fim de escrever no cartão de memória


		//verificando se o cartão de memória está disponível
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but all we need
			//  to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		//
		System.out.println("disponível: " + mExternalStorageAvailable + " possível escrever: " + mExternalStorageWriteable);


	}

}
