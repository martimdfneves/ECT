using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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
using System.Data.SqlClient;
using System.Data;

namespace Shopping_Agent
{
    /// <summary>
    /// Interaction logic for Purchase_Orders.xaml
    /// </summary>
    /// 
    public class ListPOrders : ObservableCollection<Order>
    {
        public ListPOrders()
        {


        }

        public void AddOrder(int id, String preco, String dataCompra, String dataEnvio, String armazemID)
        {
            Order o = new Order();
            o.Id = id;
            o.Preco = preco;
            o.DataCompra = dataCompra;
            o.ArmazemID = armazemID;
    
            Add(o);

        }

    }
    public partial class Purchase_Orders : Page
    {
        private SqlConnection cn;
        private DBConnection db;
        private string userEmail;
        private int userID;
        private ListPOrders list;
        private int selectedIndex;
        private List<Produto> listaProd;
        public Purchase_Orders()
        {
            InitializeComponent();
            db = new DBConnection();
            
            list = new ListPOrders();
            userEmail = Application.Current.Properties["Current_Client"].ToString();
            userID = getClientID();
            loadClientOrders();
            
            listPurchase.ItemsSource = list;

        }
        private int getClientID()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return 0;

            int id;
            SqlCommand cmd = new SqlCommand("Agente_de_Compras.getClientID",cn);
            
            cmd.CommandType = CommandType.StoredProcedure;
            SqlParameter param = new SqlParameter("@Email", SqlDbType.VarChar);
            param.Direction = ParameterDirection.Input;
            param.Value = userEmail;
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
            cmd.Parameters.AddWithValue("@ClientID", userID);
            SqlDataReader reader = cmd.ExecuteReader();
            int i = 1;
            while(reader.Read())
            {
                Order o = new Order();
                o.Id = Convert.ToInt32(reader["ID"]);
                o.Preco = String.Format("{0:0.00}", reader["preco_total"]);
                string[] tmp = ((reader["data_r"].ToString()).Split(' '));
                o.DataCompra = tmp[0];
                o.Pagamento = reader["pagamento"].ToString();
                o.ArmazemNome = reader["nome"].ToString();
                o.ClienteOrder = "order " + i.ToString();
                o.TrackingNumber = reader["tracking_num"].ToString();
                i++;
                list.Add(o);
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
        private List<Produto> getProdInfo(int orderID)
        {
            if (!db.verifySGBDConnection())
                return null;

            SqlCommand cmd = new SqlCommand("SELECT * FROM Agente_de_Compras.getProdInfo(@OrderID)", cn);
            cmd.Parameters.AddWithValue("@OrderID", orderID);

            List<Produto> listP = new List<Produto>();
            SqlDataReader reader = cmd.ExecuteReader();
            

            while (reader.Read())
            {
                Produto p = new Produto();
                p.Nome = reader["nome"].ToString();
                p.Price = String.Format("{0:0.00}", reader["preco"]); ;
                p.Quantity = Convert.ToInt32(reader["n_unidades"]);
                p.Peso = Convert.ToDecimal(reader["peso"]);
                p.Estado = reader["estado"].ToString();
                listP.Add(p);
            }
            reader.Close();
            return listP;
        }

        private void btnInfo_Click(object sender, RoutedEventArgs e)
        {
            if (list.Count > 0 && selectedIndex < list.Count)
            {
                Order selected = list.ElementAt(selectedIndex);
                int orderID = selected.Id;
                listaProd = getProdInfo(orderID);

                NavigationService navService = NavigationService.GetNavigationService(this);
                OrderInfo ord_info = new OrderInfo(listaProd);
                navService.Navigate(ord_info);
                
            }
            else { MessageBox.Show("Didn´t selected a valid Order, try again"); }
        }

        private void listPurchase_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            selectedIndex = listPurchase.SelectedIndex;
        }
    }
}
