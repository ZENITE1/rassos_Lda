package ao.znt.rassos_la.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ao.znt.rassos_la.R;
import ao.znt.rassos_la.models.Comerciante;

public class ComerciantesViewAdapter extends RecyclerView.Adapter<ComerciantesViewAdapter.ViewHolder> {

    private List<Comerciante> comerciantes;

    public ComerciantesViewAdapter(List<Comerciante> comerciantes){
         this.comerciantes = comerciantes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shape_item_comerciante,viewGroup,false);
        return new ComerciantesViewAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ComerciantesViewAdapter.ViewHolder viewHolder, int i) {

            Comerciante comerciante = comerciantes.get(i);

            viewHolder.txAvaliacaoNAO.setText(""+comerciante.getAvaliacaoNegativa());
            viewHolder.txAvaliacaoSIM.setText(""+comerciante.getAvaliacaoPositiva());
            viewHolder.txEndereco.setText(comerciante.getEndereco());
            viewHolder.txNome.setText(comerciante.getName());
            viewHolder.txProfisao.setText(comerciante.getProfissao());
            viewHolder.imageView1.setImageResource(comerciante.getImagens()[0]);
            viewHolder.imageView2.setImageResource(comerciante.getImagens()[1]);
            viewHolder.imageView3.setImageResource(comerciante.getImagens()[2]);
            viewHolder.imageView4.setImageResource(comerciante.getImagens()[3]);
    }

    @Override
    public int getItemCount() {
        return comerciantes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txNome,txProfisao,txEndereco,txAvaliacaoSIM,txAvaliacaoNAO;
        ImageView imageView1, imageView2, imageView3, imageView4;
        public ViewHolder(View view){
            super(view);
            imageView1 = view.findViewById(R.id.imageView15);
            imageView2 = view.findViewById(R.id.imageView18);
            imageView3 = view.findViewById(R.id.imageView19);
            imageView4 = view.findViewById(R.id.imageView20);
            txNome = view.findViewById(R.id.textView3);
            txProfisao = view.findViewById(R.id.textView23);
            txEndereco = view.findViewById(R.id.textView4);
            txAvaliacaoSIM = view.findViewById(R.id.textviewNumeroRecomendo);
            txAvaliacaoNAO = view.findViewById(R.id.textviewNumeroNaoRecomendo);

        }

    }
    public void add(List<Comerciante> items) {
        comerciantes.addAll(items);
        notifyDataSetChanged();
    }
    public void filterList(ArrayList<Comerciante> comerciantesFiltradas){
        comerciantes = comerciantesFiltradas;
        notifyDataSetChanged();
    }
}
