package com.programadorlga.sample;

import com.programadorlga.materialdroid.annotation.model.EntityMaterial;
import com.programadorlga.materialdroid.annotation.recyclerview.AddButtonRecyclerView;
import com.programadorlga.materialdroid.annotation.recyclerview.RecyclerViewList;
import com.programadorlga.materialdroid.controller.recyclerview.RecyclerViewMaterialActivity;

@EntityMaterial(Produto.class)
@RecyclerViewList({"nome","valor","quantidade"})
@AddButtonRecyclerView(CadastrarProdutoActivity.class)
public class ExibirProdutoActivity extends RecyclerViewMaterialActivity {
}
