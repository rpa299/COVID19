package pt.ipg.covid19;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorSintoma extends RecyclerView.Adapter<AdaptadorSintoma.ViewHolderSintoma>{

    private final Context context;
    private Cursor cursor = null;

    public AdaptadorSintoma(Context context) {
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
    public ViewHolderSintoma onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemSintoma = LayoutInflater.from(context).inflate(R.layout.item_sintoma, parent, false);

        return new ViewHolderSintoma(itemSintoma);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSintoma holder, int position) {
        cursor.moveToPosition(position);
        Sintoma sintoma = Converte.cursorToSintoma(cursor);
        holder.setSintoma(sintoma);
    }

    @Override
    public int getItemCount() {
        if(cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }

    public Sintoma getSintomaSelecionado() {
        if (viewHolderSintomaSelecionado == null) return null;

        return viewHolderSintomaSelecionado.sintoma;
    }

    private AdaptadorSintoma.ViewHolderSintoma viewHolderSintomaSelecionado = null;

    public class ViewHolderSintoma extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Sintoma sintoma = null;

        private final TextView textViewItemNome;
        private final TextView textViewItemData;
        private final TextView textViewItemDoresCabeca;
        private final TextView textViewItemDoresMusculares;
        private final TextView textViewItemCansaco;
        private final TextView textViewItemDoresGarganta;
        private final TextView textViewItemTosse;
        private final TextView textViewItemTemperatura;
        private final TextView textViewItemRespiracao;
        private final TextView textViewItemCorrimentoNasal;

        public ViewHolderSintoma(@NonNull View itemView) {
            super(itemView);
            textViewItemNome = (TextView)itemView.findViewById(R.id.textViewItemNome);
            textViewItemData = (TextView)itemView.findViewById(R.id.textViewItemData);
            textViewItemDoresCabeca = (TextView)itemView.findViewById(R.id.textViewItemDoresCabeca);
            textViewItemDoresMusculares = (TextView)itemView.findViewById(R.id.textViewItemDoresMusculares);
            textViewItemCansaco = (TextView)itemView.findViewById(R.id.textViewItemCansaco);
            textViewItemDoresGarganta = (TextView)itemView.findViewById(R.id.textViewItemDoresGarganta);
            textViewItemTosse = (TextView)itemView.findViewById(R.id.textViewItemTosse);
            textViewItemTemperatura = (TextView)itemView.findViewById(R.id.textViewItemTemperatura);
            textViewItemRespiracao = (TextView)itemView.findViewById(R.id.textViewItemRespiracao);
            textViewItemCorrimentoNasal = (TextView)itemView.findViewById(R.id.textViewItemCorrimentoNasal);
            itemView.setOnClickListener(this);
        }

        public void setSintoma(Sintoma sintoma) {
            this.sintoma = sintoma;

            textViewItemNome.setText(String.valueOf(sintoma.getNomePerfil()));
            textViewItemData.setText(String.valueOf(sintoma.getData()));
            textViewItemDoresCabeca.setText(String.valueOf(sintoma.getDoresCabeca()));
            textViewItemDoresMusculares.setText(String.valueOf(sintoma.getDoresMusculares()));
            textViewItemCansaco.setText(String.valueOf(sintoma.getCansaco()));
            textViewItemDoresGarganta.setText(String.valueOf(sintoma.getDoresGarganta()));
            textViewItemTosse.setText(String.valueOf(sintoma.getTosse()));
            textViewItemTemperatura.setText(String.valueOf(sintoma.getTemperatura()));
            textViewItemRespiracao.setText(String.valueOf(sintoma.getRespiracao()));
            textViewItemCorrimentoNasal.setText(String.valueOf(sintoma.getCorrimentoNasal()));
        }


        @Override
        public void onClick(View v) {
            if (viewHolderSintomaSelecionado != null) {

                viewHolderSintomaSelecionado.desSeleciona();
            }

            viewHolderSintomaSelecionado = this;
            seleciona();

            activity_sintomas activity = (activity_sintomas) AdaptadorSintoma.this.context;
            activity.sintomaAlterado(sintoma);

        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorPrimary);
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }
    }
}
