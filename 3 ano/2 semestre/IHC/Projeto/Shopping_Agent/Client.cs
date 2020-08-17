using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Shopping_Agent
{
    class Client
    {
        private String nome;
        private String apelido;
        private String n_telem;
        private Morada address;
        private String email;
        private String password;

        public String Password
        {
            get { return password; }
            set
            {
                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("Password field can't be empty");
                    return;
                }
                password = value;
            }



        } 

        public string Nome
        {
            get { return nome; }
            set
            {
                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("First Name field can’t be empty");
                    return;
                }
                nome = value; }
        }

        public string Apelido
        {
            get { return apelido; }
            set
            {
                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("Last Name field can’t be empty");
                    return;
                }
                apelido = value;
            }
        }

        public string N_Telem
        {
            get { return n_telem; }
            set
            {
                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("Phone Number field can’t be empty");
                    return;
                }
                n_telem = value;
            }
        }

        public String Email
        {
            get { return email; }
            set
            {
                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("Email field can’t be empty");
                    return;
                }
                email = value;
            }
        }

        public Morada Address{
            get{ return address; }
        }

        public Client(Morada address) : base()
        {
            this.address = address;

        }

    }
}
