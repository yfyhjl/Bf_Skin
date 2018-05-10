package skin.smart.storm.com.skindemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by din on 2017/11/8.
 * <p>
 * Email: godcok@163.com
 */
public class SkinFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_layout, null, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager
                        .VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item,
                            parent, false));
                } else {
                    return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout
                                    .item_skin,
                            parent, false));
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemViewType(int position) {
                return position % 2;
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });
        return view;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
