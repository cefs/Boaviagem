package br.com.casadocodigo.boaviagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class ViagemListActivity extends ListActivity implements
		OnItemClickListener, OnClickListener {
	private AlertDialog alertDialog;
	private AlertDialog dialogConfirmacao;
	private int viagemSelecionada;
	
	@Override
	public void onClick(DialogInterface dialog, int item) {
		switch (item) {
		case 0:
			startActivity(new Intent(this, ViagemActivity.class));
			break;
		case 1:
			startActivity(new Intent(this, GastoActivity.class));
			break;
		case 2:
			startActivity(new Intent(this, GastoListActivity.class));
			break;
		case 3:
			dialogConfirmacao.show();
			break;
		case DialogInterface.BUTTON_POSITIVE:
			viagens.remove(this.viagemSelecionada);
			getListView().invalidateViews();
			break;
		case DialogInterface.BUTTON_NEGATIVE:			
			dialogConfirmacao.dismiss();
			break;									
		}
	}

	private AlertDialog criaAlertaDialog() {
		final CharSequence[] items = { getString(R.string.editar),
				getString(R.string.novo_gasto),
				getString(R.string.gastos_realizados),
				getString(R.string.remover) };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.opcoes);
		builder.setItems(items, this);

		return builder.create();
	}
	
	private AlertDialog criaDialogConfirmacao() {		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.confirma_exclusao_viagem);
		builder.setPositiveButton(getString(R.string.sim), this);
		builder.setNegativeButton(getString(R.string.nao), this);

		return builder.create();
	}
	
	private List<Map<String, Object>> viagens;

	private List<Map<String, Object>> listarViagens() {
		viagens = new ArrayList<Map<String, Object>>();

		Map<String, Object> item = new HashMap<String, Object>();
		item.put("imagem", R.drawable.negocios);
		item.put("destino", "São Paulo");
		item.put("data", "02/02/2012 a 04/02/2012");
		item.put("total", "Gasto total de R$ 314,98");
		item.put("barraProgresso", new Double[] { 500.0, 450.0, 314.98 });

		Map<String, Object> item2 = new HashMap<String, Object>();
		item2.put("imagem", R.drawable.lazer);
		item2.put("destino", "São Paulo");
		item2.put("data", "02/02/2012 a 04/02/2012");
		item2.put("total", "Gasto total de R$ 314,98");
		item2.put("barraProgresso", new Double[] { 800.0, 1450.0, 314.98 });
		
		viagens.add(item);
		viagens.add(item2);

		return viagens;
	}
	
	private class ViagemViewBinder implements ViewBinder {
		public boolean setViewValue(View view, Object data, String textRepresentation) {
			
			if (view.getId() == R.id.barraProgresso) { 
				Double valores[] = (Double[]) data;
				ProgressBar progressBar = (ProgressBar) view;
				progressBar.setMax(valores[0].intValue());
				progressBar.setSecondaryProgress(valores[1].intValue());
				progressBar.setProgress(valores[2].intValue());
				
				return true;
			}
			return false;
		}	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] de = { "imagem", "destino", "data",
						"total", "barraProgresso" };
		
		int[] para = { R.id.tipoViagem, R.id.destino, 
					   R.id.data, R.id.valor, R.id.barraProgresso };

		SimpleAdapter adapter = new SimpleAdapter(this, listarViagens(),
				R.layout.lista_viagem, de, para);
		
		adapter.setViewBinder(new ViagemViewBinder());
		setListAdapter(adapter);

		getListView().setOnItemClickListener(this);

		this.alertDialog = criaAlertaDialog();
		this.dialogConfirmacao = criaDialogConfirmacao(); 
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			this.viagemSelecionada = position;
			alertDialog.show();
	}
}
