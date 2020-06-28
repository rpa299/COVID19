package pt.ipg.covid19;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorSusInf extends RecyclerView.Adapter<AdaptadorSusInf.ViewHolderSusInf>{
    private final Context context;
    private Cursor cursor = null;

    public AdaptadorSusInf(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        if (cursor != this.cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public AdaptadorSusInf.ViewHolderSusInf onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemSusInf = LayoutInflater.from(context).inflate(R.layout.item_sus_inf, parent, false);

        return new AdaptadorSusInf.ViewHolderSusInf(itemSusInf);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorSusInf.ViewHolderSusInf holder, int position) {
        cursor.moveToPosition(position);
        SusInf susInf = Converte.cursorToSusInf(cursor);
        holder.setSusInf(susInf);
    }

    @Override
    public int getItemCount() {
        if(cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }

    public SusInf getSusInfSelecionado() {
        if (viewHolderSusInfSelecionado == null) return null;

        return viewHolderSusInfSelecionado.susInf;
    }

    private AdaptadorSusInf.ViewHolderSusInf viewHolderSusInfSelecionado = null;

    public class ViewHolderSusInf extends RecyclerView.ViewHolder implements View.OnClickListener{
        private SusInf susInf = null;

        private final TextView textViewItemUtilizador;
        private final TextView textViewItemNome;
        private final TextView textViewItemDataNascimento;
        private final TextView textViewItemDataInfecao;

        public ViewHolderSusInf(@NonNull View itemView) {
            super(itemView);

            textViewItemUtilizador = (TextView)itemView.findViewById(R.id.textViewItemUtilizador);
            textViewItemNome = (TextView)itemView.findViewById(R.id.textViewItemNomeSusInf);
            textViewItemDataNascimento = (TextView)itemView.findViewById(R.id.textViewItemDataNascimentoSusInf);
            textViewItemDataInfecao = (TextView)itemView.findViewById(R.id.textViewItemDataInfecaoSusInf);
            itemView.setOnClickListener(this);
        }

        public void setSusInf(SusInf susInf) {
            this.susInf = susInf;

            textViewItemUtilizador.setText(String.valueOf(susInf.getNomePerfil()));
            textViewItemNome.setText(String.valueOf(susInf.getNomeSusInf()));
            textViewItemDataNascimento.setText(String.valueOf(susInf.getDataNascimento()));
            textViewItemDataInfecao.setText(String.valueOf(susInf.getDataInfecao()));
        }


        @Override
        public void onClick(View v) {
            if (viewHolderSusInfSelecionado != null) {

                viewHolderSusInfSelecionado.desSeleciona();
            }

            viewHolderSusInfSelecionado = this;
            seleciona();

            activity_sus_inf activity = (activity_sus_inf) AdaptadorSusInf.this.context;
            activity.susInfAlterado(susInf);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorPrimary);
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }
    }
}
