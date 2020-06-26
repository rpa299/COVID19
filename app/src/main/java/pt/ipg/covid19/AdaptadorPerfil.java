package pt.ipg.covid19;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorPerfil extends RecyclerView.Adapter<AdaptadorPerfil.ViewHolderPerfil>{
    private final Context context;
    private Cursor cursor = null;

    public void setCursor(Cursor cursor) {
        if (cursor != this.cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public AdaptadorPerfil(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderPerfil onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemPerfil = LayoutInflater.from(context).inflate(R.layout.item_perfil, parent, false);

        return new ViewHolderPerfil(itemPerfil);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPerfil holder, int position) {
        cursor.moveToPosition(position);
        Perfil perfil = Converte.cursorToPerfil(cursor);
        holder.setPerfil(perfil);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public Perfil getPerfilSelecionado(){
        if (viewHolderPerfilSelecionado == null) return null;

        return viewHolderPerfilSelecionado.perfil;
    }

    private ViewHolderPerfil viewHolderPerfilSelecionado = null;

    public class ViewHolderPerfil extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Perfil perfil = null;

        private final TextView textViewNome;
        private final TextView textViewDataNascimento;
        private final TextView textViewSexo;
        private final TextView textViewAltura;
        private final TextView textViewPeso;
        private final TextView textViewTipoSangue;

        public ViewHolderPerfil(@NonNull View itemView) {
            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.textViewItemNome);
            textViewDataNascimento = (TextView) itemView.findViewById(R.id.textViewItemDataNascimento);
            textViewSexo = (TextView) itemView.findViewById(R.id.textViewItemSexo);
            textViewAltura = (TextView) itemView.findViewById(R.id.textViewItemAltura);
            textViewPeso = (TextView) itemView.findViewById(R.id.textViewItemPeso);
            textViewTipoSangue = (TextView) itemView.findViewById(R.id.textViewItemTipoSangue);

            itemView.setOnClickListener(this);
        }

        public void setPerfil(Perfil perfil) {
            this.perfil = perfil;

            textViewNome.setText(perfil.getNome());
            textViewDataNascimento.setText(perfil.getDataNascimento());
            textViewSexo.setText(perfil.getSexo());
            textViewAltura.setText(String.valueOf(perfil.getAltura()));
            textViewPeso.setText(String.valueOf(perfil.getPeso()));
            textViewTipoSangue.setText(perfil.getTipoSangue());
        }

        @Override
        public void onClick(View v) {
            if (viewHolderPerfilSelecionado != null) {
                viewHolderPerfilSelecionado.desSeleciona();
            }

            viewHolderPerfilSelecionado = this;

            ((activity_perfil) context).atualizaOpcoesMenu();
            seleciona();
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorPrimary);
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.darker_gray);
        }
    }
}
