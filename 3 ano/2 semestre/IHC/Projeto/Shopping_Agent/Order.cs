using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Shopping_Agent
{
    
    public class Order
    {
        private int _id;
        private string _clienteOrder;
        private List<Produto> _products;
        private ListProductsCart cart;
        private String _dataCompra;
        private String _preco;
        private String _pagamento;
        private String _armazemID;
        private String _armazemNome;
        private String _trackingNumber;
        private String _transportadora;

        public int Id { get => _id; set => _id = value; }

        public List<Produto> Products { get => _products; set => _products = value; }
        public string DataCompra { get => _dataCompra; set => _dataCompra = value; }
        public string Preco { get => _preco; set => _preco = value; }
        public string Pagamento { get => _pagamento; set => _pagamento = value; }
        public string ArmazemID { get => _armazemID; set => _armazemID = value; }
        public string ClienteOrder { get => _clienteOrder; set => _clienteOrder = value; }
        public string TrackingNumber { get => _trackingNumber; set => _trackingNumber = value; }
        public ListProductsCart Cart { get => cart; set => cart = value; }
        public string ArmazemNome { get => _armazemNome; set => _armazemNome = value; }
        public string Transportadora { get => _transportadora; set => _transportadora = value; }
    }
}
