using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Collections.ObjectModel;
using System.Data.SqlClient;
using System.Data;
namespace Shopping_Agent
{

    /// <summary>
    /// Interaction logic for Storage.xaml
    /// </summary>


  
    public partial class Storage : Page
    {
        private List<Produto> products;
        private String cliente_email;
        private SqlConnection cn;
        private DBConnection db;
        private int cliente_id;
        private List<Int32> client_orders = new List<Int32>();

       
        public Storage()
        {
            InitializeComponent();
            db = new DBConnection();
            products = new List<Produto>();
            cliente_email = Application.Current.Properties["Current_Client"].ToString();
            cliente_id = getClientID();
            loadClientOrders();
            getProducts();
            getProductsName();

            //products.Add(new Produto { Nome = " Camisola Branca", Product_id = 101234, Peso = 0.5m });
            //products.Add(new Produto { Nome = "Smartphone", Product_id = 199231, Peso = 1 });
            //products.Add(new Produto { Nome = "Televisao", Product_id = 987123, Peso = 4 });
            checkedList.ItemsSource = products;
        }
        private void getProductsName()
        {
           
            int ciclos = products.Count;
            for (int i = 0; i < ciclos; i++)
            {
                if (!db.verifySGBDConnection())
                    return;


                SqlCommand cmd = new SqlCommand("SELECT * FROM Agente_de_Compras.getProduct(@Product_ID)", cn);
                //MessageBox.Show(products.ElementAt(i).Product_id.ToString());
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@Product_ID", products.ElementAt(i).Product_id);
                SqlDataReader reader = cmd.ExecuteReader();
               
                while (reader.Read())
                {

                    products.ElementAt(i).Nome = Convert.ToString(reader["nome"]);
                    products.ElementAt(i).Peso = Convert.ToDecimal(reader["peso"]);

                    
                }


                reader.Close();
            }

        }
        private void getProducts()
        {
            
            int ciclos = client_orders.Count;
            for(int i = 0; i < ciclos; i++)
            {
                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("SELECT * FROM Agente_de_Compras.getContem (@Order_ID)", cn);
                cmd.Parameters.AddWithValue("@Order_ID", client_orders.ElementAt(i));
                SqlDataReader reader = cmd.ExecuteReader();
               
                while (reader.Read())
                {
                    Produto tmp = new Produto();
                    tmp.Quantity = Convert.ToInt32(reader["n_unidades"]);
                    tmp.Product_id = Convert.ToInt32(reader["produto_ID"]);
                    //MessageBox.Show(tmp.Product_id.ToString());
                    
                    products.Add(tmp);

                   
                }
                reader.Close();

                
            }
        }
        private int getClientID()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return 0;

            int id;
            SqlCommand cmd = new SqlCommand("Agente_de_Compras.getClientID", cn);

            cmd.CommandType = CommandType.StoredProcedure;
            SqlParameter param = new SqlParameter("@Email", SqlDbType.VarChar);
            param.Direction = ParameterDirection.Input;
            param.Value = cliente_email;
            cmd.Parameters.Add(param);
            SqlParameter retval = cmd.Parameters.Add("@ClientID", SqlDbType.Int);
            retval.Direction = ParameterDirection.Output;


            try
            {
                cmd.ExecuteNonQuery();


            }
            catch (Exception ex)
            {
                throw new Exception(" \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                cn.Close();
            }
            id = Convert.ToInt32(cmd.Parameters["@ClientID"].Value);

            return id;

        }
        private void loadClientOrders()
        {


            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("SELECT * FROM Agente_de_Compras.getOrdersByClient (@ClientID)", cn);
            cmd.Parameters.AddWithValue("@ClientID", cliente_id);
            SqlDataReader reader = cmd.ExecuteReader();
            Int32 tmp = 0;
            while (reader.Read())
            {
                
                tmp = Convert.ToInt32(reader["ID"]);
               
               client_orders.Add(tmp);
            }
           
            reader.Close();
        }
        private void btnOrders_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Orders ord_page = new Orders();
            navService.Navigate(ord_page);
        }

        private void btnHome_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Client_Home cl_page = new Client_Home();
            navService.Navigate(cl_page);
        }

        private void btnStorage_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Storage st_page = new Storage();
            navService.Navigate(st_page);
        }

        private void btnPurchase_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Purchase pur_page = new Purchase();
            navService.Navigate(pur_page);
        }

        private void btnAccount_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Account acc_page = new Account();
            navService.Navigate(acc_page);
        }

       
        private void btnPurchasedOrd_Click(object sender, RoutedEventArgs e)
        {

            List<Produto> checkedProducts = new List<Produto>();
            foreach (Produto p in products) {
                if (p.IsChecked) {
                    checkedProducts.Add(p);
                }

            }

            NavigationService navService = NavigationService.GetNavigationService(this);
            Shipping_Sending ship_page = new Shipping_Sending(checkedProducts,cliente_email,cliente_id);
            navService.Navigate(ship_page);
        }

        private void Check_checked(object sender, RoutedEventArgs e)
        {

            
            //MessageBox.Show("Ok");


        }

    
    }
}
