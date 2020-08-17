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
    /// Interaction logic for SignUp.xaml
    /// </summary>
    public partial class SignUp : Page
    {
        private SqlConnection cn;
        private DBConnection db;
        
        public SignUp()
        {
            InitializeComponent();
            db = new DBConnection();
            
        }

        private void SaveClient(Morada address)
        {
            Client client = new Client(address);
            try
            {
                client.Nome = txtFirstName.Text;
                client.Apelido = txtLastName.Text;
                client.N_Telem = txtPhoneNumber.Text;
                client.Email = txtEmail.Text;
                client.Password = txtPassword.Text;
            }

            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            // Submit to DataBase
            SubmitClient(client);
        }

        private Morada SaveAddress()
        {
            Morada morada = new Morada();
            try
            {
                morada.Endereco = txtAddress.Text;
                morada.Cidade = txtCity.Text;
                morada.Pais = txtCountry.Text;
                morada.Zip_Code = txtZipCode.Text;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            
            return morada ;
        }

        private void SubmitClient(Client c) {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand();

            cmd.CommandText = "EXEC Agente_De_Compras.New_Client @Nome, @Apelido, @N_Telem, @Email, @Endereco, @Cidade, @Pais, @Zip_Code , @Password";
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@Nome", c.Nome);
            cmd.Parameters.AddWithValue("@Apelido", c.Apelido);
            cmd.Parameters.AddWithValue("@N_Telem", c.N_Telem);
            cmd.Parameters.AddWithValue("@Email", c.Email);
            cmd.Parameters.AddWithValue("@Endereco", c.Address.Endereco);
            cmd.Parameters.AddWithValue("@Cidade", c.Address.Cidade);
            cmd.Parameters.AddWithValue("@Pais", c.Address.Pais);
            cmd.Parameters.AddWithValue("@Zip_Code", c.Address.Zip_Code);
            cmd.Parameters.AddWithValue("@Password", c.Password);
            cmd.Connection = cn;

            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
                string[] message = ex.Message.Split('\n');
                MessageBox.Show("Failed to Sign Up. \n ERROR MESSAGE: \n" + message[0]);
                return;
                //throw new Exception("Failed to Sign Up. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                cn.Close();
            }
            MessageBox.Show("Signed Up Successfull");
        }

        //Limpar campos depois de submeter
        private void ClearFields() 
        {
            txtAddress.Text = "";
            txtCity.Text = "";
            txtCountry.Text = "";
            txtEmail.Text = "";
            txtFirstName.Text = "";
            txtLastName.Text = "";
            txtPhoneNumber.Text = "";
            txtZipCode.Text = "";
            txtPassword.Text = "";
           
        }

        private void btnSubmit_Click(object sender, RoutedEventArgs e)
        {
            Morada morada = SaveAddress();
            SaveClient(morada);
            ClearFields();
            
            
        }

        private void TextBox_TextChanged(object sender, TextChangedEventArgs e)
        {

        }
    }
}
