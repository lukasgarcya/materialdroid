Criação de formulário e lista de um determinado model permitindo a persistencia com Realm.

Primeiramente para que esta lib funcione o modelo precisa ter uma @PrimaryKey do Realm Annotation do tipo long.

A criação de um formulário funciona da seguinte maneira: Cria-se uma Activity que herda de FormActivity. Faz a anotação de EntityMaterial indicando qual o RealmObject será usado. Também acrescenta a anotação OrderFieldForm para indicar quais campos serão exibidos no formulário.

A lista utiliza um RecylcerView então para criar um tem-se de herdar de RecyclerViewMaterialActivity. Faz a anotação de EntityMaterial indicando qual o RealmObject será usado. Também acrescenta a anotação RecyclerViewList definindo quais campos do model serão utilizado.
