using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Shopping_Agent
{
    class Morada
    {
        private String endereco;
        private String cidade;
        private String pais;
        private String zip_code;

        public string Endereco
        {
            get { return endereco; }
            set
            {

                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("Adrress field can’t be empty");
                    return;
                }
                endereco = value;
            }
        }

        public string Cidade
        {
            get { return cidade; }
            set
            {

                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("City field can’t be empty");
                    return;
                }
                cidade = value;
            }
        }

        public string Pais
        {
            get { return pais; }
            set
            {

                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("Country field can’t be empty");
                    return;
                }
                pais = value;
            }
        }

        public string Zip_Code
        {
            get { return zip_code; }
            set
            {

                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("Zip-Code field can’t be empty");
                    return;
                }
                zip_code = value;
            }
        }

        public Morada() : base()
        {
        }

    }
}
