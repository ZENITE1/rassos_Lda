package ao.znt.rassos_la;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;
import com.paginate.recycler.LoadingListItemSpanLookup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ao.znt.rassos_la.adapters.ComerciantesViewAdapter;
import ao.znt.rassos_la.data.DataProvider;
import ao.znt.rassos_la.models.Comerciante;
import ao.znt.rassos_la.utils.ZeniteUtil;

public class ComerciantesActivity extends AppCompatActivity {

    private static final int GRID_SPAN = 3;

    private boolean loading = false;
    private int page = 0;
    private int totalPage = 10;
    private Handler handler;
    private Paginate paginate;

    ComerciantesViewAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<Comerciante> comerciantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comerciantes);
        handler = new Handler();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewComerciantes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        comerciantes = DataProvider.getRandomData(3);
        adapter = new ComerciantesViewAdapter(comerciantes);
        recyclerView.setAdapter(adapter);

        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                loading = true;
                // Fake asynchronous loading that will generate page of random data after some delay
                handler.postDelayed(fakeCallback, 1000);
            }
            @Override
            public boolean isLoading() {
                return loading;
            }
            @Override
            public boolean hasLoadedAllItems() {
                return page==totalPage;
            }
        };
        Paginate.with(recyclerView,callbacks)
                .addLoadingListItem(true)
                .setLoadingListItemSpanSizeLookup(new LoadingListItemSpanLookup() {
                    @Override
                    public int getSpanSize() {
                        return 5;
                    }
                })
                .setLoadingListItemCreator(new CustomLoadingListItemCreator())
                .setLoadingTriggerThreshold(2)
                .build();
    }
    private Runnable fakeCallback = new Runnable() {
        @Override
        public void run() {
            page++;
            adapter.add(DataProvider.getRandomData(3));
            loading = false;
        }
    };

    private class CustomLoadingListItemCreator implements LoadingListItemCreator {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.loading_list_itens, parent, false);
            return new VH(view);
        }

        @SuppressLint("DefaultLocale")
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            vh.tvLoading.setText(String.format("%d serviços carregados\n carregar mais...", adapter.getItemCount()));

            // This is how you can make full span if you are using StaggeredGridLayoutManager
            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) vh.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull @NotNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pesquisa,menu);

        MenuItem menuItem = menu.findItem(R.id.pesquisa);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { filtro(query); return true; }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { finish();/*TODO*/}

    static class VH extends RecyclerView.ViewHolder {
        TextView tvLoading;

        public VH(View itemView) {
            super(itemView);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading_text);
        }
    }
    private void filtro(String texto){
        ArrayList<Comerciante> comerciantesFiltrados = new ArrayList<>();
        for (Comerciante comerciante : comerciantes){
            if(comerciante.getProfissao().toLowerCase().contains(texto.toLowerCase())){
                comerciantesFiltrados.add(comerciante);
            }
        }
        if(comerciantesFiltrados.isEmpty()){
            ZeniteUtil.alerta(ComerciantesActivity.this,"Ooops!","Nenhum serviço encontrado");
        }else {
            adapter.filterList(comerciantesFiltrados);
        }
    }
}