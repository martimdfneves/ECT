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
using System.Data.SqlClient;
using System.Data;
namespace Shopping_Agent
{
    /// <summary>
    /// Interaction logic for Shipping_Sending.xaml
    /// </summary>
    public partial class Shipping_Sending : Page
    {
        private SqlConnection cn;
        private DBConnection db;
        private String cliente_email;
        private int cliente_ID;
        List<Produto> lista_produtos_enviar;
        private decimal total_peso;
        public Shipping_Sending(List<Produto> listProd2Send,String cl_email,int cl_ID)
        {
            InitializeComponent();
            db = new DBConnection();
            cliente_email = cl_email;
            cliente_ID = cl_ID;
            lista_produtos_enviar = listProd2Send;
            listShip.ItemsSource = listProd2Send;
            decimal total_peso = 0;
            
            int ciclo = lista_produtos_enviar.Count;
            for (int i = 0; i < ciclo; i++)
            {
                Produto tmp = lista_produtos_enviar.ElementAt(i);

                total_peso = total_peso + (tmp.Quantity * tmp.Peso);


            }
            pesoDisplay.Text = total_peso.ToString();





        }
        private decimal transp_preço(String name,decimal peso)
        {
            decimal preço_final = 0m;

            if(name.Equals("DHL"))
            {
                preço_final = 3.45m * peso;
            }else if (name.Equals("EUB"))
            {
                preço_final = 5.45m * peso;
            }else if (name.Equals("EMS"))
            {
                preço_final = 2.15m * peso;


            }
            return preço_final;

        }
        private void order_submit()
        {
            decimal preço_total;
            decimal peso_total = 0;
            String transportadora = comboTransp.Text.ToString();

            String date_tmp = DateTime.Now.ToString("yyyy-MM-dd");
            int ciclo = lista_produtos_enviar.Count;
            String tracking = trackingGenerator();
            for(int i = 0; i < ciclo; i++)
            {
                Produto tmp = lista_produtos_enviar.ElementAt(i);

                peso_total = peso_total + (tmp.Quantity * tmp.Peso);
               

            }
            
            preço_total = transp_preço(transportadora,peso_total);
            total_price_box.Text = preço_total.ToString();
            
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return ; 

            SqlCommand cmd = new SqlCommand("Agente_de_Compras.New_Ordem_Envio", cn);
            
            cmd.CommandType = CommandType.StoredProcedure;
            SqlParameter param = new SqlParameter("@Client_ID", SqlDbType.Int);
            SqlParameter param1 = new SqlParameter("@Peso_Total", SqlDbType.Decimal);
            SqlParameter param2 = new SqlParameter("@Transportadora", SqlDbType.VarChar);
            SqlParameter param3 = new SqlParameter("@Data_r", SqlDbType.Date);
            SqlParameter param4 = new SqlParameter("@Preço_Total", SqlDbType.Money);
            SqlParameter param5 = new SqlParameter("@Tracking_number", SqlDbType.VarChar);
            param.Direction = ParameterDirection.Input;
            param1.Direction = ParameterDirection.Input;
            param2.Direction = ParameterDirection.Input;
            param3.Direction = ParameterDirection.Input;
            param4.Direction = ParameterDirection.Input;
            param5.Direction = ParameterDirection.Input;
            
            param.Value = cliente_ID;
            param1.Value = peso_total;
            param2.Value = transportadora;
            param3.Value = date_tmp;
            param4.Value = preço_total;
            param5.Value = tracking;

            cmd.Parameters.Add(param);
            cmd.Parameters.Add(param1);
            cmd.Parameters.Add(param2);
            cmd.Parameters.Add(param3);
            cmd.Parameters.Add(param4);
            cmd.Parameters.Add(param5);


            int order_id;
            SqlParameter retval = cmd.Parameters.Add("@Ordem_ID", SqlDbType.Int);
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

            order_id = Convert.ToInt32(cmd.Parameters["@Ordem_ID"].Value);
            //MessageBox.Show(order_id.ToString());

            SqlCommand cmd2 = new SqlCommand();
            for (int i = 0; i < ciclo; i++)
            {
                Produto tmp = lista_produtos_enviar.ElementAt(i);
                
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                
                cmd2.CommandText = "EXEC Agente_de_Compras.Update_Contem_Envio @Id_Produto , @N_unidades , @Order_envio_id";
                cmd2.Parameters.Clear();
                cmd2.Parameters.AddWithValue("@Id_Produto", tmp.Product_id);
                cmd2.Parameters.AddWithValue("@N_unidades", tmp.Quantity);
                cmd2.Parameters.AddWithValue("@Order_envio_id", order_id);
                cmd2.Connection = cn; 
                
                try
                {
                    cmd2.ExecuteNonQuery();


                }
                catch (Exception ex)
                {
                    throw new Exception(" \n ERROR MESSAGE: \n" + ex.Message);
                }
                finally
                {
                    cn.Close();
                }

               


            }

        }


        
        private String trackingGenerator()
        {
            var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            var stringChars = new char[8];
            var random = new Random();

            for (int i = 0; i < stringChars.Length; i++)
            {
                stringChars[i] = chars[random.Next(chars.Length)];
            }

            var finalString = new String(stringChars);
            return finalString;





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

        private void Confirm_Shipping_Click(object sender, RoutedEventArgs e)
        {
            order_submit();
            
            MessageBox.Show("Shipping Order Submitted sucessfuly ");
        }

        private void comboTransp_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            decimal preço_total;
            decimal peso_total = 0;
            String transportadora = comboTransp.Text.ToString();

           
            int ciclo = lista_produtos_enviar.Count;
            
            for (int i = 0; i < ciclo; i++)
            {
                Produto tmp = lista_produtos_enviar.ElementAt(i);

                peso_total = peso_total + (tmp.Quantity * tmp.Peso);


            }

            preço_total = transp_preço(transportadora, peso_total);
            total_price_box.Text = preço_total.ToString();
        }
    }

}
    
    
 




