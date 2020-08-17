using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Shopping_Agent
{
    public class Produto
    {
        private String _Nome;
        private String _link;
        private String _estado;
        private int _Product_id;
        private decimal _Peso;
        private String _price;
        private int _quantity;
        private String _imagesource;
        private String _dataEnvio;
        private bool _isChecked;
        public decimal Peso
        {
            get { return _Peso; }
            set { _Peso = value; }
        }
        public String link
        {
            get { return _link; }
            set { _link = value; }
        }

        public String Nome
        {
            get { return _Nome; }
            set { _Nome = value; }

        }
        public int Product_id
        {
            get { return _Product_id; }
            set {_Product_id = value; }

        }

        public String Price {
            get { return _price; }
            set { _price = value;  }
        }

        public int Quantity {
            get { return _quantity; }
            set { _quantity = value; }
        }

        public String ImageSource
        {
            get { return _imagesource; }
            set { _imagesource = value; }
        }

        public string DataEnvio { get => _dataEnvio; set => _dataEnvio = value; }
        public bool IsChecked { get => _isChecked; set => _isChecked = value; }
        public string Estado { get => _estado; set => _estado = value; }
    }
}
