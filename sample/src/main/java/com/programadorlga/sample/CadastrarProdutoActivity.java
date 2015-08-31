package com.programadorlga.sample;

import com.programadorlga.materialdroid.annotation.form.OrderFieldForm;
import com.programadorlga.materialdroid.annotation.model.EntityMaterial;
import com.programadorlga.materialdroid.controller.form.FormActivity;

@EntityMaterial(Produto.class)
@OrderFieldForm({"nome", "dataFabricacao", "valor", "quantidade", "excluido"})
public class CadastrarProdutoActivity extends FormActivity {
}
