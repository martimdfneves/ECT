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
namespace Shopping_Agent
{
    /// <summary>
    /// Interaction logic for Login.xaml
    /// </summary>
    public partial class Login : Page
    {
        private SqlConnection cn;
        private DBConnection db;


        public Login()
        {
            InitializeComponent();
            db = new DBConnection();
        }

       private void getVerification()
        {
            String pass =  txtPassword.Password;
            String email = txtUsername.Text;

            verification(pass, email);

        }

        private void verification(String pass,String email)
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand();
            
           
            
            cmd.CommandText = "EXEC Agente_De_Compras.Verification @Email";
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@Email", email);
            cmd.Connection = cn;

            SqlDataReader leitor = cmd.ExecuteReader();
            String obter = "";
            Boolean sucessfull = false;
            while (leitor.Read())
            {
               obter = leitor.GetString(0);
            }
            
            if(pass == obter)
            {
                MessageBox.Show("palavra pass correta");
                sucessfull = true;

                

            }
            else
            {
                MessageBox.Show("palavra pass ou username errados");
                
            }
            leitor.Close();
            cn.Close();

            if (sucessfull)
            {
               
                Application.Current.Properties["Current_Client"] = email;
                NavigationService navService = NavigationService.GetNavigationService(this);
                Client_Home cliente_home_page = new Client_Home();
                navService.Navigate(cliente_home_page);
            }

            



        }

        private void ClearFields()
        {
            
            txtPassword.Password = "";
            txtUsername.Text = "";

        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            getVerification();
            ClearFields();

        
        
        }

        private void txtUsername_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void btnSkip_Click(object sender, RoutedEventArgs e)
        {
            Application.Current.Properties["Current_Client"] = "Skiped@gmail.com";
            NavigationService navService = NavigationService.GetNavigationService(this);
            Client_Home cliente_home_page = new Client_Home();
            navService.Navigate(cliente_home_page);

        }
    }
}
