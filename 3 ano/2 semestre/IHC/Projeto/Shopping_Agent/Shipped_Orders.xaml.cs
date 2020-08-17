using System;
using System.Collections.Generic;
using System.Data.SqlClient;
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

namespace Shopping_Agent
{
    /// <summary>
    /// Interaction logic for Shipped_Orders.xaml
    /// </summary>
    public partial class Shipped_Orders : Page
    {
        private SqlConnection cn;
        private DBConnection db;
        private string userEmail;
        private List<Order> list;
        private List<Produto> listaProd;
        private int selectedIndex;

        public Shipped_Orders()
        {
            InitializeComponent();
            db = new DBConnection();
            list = new List<Order>();
            userEmail = Application.Current.Properties["Current_Client"].ToString();
            loadClientOrders();

            listShip.ItemsSource = list;
        }
        private void loadClientOrders()
        {
            cn = db.getSGBDConnection();
            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("SELECT * FROM Agente_de_Compras.getShipOrders (@Email)", cn);
            cmd.Parameters.AddWithValue("@Email", userEmail);
            SqlDataReader reader = cmd.ExecuteReader();
            int i = 1;
            while (reader.Read())
            {
                Order o = new Order();
                o.Id = Convert.ToInt32(reader["ID"]);
                o.Preco = String.Format("{0:0.00}", reader["preco_final"]);
                string[] tmp = ((reader["data_envio"].ToString()).Split(' '));
                o.DataCompra = tmp[0];
                o.Transportadora = reader["transportadora"].ToString();
                o.ClienteOrder = "order " + i.ToString();
                o.TrackingNumber = reader["tracking_num"].ToString();
                i++;
                list.Add(o);
            }
            reader.Close();
        }

        private List<Produto> getProdInfo(int orderID)
        {
            if (!db.verifySGBDConnection())
                return null;

            SqlCommand cmd = new SqlCommand("SELECT * FROM Agente_de_Compras.getShipProd(@OrderID)", cn);
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

        private void listShip_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            selectedIndex = listShip.SelectedIndex;
        }
    }
}
