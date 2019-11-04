package br.com.rafael.firebaseapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaUniversidadesActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private ListenerRegistration listenerRegistration;
    //
    private RecyclerView rvLista;
    //
    private List<Universidade> listaUniversidades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_universidades);
        iniciaComponentes();
        iniciaFirestore();
        adicionaListenerAlteracaoFirestore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listenerRegistration.remove();
    }

    private void iniciaComponentes() {
        rvLista = findViewById(R.id.rvLista);
        rvLista.setAdapter(new Adapter());
    }

    private void iniciaFirestore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void adicionaListenerAlteracaoFirestore() {
        listenerRegistration = firebaseFirestore.collection("UNIVERSIDADES").orderBy("descricao").addSnapshotListener(listenerAlteracoesFirebase);

        firebaseFirestore.collection("UNIVERSIDADES").orderBy("descricao").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                task.getResult().getDocuments().get(0).toObject(Universidade.class);
                task.getResult().getDocuments().get(0).getId();
            }
        });
    }

    private EventListener<QuerySnapshot> listenerAlteracoesFirebase = new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
            for (DocumentChange documentoAlterado : queryDocumentSnapshots.getDocumentChanges()) {
                Universidade universidade = documentoAlterado.getDocument().toObject(Universidade.class);
                universidade.setId(documentoAlterado.getDocument().getId());
                switch (documentoAlterado.getType()) {
                    case ADDED:
                        listaUniversidades.add(universidade);
                        rvLista.getAdapter().notifyItemInserted(listaUniversidades.indexOf(universidade));
                        break;
                    case MODIFIED:
                        listaUniversidades.set(listaUniversidades.indexOf(universidade), universidade);
                        rvLista.getAdapter().notifyItemChanged(listaUniversidades.indexOf(universidade));
                        break;
                    case REMOVED:
                        listaUniversidades.remove(universidade);
                        rvLista.getAdapter().notifyItemRemoved(listaUniversidades.indexOf(universidade));
                        break;
                }
            }
        }
    };

    private class Holder extends RecyclerView.ViewHolder {

        private TextView tvDescricao;
        private TextView tvAno;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvAno = itemView.findViewById(R.id.tvAno);
        }
    }

    private class Adapter extends RecyclerView.Adapter<Holder> {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(getLayoutInflater().inflate(R.layout.item_lista_universidades, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            final Universidade universidade = listaUniversidades.get(position);
            holder.tvDescricao.setText(universidade.getDescricao());
            holder.tvAno.setText(universidade.getAnoFundacao().toString());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    firebaseFirestore.collection("UNIVERSIDADES").document(universidade.getId()).delete();
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return listaUniversidades != null ? listaUniversidades.size() : 0;
        }
    }


}
