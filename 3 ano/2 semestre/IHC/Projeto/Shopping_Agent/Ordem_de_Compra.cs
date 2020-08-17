using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Shopping_Agent
{
    class Ordem_de_Compra
    {
        private ListProductsCart _shopping_cart;
        private double _preço_total;
        private String _data_realizaçao;
        private String _metodo_pagamento;
        private int _order_id;
        private int _armazem;
        public int Armazem
        {
            get { return _armazem; }
            set { _armazem = value; }

        }   
        public int Order_id
        {
            get { return _order_id; }
            set { _order_id = value; }
        }
        public double Preço_total
        {
            get { return _preço_total; }
            set { _preço_total = value; }

        }
        public String Data_Realizaçao
        {
            get { return _data_realizaçao; }
            set { _data_realizaçao = value; }
        }

        public String Metodo_Pagamento
        {
            get { return _metodo_pagamento; }
            set { _metodo_pagamento = value; }
        }
        public ListProductsCart Shopping_Cart
        {
            get { return _shopping_cart; }
            set { _shopping_cart = value; }

        }
       

        


    }







}
