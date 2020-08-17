using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Motoshop
{
    class Localization
    {
        private String _local;
        private int _number;

        public String local
        {
            get { return _local; }

   
        }


        public int number
        {
            get { return _number; }

        }

        public override String ToString()
        {
            return "[" + _number + "] " + _local;
        }

  
        public Localization(int number, String local): base()
        {
            this._number = number;
            this._local = local;
        }


    }


}
