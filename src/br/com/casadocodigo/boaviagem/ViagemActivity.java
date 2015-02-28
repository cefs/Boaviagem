package br.com.casadocodigo.boaviagem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ViagemActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viagem);
	}
	
	public void salvarViagem(View view) {
		Toast.makeText(this, "Em breve!", Toast.LENGTH_LONG).show();
	}
}
