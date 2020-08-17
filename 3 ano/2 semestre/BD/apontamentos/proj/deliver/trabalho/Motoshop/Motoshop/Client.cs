using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motoshop
{
    class Client
    {
        private String _NIF;
        private String _Name;
        private String _Address;

        public String NIF
        {
            get { return _NIF; }

            set
            {
                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("NIF field can’t be empty");
                }
             _NIF = value;
            }
        }


        public String Name
        {
            get { return _Name; }
            set
            {
                if (value == null | String.IsNullOrEmpty(value))
                {
                    throw new Exception("Name field can’t be empty");
                }
                _Name = value;
            }
        }

        public String Address
        {
            get { return _Address; }
            set { _Address = value; }
        }

        public override String ToString()
        {
            return _NIF + "     " + _Name + "     " + _Address;
        }

  
        public Client(String NIF, String Name, String Address) : base()
        {
            this.Name = Name;
            this.NIF = NIF;
            this.Address = Address;
        }


    }


}
