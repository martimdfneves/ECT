using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Data; 


namespace Shopping_Agent
{
    class DBConnection
    {
        private SqlConnection cn;

        public DBConnection()
        {
            cn = new SqlConnection("data source = tcp:mednat.ieeta.pt\\SQLSERVER,8101; initial catalog=p7g5; User ID = p7g5; Password=apontamentos@2020");
        }

        public SqlConnection getSGBDConnection()
        {
            return cn;
        }
        
        public bool verifySGBDConnection()
        {
            if (cn == null)
                cn = getSGBDConnection();

            if (cn.State != ConnectionState.Open)
                try
                {
                    cn.Open();
                }
                catch(Exception ex)
                {
                   
                    throw new Exception("Could not establish connection with the DataBase");
                }

            return cn.State == ConnectionState.Open;
        }

    }
}
