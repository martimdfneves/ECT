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
    /// Interaction logic for SetOrderInformation.xaml
    /// </summary>
    public partial class SetOrderInformation : Page
    {
        private List<Warehouse> warehouses;
        private SqlConnection cn;
        private DBConnection db;
        private Order ordemCompra;
        private String client_email;
        public SetOrderInformation(Order ordem)
        {
            InitializeComponent();
            db = new DBConnection();
            warehouses = new List<Warehouse>();
            listWarehouse.ItemsSource = warehouses;
            ordemCompra = ordem;
            client_email = Application.Current.Properties["Current_Client"].ToString();
            loadWarehouses();
        }

        private void loadWarehouses() 
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("SELECT * FROM Agente_de_Compras.getWarehouses()", cn);
            SqlDataReader reader = cmd.ExecuteReader();

            while(reader.Read())
            {
                Warehouse w = new Warehouse();
                w.Nome = reader["nome"].ToString();
                w.Id = (reader["ID"]).ToString();
                warehouses.Add(w);

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
        private void Ordem_Compra_Submition(Order ordem)
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand();

            cmd.CommandText = "EXEC Agente_de_Compras.New_Ordem_Compra @Email, @Data_realizaçao, @Metodo_pagamento, @Preço_total, @N_armazem, @TrackingNumber";
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@Email", client_email);
            cmd.Parameters.AddWithValue("@Data_realizaçao", ordem.DataCompra);
            cmd.Parameters.AddWithValue("@Metodo_pagamento", ordem.Pagamento);
            cmd.Parameters.AddWithValue("@Preço_total", ordem.Preco);
            cmd.Parameters.AddWithValue("@N_Armazem", ordem.ArmazemID);
            cmd.Parameters.AddWithValue("@TrackingNumber", ordem.TrackingNumber);
            cmd.Connection = cn;


            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {

                throw new Exception("Failed to Submit ordemd e compra. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                cn.Close();
            }

            IEnumerator<Produto> iterador = ordem.Cart.GetEnumerator();
            Produto tmp_product;
            
            SqlCommand cmd2 = new SqlCommand();
            while (iterador.MoveNext())
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;
                tmp_product = iterador.Current;
                //MessageBox.Show(tmp_product.Nome.ToString());

                cmd2.CommandText = "EXEC Agente_de_Compras.Insert_ProductAndContem @Url, @Nome, @Peso, @Categoria, @N_unidades,@Email, @Preço, @Preço_total";
                cmd2.Parameters.Clear();
                cmd2.Parameters.AddWithValue("@Url", tmp_product.link);
                cmd2.Parameters.AddWithValue("@Nome", tmp_product.Nome);
                cmd2.Parameters.AddWithValue("@Peso", tmp_product.Peso);
                cmd2.Parameters.AddWithValue("@Categoria", "No implementado");
                cmd2.Parameters.AddWithValue("@N_unidades", tmp_product.Quantity);
                cmd2.Parameters.AddWithValue("@Email", client_email);
                cmd2.Parameters.AddWithValue("@Preço", tmp_product.Price);
                cmd2.Parameters.AddWithValue("@Preço_total", ordem.Preco);
                cmd2.Connection = cn;


                try
                {
                    cmd2.ExecuteNonQuery();
                }
                catch (Exception ex)
                {
                    //MessageBox.Show("Failed to insert Product. \n ERROR MESSAGE: \n" + ex.Message);
                    throw new Exception("Failed to Submite produtos. \n ERROR MESSAGE: \n" + ex.Message);
                }
                finally
                {
                    cn.Close();
                }
            }
        }
        private void btnBuy_Click(object sender, RoutedEventArgs e)
        {
            List<Warehouse> checkedWarehouse = new List<Warehouse>();
            foreach (Warehouse w in warehouses)
            {
                if (w.IsChecked)
                {
                    ordemCompra.ArmazemID = w.Id;
                }

            }
            
            ordemCompra.Pagamento = Combox_Pagamento.Text;
            Ordem_Compra_Submition(ordemCompra);

            MessageBox.Show("Buy Successfully, check your Orders");
        }
    }
}
