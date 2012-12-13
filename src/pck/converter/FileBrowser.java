package pck.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import musicXMLUtilities.MusicXMLHandler;

import android.os.Bundle;
import android.os.Environment;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FileBrowser extends ListActivity {

	private static final String MEDIA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	private List<String> songs = new ArrayList<String>();
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
    	updateSongList();
    }
    
    public void updateSongList() {
    	File home = new File(MEDIA_PATH);
		if (home.listFiles().length > 0) {
    		for (File file : home.listFiles()) {
    			songs.add(file.getName());
    		}
		
    		ArrayAdapter<String> songList = new ArrayAdapter<String>(this,R.layout.song_item,songs);
    		setListAdapter(songList);
		}    	
    }

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		String selection = l.getItemAtPosition(position).toString();	
		String selectedFile = MEDIA_PATH + "/" + selection;

		if (selectedFile.endsWith(".xml")){
			//converter o arquivo: enviar para o parser e gerar o novo arquivo
			MusicXMLHandler mxmlHandler = new MusicXMLHandler();
			mxmlHandler.ParseThis(selectedFile);
			//System.out.println("Passou pelo parser. arquivo enviado: " + selectedFile);
			//retornar o arquivo convertido
	        selectedFile = selectedFile.substring(0, selectedFile.length() - 3) + "musa.mid"; // new name for the converted file
			//mxmlHandler.
		} 
		
		Intent intent = new Intent(l.getContext(), MainActivity.class);
        Bundle params = new Bundle();
              
        String fileToPlay = selectedFile;
        params.putString("mensagem", fileToPlay); // verificar o que fazer com a "mensagem"
        intent.putExtras(params);
        startActivity(intent);

	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_file_browser, menu);
        return true;
    }
}
