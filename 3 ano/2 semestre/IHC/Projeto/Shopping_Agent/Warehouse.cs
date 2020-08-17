using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Shopping_Agent
{
    class Warehouse
    {
        private String _nome;
        private String _localizacao;
        private string _id;
        private bool _isChecked;
        public string Nome { get => _nome; set => _nome = value; }
        public string Localizacao { get => _localizacao; set => _localizacao = value; }
        public bool IsChecked { get => _isChecked; set => _isChecked = value; }
       
        public string Id { get => _id; set => _id = value; }
    }
}
